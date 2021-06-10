package com.example.demosystem.View.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.demosystem.R;
import com.example.demosystem.ShowReportActivity;
import com.example.demosystem.adapter.ViewPagerAdapter;
import com.example.demosystem.bean.ReportBean;
import com.example.demosystem.databinding.FragmentHomeBinding;
import com.example.demosystem.View.ui.reportinfo.DetailReportViewModel;
import com.example.demosystem.View.search.SharedViewModel;
import com.example.demosystem.helper.RetrofitManager;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment {
    private ViewPager2 viewPager2;
    private int lastPosition;                           //记录轮播图最后所在的位置
    private List<Integer> pictures = new ArrayList<>();   //轮播图的颜色
    private LinearLayout indicatorContainer;            //填充指示点的容器
    private Handler mHandler = new Handler();


    private FragmentHomeBinding binding;
    private DetailReportViewModel detailReportViewModel;
    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        detailReportViewModel =new ViewModelProvider(requireActivity()).get(DetailReportViewModel.class);
        sharedViewModel =new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //初始化组件
        viewPager2 =binding.viewpager2;
        indicatorContainer = binding.containerIndicator;

        InitPictures();
        //初始化指示点
        initIndicatorDots();

        //添加适配器
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(pictures);
        viewPager2.setAdapter(viewPagerAdapter);
        //设置轮播图初始位置在500,以保证可以手动前翻
        viewPager2.setCurrentItem(500);
        //最后所在的位置设置为500
        lastPosition = 500;

        //注册轮播图的滚动事件监听器
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //轮播时，改变指示点
                int current = position % 4;
                int last = lastPosition % 4;
                indicatorContainer.getChildAt(current).setBackgroundResource(R.drawable.point_selected);
                indicatorContainer.getChildAt(last).setBackgroundResource(R.drawable.point_notselected);
                lastPosition = position;
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvHomeLatest.setOnClickListener(v->{
            //查找最新报表
            getLatestReportRID();
        });

        binding.tvHomeFind.setOnClickListener(v->{
            NavController controller= Navigation.findNavController(v);
            controller.navigate(R.id.action_navigation_home_to_navigation_dashboard);
        });

    }
    private void InitPictures(){
        pictures.add(R.drawable.index1);
        pictures.add(R.drawable.index2);
        pictures.add(R.drawable.index3);
        pictures.add(R.drawable.index4);
    }

    /**
     * 初始化指示点
     */
    private void initIndicatorDots(){
        for(int i = 0; i < pictures.size(); i++){
            ImageView imageView = new ImageView(getContext());
            if (i == 0) imageView.setBackgroundResource(R.drawable.point_selected);
            else imageView.setBackgroundResource(R.drawable.point_notselected);
            //为指示点添加间距
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMarginEnd(4);
            imageView.setLayoutParams(layoutParams);
            //将指示点添加进容器
            indicatorContainer.addView(imageView);
        }
    }

    /* 当应用被唤醒时，让轮播图开始轮播 */
    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(runnable,5000);
    }

    /* 当应用被暂停时，让轮播图停止轮播 */
    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //获得轮播图当前的位置
            int currentPosition = viewPager2.getCurrentItem();
            currentPosition++;
            viewPager2.setCurrentItem(currentPosition,true);
            mHandler.postDelayed(runnable,5000);
        }
    };



    private void getLatestReportRID(){
        //最新报表的RID
        Observable<List<String>> observable=RetrofitManager.getApiService().SelectPeportWithNoComment();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<String> strings) {
                        if(strings.size()>0){
                            sharedViewModel.setNoNewReport(false);
                            sharedViewModel.setR_id(Integer.parseInt(strings.get(strings.size()-1)));
                        }else{
                            sharedViewModel.setNoNewReport(true);
                        }
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("ERR_home_异常",e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        getReport();
                    }
                });
    }

    //根据最新的Rid来获取报表数据
    private void getReport(){
        //代表不存在新的步态报表，提示错误消息
        if(sharedViewModel.getFlag()){
            Toast.makeText(getContext(),"不存在最新未评论报表！",Toast.LENGTH_SHORT).show();
        }else{
            Observable<ReportBean> reportBeanObservable= RetrofitManager.getApiService().getReportByR_ID2(sharedViewModel.getR_id());
            reportBeanObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ReportBean>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        }
                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull ReportBean reportBean) {
                            if(reportBean==null){
                                Toast.makeText(getContext(),"数据异常！",Toast.LENGTH_SHORT).show();
                            }else{
                                int rid=reportBean.getR_ID();
                                int tid=reportBean.getT_ID();
                                Intent intent=new Intent(getContext(), ShowReportActivity.class);
                                intent.putExtra("R_ID",rid);
                                intent.putExtra("T_ID",tid);
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            Toast.makeText(getContext(),"网络异常！",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}