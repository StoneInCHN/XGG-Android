package com.hentica.app.module.common.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.fiveixlg.app.customer.R;

public class ImageGallerPager extends Fragment {
	private static final String TAG = ImageGallerPager.class.getSimpleName();

	private static final String BUNDLE = "img";
	private String imageUrl;

	public void setImage(String url) {
		imageUrl = url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.common_imagegallery_page, container, false);

		if (savedInstanceState != null) {
			if (imageUrl == null && savedInstanceState.containsKey(BUNDLE)) {
				imageUrl = savedInstanceState.getString(BUNDLE);
			}
		}
		final SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) view
				.findViewById(R.id.gallery_page_img);
		final ProgressBar progress = (ProgressBar) view.findViewById(R.id.gallery_page_progress);
		final TextView mTvError = (TextView) view.findViewById(R.id.gallery_tv_error);
		if (imageUrl != null) {
			progress.setVisibility(View.VISIBLE);
			Glide.with(getContext())
					.load(imageUrl)
					.asBitmap()
					.override(800,800)
					.skipMemoryCache(true)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					.into(new SimpleTarget<Bitmap>() {

						@Override
						public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
							progress.setVisibility(View.GONE);
							imageView.setImage(ImageSource.bitmap(resource));
						}

						@Override
						public void onLoadFailed(Exception e, Drawable errorDrawable) {
							super.onLoadFailed(e, errorDrawable);
							Log.i(TAG, "onLoadFailed");
							//隐藏进度
							progress.setVisibility(View.GONE);
							//显示提示文字
							mTvError.setVisibility(View.VISIBLE);
						}
					});

		}
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		View view = getView();
		if (view != null) {
			outState.putString(BUNDLE, imageUrl);
		}
	}
}
