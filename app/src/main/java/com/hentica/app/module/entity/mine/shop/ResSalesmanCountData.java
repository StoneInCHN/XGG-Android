package com.hentica.app.module.entity.mine.shop;

import java.util.List;

/**
 * Created by YangChen on 2017/7/7 15:00.
 * E-mail:656762935@qq.com
 */

public class ResSalesmanCountData {


    /**
     * id : 2484
     * name : 南明区
     * count : 5
     * list : [{"id":5,"userName":"1m1663273e20","nickName":"享个购徐刚","userPhoto":"/upload/profile/src_0192425c-ba10-4238-bc13-19bb367744c6.jpg","cellPhoneNum":"15339598777"},{"id":7,"userName":"f152640649i5","nickName":"享个购-熊正松。","userPhoto":"/upload/profile/src_3a911cd4-847b-461d-b568-445a3cc5ae8f.jpg","cellPhoneNum":"15597776668"},{"id":320,"userName":"18O2Q2415O61","nickName":"捕风的汉子","userPhoto":null,"cellPhoneNum":"18485408091"},{"id":652,"userName":"506i046ue117","nickName":"东哥哥","userPhoto":null,"cellPhoneNum":"13566218882"},{"id":1716,"userName":"156Y1Y8P2073","nickName":"假想敌","userPhoto":"/upload/profile/src_67151f47-5cf4-429e-9554-fec3b238eb8f.png","cellPhoneNum":"18623521311"}]
     */

    private int id;
    private String name;
    private int count;
    /**
     * id : 5
     * userName : 1m1663273e20
     * nickName : 享个购徐刚
     * userPhoto : /upload/profile/src_0192425c-ba10-4238-bc13-19bb367744c6.jpg
     * cellPhoneNum : 15339598777
     */

    private List<ListBean> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int id;
        private String userName;
        private String nickName;
        private String userPhoto;
        private String cellPhoneNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }

        public String getCellPhoneNum() {
            return cellPhoneNum;
        }

        public void setCellPhoneNum(String cellPhoneNum) {
            this.cellPhoneNum = cellPhoneNum;
        }
    }
}
