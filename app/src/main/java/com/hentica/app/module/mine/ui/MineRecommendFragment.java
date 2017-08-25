package com.hentica.app.module.mine.ui;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hentica.app.module.common.base.BaseFragment;
import com.hentica.app.module.entity.mine.ResMineQrCode;
import com.hentica.app.module.mine.presenter.MineQrCodePresenterImpl;
import com.hentica.app.module.mine.presenter.QrCodePresenter;
import com.hentica.app.module.mine.view.QrCodeView;
import com.hentica.app.util.LogUtils;
import com.hentica.app.util.UmengShareUtil;
import com.hentica.app.widget.view.TitleView;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.fiveixlg.app.customer.R;

/**
 * 推荐有礼界面
 *
 * @author
 * @createTime 2017-03-23 下午15:13:27
 */
public class MineRecommendFragment extends BaseFragment implements QrCodeView<ResMineQrCode>, UMShareListener {

    private QrCodePresenter mQrCodePresenter;

    private String mShareUrl ;//分享地址
    private String mShareDownloadUrl;//分享下载地址

    @Override
    public int getLayoutId() {
        return R.layout.mine_recommend_fragment;
    }

    @Override
    protected boolean hasTitleView() {
        return true;
    }

    @Override
    protected TitleView initTitleView() {
        return getViews(R.id.common_title);
    }

    @Override
    protected void configTitleValues(TitleView title) {
        super.configTitleValues(title);
    }

    @Override
    protected void initData() {
        mQrCodePresenter = new MineQrCodePresenterImpl(this);
        mQrCodePresenter.getQrCode();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {
        getViews(R.id.mine_btn_recommend_records).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFrameActivity(MineRecommendHisFragment.class);
            }
        });
        getViews(R.id.mine_btn_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toShare(mShareUrl, getString(R.string.recommend_share_content));
            }
        });
        getViews(R.id.mine_btn_share_url).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toShare(mShareDownloadUrl, getString(R.string.recommend_share_download_content));
            }
        });
    }

    @Override
    public void setQrCode(byte[] datas) {
        //显示二维码
        Glide.with(getActivity()).load(datas).into((ImageView) getViews(R.id.recommend_img_qr_code));
    }

    @Override
    public void setData(ResMineQrCode data) {
        if(data != null){
            mShareUrl = data.getRecommendUrl();
            mShareDownloadUrl = data.getDownloadUrl();
        }
    }

    /**
     * 分享
     * @param url 链接
     * @param content 内容
     */
    private void toShare(String url, String content){
        UmengShareUtil.shareList(getActivity(), getString(R.string.app_name),
                content, url, "", this);
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        LogUtils.i(TAG, "onResult: 分享成功");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
