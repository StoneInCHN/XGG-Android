package com.hentica.app.framework.activity;

import android.os.Bundle;

import com.hentica.app.module.home.ui.WelcomeFragment;


/** 根界面 */
public class WelcomeActivity extends FrameActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setFragment(new WelcomeFragment());
	}

}
