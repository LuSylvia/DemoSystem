package com.example.demosystem.View.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.demosystem.R;
import com.example.demosystem.bean.ReportBean;
import com.example.demosystem.bean.TestedPersonInfoBean;
import com.example.demosystem.databinding.FragmentDetailStatementBinding;
import com.example.demosystem.View.search.SharedViewModel;
import com.example.demosystem.helper.RetrofitManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

//不要使用该类！
@Deprecated
public class DetailStatementFragment extends Fragment {
    SharedViewModel sharedViewModel;
    FragmentDetailStatementBinding binding;
    Observable<ReportBean> reportBeanObservable;
    Observable<TestedPersonInfoBean> userInfoBeanObservable;

    public DetailStatementFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailStatementBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel =new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        int a=1;
        reportBeanObservable= RetrofitManager.getApiService().getReportByR_ID2(a);
        reportBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReportBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ReportBean reportBean) {
                        setReportData(reportBean);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("ERR_报表显示异常","异常是"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        userInfoBeanObservable=RetrofitManager.getApiService().FindTestedPersonByT_ID(a);
        userInfoBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TestedPersonInfoBean>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull TestedPersonInfoBean testedPersonInfoBean) {
                        setUserInfoData(testedPersonInfoBean);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("ERR_报表显示异常","异常是"+e.getMessage());
                        Toast.makeText(getContext(),"网络异常！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        binding.button.setOnClickListener(v->{
            NavController controller=Navigation.findNavController(v);
            controller.navigate(R.id.action_detailStatementFragment2_to_commentsFragment);
        });



    }





    void setUserInfoData(TestedPersonInfoBean item){
        //基础信息
//        binding.tvName2.setText(item.getName());
//        binding.tvGender2.setText(item.getGender());
//        binding.tvPhone2.setText(item.getPhone_Number());
//        binding.tvAge2.setText(String.valueOf(item.getAge()));
//        binding.tvSite2.setText(item.getSite());
    }


    void setReportData(ReportBean item) {
        //报表参数
//        binding.tvPeriod2.setText(String.valueOf(item.getPeriod()));
//        binding.tvStride2.setText(String.valueOf(item.getStride()));
//        binding.tvStepLength2.setText(String.valueOf(item.getStep_length()));
//        binding.tvStepWidth2.setText(String.valueOf(item.getStride()));
//        binding.tvStrideFrequency2.setText(String.valueOf(item.getStride_frequency()));
//        binding.tvPace2.setText(String.valueOf(item.getPace()));
//        binding.tvBarycenterLR2.setText(item.getBarycenter_LR());
//        binding.tvBarycenterUD2.setText(item.getBarycenter_UD());
//        binding.tvTID2.setText(String.valueOf(item.getT_ID()));
//        binding.tvCreateTime2.setText(item.getCreate_time());
    }

    //用于绘制四张图片
    void setImage(ReportBean item){

    }


    @Override
    public void onResume() {
        super.onResume();
        monitor();
    }

    private void monitor() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK&& sharedViewModel.getJumpVal()) {
                // 监听到返回按钮点击事件,且该页面是从报表list页面跳转而来
                NavController controller= Navigation.findNavController(v);
                controller.navigate(R.id.action_detailStatementFragment2_to_statementListFragment);
                return true;
            }else if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK&& !sharedViewModel.getJumpVal()){
                //否则，说明从主页跳转而来
                NavController controller=Navigation.findNavController(v);
                controller.navigate(R.id.action_detailStatementFragment_to_navigation_home);
                return true;
            }

            return false;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
        //reportBeanObservable.unsubscribeOn(Schedulers.io());
        //userInfoBeanObservable.unsubscribeOn(Schedulers.io());
    }

}
