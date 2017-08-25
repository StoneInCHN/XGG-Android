package com.hentica.app.module.mine.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.module.entity.mine.ResBankListData;
import com.hentica.app.module.mine.ui.intf.OnDeleteViewClickListener;
import com.hentica.app.util.StringUtil;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.hentica.appbase.famework.adapter.QuickSingleChooseAdapter;

/**
 * Created by Snow on 2017/5/28 0028.
 */

public class BankCardAdapter extends QuickSingleChooseAdapter<ResBankListData> {

    private OnDeleteViewClickListener<ResBankListData> mDeleteClickLisntener;

    public void setDeleteClickLisntener(OnDeleteViewClickListener<ResBankListData> deleteClickLisntener) {
        mDeleteClickLisntener = deleteClickLisntener;
    }

    @Override
    protected int getItemCheckBoxId() {
        return R.id.tv_default;
    }

    @Override
    protected int getLayoutRes(int type) {
        return R.layout.mine_bank_card_list_item;
    }

    @Override
    protected void showView(final View convertView, final int position, final ResBankListData data) {
        super.showView(convertView, position, data);
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        tvName.setText(data.getBankName());
        TextView tvCardNum = (TextView) convertView.findViewById(R.id.tv_card_number);
        tvCardNum.setText(StringUtil.keepLastWords(data.getCardNum(), '*', 4));
        TextView tvType = (TextView) convertView.findViewById(R.id.tv_card_type);
        tvType.setText(data.getCardType());
        final View viewDelete = convertView.findViewById(R.id.btn_delete);
        viewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDeleteClickLisntener != null){
                    mDeleteClickLisntener.onDeleteClick(viewDelete, position, data);
                }
            }
        });
        View dividerTop = convertView.findViewById(R.id.divider_top);
        dividerTop.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
    }

}
