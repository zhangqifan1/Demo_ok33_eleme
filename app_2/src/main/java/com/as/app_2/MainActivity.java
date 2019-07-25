package com.as.app_2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.as.app_2.adapter.LeftAdapter;
import com.as.app_2.adapter.RightAdapter;
import com.as.app_2.bean.ContentBean;
import com.as.app_2.bean.RightBean;
import com.as.app_2.bean.TitleBean;
import com.as.app_2.bean.left.LeftBean;
import com.as.app_2.layoutmanager.ScrollTopGridManager;
import com.as.app_2.layoutmanager.ScrollTopLinerManager;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //默认是Linear
    private boolean flag = true;
    private RightAdapter rightAdapter;

    /**
     * 左侧是否点击选择
     */
    private boolean isLeftSelect;
    private List<RightBean> rightBeans;
    private List<LeftBean> leftBeans;
    private RecyclerView rv_left;
    private RecyclerView rv_right;
    private LeftAdapter leftAdapter;


    /**
     * 用来顶替悬浮条的
     */
    private ImageView image_title;
    private TextView tv_title;
    private View mRightFooterView;
    private LinearLayoutManager ll_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://github.com/KunMinX/Linkage-RecyclerView
        //https://github.com/PartingSoul/LinkageRecyclerviewDemo

        image_title = findViewById(R.id.image_title);
        tv_title = findViewById(R.id.tv_title);

        rv_left = findViewById(R.id.rv_left);
        rv_right = findViewById(R.id.rv_main);

        Button butChange = findViewById(R.id.butChange);
        butChange.setOnClickListener(this);

        initData();

        initLeftRv();

        initRightRv();

    }

    private void initRightRv() {
        mRightFooterView = LayoutInflater.from(this).inflate(R.layout.footer_empty, null);

        rightAdapter = new RightAdapter(rightBeans);

        /**
         * 使用这个Layoutmanager  让smooth 的position 到达顶部
         */
        ScrollTopLinerManager ll_main = new ScrollTopLinerManager(this, LinearLayoutManager.VERTICAL, false);
        rv_right.setLayoutManager(ll_main);


        rv_right.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isLeftSelect = false;
                    changeLeftListSelectState();

                    //让最后一条也可以悬浮头部 就是放空的 顶起来
                    LinearLayoutManager manager = (LinearLayoutManager) rv_right.getLayoutManager();
                    int lastCompleteVisiblePosition = manager.findLastCompletelyVisibleItemPosition();
                    if (lastCompleteVisiblePosition == rightBeans.size() - 1 && rightAdapter.getFooterLayoutCount() == 0) {
                        //已经滑动到最后一个完全显示的item并且之前没有添加过底部填充
                        View lastItemView = manager.findViewByPosition(findLastGroupTitlePositionInRightLists());
                        if (lastItemView == null) {
                            return;
                        }
                        //设置填充高度，使得最后一个分类标题能够置顶悬浮
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, lastItemView.getTop());
                        mRightFooterView.setLayoutParams(params);
                        rightAdapter.setFooterView(mRightFooterView);
                        rv_right.scrollBy(0, lastItemView.getTop());
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLeftSelect) {
                    changeLeftListSelectState();
                }
            }
        });
        rightAdapter.setLayoutType(RightAdapter.TYPE_linear);
        rv_right.setAdapter(rightAdapter);

    }

    private void initLeftRv() {
//
        ll_left = new LinearLayoutManager(this);
        rv_left.setLayoutManager(ll_left);

        rv_left.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ((SimpleItemAnimator) rv_left.getItemAnimator()).setSupportsChangeAnimations(false);

        leftAdapter = new LeftAdapter(R.layout.item_left_content, leftBeans);
        rv_left.setAdapter(leftAdapter);

        leftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isLeftSelect = true;
                leftAdapter.setSelectPosition(position);

                //选中左边分类，使得右边滚动到指定位置
                String lableName = leftBeans.get(position).getLableName();
                int resid = leftBeans.get(position).getResid();

                int rightPosition = findRightItemPositionBySortName(lableName);
                if (rightPosition != -1) {
                    rv_right.smoothScrollToPosition(rightPosition);
                    tv_title.setText(lableName);
                    image_title.setImageResource(resid);
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butChange:

                if (flag) {
                    rightAdapter.setLayoutType(RightAdapter.TYPE_Grid);
                } else {
                    rightAdapter.setLayoutType(RightAdapter.TYPE_linear);
                }
                flag = !flag;
                break;
            default:
                break;
        }
    }

    /**
     * 跟据分组名获取分组名在右侧的位置
     *
     * @param name
     * @return
     */
    private int findRightItemPositionBySortName(String name) {
        RightBean bean = null;
        for (int i = 0; i < rightBeans.size(); i++) {
            bean = rightBeans.get(i);
            if (bean.getItemType() == RightBean.TYPE_Title) {
                if (TextUtils.equals(name, bean.getGroupName())) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 改变左侧列表选中状态
     */
    private void changeLeftListSelectState() {

        LinearLayoutManager manager = (LinearLayoutManager) rv_right.getLayoutManager();
        int firstVisiblePosition = manager.findFirstVisibleItemPosition();
        RightBean bean = rightAdapter.getItem(firstVisiblePosition);
        if (bean != null) {
            int leftPosition = findLeftItemPositionBySortName(bean.getGroupName());
            leftAdapter.setSelectPosition(leftPosition);


            int size = leftBeans.size() - 1;

            //如果他在3个之内的可见  不处理

            rv_left.smoothScrollToPosition(leftPosition);

            if (isChangeGroup(firstVisiblePosition)) {
                tv_title.setText(bean.getGroupName());
            }

            int firstVisibleItemPosition = ll_left.findFirstVisibleItemPosition();
            int lastVisibleItemPosition = ll_left.findLastVisibleItemPosition();

            if (leftPosition != 0 && leftPosition != size) {
                if (leftPosition == firstVisibleItemPosition) {
                    rv_left.smoothScrollToPosition(leftPosition - 1);
                }
                if (leftPosition == lastVisibleItemPosition) {
                    rv_left.smoothScrollToPosition(leftPosition + 1);
                }
            }

//            ll_left.scrollToPositionWithOffset(leftPosition,0);

//            final TopSmoothScroller mScroller = new TopSmoothScroller(this);
//            mScroller.setTargetPosition(leftPosition);
//            ll_left.startSmoothScroll(mScroller);


        }
    }

    /**
     * 获取右侧列表最后一个分组白标题的位置
     *
     * @return
     */
    private int findLastGroupTitlePositionInRightLists() {
        RightBean bean = null;
        for (int i = rightBeans.size() - 1; i >= 0; i--) {
            bean = rightBeans.get(i);
            if (bean.getItemType() == RightBean.TYPE_Title) {
                return i;
            }
        }
        return -1;
    }

    private boolean isChangeGroup(int position) {
        LinearLayoutManager manager = (LinearLayoutManager) rv_right.getLayoutManager();
        RightBean bean = rightAdapter.getItem(position);
        boolean isChanged = false;
        int dataSize = rightBeans.size();

        //右侧列表往下滑动时，若第一个可见的是标题之前的item列表，表示可以改变分组名
        if (bean != null && bean.getItemType() == RightBean.TYPE_Content) {
            if (position + 1 < dataSize) {
                isChanged = isChanged || rightBeans.get(position + 1).getItemType() == RightBean.TYPE_Title;
            }
        } else if (bean != null && bean.getItemType() == RightBean.TYPE_Title) {
            //从下往上滑动，第一个可见的是标题
            isChanged = true;
        }

        return isChanged;
    }

    /**
     * 根据f分组名名获取左侧列表的位置
     *
     * @param name
     * @return
     */
    private int findLeftItemPositionBySortName(String name) {
        LeftBean bean = null;
        for (int i = 0; i < leftBeans.size(); i++) {
            bean = leftBeans.get(i);
            if (TextUtils.equals(name, bean.getLableName())) {
                return i;
            }
        }
        return -1;
    }

    private void initData() {

        // 这里可以用右边 title的数据 自定义的话 随便塞五条算了
        leftBeans = new ArrayList<>();

        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "葫芦娃"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "黑猫警长"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "小和尚"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "阿童木"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "奥特曼"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "小鲤鱼"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "小福贵"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "憨八龟"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "大风车"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "雨花剑"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "福五鼠"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "神兵小将"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "图图"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "小哪吒"));
        leftBeans.add(new LeftBean(false, R.drawable.ic_launcher_background, "龙凤双娃"));

        rightBeans = new ArrayList<>();

        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "葫芦娃"));
        List<ContentBean> l1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l1.add(new ContentBean("葫芦娃" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l1, "葫芦娃"));

        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "黑猫警长"));
        List<ContentBean> l2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l2.add(new ContentBean("黑猫警长" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l2, "黑猫警长"));


        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "小和尚"));
        List<ContentBean> l3 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l3.add(new ContentBean("小和尚" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l3, "小和尚"));

        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "小和尚"));
        List<ContentBean> l4 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l4.add(new ContentBean("小和尚" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l4, "小和尚"));

        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "阿童木"));
        List<ContentBean> l5 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l5.add(new ContentBean("阿童木" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l5, "阿童木"));


        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "奥特曼"));
        List<ContentBean> l6 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l6.add(new ContentBean("奥特曼" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l6, "奥特曼"));

        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "小鲤鱼"));
        List<ContentBean> l7 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l7.add(new ContentBean("小鲤鱼" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l7, "小鲤鱼"));

        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "小福贵"));
        List<ContentBean> l8 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l8.add(new ContentBean("小福贵" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l8, "小福贵"));


        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "憨八龟"));
        List<ContentBean> l9 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l9.add(new ContentBean("憨八龟" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l9, "憨八龟"));

        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "大风车"));
        List<ContentBean> l10 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l10.add(new ContentBean("大风车" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l10, "大风车"));


        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "雨花剑"));
        List<ContentBean> l11 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l11.add(new ContentBean("雨花剑" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l11, "雨花剑"));

        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "福五鼠"));
        List<ContentBean> l12 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l12.add(new ContentBean("福五鼠" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l12, "福五鼠"));


        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "神兵小将"));
        List<ContentBean> l13 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l13.add(new ContentBean("神兵小将" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l13, "神兵小将"));


        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "图图"));
        List<ContentBean> l14 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l14.add(new ContentBean("图图" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l14, "图图"));


        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "小哪吒"));
        List<ContentBean> l15 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            l15.add(new ContentBean("小哪吒" + i, "Description" + i, R.drawable.ic_launcher_background));
        }
        rightBeans.add(new RightBean(RightBean.TYPE_Content, l15, "小哪吒"));


        rightBeans.add(new RightBean(RightBean.TYPE_Title, new TitleBean(R.drawable.ic_launcher_background), "龙凤双娃"));
//        for (int i = 0; i < 2; i++) {
//            rightBeans.add(new RightBean(RightBean.TYPE_Content, new ContentBean("龙凤双娃" + i, "Description" + i, R.drawable.ic_launcher_background), "龙凤双娃"));
//        }
    }

}
