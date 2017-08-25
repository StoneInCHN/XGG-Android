package com.hentica.app.module.down;

import com.hentica.app.lib.util.NetHelper;

/**
 * Created by Snow on 2017/5/8.
 */

public class NetHelperDown implements DownloadManager{
    @Override
    public void download(String url, String savePath, NetHelper.DownLoadListener l) {
        NetHelper.downLoadToFile(url, savePath, l);
    }
}
