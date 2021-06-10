package com.example.demosystem.View.userSetting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demosystem.R;
import com.example.demosystem.bean.ProfileBean2;
import com.example.demosystem.databinding.FragmentProfileBinding;
import com.example.demosystem.helper.RetrofitManager;
import com.example.demosystem.helper.UserHelper;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        profileViewModel =new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        //从网络中获取数据存储到profileViewModel里
        profileViewModel.getProfile();
        return view;
    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO:use the viewModel
        //livedata+viewmodel组合的基本用法
        //看不懂请去看Android官方开发文档
        //https://developer.android.google.cn/jetpack/guide?hl=zh_cn
        profileViewModel.getProfileLiveData().observe(getViewLifecycleOwner(), profileBean2 -> setData(profileBean2));

    }


    private  void setData(ProfileBean2 bean){
        Log.d("ERR-setData2","在填充数据");
        binding.tvNameproR.setText(bean.getName());
        binding.tvGenderproR.setText(bean.getGender());
        String age=String.valueOf(bean.getAge());
        binding.tvAgeproR.setText(age);
        binding.tvEmailproR.setText(bean.getEmail());
        //这是手机
        binding.tvPhoneproR.setText(bean.getTel());
        binding.tvIdcardproR.setText(bean.getIDcard());
        binding.tvHomeproR.setText(bean.getHome());
        binding.tvBirthdayproR.setText(bean.getBirthday());

    }


    @Override
    public void onResume() {
        super.onResume();
        monitor();
    }

    //重写返回键事件
    private void monitor() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {

                NavController controller= Navigation.findNavController(v);
                controller.navigate(R.id.action_profileFragment_to_navigation_notifications);
                return true;
            }

            return false;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}