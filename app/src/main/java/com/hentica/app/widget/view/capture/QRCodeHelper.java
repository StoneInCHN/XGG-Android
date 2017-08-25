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

import android.support.v4.app.FragmentTransaction;

import com.hentica.app.framework.activity.UsualActivity;

public class QRCodeHelper {

	public static void getQRCode(UsualActivity activity, int layout, CaptureQRCodeListener listener) {

		FragmentTransaction tr = activity.getUsualFragmentManager().beginTransaction();
		tr.add(layout, new CaptureFragment(listener), "扫描框");
		tr.commit();
	}

	public interface CaptureQRCodeListener {
		public void onResult(String result);
	}
}