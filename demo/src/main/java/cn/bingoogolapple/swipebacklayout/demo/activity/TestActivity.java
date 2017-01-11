package cn.bingoogolapple.swipebacklayout.demo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import cn.bingoogolapple.swipebacklayout.demo.R;
import cn.bingoogolapple.swipebacklayout.demo.fragment.ContentFragment;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/27 下午5:35
 * 描述:测试滑动返回布局的接口
 * <p>
 * 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
 */
public class TestActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SwitchCompat mSwipeBackEnableSwitch;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
        mToolbar = getViewById(R.id.toolbar);
        mViewPager = getViewById(R.id.viewPager);
        mSwipeBackEnableSwitch = getViewById(R.id.swipeBackEnableSwitch);
    }

    @Override
    protected void setListener() {
        testSwipeBackLayout();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initToolbar();
        setUpTabLayoutAndViewPager();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("测试滑动返回布局的接口");
    }

    private void setUpTabLayoutAndViewPager() {
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                changeTopBgColor(position);
            }
        });
        mViewPager.setAdapter(new ContentPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 测试只有ViewPager在第0页时才开启滑动返回
                mSwipeBackHelper.setSwipeBackEnable(position == 0);
                mSwipeBackEnableSwitch.setChecked(position == 0);
            }
        });

        mTabLayout = getViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        changeTopBgColor(mViewPager.getCurrentItem());
    }

    /**
     * 测试滑动返回相关接口
     */
    private void testSwipeBackLayout() {
        // 测试动态设置滑动返回是否可用
        mSwipeBackEnableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean swipeBackEnable) {
                /**
                 * 设置滑动返回是否可用
                 */
                mSwipeBackHelper.setSwipeBackEnable(swipeBackEnable);
            }
        });

        // 测试动态设置是否仅仅跟踪左侧边缘的滑动返回
        ((SwitchCompat) getViewById(R.id.onlyTrackingLeftEdgeSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isOnlyTrackingLeftEdge) {
                /**
                 * 设置是否仅仅跟踪左侧边缘的滑动返回
                 */
                mSwipeBackHelper.setIsOnlyTrackingLeftEdge(isOnlyTrackingLeftEdge);
            }
        });

        // 测试动态设置是否是微信滑动返回样式
        ((SwitchCompat) getViewById(R.id.weChatStyleSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isWeChatStyle) {
                /**
                 * 设置是否是微信滑动返回样式
                 */
                mSwipeBackHelper.setIsWeChatStyle(isWeChatStyle);
            }
        });

        // 测试动态设置是否显示滑动返回的阴影效果
        ((SwitchCompat) getViewById(R.id.needShowShadowSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isNeedShowShadow) {
                /**
                 * 设置是否显示滑动返回的阴影效果
                 */
                mSwipeBackHelper.setIsNeedShowShadow(isNeedShowShadow);
            }
        });

        // 测试动态设置阴影区域的透明度是否根据滑动的距离渐变
        ((SwitchCompat) getViewById(R.id.shadowAlphaGradientSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isShadowAlphaGradient) {
                /**
                 * 设置阴影区域的透明度是否根据滑动的距离渐变
                 */
                mSwipeBackHelper.setIsShadowAlphaGradient(isShadowAlphaGradient);
            }
        });
    }

    public void testTranslucent(View v) {
        mSwipeBackHelper.forward(TranslucentActivity.class);

//        mSwipeBackHelper.forwardAndFinish(TranslucentActivity.class);
    }

    public void testPullRefreshAndWebView(View v) {
        mSwipeBackHelper.forward(WebViewActivity.class);
    }

    public void testSwipeDelete(View v) {
        mSwipeBackHelper.forward(SwipeDeleteActivity.class);
    }

    public void testRecyclerView(View v) {
        mSwipeBackHelper.forward(RecyclerIndexActivity.class);
    }

    /**
     * 测试 ViewPager 的适配器
     */
    private class ContentPagerAdapter extends FragmentPagerAdapter {
        private ContentFragment mOneFragment;
        private ContentFragment mTwoFragment;
        private ContentFragment mThreeFragment;

        public ContentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (mOneFragment == null) {
                    mOneFragment = ContentFragment.newInstance(position);
                }
                return mOneFragment;
            } else if (position == 1) {
                if (mTwoFragment == null) {
                    mTwoFragment = ContentFragment.newInstance(position);
                }
                return mTwoFragment;
            } else {
                if (mThreeFragment == null) {
                    mThreeFragment = ContentFragment.newInstance(position);
                }
                return mThreeFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "第" + (position + 1) + "页";
        }
    }

    /**
     * 根据Palette提取的颜色，修改tab和toolbar以及状态栏的颜色
     */
    private void changeTopBgColor(int position) {
        int imageResource = R.mipmap.one;
        if (position == 1) {
            imageResource = R.mipmap.two;
        } else if (position == 2) {
            imageResource = R.mipmap.three;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageResource);
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                mTabLayout.setBackgroundColor(vibrant.getRgb());
                mTabLayout.setSelectedTabIndicatorColor(colorBurn(vibrant.getRgb()));
                mTabLayout.setSelectedTabIndicatorColor(colorBurn(vibrant.getRgb()));
                mToolbar.setBackgroundColor(vibrant.getRgb());

                setStatusBarColor(colorBurn(vibrant.getRgb()));
            }
        });
    }

    /**
     * 颜色加深处理
     */
    private int colorBurn(int RGBValues) {
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }
}
