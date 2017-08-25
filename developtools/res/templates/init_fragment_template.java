package {$package};

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import {$appPackage}.R;
import com.hentica.app.widget.view.TitleView;
import com.hentica.app.module.common.base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {$classComment}
 *
 * @author {$user}
 * @createTime {$createTime}
 */
public class {$className} extends BaseFragment {

        @Override
        public int getLayoutId() {
            return R.layout.{$layoutName};
        }

        @Override
        protected TitleView initTitleView() {
            return getViews(R.id.common_title);
        }

        @Override
        protected void initData() {

        }

        @Override
        protected void initView() {

        }

        @Override
        protected void setEvent() {

        }

}
