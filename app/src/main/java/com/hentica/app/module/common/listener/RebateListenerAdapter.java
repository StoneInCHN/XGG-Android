package com.hentica.app.module.common.listener;

import com.hentica.app.lib.json.JNode;
import com.hentica.app.lib.net.NetData;
import com.hentica.app.util.ParseUtil;

import java.util.List;

/**
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/4/10.11:08
 */

public abstract class RebateListenerAdapter<T> extends ListenerAdapter<T> {

    private RebateDataBackListener l;

    /**
     * 构造函数
     *
     * @param listener 输出
     */
    public RebateListenerAdapter(RebateDataBackListener listener) {
        super(listener);
        this.l = listener;
    }

    /**
     * 创建一个对象回调适配器，自动把网络数据转换为指定对象
     *
     * @param objClass 对象类型
     * @param listener 对象回调
     * @return 适配器
     */
    public static <T> RebateListenerAdapter<T> createObjectListener(final Class<T> objClass, RebateDataBackListener<T> listener) {

        return new RebateListenerAdapter<T>(listener) {
            @Override
            protected T parse(String json) {
                return ParseUtil.parseObject(json, objClass);
            }
        };
    }

    /**
     * 创建一个列表回调适配器，自动把网络数据转换为指定对象
     *
     * @param objClass 对象类型
     * @param listener 列表回调
     * @return 适配器
     */
    public static <T> RebateListenerAdapter<List<T>> createArrayListener(final Class<T> objClass, RebateDataBackListener<List<T>> listener) {

        return new RebateListenerAdapter<List<T>>(listener) {
            @Override
            protected List<T> parse(String json) {
                return ParseUtil.parseArray(json, objClass);
            }
        };
    }

    @Override
    public void onResult(NetData result) {
        super.onResult(result);
        if (result.isSuccess()) {
            String resultString = result.getResult();
            if (resultString != null) {
                JNode resultNode = new JNode(resultString);
                String page = resultNode.getChild("page").getJsonString();
                extralData(page);
                setResult(result);
            }
        }
    }

    /**
     * 扩展数据
     */
    public void extralData(String extral) {
        if (l != null) {
            l.extralData(extral);
        }
    }

    /**
     * 扩展数据
     */
    public void setResult(NetData netData) {
        if (l != null) {
            l.setResult(netData);
        }
    }

}
