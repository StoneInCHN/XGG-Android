package com.hentica.app.module.mine.ui.shop;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.fiveixlg.app.customer.R;
import com.hentica.app.module.entity.mine.ResUserProfile;
import com.hentica.app.util.FragmentJumpUtil;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/5.20:38
 */

public class MineSettleFailureFragment2 extends MineSettleFailureFragment {

    @Override
    protected boolean hasTitleView() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
        getViews(R.id.mine_failure_btn_resettle).setVisibility(View.GONE);
        getViews(R.id.layout_failure).setVisibility(View.VISIBLE);
        final ResUserProfile profile = getData();
        final TextView phone = getViews(R.id.tv_phone);
        phone.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        if(profile != null){
            if(!TextUtils.isEmpty(profile.getFailReason())){
                setViewText("失败原因："+profile.getFailReason(), R.id.tv_reason);
                setViewVisiable(true, R.id.tv_reason);
            }
            setViewText(profile.getSalesmanCellphone(), R.id.tv_phone);
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentJumpUtil.toCalling(getUsualFragment(), profile.getSalesmanCellphone());
                }
            });
        }

    }

}
