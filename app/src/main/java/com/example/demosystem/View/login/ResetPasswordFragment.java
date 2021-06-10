package com.example.demosystem.View.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.demosystem.R;
import com.example.demosystem.Repository.LoginRepository;
import com.example.demosystem.databinding.FragmentResetPasswordBinding;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


public class ResetPasswordFragment extends Fragment {
    FragmentResetPasswordBinding binding;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentResetPasswordBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //重置密码按钮添加点击事件
        binding.btnResetPwd.setOnClickListener(v->{
            String U_ID=binding.edResetUsername.getText().toString();
            String NewPassword=binding.edResetNewpwd.getText().toString();
            String IDcard=binding.edResetIdcard.getText().toString();
            String Tel=binding.edResetPhone.getText().toString();
            if(U_ID.isEmpty()||NewPassword.isEmpty()||IDcard.isEmpty()||Tel.isEmpty()){
                Toast.makeText(getContext(),"请重新输入！",Toast.LENGTH_SHORT).show();
            }else{
                resetPwd(U_ID,NewPassword,IDcard,Tel);
            }

        });
    }



    private void resetPwd(String U_ID,String NewPassword,String IDcard,String Tel){
        LoginRepository.getInstance().resetPassword(U_ID, NewPassword, IDcard, Tel, new Observer<Integer>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Integer result) {
                Log.d("ERR_结果","结果是"+result);
                switch (result){
                    case 1:
                        Toast.makeText(getContext(),"密码重置成功！",Toast.LENGTH_SHORT).show();
                        //跳转回登录界面
                        NavController controller= Navigation.findNavController(getView());
                        controller.navigate(R.id.action_resetPasswordFragment_to_loginFragment);
                        break;
                    case 0:
                        Toast.makeText(getContext(),"您的个人信息输入有误！",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Toast.makeText(getContext(),"网络异常！",Toast.LENGTH_SHORT).show();
                Log.d("ERR_重置密码异常","异常是"+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}