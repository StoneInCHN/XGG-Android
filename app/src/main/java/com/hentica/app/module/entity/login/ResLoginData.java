package com.hentica.app.module.entity.login;

/**
 * 登录返回数据
 * 邮箱：396926020@qq.com
 * Created by 钟科 on 2017/3/30.17:27
 */

public class ResLoginData {

    /**
     * area : {"name":"北京市","id":1}
     * totalLeBean : 0
     * curLeMind : 0
     * agent : {"agencyLevel":"CITY"}
     * isBindWeChat : false
     * userPhoto : null
     * sellerStatus : YES
     * curScore : 0
     * totalLeScore : 0
     * nickName : Andrea
     * curLeBean : 0
     * totalScore : 0
     * cellPhoneNum : 15902823856
     * isSetPayPwd : true
     * id : 1
     * isSetLoginPwd : true
     * recommender : null
     * totalLeMind : 0
     * curLeScore : 0
     * sellerStatus=NO表示用户无店铺,且无申请记录；
     * sellerStatus=YES表示用户申请已经通过，拥有店铺；
     * sellerStatus=AUDIT_WAITING表示用户申请店铺请求正在审核中；
     * sellerStatus=AUDIT_FAILED表示用户申请店铺请求审核失败，此时返回字段会增加一个applyId，
     * 该字段需要在手机端记录，同一用户再次提交店铺入驻申请记录时需传此ID到接口
     */


    private AreaBean area;
    private AgentBean agent;
    private boolean isBindWeChat;
    private String userPhoto;
    private String sellerStatus;
    private String nickName;
    private String cellPhoneNum;
    private String applyId;
    private boolean isSetPayPwd;
    private long id;
    private boolean isSetLoginPwd;
    private String recommender;

    private double totalScore;//总积分
    private double curScore;//当前积分

    private double totalLeBean;//累计乐豆
    private double curLeBean;

    private int totalLeMind;//累计乐心
    private int curLeMind;

    private double totalLeScore;//累计乐分
    private double curLeScore;

    private String wechatNickName;

    private boolean isAuth;

    private boolean isOwnBankCard;//是否有银行卡
    private boolean isSalesmanApply;//null没有权限
    private boolean isPushMsg;//极光推送开关

    public boolean getIsSalesmanApply() {
        return isSalesmanApply;
    }

    public void setIsSalesmanApply(boolean isSalesmanApply) {
        this.isSalesmanApply = isSalesmanApply;
    }

    /**
     * totalLeBean : 0
     * userPhoto : null
     * curScore : 0
     * totalLeScore : 0
     * curLeBean : 0
     * totalScore : 0
     * isSalesman : false
     * recommender : null
     * curLeScore : 0
     */

    private boolean isSalesman;

    private String failReason;
    private String salesmanCellphone;

    public boolean isOwnBankCard() {
        return isOwnBankCard;
    }

    public void setOwnBankCard(boolean ownBankCard) {
        isOwnBankCard = ownBankCard;
    }

    public String getSalesmanCellphone() {
        return salesmanCellphone;
    }

    public void setSalesmanCellphone(String salesmanCellphone) {
        this.salesmanCellphone = salesmanCellphone;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public AreaBean getArea() {
        return area;
    }

    public void setArea(AreaBean area) {
        this.area = area;
    }

    public double getTotalLeBean() {
        return totalLeBean;
    }

    public void setTotalLeBean(int totalLeBean) {
        this.totalLeBean = totalLeBean;
    }

    public int getCurLeMind() {
        return curLeMind;
    }

    public void setCurLeMind(int curLeMind) {
        this.curLeMind = curLeMind;
    }

    public AgentBean getAgent() {
        return agent;
    }

    public void setAgent(AgentBean agent) {
        this.agent = agent;
    }

    public boolean isIsBindWeChat() {
        return isBindWeChat;
    }

    public void setIsBindWeChat(boolean isBindWeChat) {
        this.isBindWeChat = isBindWeChat;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(String sellerStatus) {
        this.sellerStatus = sellerStatus;
    }

    public double getCurScore() {
        return curScore;
    }

    public void setCurScore(double curScore) {
        this.curScore = curScore;
    }

    public double getTotalLeScore() {
        return totalLeScore;
    }

    public void setTotalLeScore(double totalLeScore) {
        this.totalLeScore = totalLeScore;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getCurLeBean() {
        return curLeBean;
    }

    public void setCurLeBean(double curLeBean) {
        this.curLeBean = curLeBean;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public String getCellPhoneNum() {
        return cellPhoneNum;
    }

    public void setCellPhoneNum(String cellPhoneNum) {
        this.cellPhoneNum = cellPhoneNum;
    }

    public boolean isIsSetPayPwd() {
        return isSetPayPwd;
    }

    public void setIsSetPayPwd(boolean isSetPayPwd) {
        this.isSetPayPwd = isSetPayPwd;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsSetLoginPwd() {
        return isSetLoginPwd;
    }

    public void setIsSetLoginPwd(boolean isSetLoginPwd) {
        this.isSetLoginPwd = isSetLoginPwd;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public int getTotalLeMind() {
        return totalLeMind;
    }

    public void setTotalLeMind(int totalLeMind) {
        this.totalLeMind = totalLeMind;
    }

    public double getCurLeScore() {
        return curLeScore;
    }

    public void setCurLeScore(double curLeScore) {
        this.curLeScore = curLeScore;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getWechatNickName() {
        return wechatNickName;
    }

    public void setWechatNickName(String wechatNickName) {
        this.wechatNickName = wechatNickName;
    }

    public boolean isIsSalesman() {
        return isSalesman;
    }

    public void setIsSalesman(boolean isSalesman) {
        this.isSalesman = isSalesman;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public boolean isPushMsg() {
        return isPushMsg;
    }

    public void setPushMsg(boolean pushMsg) {
        isPushMsg = pushMsg;
    }

    public static class AreaBean {
        /**
         * name : 北京市
         * id : 1
         */

        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class AgentBean {
        /**
         * agencyLevel : CITY
         */
        private String agencyLevel;

        private String areaName;

        private int areaId;

        public String getAgencyLevel() {
            return agencyLevel;
        }

        public void setAgencyLevel(String agencyLevel) {
            this.agencyLevel = agencyLevel;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String mAreaName) {
            areaName = mAreaName;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int mAreaId) {
            areaId = mAreaId;
        }
    }
}
