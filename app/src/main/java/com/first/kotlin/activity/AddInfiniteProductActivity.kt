package com.first.kotlin.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.view.View
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout
import com.first.kotlin.R
import com.first.kotlin.base.BaseActivity
import com.first.kotlin.util.Constant
import com.first.kotlin.view.CommonDialog
import com.first.kotlin.view.CustomTitleBar
import kotlinx.android.synthetic.main.activity_add_infinite_product.*
import permissions.dispatcher.*
import java.io.File
import java.util.*

/**
 * @author zhangxin
 * @date 2018/9/21
 * @description 新增无限竞派商品
 */
@RuntimePermissions
class AddInfiniteProductActivity : BaseActivity(),CustomTitleBar.TitleBarListener{

    companion object {
        var ADD_PHOTO_ITEM = 1 //添加图片
        var LOOK_PHOTO_ITEM = 2 //查看图片
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_add_infinite_product
    }

    override fun initView() {
        initPhotoLayout()
        titleBar.setTitleBarListener(this)
    }

    private fun initPhotoLayout() {
        photoLayout.setDelegate(object : BGASortableNinePhotoLayout.Delegate {

            override fun onClickNinePhotoItem(sortableNinePhotoLayout: BGASortableNinePhotoLayout?, view: View?, position: Int, model: String?, models: ArrayList<String>?) {
                startActivityForResult(BGAPhotoPickerPreviewActivity.IntentBuilder(this@AddInfiniteProductActivity)
                        .maxChooseCount(9)
                        .currentPosition(position)
                        .selectedPhotos(models)
                        .previewPhotos(models)
                        .isFromTakePhoto(false)
                        .build(), LOOK_PHOTO_ITEM)
            }

            override fun onClickAddNinePhotoItem(sortableNinePhotoLayout: BGASortableNinePhotoLayout?, view: View?, position: Int, models: ArrayList<String>?) {
                AddInfiniteProductActivityPermissionsDispatcher.getStorageWithCheck(this@AddInfiniteProductActivity)
            }

            override fun onNinePhotoItemExchanged(sortableNinePhotoLayout: BGASortableNinePhotoLayout?, fromPosition: Int, toPosition: Int, models: ArrayList<String>?) {
            }

            override fun onClickDeleteNinePhotoItem(sortableNinePhotoLayout: BGASortableNinePhotoLayout?, view: View?, position: Int, model: String?, models: ArrayList<String>?) {
                photoLayout.removeItem(position)
            }


        })
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun getStorage() {
        val file = File(Constant.SD_PATH)
        startActivityForResult(BGAPhotoPickerActivity.IntentBuilder(this)
                .cameraFileDir(file)
                .maxChooseCount(9 - photoLayout.itemCount)
                .pauseOnScroll(false)
                .selectedPhotos(null)
                .build(), ADD_PHOTO_ITEM)
    }

    /**
     * 被用户拒绝
     */
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun getStorageDenied() {
        showToast("权限未授予，功能无法使用")
    }

    /**
     * 被拒绝并勾选不在提醒授权时，应用需提示用户未获取权限，需用户自己去设置中打开
     */
    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun getStorageNeverAskAgain() {
        showToast("存储权限被拒绝且不再提示")
    }

    /**
     * 告知用户具体需要权限的原因
     * @param messageResId
     * @param request
     */
    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
    fun showRationaleDialog(request: PermissionRequest) {
        val commonDialog = CommonDialog()
        commonDialog.setMessage("使用此功能需要存储的权限")
                .setConfirmText("确认")
                .setCancelText("取消")
                .setCommonDialogListener(object : CommonDialog.CommonDialogListener {
                    override fun onConfirm() {
                        request.proceed()//请求权限
                    }

                    override fun onCancel() {
                        request.cancel()
                    }

                }).show(supportFragmentManager, "dialog")
    }

    /**
     * 权限请求回调，提示用户之后，用户点击“允许”或者“拒绝”之后调用此方法
     *
     * @param requestCode  定义的权限编码
     * @param permissions  权限名称
     * @param grantResults 允许/拒绝
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        AddInfiniteProductActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOOK_PHOTO_ITEM && resultCode == Activity.RESULT_OK){
            photoLayout.data = BGAPhotoPickerPreviewActivity.getSelectedPhotos(data!!)
        }

        if(requestCode == ADD_PHOTO_ITEM && resultCode == Activity.RESULT_OK){
            photoLayout.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data!!))
        }
    }

    override fun onLeftClick() {
        finish()
    }

    override fun onRightClick() {

    }

}
