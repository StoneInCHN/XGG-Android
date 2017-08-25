package com.hentica.app.widget.photo;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.RequestPermissionResultListener;
import com.hentica.app.lib.util.FileHelper;
import com.hentica.app.util.LogUtils;
import com.hentica.app.util.PermissionHelper;
import com.hentica.app.util.UriUtil;
import com.fiveixlg.app.customer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 取图片对话框
 */
public class MakePhotoDialog extends DialogFragment implements RequestPermissionResultListener {

    public static final String TAG = MakePhotoDialog.class.getSimpleName();

    // /** 最小边长度定义 */
    // private final static int s_nMinSideLength = 800;
    // /** 最大像素尺寸 */
    // private final static int s_nMaxNumOfPixels = 800 * 1024;
    /**
     * 裁剪边长
     */
    private final static int s_nCropSizeLength = 300;

    private boolean crop = false;

    private final static String CAMERA_PERMISSION = Manifest.permission.CAMERA;

    /**
     * 临时文件夹
     */
    private final static String TMP_DIR = ApplicationData.getInstance().getTempDir() + "/tmp/";
    private final static String TMP_APP_DIR = ApplicationData.getInstance().getSystemTempDir() + "tmp";

    private List<String> m_arrImgFilePaths = new ArrayList<String>();
    private String m_strCurrImgFilePath = null;

    private Fragment mParentFragment;

    /**
     * 取图片事件
     */
    public interface OnMakePhotoListener {

        /**
         * 取图片输入完成
         *
         * @param imgFilePaths 图片路径
         */
        public void onComplete(List<String> imgFilePaths);
    }

    /**
     * 取图片事件
     */
    protected OnMakePhotoListener mOnMakePhotoListener = null;

    /**
     * 取图片事件
     */
    public OnMakePhotoListener getOnMakePhotoListener() {
        return mOnMakePhotoListener;
    }

    /**
     * 取图片事件
     */
    public void setOnMakePhotoListener(OnMakePhotoListener onMakePhotoListener) {
        mOnMakePhotoListener = onMakePhotoListener;
    }

    /**
     * 设置是否裁剪
     */
    public void setCrop(boolean crop) {
        this.crop = crop;
    }

    public void setParentFragment(Fragment fragment) {
        this.mParentFragment = fragment;
    }

    private PermissionHelper.PermissionGrant mPermissionGrant = new PermissionHelper.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionHelper.CODE_CAMERA:
                    toTakePhoto();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_dialog_select_photo, container);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.setStyle(STYLE_NORMAL, R.style.full_screen_dialog);
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        // 初始化界面
        this.initView();
        this.setEvent();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case Constants.PHOTO_REQUEST_TAKEPHOTO:
                // 若从拍照回来,路径必然不为空，判断文件是否存在
                dispostCameraResult(intent);

                break;

            case Constants.PHOTO_REQUEST_GALLERY:
                // 若从图库回来
                // 清空旧的文件记录
                if (intent != null) {

                    Uri originalUri = intent.getData();
                    String imgPath = UriUtil.getPath(getContext(), originalUri);

                    // 若需要裁剪
                    if (crop) {
                        m_strCurrImgFilePath = TMP_APP_DIR + getPhotoFileName();
                        startPhotoZoom(originalUri, s_nCropSizeLength, m_strCurrImgFilePath);
                    }
                    // 若不需要裁剪
                    else {
                        m_strCurrImgFilePath = TMP_APP_DIR + getPhotoFileName();
                        FileHelper.copyFile(imgPath, m_strCurrImgFilePath);
                        m_arrImgFilePaths.add(m_strCurrImgFilePath);
                        selectFinish();
                    }

                }
                break;

            case Constants.PHOTO_REQUEST_CUT:
                // 触发照片选中事件
                m_arrImgFilePaths.add(m_strCurrImgFilePath);
                selectFinish();
                break;
        }
    }

    /**
     * 处理相机返回数据
     */
    protected void dispostCameraResult(Intent data) {
        File photoFile = new File(m_strCurrImgFilePath);
        if (photoFile.exists()) {
            if (crop) {
                m_strCurrImgFilePath = TMP_APP_DIR + photoFile.getName();
                startPhotoZoom(Uri.fromFile(photoFile), s_nCropSizeLength, m_strCurrImgFilePath);
            } else {
                selectFinish();
            }
        }
    }

    private void startPhotoZoom(Uri uri, int size, String savePath) {
        if (uri != null) {

            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");

            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX,outputY 是剪裁图片的宽高
            intent.putExtra("outputX", size);
            intent.putExtra("outputY", size);

            intent.putExtra("scale", true);
            // 保存路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(savePath)));
            // 不返回图片，直接保存
            intent.putExtra("return-data", false);
            // 保存格式
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
            // 不禁用人脸识别
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, Constants.PHOTO_REQUEST_CUT);
        } else {

            Toast.makeText(getContext(), "打开源图片失败，不能开启图片裁剪功能", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 选择结束
     */
    private void selectFinish() {

        if (mOnMakePhotoListener != null) {
            mOnMakePhotoListener.onComplete(m_arrImgFilePaths);
        }

        if (this.isResumed()) {

            // 关闭对话框
            dismiss();
        } else {

            dismissAllowingStateLoss();
        }
    }

    private void initView() {

        // final AQuery query = new AQuery(getView());
        // TODO 动画：从底部弹出
    }

    private void setEvent() {

        AQuery query = new AQuery(getView());

        // 大背景
        query.clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // 关闭对话框
                dismiss();
            }
        });

        // 拍照
        query.id(R.id.dialog_photo_take_photo_btn).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {
                PermissionHelper.requestPermission(mParentFragment, PermissionHelper.CODE_CAMERA, mPermissionGrant);
            }
        });

        // 从相册中选
        query.id(R.id.dialog_photo_select_from_album_btn).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, Constants.PHOTO_REQUEST_GALLERY);
            }
        });

        // 取消
        query.id(R.id.dialog_photo_cancel_btn).clicked(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!m_arrImgFilePaths.isEmpty()) {

                    // 取消操作：新建异步任务删除生成的图片文件
                    AsyncTask<Void, Float, Void> task = new AsyncTask<Void, Float, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {

                            int length = m_arrImgFilePaths.size();
                            for (int i = 0; i < length; i++) {

                                FileHelper.deleteFile(m_arrImgFilePaths.get(i));
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {
                            // onResult(result);
                        }
                    };
                    task.execute();
                }

                // 关闭对话框
                dismiss();
            }
        });
    }

    private void toTakePhoto() {
        createImgFile();
        toCamera();
    }

    /**
     * 创建图片文件
     */
    private void createImgFile() {
        File toDir = new File(TMP_DIR); //
        if (!toDir.exists() || !toDir.isDirectory()) {
            toDir.mkdirs();
        }

        if (m_strCurrImgFilePath == null) {
            m_strCurrImgFilePath = TMP_DIR + getPhotoFileName();
            m_arrImgFilePaths.add(m_strCurrImgFilePath);
        }
    }

    /**
     * 获取图片文件路径
     */
    protected final String getImgFilePath() {
        return m_strCurrImgFilePath;
    }

    protected void toCamera() {
        //
        File toDir = new File(TMP_DIR); //
        if (!toDir.exists() || !toDir.isDirectory()) {
            toDir.mkdirs();
        }

        if (m_strCurrImgFilePath == null) {
            m_strCurrImgFilePath = TMP_DIR + getPhotoFileName();
            m_arrImgFilePaths.add(m_strCurrImgFilePath);
        }

        File imgFile = new File(m_strCurrImgFilePath);
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        startActivityForResult(intent, Constants.PHOTO_REQUEST_TAKEPHOTO);
    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date();
        return date.getTime() + ".jpg";
    }

    /**
     * 所需常量
     */
    protected static class Constants {

        public static final int PHOTO_REQUEST_TAKEPHOTO = 1;
        public static final int PHOTO_REQUEST_GALLERY = 2;
        public static final int PHOTO_REQUEST_CUT = 3;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogUtils.i("MakePhotoDialog", "onRequestPermissionsResult");
        PermissionHelper.requestPermissionsResult(mParentFragment, requestCode, permissions, grantResults, mPermissionGrant);
    }

}
