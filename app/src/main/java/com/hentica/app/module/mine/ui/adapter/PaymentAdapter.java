package com.hentica.app.module.mine.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;
import com.hentica.app.module.entity.index.IndexPayTypeListData;
import com.hentica.app.util.PriceUtil;
import com.hentica.appbase.famework.adapter.QuickAdapter;

/**
 * Created by Snow on 2017/5/25 0025.
 */

public class PaymentAdapter extends QuickAdapter<IndexPayTypeListData> {

        @Override
        protected int getLayoutRes(int type) {
            return R.layout.index_pay_list_item;
        }

        @Override
        protected void fillView(int position, View convertView, ViewGroup parent, IndexPayTypeListData data) {
            AQuery query = new AQuery(convertView);
            TextView titleTv = query.id(R.id.index_pay_list_item_title).getTextView();
            titleTv.setText(data.getConfigValue());
            TextView contentTv = query.id(R.id.index_pay_list_item_content).getTextView();
            convertView.setTag(data);
            switch (data.getId()) {
                case 1:
                    // 微信支付
                    titleTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rebate_homepage_pay_icon_wechat, 0, 0, 0);
                    contentTv.setText("");
                    break;
                case 2:
                    // 支付宝支付
                    titleTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rebate_homepage_pay_icon_alipay, 0, 0, 0);
                    contentTv.setText("");
                    break;
                case 3:
                    // 快捷支付
                    titleTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.rebate_homepage_pay_icon_tl, 0, 0, 0);
                    contentTv.setText("");
                    break;
                case 4:
                    // 乐豆支付
                    break;
            }
        }
    }
