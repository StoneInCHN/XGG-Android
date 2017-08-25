package com.hentica.app.module.mine.ui.shop;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.fiveixlg.app.customer.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hentica.app.framework.fragment.ptr.PtrView;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.shop.ResGenerateOrder;
import com.hentica.app.module.entity.mine.shop.ResShpCartListData;
import com.hentica.app.module.mine.presenter.shop.ShopCartListPresenter;
import com.hentica.app.module.mine.ui.adapter.ShopCartAdapter;
import com.hentica.app.module.mine.ui.intf.OnDeleteViewClickListener;
import com.hentica.app.module.mine.view.shop.ShopCartView;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.ListViewUtils;
import com.hentica.app.util.PriceUtil;
import com.hentica.app.util.event.DataEvent;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.widget.view.ptrview.CustomPtrListView;
import com.hentica.appbase.famework.adapter.QuickChooseAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public class MineShopCartFragment extends BaseFragment implements PtrView<ResShpCartListData>, ShopCartView{

    private CustomPtrListView mPtrListView;

    private ShopCartListPresenter mShopCartListPresenter;

    private ShopCartAdapter mAdapter;

    private ShopCartPresenter mShopCartPresenter;


    public static final String SELLER_ID = "sellerId";//商户id

    private long sellerId = 0;

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    public int getLayoutId() {
        return R.layout.mine_shop_cart_fragment;
    }

    @Override
    protected void handleIntentData(Intent intent) {
        super.handleIntentData(intent);
        sellerId = intent.getLongExtra(SELLER_ID, sellerId);
    }

    @Override
    protected void initData() {
        mShopCartListPresenter = new ShopCartListPresenter(this);
        mAdapter = new ShopCartAdapter();
        mShopCartPresenter = new ShopCartPresenterImpl();
        mShopCartPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mPtrListView = getViews(R.id.shop_cart_list);
        mPtrListView.setAdapter(mAdapter);
        ListViewUtils.setDefaultEmpty(mPtrListView.getRefreshableView());
    }

    @Override
    protected void setEvent() {
        mPtrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mShopCartListPresenter.onRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mShopCartListPresenter.onLoadMore();
            }
        });
        //全选按钮
        final CheckBox ckbSelectAll = getViews(R.id.ckb_select_all);
        ckbSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckbSelectAll.isChecked()){
                    mAdapter.selectAll();
                }else{
                    mAdapter.clearAllSelected();
                }
            }
        });
        //item点击事件
        mAdapter.setOnItemClickListener(new QuickChooseAdapter.OnItemClickListener<ResShpCartListData>() {
            @Override
            public void onItemClick(View view, long position, ResShpCartListData data) {
                //判断是否全部选中
                ckbSelectAll.setChecked(mAdapter.isAllSelected());
            }
        });
        //删除按钮事件
        mAdapter.setDeleteViewClickListener(new OnDeleteViewClickListener<ResShpCartListData>() {
            @Override
            public void onDeleteClick(View view, int pos, final ResShpCartListData data) {
                showDeleteAlert(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mShopCartPresenter.deleteOrders(data);
                    }
                });
            }
        });
        //选中数据变化事件
        mAdapter.setSelectedDatasChangeListener(new QuickChooseAdapter.OnSelectedDatasChangeListener<ResShpCartListData>() {
            @Override
            public void onSelectedDatasChanged(List<ResShpCartListData> selectedDatas) {
                calculateSelected();
                ckbSelectAll.setChecked(mAdapter.isAllSelected());
            }
        });
        //支付按钮
        getViews(R.id.btn_to_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ResShpCartListData> selectedDatas = mAdapter.getSelectedDatas();
                if(selectedDatas == null || selectedDatas.isEmpty()){
                    showToast("未选择录单列表！");
                    return;
                }
                mShopCartPresenter.toPayOrders(selectedDatas, sellerId);
            }
        });
    }

    private void showDeleteAlert(View.OnClickListener l){
        SelfAlertDialogHelper.showDialog(getFragmentManager(), "确定要删除吗？删除后不可恢复！", l);
    }

    /**
     * 计算选中数据
     */
    private void calculateSelected(){
        //计算合计金额
        List<ResShpCartListData> selectedDatas = mAdapter.getSelectedDatas();
        double rebateAmount = calculateRebateAmount(selectedDatas);
        setTotalRebateAmount(rebateAmount);
        setViewText(String.format("支付（%d）", getSelectedCount(selectedDatas)), R.id.btn_to_pay);
    }

    /**
     * 计算让利金额合计
     * @param datas
     * @return
     */
    private double calculateRebateAmount(List<ResShpCartListData> datas){
        double result = 0;
        if(datas != null && !datas.isEmpty()){
            for(ResShpCartListData data : datas){
                result += data.getRebateAmount();
            }
        }
        return result;
    }

    /**
     * 设置合计让利金额
     * @param amount
     */
    private void setTotalRebateAmount(double amount){
        HtmlBuilder builder = HtmlBuilder.newInstance();
        builder.appendNormal("合计：").appendRed(String.format("￥%s", PriceUtil.format(amount)));
        setViewText(builder.create(), R.id.tv_total_amount);
    }

    /**
     * 获取选中数据
     * @param datas
     * @return
     */
    private int getSelectedCount(List<ResShpCartListData> datas){
        return datas != null ? datas.size() : 0;
    }

    @Override
    public int getListSize() {
        return 0;
    }

    @Override
    public void setListDatas(List<ResShpCartListData> datas) {
        mPtrListView.onRefreshComplete();
        mAdapter.setDatas(datas);
        mAdapter.clearAllSelected();
    }

    @Override
    public void addListDatas(List<ResShpCartListData> datas) {
        mPtrListView.onRefreshComplete();
        mAdapter.addAll(datas);
    }

    @Override
    public void deleteSuccess(ResShpCartListData data) {
        mAdapter.remove(data);
    }

    @Override
    public void generateSuccess(ResGenerateOrder data) {
        FragmentJumpUtil.toRecordOrderPayFragment(getUsualFragment(), data.getOrderSn(), sellerId+"");
    }

    @Override
    public void finish() {
        super.finish();
        if(mShopCartPresenter != null){
            mShopCartPresenter.detachView();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrListView.pullDownRefresh();
            }
        }, 200);
    }


    @Subscribe
    public void onEvent(DataEvent.OnCloseRecordOrderFragmentEvent evet){
        finish();
    }
}
