package com.example.demosystem.View.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.demosystem.MainActivity;
import com.example.demosystem.R;
import com.example.demosystem.Repository.LoginRepository;
import com.example.demosystem.databinding.FragmentLoginBinding;
import com.example.demosystem.helper.RetrofitManager;
import com.example.demosystem.helper.UserHelper;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    Observable<ResponseBody> observable;

    public LoginFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentLoginBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        //给注册按钮设置点击事件
        binding.tvRegister.setOnClickListener(v -> {
            //只负责跳转到RegisterFragment
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_loginFragment_to_registerFragment);
        });
        //给登录按钮注册点击事件
        binding.btnLogin.setOnClickListener(v -> {
            //获取账号密码
            String U_ID =binding.edUsername.getText().toString();//账号
            String Password = binding.edPwd.getText().toString();//密码
            //非空判断
            if (U_ID.isEmpty() || Password.isEmpty()) {
                Toast.makeText(getContext(), "用户名或者密码不能为空！", Toast.LENGTH_SHORT).show();
            } else {
                LoginRepository.getInstance().login(U_ID,Password,new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseBody responseBody) {
                        try{
                            String res=responseBody.string();
                            Log.d("ERR_登录","返回值是："+res);
                            switch (res) {
                                case "账号密码错误或未注册":
                                    //代表登录失败，比如账号不存在，密码错误
                                    Toast.makeText(getContext(), "账号或者密码错误！", Toast.LENGTH_SHORT).show();
                                    break;
                                case "医生":
                                    //代表登录成功，准备跳转到主页面Activity
                                    //Toast.makeText(getContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                                    UserHelper userHelper=UserHelper.newInstance();
                                    userHelper.setuId(U_ID);
                                    //TODO:存储到本地，实现记住登录功能
                                    SharedPreferences sharedPref=getActivity().getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPref.edit();
                                    editor.putString("智慧医疗用户名",U_ID);
                                    editor.putString("智慧医疗密码",Password);

                                    editor.apply();
                                    //跳转到主页面
                                    Intent intent=new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    break;
                                default:
                                    Toast.makeText(getContext(), "未知错误，请联系管理员！", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(getContext(), "网络错误，连接失败！", Toast.LENGTH_SHORT).show();
                        Log.d("ERR", "错误是" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


            }
        });

        binding.tvFindPsw.setOnClickListener(v->{
            //跳转到重置密码界面
            NavController controller=Navigation.findNavController(v);
            controller.navigate(R.id.action_loginFragment_to_resetPasswordFragment);
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
        //取消订阅，防止内存泄露
        //observable.unsubscribeOn(Schedulers.io());
    }





}
