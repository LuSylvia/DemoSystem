package com.example.demosystem.View.userSetting;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.demosystem.R;
import com.example.demosystem.Repository.ProfileRepository;
import com.example.demosystem.databinding.FragmentSettingBinding;
import com.example.demosystem.helper.RetrofitManager;
import com.example.demosystem.helper.UserHelper;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

//用于修改密码
public class changePasswordFragment extends Fragment {
    private FragmentSettingBinding binding;
    Observable<ResponseBody> observable;

    public changePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSettingBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        View view= binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@androidx.annotation.NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //修改密码的实现
        binding.btnChangePwdReal.setOnClickListener(v->{
            String U_ID=UserHelper.newInstance().getuId();
            String OriginalPassword=binding.edOldPwd.getText().toString();
            String NewPassword=binding.edNewPwd.getText().toString();
            if(OriginalPassword.isEmpty()||NewPassword.isEmpty()){
                Toast.makeText(getContext(),"旧密码或者新密码不能为空！",Toast.LENGTH_SHORT).show();
            }else{
                if(OriginalPassword.equals(NewPassword)){
                    Log.d("Debug","旧密码不能与新密码相同！");
                    return;
                }
                ProfileRepository.getInstance().changePassword(U_ID, NewPassword, OriginalPassword, new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            int res = Integer.valueOf(responseBody.string());
                            if (res==1) {
                                Toast.makeText(getContext(), "修改密码成功！", Toast.LENGTH_SHORT).show();
                                //跳转回设置界面
                                NavController controller = Navigation.findNavController(v);
                                controller.navigate(R.id.action_settingFragment2_to_navigation_notifications2);

                            } else if (res==0) {
                                Toast.makeText(getContext(), "修改密码失败！", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getContext(), "网络异常！", Toast.LENGTH_SHORT).show();
                        Log.d("Debug",e.getMessage());
                        return;
                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }

        });
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
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                // 监听到返回按钮点击事件,且该页面是从报表list页面跳转而来
                NavController controller= Navigation.findNavController(v);
                controller.navigate(R.id.action_settingFragment2_to_navigation_notifications2);
                return true;
            }

            return false;
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
        //observable.unsubscribeOn(Schedulers.io());
    }
}
