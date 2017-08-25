package com.hentica.app.widget;

import android.text.TextUtils;

import com.hentica.app.lib.util.TextGetter;
import com.hentica.app.widget.view.CharacterHeadHelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 字母表头辅助工具（自带拼音，不需要解析）<br />
 *
 * 用法:<br />
 *
 * 1.绑定视图:<br />
 * helper.setViews(listView, sideBar, tipText); <br />
 * <br />
 *
 * 2.设置数据转换规则，用于取拼音和排序<br />
 * helper.setTextGetter(xxx);<br />
 * <br />
 *
 * 3.设置原始数据，并取得排序后的数据 <br />
 * helper.setPeccancyDatas(mShowDatas); <br />
 * mShowDatas = helper.getSortedDatas(); <br />
 * <br />
 *
 * 4.取数据<br />
 * 在getView()中调用 helper.refreshLetterView(position, letterView);
 *
 * @author yaChen
 * @createTime 2017-4-5 上午10:04:05
 */

public class CharacterHeadHelper2<T> extends CharacterHeadHelper<T> {

    /** 默认字符串转换规则 */
    private LetterGetter<T> mDefaultLetterGetter = new LetterGetter<T>() {

        @Override
        public char getLetter(T obj) {
            return '#';
        }

        @Override
        public String getPinyin(T obj) {
            return "#";
        }
    };

    /** 字符串转换规则 */
    private LetterGetter<T> mLetterGetter = mDefaultLetterGetter;

    public interface LetterGetter<T>{
        /** 获取拼音首字母 */
        char getLetter(T obj);
        /** 获取拼音 */
        String getPinyin(T obj);
    }

    /** 获取排序后的数据 */
    @Override
    public List<T> getSortedDatas() {
        this.createSortData(mDatas, mLetterGetter);
        return mSortedDatas;
    }

    /** 创建排序后的数据 */
    protected void createSortData(List<T> datas, LetterGetter<T> letterGetter) {
        mSortedDatas.clear();
        if (datas != null) {

            mSortedDatas.addAll(datas);
        }
        if (letterGetter != null) {

            Collections.sort(mSortedDatas, new PinyinComparator<T>(letterGetter));
        }
    }

    /** 获取某数据首字母 */
    @Override
    protected char getLetter(T data) {
        if (mLetterGetter != null) {

            String pinyin = mLetterGetter.getPinyin(data);
            Character cacheChar = mCharCacheMap.get(pinyin);

            // 若未缓存过
            if (cacheChar == null) {
                if (!TextUtils.isEmpty(pinyin)) {

                    char letter = pinyin.substring(0, 1).toUpperCase().charAt(0);
                    mCharCacheMap.put(pinyin, letter);
                    return letter;
                }
            }
            // 若有缓存
            else {
                return cacheChar;
            }
        }

        return '#';
    }

    /** 某字母是否首次出现的问题 */
    @Override
    public boolean isFirstLetter(int pos) {
        if (pos >= 0 && pos < mSortedDatas.size()) {

            T data = mSortedDatas.get(pos);
            int section = getLetterSection(mLetterGetter.getLetter(data));

            return section == pos;
        }

        return false;
    }

    /** 比较 */
    private static class PinyinComparator<V> implements Comparator<V> {

        private LetterGetter<V> mLetterGetter;

        /** 构造函数 */
        public PinyinComparator(LetterGetter<V> letterGetter) {
            mLetterGetter = letterGetter;
        }

        @Override
        public int compare(V o1, V o2) {

            String pinyin1 = mLetterGetter.getPinyin(o1);
            String pinyin2 = mLetterGetter.getPinyin(o2);

            return pinyin1.compareTo(pinyin2);
        }
    }


    public void setLetterGetter(LetterGetter<T> letterGetter) {
        mLetterGetter = letterGetter;
    }

    public LetterGetter<T> getLetterGetter() {
        return mLetterGetter;
    }

    public TextGetter<T> getTextGetter(){
        return mTextGetter;
    }
}
