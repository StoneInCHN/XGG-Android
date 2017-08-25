package com.hentica.app.util.event;

/**
 * 数据层事件
 *
 * @author mili
 * @createTime 2016-5-30 下午12:28:05
 */
public class DataEvent {

    /**
     * 登录成功了
     */
    public static class OnLoginEvent {

        /**
         * 是否切换账号了
         */
        private boolean mIsSwitchAccount;

        /**
         * 是否切换账号了
         */
        public boolean isSwitchAccount() {
            return mIsSwitchAccount;
        }

        /**
         * 是否切换账号了
         */
        public void setIsSwitchAccount(boolean isSwitchAccount) {
            mIsSwitchAccount = isSwitchAccount;
        }

        /**
         * 构造函数
         *
         * @param isSwitchAccount 是否切换了账号
         */
        public OnLoginEvent(boolean isSwitchAccount) {
            mIsSwitchAccount = isSwitchAccount;
        }
    }

    /**
     * 登录失效了
     */
    public static class OnLoginInvaildEvent {

        /**
         * true用户主动退出的，false服务器掉线
         */
        private boolean mIsLogoutByUser;

        /**
         * true用户主动退出的，false服务器掉线
         */
        public boolean isIsLogoutByUser() {
            return mIsLogoutByUser;
        }

        /**  */
        public void setIsLogoutByUser(boolean isLogoutByUser) {
            mIsLogoutByUser = isLogoutByUser;
        }

        /**
         * 构造函数
         *
         * @param isLogoutByUser true用户主动退出的，false服务器掉线
         */
        public OnLoginInvaildEvent(boolean isLogoutByUser) {
            mIsLogoutByUser = isLogoutByUser;
        }
    }

    /**
     * 微信支付事件
     */
    public static class OnWXPayRespEvent{
        private int mErrCode;

        public OnWXPayRespEvent(int mErrCode) {
            this.mErrCode = mErrCode;
        }

        public int getmErrCode() {
            return mErrCode;
        }

        public void setmErrCode(int mErrCode) {
            this.mErrCode = mErrCode;
        }
    }

    /**
     * 需更新用户资料事件
     */
    public static class OnToUpdataUserProfileEvent{

    }

    /**
     * 店铺资料更新
     */
    public static class OnUpdateShopProfileEvent{

    }

    /**
     * 收到推送消息
     */
    public static class OnReceivedMessageEvent {
    }


    /** 数据库更新事件 */
    public static class OnDBUploadSuccess{

    }

    /** 选择城市事件 */
    public static class OnSelectedCityEvent{

    }

    /** 收藏成功事件 */
    public static class OnCollectedEvent{

    }

    /** 评价成功 */
    public static class OnEvaluatedEvent{

    }

    /** 商家回复评价成功 */
    public static class OnShopReplySuccessEvent{

    }

    /** 支付成功 */
    public static class OnPaySuccess{

    }

    /** 录单支付成功 */
    public static class OnCloseRecordOrderFragmentEvent {

    }

    /**
     * 添加银行卡成功
     */
    public static class OnBankCardAddSuccess{

    }

}
