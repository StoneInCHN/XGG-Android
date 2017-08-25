package com.hentica.app.module.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.util.ViewUtil;
import com.fiveixlg.app.customer.R;

import java.util.List;


/**
 * intent.putStringArrayListExtra("images"); // List&lt;String> <br />
 * intent.putExtra("pos", 0); // 从第几个图片开始显示 <br />
 *
 * @author nnnn
 * @createTime 2016年4月8日 下午2:38:33
 */
public class ImageGallery extends UsualFragment {
    private List<String> list;
    private int position;
    private ScreenSlidePagerAdapter adapter;
    private ViewPager pager;
    private AQuery query;
    private View mStatusBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.common_imagegallery, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
        initView();
    }

    private void initDatas() {
        Intent intent = getIntent();
        list = intent.getStringArrayListExtra("images");
        position = intent.getIntExtra("pos", 0);
        query = new AQuery(getView());
    }

    private void initView() {

        // 标题
        query.id(R.id.common_title_text).text("");
        // 返回
        query.id(R.id.common_title_left_btn_back).visibility(View.VISIBLE)
                .clicked(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        finish();
                    }
                });
        pager = (ViewPager) query.id(R.id.image_gallery_pager).getView();
        adapter = new ScreenSlidePagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);
        mStatusBar = query.id(R.id.title_status_bar_view).getView();
        ViewUtil.setKeepStatusBar(getView(), mStatusBar, getContext(), true);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int arg0) {
            ImageGallerPager fragment = new ImageGallerPager();
            fragment.setImage(list.get(arg0));
            return fragment;
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }
}
