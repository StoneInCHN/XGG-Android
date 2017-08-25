package com.hentica.app.module.mine.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.hentica.app.module.entity.mine.shop.ResShpCartListData;
import com.hentica.app.module.mine.ui.intf.OnDeleteViewClickListener;
import com.hentica.app.util.DateHelper;
import com.hentica.app.util.HtmlBuilder;
import com.hentica.app.util.PriceUtil;
import com.hentica.appbase.famework.adapter.QuickMultipleChooseAdapter;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public class ShopCartAdapter extends QuickMultipleChooseAdapter<ResShpCartListData> {

    private OnDeleteViewClickListener<ResShpCartListData> mDeleteViewClick;

    public void setDeleteViewClickListener(OnDeleteViewClickListener<ResShpCartListData> deleteViewClick) {
        mDeleteViewClick = deleteViewClick;
    }

    @Override
    protected int getItemCheckBoxId() {
        return R.id.ckb;
    }

    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_shop_cart_item;
    }

    @Override
    protected void fillView(final int position, final View convertView, ViewGroup parent, final ResShpCartListData data) {
        super.fillView(position, convertView, parent, data);
        CheckBox ckb = (CheckBox) convertView.findViewById(R.id.ckb);
        ckb.setText(data.getEndUser().getNickName());//消费者昵称
        TextView tvMobile = (TextView) convertView.findViewById(R.id.tv_customer_mobile);
        tvMobile.setText(data.getEndUser().getCellPhoneNum());//消费者手机号
        TextView tvRebateAmount = (TextView) convertView.findViewById(R.id.tv_rangli_amount);
        tvRebateAmount.setText(String.format("让利金额：￥%s", PriceUtil.format(data.getRebateAmount())));//让利金额
        TextView tvAmount = (TextView) convertView.findViewById(R.id.tv_custom_amount);
        HtmlBuilder builder = HtmlBuilder.newInstance();
        builder.appendNormal("消费金额：").appendRed(String.format("￥%s", PriceUtil.format(data.getAmount())));
        tvAmount.setText(builder.create());
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_custom_date);
        tvDate.setText(DateHelper.stampToDate(data.getCreateDate()+"", "yyyy-MM-dd"));//日期
        //删除按钮事件
        View deleteView = convertView.findViewById(R.id.img_delete);
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDeleteViewClick != null){
                    mDeleteViewClick.onDeleteClick(convertView, position, data);
                }
            }
        });
    }
}
