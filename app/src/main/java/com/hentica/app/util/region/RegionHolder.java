package com.hentica.app.util.region;

/**
 * Created by YangChen on 2016/10/28 10:49.
 * E-mail:656762935@qq.com
 */

public class RegionHolder {
        /** 省 */
        private Region mProRegion;
        /** 市 */
        private Region mCityRegion;
        /** 区 */
        private Region mCountyRegion;

        public Region getProRegion() {
            return mProRegion;
        }

        public void setProRegion(Region mProRegion) {
            this.mProRegion = mProRegion;
        }

        public Region getCityRegion() {
            return mCityRegion;
        }

        public void setCityRegion(Region mCityRegion) {
            this.mCityRegion = mCityRegion;
        }

        public Region getCountyRegion() {
            return mCountyRegion;
        }

        public void setCountyRegion(Region mDisRegion) {
            this.mCountyRegion = mDisRegion;
        }

        /** 获得省id */
        public String getProId(){
            return mProRegion != null ? mProRegion.getId() : "";
        }

        /** 获得省名字 */
        public String getProName()  {
            return mProRegion != null ? mProRegion.getName() : "";
        }

        /** 获得市id */
        public String getCityId(){
            return mCityRegion != null ? mCityRegion.getId() : "";
        }

        /** 获得市名字 */
        public String getCityName(){
            return mCityRegion != null ? mCityRegion.getName() : "";
        }

        /** 获得区id */
        public String getCountyId(){
            return mCountyRegion != null ? mCountyRegion.getId() : "";
        }

        /** 获得区名字 */
        public String getCountyName(){
            return mCountyRegion != null ? mCountyRegion.getName() : "";
        }
}
