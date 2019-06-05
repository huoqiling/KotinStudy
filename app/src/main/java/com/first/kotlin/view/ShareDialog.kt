package com.first.kotlin.view

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.first.kotlin.R
import com.first.kotlin.base.BaseDialogFragment
import com.first.kotlin.bean.ShareBean

class ShareDialog : BaseDialogFragment() {

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    override fun getLayoutResId(): Int {
        return R.layout.dialog_share
    }

    override fun onStart() {
        super.onStart()
        viewGravityButtom()
    }

    override fun initView() {
        init()
    }

    private fun init() {
        recyclerView.layoutManager = GridLayoutManager(activity, 5)
        var adapter = ShareAdapter()
        recyclerView.adapter = adapter
        adapter.setNewData(getData())
        adapter.setOnItemClickListener { adapter, view, position ->
            when(position){
                0->{
                    showToast("朋友圈")
                    dismiss()
                }
            }
        }
    }

    private fun getData(): ArrayList<ShareBean> {
        var dataList = ArrayList<ShareBean>()
        var weiXinCircle = ShareBean(R.mipmap.iv_share_weixin_circle, "朋友圈")
        var weiXin = ShareBean(R.mipmap.iv_share_weixin, "微信")
        var qq = ShareBean(R.mipmap.iv_share_qq, "QQ")
        var qqZone = ShareBean(R.mipmap.iv_share_qq_zone, "QQ空间")
        var weiBo = ShareBean(R.mipmap.iv_share_weibo, "微博")
        var reward = ShareBean(R.mipmap.iv_reward_chat, "打赏聊天")
        var complaint = ShareBean(R.mipmap.iv_complaint, "投诉")
        var collection = ShareBean(R.mipmap.iv_collection, "取消收藏")
        var save = ShareBean(R.mipmap.iv_save, "保存")
        dataList.add(weiXinCircle)
        dataList.add(weiXin)
        dataList.add(qq)
        dataList.add(qqZone)
        dataList.add(weiBo)
        dataList.add(reward)
        dataList.add(complaint)
        dataList.add(collection)
        dataList.add(save)
        return dataList
    }

    class ShareAdapter : BaseQuickAdapter<ShareBean, BaseViewHolder>(R.layout.item_share) {
        override fun convert(helper: BaseViewHolder?, item: ShareBean?) {
            helper!!.setImageResource(R.id.ivIcon, item!!.getImgRes())
            helper.setText(R.id.tvName, item!!.getImgName())
            if (helper.layoutPosition < 5) {
                helper.setVisible(R.id.viewLine, true)
            } else {
                helper.setVisible(R.id.viewLine, false)
            }
        }

    }

    @OnClick(R.id.btnCancel)
    fun OnClick(view: View) {
        when (view.id) {
            R.id.btnCancel -> {
                dismiss()
            }
        }

    }
}