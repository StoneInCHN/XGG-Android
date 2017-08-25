package com.hentica.app.widget.dialog;


import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;

import com.androidquery.AQuery;
import com.fiveixlg.app.customer.R;

public class SelfAlertDialog2 extends SelfAlertDialog {

	@Override
	protected void initView() {
		super.initView();
		AQuery query = new AQuery(getView());
		query.id(R.id.dialog_alert_title_img_close).visibility(View.INVISIBLE);
	}

	@Override
	protected void setEvent() {
		super.setEvent();
		getDialog().setCanceledOnTouchOutside(false);
		getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return false;
			}
		});
	}
}
