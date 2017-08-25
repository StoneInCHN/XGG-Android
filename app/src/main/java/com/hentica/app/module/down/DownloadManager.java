package com.hentica.app.module.down;

import com.hentica.app.lib.util.NetHelper;

/**
 * Created by Snow on 2017/5/8.
 */

public interface DownloadManager {

    void download(String url, String savePath, NetHelper.DownLoadListener l);

}
