/*
 * Copyright (C) 2015 excelsecu authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hentica.app.widget.view.capture;

import java.io.IOException;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.android.camera.CameraManager;

@SuppressLint("ValidFragment")
public class CaptureFragment extends Fragment implements Callback {

	private CaptureFragmentHandler handler;
	private CaptureViewfinderView viewfinderView2;
	private SurfaceView surfaceView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private boolean vibrate;
	private CameraManager cameraManager;

	private QRCodeHelper.CaptureQRCodeListener listener;

	private View preViewFrame;

	public CaptureFragment(QRCodeHelper.CaptureQRCodeListener listener) {
		this.listener = listener;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {

		return buildLayout();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// cameraManager = new CameraManager(getContext().getApplication());
		cameraManager = new CameraManager(getContext());

		// @ch use Java code to build layout instead of xml file
		// ViewfinderView2 = (ViewfinderView2)
		// findViewById(R.id.viewfinder_view);
		if (viewfinderView2 != null) {

			viewfinderView2.setCameraManager(cameraManager);
		}
		hasSurface = false;

		this.resizeCamera();
	}

	public View getPreViewFrame() {
		return preViewFrame;
	}

	public void setPreViewFrame(View preViewFrame) {
		this.preViewFrame = preViewFrame;
	}

	/** 调整相机区域大小 */
	private void resizeCamera() {

		if (preViewFrame != null) {

			preViewFrame.postDelayed(new Runnable() {

				@Override
				public void run() {

					// [0]left [1]top
					int[] location = new int[2];
					preViewFrame.getLocationOnScreen(location);

					Rect r = new Rect();
					r.left = location[0];
					r.top = location[1];
					r.right = r.left + preViewFrame.getWidth();
					r.bottom = r.top + preViewFrame.getHeight();

					cameraManager.setFramingRect(new Rect(r));
				}
			}, 100);

		}
	}

	/**
	 * use Java code to build layout instead of xml file
	 * 
	 * @ch
	 */
	private View buildLayout() {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		FrameLayout layout = new FrameLayout(getContext());
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);

		surfaceView = new SurfaceView(getContext());
		surfaceView.setLayoutParams(params);
		layout.addView(surfaceView);

		viewfinderView2 = new CaptureViewfinderView(getContext(), null);
		viewfinderView2.setLayoutParams(params);
		layout.addView(viewfinderView2);

		return layout;
	}

	@SuppressWarnings("deprecation")
	public void onResume() {
		super.onResume();

		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.removeCallback(this);
			surfaceHolder.addCallback(this);
		}
		// @ch api compatible
		if (VERSION.SDK_INT < 11) {
			// surfaceview will push buffer automatically
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getContext().getSystemService(
				Context.AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	public void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		cameraManager.closeDriver();

		super.onPause();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			cameraManager.openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureFragmentHandler(this, decodeFormats, null, characterSet,
					cameraManager);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	CaptureViewfinderView getViewfinderView2() {
		return viewfinderView2;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {

		if (viewfinderView2 != null) {

			viewfinderView2.drawViewfinder();
		}
	}

	public void handleDecode(Result obj, Bitmap barcode) {
		// @ch no need
		// ViewfinderView2.drawResultBitmap(barcode);
		playBeepSoundAndVibrate();
		listener.onResult(obj.getText());
	}

	private void initBeepSound() {
		// @ch beep need sound file, put it in assets folder
		// when you want to use this feature, we haven't implement it
		/*
		 * if (playBeep && mediaPlayer == null) { // The volume on STREAM_SYSTEM
		 * is not adjustable, and users found it // too loud, // so we now play
		 * on the music stream.
		 * setVolumeControlStream(AudioManager.STREAM_MUSIC); mediaPlayer = new
		 * MediaPlayer();
		 * mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		 * mediaPlayer.setOnCompletionListener(beepListener);
		 * 
		 * 
		 * try { mediaPlayer.setDataSource(file.getFileDescriptor(),
		 * file.getStartOffset(), file.getLength()); file.close();
		 * mediaPlayer.prepare(); } catch (IOException e) { mediaPlayer = null;
		 * } }
		 */
	}

	private static final long VIBRATE_DURATION = 50L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}
	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 * 
	 * @ch beep need sound file, put it in assets folder when you want to use
	 *     this feature, we haven't implement it
	 */
	/*
	 * private final OnCompletionListener beepListener = new
	 * OnCompletionListener() { public void onCompletion(MediaPlayer
	 * mediaPlayer) { mediaPlayer.seekTo(0); } };
	 */
}