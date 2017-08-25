package com.hentica.app.widget.photo;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.hentica.app.framework.data.ApplicationData;
import com.hentica.app.framework.fragment.RequestPermissionResultListener;
import com.hentica.app.framework.fragment.UsualFragment;
import com.hentica.app.util.LogUtils;
import com.hentica.app.widget.dialog.SelfAlertDialogHelper;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.fiveixlg.app.customer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 取图片对话框
 */
public class MakePhotoDialog2 extends DialogFragment implements TakePhoto.TakeResultListener, InvokeListener, IGetPhoto , RequestPermissionResultListener{

    public static final String TAG = MakePhotoDialog.class.getSimpleName();

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private MakePhotoListener mListener;

    private CropOptions mCropOptions;//剪裁配置

    private CompressConfig mCompressConfig;//压缩设置

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_dialog_select_photo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
        setEvent();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.setStyle(STYLE_NORMAL, R.style.full_screen_dialog);
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setEvent() {
        AQuery query = new AQuery(getView());

        // 大背景
        query.clicked(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 关闭对话框
                dismiss();
            }
        });

        // 拍照
        query.id(R.id.dialog_photo_take_photo_btn).clicked(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                compressTakePhoto(takePhoto, mCompressConfig, true);
                if (mCropOptions != null) {
                    //拍照并裁剪
                    takePhoto.onPickFromCaptureWithCrop(getFilePath(), getCropOptions());
                } else {
                    //只拍照
                    takePhoto.onPickFromCapture(getFilePath());
                }
            }
        });

        // 从相册中选
        query.id(R.id.dialog_photo_select_from_album_btn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compressTakePhoto(takePhoto, mCompressConfig, true);
                if (mCropOptions != null) {
                    takePhoto.onPickFromGalleryWithCrop(getFilePath(), getCropOptions());
                } else {
                    takePhoto.onPickFromGallery();
                }
            }
        });

        // 取消
        query.id(R.id.dialog_photo_cancel_btn).clicked(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 关闭对话框
                dismiss();
            }
        });
    }

    private void compressTakePhoto(TakePhoto takePhoto, CompressConfig config, boolean showProgress){
        if(takePhoto != null && config != null){
            takePhoto.onEnableCompress(config, showProgress);
        }
    }

    private Uri getFilePath() {
        File file = new File(ApplicationData.getInstance().getTempPhotoDir(), System.currentTimeMillis() + ".jpg");
//        File file = new File(getContext().getCacheDir(), System.currentTimeMillis() + ".jpg");
        Log.i(TAG, file.getAbsolutePath());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return Uri.fromFile(file);
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(1).setAspectY(1);
        builder.setOutputX(300).setOutputY(300);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        dismiss();
        if (mListener != null) {
            List<String> imgs = new ArrayList<>();
            String resultPath = result.getImage().getCompressPath();
            if(TextUtils.isEmpty(resultPath)){
                resultPath = result.getImage().getOriginalPath();
            }
            imgs.add(resultPath);
            mListener.onComplete(imgs);
        }
        LogUtils.i(TAG, "takeSuccess：" + result.getImage().getOriginalPath());
    }

    @Override
    public void takeFail(TResult result, final String msg) {
        SelfAlertDialogHelper.showDialog(getFragmentManager(), msg, "去设置", "暂不", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Log.d(TAG, "getPackageName(): " + getActivity().getPackageName());
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                getActivity().startActivity(intent);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        LogUtils.i(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        LogUtils.i(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        LogUtils.i(TAG, "onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }


    @Override
    public void setCropConfig(int aspectX, int aspectY, int outputX, int outputY) {
        CropOptions.Builder builder = new CropOptions.Builder();
        if (aspectX > 0 && aspectY > 0) {
            builder.setAspectX(aspectX)
                    .setAspectY(aspectY);
        }
        if (outputX > 0 && outputY > 0) {
            builder.setOutputX(300)
                    .setOutputY(300);
        }
        builder.setWithOwnCrop(false);//使用系统自带裁剪
        mCropOptions = builder.create();
    }

    @Override
    public void setCompressConfig(int maxSize, int maxWidthPx, int maxHeightPx) {
        CompressConfig.Builder builder = new CompressConfig.Builder();
        builder.setMaxSize(maxSize).setMaxPixel(Math.max(maxWidthPx, maxHeightPx));
        mCompressConfig = builder.create();
    }

    @Override
    public void setOnMakePhotoListener(MakePhotoListener l) {
        mListener = l;
    }


}
