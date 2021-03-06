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
import com.example.demosystem.databinding.FragmentRegister2Binding;
import com.example.demosystem.helper.RetrofitManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class Register2Fragment extends Fragment {
    FragmentRegister2Binding binding;

    public Register2Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRegister2Binding.inflate(inflater,container,false);
        View view=binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }

    private boolean checkIDcard(String IDcard){
        boolean flag=false;
        try{
            String check="^\\d{17}[0-9Xx]$";
            Pattern regex=Pattern.compile(check);
            Matcher matcher=regex.matcher(IDcard);
            flag=matcher.matches();
        }catch (Exception e){
            flag=false;
        }
        return  flag;
    }



    private void setListener(){
        binding.btnRegister.setOnClickListener(v -> {
            //1.??????Bundle????????????
            String U_ID=getArguments().getString("username");
            String Password = getArguments().getString("pwd");
            String email=getArguments().getString("email");
            //????????????
            String tel=getArguments().getString("tel");
            //2.????????????????????????
            String gender="";
            if(binding.rbGender.getCheckedRadioButtonId()==R.id.rb_male){
                gender="???";
                Log.d("ERR_gender","male");
            }else if(binding.rbGender.getCheckedRadioButtonId()==R.id.rb_female){
                gender="???";
                Log.d("ERR_gender","female");
            }

            String name=binding.edNameRg.getText().toString();
            int age= binding.edAgeRegister.getText().toString().equals("") ?0:Integer.parseInt(binding.edAgeRegister.getText().toString());
            String identification=binding.edIdentificationRg.getText().toString();
            String homeSite=binding.edHomesiteRg.getText().toString();
            String birthday=binding.edBirthdayRg.getText().toString();
//            Log.d("ERR_NAME",name);
//            Log.d("ERR_EMAIL",email);
//            Log.d("ERR_TEL_??????",tel);
//            Log.d("ERR_uid",U_ID);
//            Log.d("ERR_pwd",Password);
//            Log.d("ERR_age","?????????"+age);
//            Log.d("ERR_idcard",identification);
//            Log.d("ERR_home",homesite);
//            Log.d("ERR_birthday",birthday);

            //????????????
            if (name.isEmpty() || gender.isEmpty()||age==0||identification.isEmpty()||homeSite.isEmpty()||birthday.isEmpty()||gender.equals("")) {
                Toast.makeText(getContext(),"??????????????????",Toast.LENGTH_SHORT).show();
            } else {
                //?????????????????????
                if(!checkIDcard(identification)){
                    Toast.makeText(getContext(),"????????????????????????",Toast.LENGTH_SHORT).show();
                    return;
                }
                LoginRepository.getInstance().register(U_ID, Password, gender, identification,
                        birthday, age, email, name, homeSite, tel, new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseBody responseBody) {
                                try {
                                    String res = responseBody.string();
                                    Log.d("ERR_??????????????????",res);
                                    switch (res){
                                        case "???????????????":
                                            Toast.makeText(requireActivity(), "???????????????", Toast.LENGTH_SHORT).show();
                                            //?????????????????????
                                            NavController controller = Navigation.findNavController(v);
                                            controller.navigate(R.id.action_register2Fragment_to_loginFragment);
                                            break;
                                        case "????????????????????????":
                                            Toast.makeText(requireActivity(), "?????????????????????", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "??????????????????????????????":
                                            Toast.makeText(requireActivity(), "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Toast.makeText(requireActivity(), "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                                            break;

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                Toast.makeText(getContext(), "??????????????????????????????", Toast.LENGTH_SHORT).show();
                                Log.d("ERR", "?????????" + e.getMessage());

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        });
    }
}