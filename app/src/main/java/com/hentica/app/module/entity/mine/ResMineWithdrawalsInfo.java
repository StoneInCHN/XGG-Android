package com.hentica.app.module.entity.mine;

/**
 * 提现信息返回数据
 * Created by Snow on 2017/5/3.
 */

public class ResMineWithdrawalsInfo {


    /**
     * transactionFee : 1.5 //提现手续费
     * agentLeScore : 1158.1983
     * minLimitAmount : 100
     * incomeLeScore : 2562.57
     * motivateRule : 统一提现规则
     * motivateLeScore : 1.0665
     */
//提现手续费
    private String transactionFee;
    //代理商乐分
    private double agentLeScore;
    //满配置金额才能提现
    private String minLimitAmount;
    //业务员乐分
    private double incomeLeScore;
    //统一提现规则
    private String motivateRule;
    //会员乐分
    private double motivateLeScore;

    public String getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(String transactionFee) {
        this.transactionFee = transactionFee;
    }

    public double getAgentLeScore() {
        return agentLeScore;
    }

    public void setAgentLeScore(double agentLeScore) {
        this.agentLeScore = agentLeScore;
    }

    public String getMinLimitAmount() {
        return minLimitAmount;
    }

    public void setMinLimitAmount(String minLimitAmount) {
        this.minLimitAmount = minLimitAmount;
    }

    public double getIncomeLeScore() {
        return incomeLeScore;
    }

    public void setIncomeLeScore(double incomeLeScore) {
        this.incomeLeScore = incomeLeScore;
    }

    public String getMotivateRule() {
        return motivateRule;
    }

    public void setMotivateRule(String motivateRule) {
        this.motivateRule = motivateRule;
    }

    public double getMotivateLeScore() {
        return motivateLeScore;
    }

    public void setMotivateLeScore(double motivateLeScore) {
        this.motivateLeScore = motivateLeScore;
    }
}
