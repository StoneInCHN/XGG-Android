package com.hentica.app.widget.view;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;

import com.hentica.app.util.LogUtils;

import java.util.List;

/**
 * Created by Snow on 2016/11/19.
 */

public class CameraParamsUtils {
    private static final String TAG = "CameraParamsUtils";

    private static final boolean DEBUG = true;

    /**
     * 设置相机照片最大边像素
     * @param mCamera
     * @param maxPx
     */
    public static void setCameraParams(Camera mCamera, int maxPx){
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = getPictureSize(parameters.getSupportedPictureSizes(), maxPx);
        parameters.setPictureSize(size.width, size.height);
        // 设置照片质量
        parameters.setJpegQuality(100);
        // 连续对焦模式
        if(parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
//        //自动对焦
        mCamera.cancelAutoFocus();
//      // 设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
//      mCamera.setDisplayOrientation(90);
        mCamera.setParameters(parameters);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void setCameraParams(Camera mCamera, int wight, int height){
        Camera.Parameters parameters = mCamera.getParameters();
        setPictureSize(mCamera, wight, height);
        // 设置照片质量
        parameters.setJpegQuality(100);
        // 连续对焦模式
        if(parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
//        //自动对焦
        mCamera.cancelAutoFocus();
//      // 设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
//      mCamera.setDisplayOrientation(90);
        mCamera.setParameters(parameters);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void setPictureSize(Camera mCamera, int width, int height){
        Camera.Parameters parameters = mCamera.getParameters();
        // 获取摄像头支持的PictureSize列表
        List<Camera.Size> sizeList =parameters.getSupportedPictureSizes();
        // 从列表中选取合适的分辨率
        Camera.Size size = getPictureSize(sizeList, (float)width / height);
        if(size == null){
            size = parameters.getPictureSize();
        }
        LogUtils.i(TAG, "size.width=" + size.width + "  size.height=" + size.height);
        // 根据选出的PictureSize重新设置SurfaceView大小
        parameters.setPictureSize(size.width, size.height);
        mCamera.setParameters(parameters);
    }

    private static Camera.Size getPictureSize(List<Camera.Size> sizeList , int maxPx){
        Camera.Size result = null;
        for(Camera.Size it : sizeList){
            if(it.height < maxPx && it.width < maxPx){
                result = it;
                break;
            }
        }
        /*
         * 未找到合适的宽高比
         * 采用默认宽高比——4：3
         */
        if(result == null){
            for(Camera.Size it : sizeList){
                //当前宽高比
                float currentRatio = (float) it.width / it.height;
                if(currentRatio == 4f / 3){
                    result = it;
                    break;
                }
            }
        }
        return result;
    }

    private static Camera.Size getPictureSize(List<Camera.Size> sizeList, float screenRatio){
        Camera.Size result = null;
        for(Camera.Size it : sizeList){
            //当前宽高比
            float currentRatio = (float) it.width / it.height;
            if(currentRatio == screenRatio){
                result = it;
                break;
            }
        }

        /*
         * 未找到合适的宽高比
         * 采用默认宽高比——4：3
         */
        if(result == null){
            for(Camera.Size it : sizeList){
                //当前宽高比
                float currentRatio = (float) it.width / it.height;
                if(currentRatio == 4f / 3){
                    result = it;
                    break;
                }
            }
        }
        return result;
    }

}
