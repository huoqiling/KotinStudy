package com.first.kotlin.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.first.kotlin.MyApp
import com.first.kotlin.R
import com.first.kotlin.adapter.InfiniteProductListAdapter
import com.first.kotlin.base.BaseLazyFragment
import com.first.kotlin.base.BaseRefreshLayout
import com.first.kotlin.bean.MerchantQueryProductList
import com.first.kotlin.net.Requester
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import retrofit2.Response

/**
 * @author zhangxin
 * @date 2018/9/13
 * @description
 */
class InfiniteGoodFragment : BaseLazyFragment() {

    private var productAdapter: InfiniteProductListAdapter? = null
    private var pageIndex: Int = 1
    private val pageSize = 10
    private var queryType = 1
    private var refreshType = 1 //1下拉刷新 2：上拉加载

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.refreshLayout)
    lateinit var refreshLayout: BaseRefreshLayout

    @BindView(R.id.tvEmpty)
    lateinit var tvEmpty: TextView

    override fun getLayoutResId(): Int {
        return R.layout.fragment_infinite_goods
    }

    override fun initView() {
        queryType = arguments!!.get("queryType") as Int
        initAdapter()
    }

    override fun lazyData() {
        super.lazyData()
        showLoadingDialog()
        getData()
    }

    private fun initAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(MyApp.instance.applicationContext)
        productAdapter = InfiniteProductListAdapter()
        recyclerView.adapter = productAdapter
        productAdapter!!.setOnItemClickListener { adapter, view, position ->

        }
        refreshLayout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                pageIndex = 1
                refreshType = 1
                getData()
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                pageIndex += 1
                refreshType = 2
                getData()
            }
        })
    }

    private fun getData() {

        Requester().merchantQueryProductList(queryType, pageIndex, pageSize, object : Requester.RetrofitResponseListener<MerchantQueryProductList> {
            override fun isSuccessful(response: Response<MerchantQueryProductList>?) {
                try {
                    dismissDialog()
                    val productListInfo = response!!.body()
                    if (productListInfo!!.isSuccessful()) {
                        if (refreshType == 2) {
                            refreshLayout.finishLoadmore()
                            updateData(productListInfo.data!!.infiniteSaleProductList, true)
                        } else {
                            refreshLayout.finishRefreshing()
                            updateData(productListInfo.data!!.infiniteSaleProductList, false)
                        }
                    } else {
                        if(refreshType == 2){
                            refreshLayout.finishLoadmore()
                        }else{
                            refreshLayout.finishRefreshing()
                        }
                        showEmptyView()
                        showToast(productListInfo.msg)
                    }
                } catch (e: Exception) {
                }
            }

            override fun isFailed() {
                try {
                    if(refreshType == 2){
                        refreshLayout.finishLoadmore()
                    }else{
                        refreshLayout.finishRefreshing()
                    }
                    dismissDialog()
                    showEmptyView()
                    showToast("请求失败")
                } catch (e: Exception) {
                }
            }

        })
    }

    private fun updateData(productList: List<MerchantQueryProductList.InfiniteSaleProductListBean>?, isAdd: Boolean) {
        if (productList!!.isEmpty()) {
            if (isAdd) {
                showToast("全部加载完成")
                pageIndex -= 1
            } else {
                productAdapter!!.data.clear()
            }
        } else {
            if (isAdd) {
                productAdapter!!.addData(productList)
            } else {
                productAdapter!!.data.clear()
                productAdapter!!.setNewData(productList)
            }
        }
        productAdapter!!.notifyDataSetChanged()
        showEmptyView()

    }

    private fun showEmptyView() {
        if (productAdapter!!.data.size > 0) {
            tvEmpty.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.VISIBLE
        }
    }


}

