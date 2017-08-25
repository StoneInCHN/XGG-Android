package com.hentica.app.widget.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.hentica.app.util.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Snow on 2016/11/19.
 */

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private Camera mCamera;

    private static int screenWitdh;
    private static int screenHeight;

    public CameraSurfaceView(Context context) {
        this(context, null);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getScreenMetrix(context);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getScreenMetrix(context);
        initView();
    }

    private void getScreenMetrix(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenWitdh  = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    private void initView(){
        this.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(mCamera == null){
            try{
                mCamera = Camera.open();
            } catch (Exception e){
                Toast.makeText(getContext(), "请打开相机权限！", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                mCamera.setPreviewDisplay(surfaceHolder);
                CameraParamsUtils.setCameraParams(mCamera, 800);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPictureSize(int width, int height){
        if(mCamera == null){
            return;
        }
        CameraParamsUtils.setPictureSize(mCamera, width, height);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if(mCamera != null) {
            mCamera.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if(mCamera != null){
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 拍照，压缩为jpg后回调
     * @param back
    */
    public void takePicture(final String filePath, final takePictureCallBack back){
        // 2016/11/19 处理拍照回调
        // 2016/11/21 创建文件
        if(mCamera == null){
            return;
        }
        if(!FileUtils.createFile(filePath)){
            //创建文件失败
            if(back != null){
                back.takePicture(false, "文件创建失败");
            }
            return;
        }
        mCamera.takePicture(null, null, new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                File file = new File(filePath);
                FileOutputStream out = null;
                Bitmap bitmap = null;
                BufferedOutputStream bos = null;
                try {
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    out = new FileOutputStream(file);
                    bos = new BufferedOutputStream(out);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    bos.flush();
                    if(back != null){
                        back.takePicture(true, file.getAbsolutePath());
                        back.takePicture(bitmap);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    if(back != null){
                        back.takePicture(false, "FileNotFoundException");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(back != null){
                        back.takePicture(false, "IOException");
                    }
                } finally {
                    try {
                        out.close();
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mCamera.stopPreview();
                    mCamera.startPreview();
                    bitmap.recycle();
                }
            }
        });
    }

    public interface takePictureCallBack{

        /**
         *
         * @param success
         *          true：成功，false：失败
         * @param result
         *          成功，返回文件路径，失败，返回消息
         */
        void takePicture(boolean success, String result);

        /**
         * @param bitmap
         *          成功返回bitmap对象
         */
        void takePicture(Bitmap bitmap);
    }
}
