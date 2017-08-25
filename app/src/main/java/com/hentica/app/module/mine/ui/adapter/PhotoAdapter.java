/**
 * 
 */
package com.hentica.app.module.mine.ui.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.util.BitmapCompress;
import com.hentica.app.util.FragmentJumpUtil;
import com.hentica.app.widget.photo.MakePhotoDialog2;
import com.hentica.app.widget.photo.MakePhotoListener;
import com.fiveixlg.app.customer.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 网格照片适配器，可添加图片，可删除图片
 * 
 * @author mili
 * @createTime 2016-3-11 上午11:05:55
 */
public class PhotoAdapter extends BaseAdapter {

	/** 对应的界面 */
	private UsualFragment mFragment;

	/** 图片列表 */
	private List<String> mImages;

	/** 是否可用 */
	private boolean mEnable = true;

	/** 最多显示图片数 */
	private int mMaxCount = Integer.MAX_VALUE;

	/** 是否裁剪图片 */
	private boolean mIsCrop;

	/** 是否可用 */
	public boolean isEnable() {
		return mEnable;
	}

	/** 是否可用 */
	public void setEnable(boolean enable) {
		mEnable = enable;
	}

	public int getMaxCount() {
		return mMaxCount;
	}

	/** 最多显示图片数 */
	public int isMaxCount() {
		return mMaxCount;
	}

	/** 最多显示图片数 */
	public void setMaxCount(int maxCount) {
		mMaxCount = maxCount;
	}

	public boolean isIsCrop() {
		return mIsCrop;
	}

	/** 是否裁剪图片 */
	public void setIsCrop(boolean isCrop) {
		mIsCrop = isCrop;
	}

	/** 构造函数 */
	public PhotoAdapter(final UsualFragment fragment, AbsListView listView) {
		this.mFragment = fragment;
		if (listView != null) {

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					if (mEnable) {

						// 若点击的最后一个
						if (position == parent.getAdapter().getCount() - 1) {
							MakePhotoDialog2 dialog = new MakePhotoDialog2();
							//设置图片压缩
							dialog.setCompressConfig(200 * 1024, 1024, 1024);
							dialog.setOnMakePhotoListener(new MakePhotoListener() {
								@Override
								public void onComplete(List<String> imgFilePaths) {
									if (imgFilePaths != null) {
										//
										addImages(imgFilePaths);
									}
								}
							});
							dialog.show(mFragment.getFragmentManager(), dialog.getClass().getSimpleName());
						}
						// 若点击不是最后一个
						else {

							// 跳转到查看大图界面
							if (mImages != null) {

								FragmentJumpUtil.toImageGallery(mFragment, mImages, position);
							}
						}
					}
				}
			});
		}
	}

	/** 构造函数 */
	public PhotoAdapter(final FragmentActivity appCompatActivity, AbsListView listView) {
		if (listView != null) {
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					if (mEnable) {

						// 若点击的最后一个
						if (position == parent.getAdapter().getCount() - 1) {
							MakePhotoDialog2 dialog = new MakePhotoDialog2();
							//设置图片压缩
							dialog.setCompressConfig(200 * 1024, 1024, 1024);
							dialog.setOnMakePhotoListener(new MakePhotoListener() {
								@Override
								public void onComplete(List<String> imgFilePaths) {
									if (imgFilePaths != null) {
										//
										addImages(imgFilePaths);
									}
								}
							});
							dialog.show(appCompatActivity.getSupportFragmentManager(), dialog.getClass().getSimpleName());
						}
						// 若点击不是最后一个
						else {

							// 跳转到查看大图界面
							if (mImages != null) {

								FragmentJumpUtil.toImageGallery(appCompatActivity, mImages, position);
							}
						}
					}
				}
			});
		}
	}

	private List<String> compressImages(List<String> images){
		List<String> result = new ArrayList<>();
		for(String file : images){
			result.add(compressImages(file));
		}
		return result;
	}

	private String compressImages(String image){
		String outfile = ApplicationData.getInstance().getTempPhotoDir() + new Date().getTime() + ".jpg";
		BitmapCompress.compress(image, outfile);
		return outfile;
	}

	/** 对应的界面 */
	public UsualFragment getFragment() {
		return mFragment;
	}

	/** 对应的界面 */
	public void setFragment(UsualFragment fragment) {
		mFragment = fragment;
	}

	/** 图片列表 */
	public List<String> getImages() {
		return mImages;
	}

	/** 图片列表 */
	public void setImages(List<String> images) {
		mImages = images;
		this.notifyDataSetChanged();
	}

	/** 添加图片列表 */
	public void addImages(List<String> images) {

		if (mImages == null) {

			mImages = new ArrayList<String>();
		}

		if (images != null) {

			mImages.addAll(images);
			this.notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {

		int size = mImages != null ? mImages.size() : 0;
		if (size < mMaxCount) {

			size += 1;
		}
		return size;
	}

	@Override
	public String getItem(int position) {
		return (mImages != null && position < mImages.size()) ? mImages.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/** 删除某位置的图片 */
	private void deleteImage(int position) {

		if (position >= 0 && position < mImages.size()) {

			mImages.remove(position);
			notifyDataSetChanged();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			convertView = View.inflate(parent.getContext(), R.layout.mine_fill_evaluate_photo_item, null);

			AQuery query = new AQuery(convertView);
			query.id(R.id.e07_item_del_photo_btn).clicked(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// 可用时才生效
					if (mEnable) {

						int delpos = (Integer) v.getTag();
						deleteImage(delpos);
					}
				}
			});
		}

		final AQuery query = new AQuery(convertView);

		// 是否显示图片；是否显示添加按钮
		int imgSize = (mImages != null ? mImages.size() : 0);
		boolean isMaxCount = imgSize >= mMaxCount;
		boolean isShowImage = (isMaxCount ? true : position < this.getCount() - 1);
		boolean isShowAdd = !isMaxCount;

		// 若显示图片
		if (isShowImage) {

			// 显示图片布局
			query.id(R.id.evaluate_item_photo_layout).visible();
			query.id(R.id.evaluate_add_photo_btn).gone();

			// 显示图片
			String imgPath = this.getItem(position);
//			query.id(R.id.e07_item_photo_img).image(imgPath, true, true, 200, 0);
			final ProgressBar progress = query.id(R.id.e07_item_progress).getProgressBar();
			final ImageView img = query.id(R.id.e07_item_photo_img).getImageView();
			progress.setVisibility(View.VISIBLE);
			Glide.with(convertView.getContext())
					.load(imgPath)
					.asBitmap()
					.into(new SimpleTarget<Bitmap>() {
						@Override
						public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
							progress.setVisibility(View.GONE);
							img.setImageBitmap(resource);
						}
					});
			// 记录图片tag
			query.id(R.id.e07_item_del_photo_btn).tag(position);

		}
		// 若显示添加按钮
		else if (isShowAdd) {

			// 显示添加布局
			query.id(R.id.evaluate_item_photo_layout).gone();
			query.id(R.id.evaluate_add_photo_btn).visible();
		}

		return convertView;
	}
}
