package com.example.demosystem.View.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.demosystem.R;
import com.example.demosystem.databinding.FragmentRegisterBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;

    public RegisterFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //使用viewbinding代替findviewbyID
        /*
        与使用 findViewById 相比，视图绑定具有一些很显著的优点：
        Null 安全：由于视图绑定会创建对视图的直接引用，因此不存在因视图 ID 无效而引发 Null 指针异常的风险。
        此外，如果视图仅出现在布局的某些配置中，则绑定类中包含其引用的字段会使用 @Nullable 标记。
        类型安全：每个绑定类中的字段均具有与它们在 XML 文件中引用的视图相匹配的类型。这意味着不存在发生类转换异常的风险。
        这些差异意味着布局和代码之间的不兼容将会导致构建在编译时（而非运行时）失败。
         */
        binding=FragmentRegisterBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        setListener();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void setListener(){
        binding.btnNext.setOnClickListener(v->{
            String username=binding.edUsernameRegister.getText().toString();
            String pwd=binding.edPwdRegister.getText().toString();
            String email=binding.edEmailRegister.getText().toString();
            //这是手机
            String tel=binding.edTelRegister.getText().toString();

            
            if(username.isEmpty()||pwd.isEmpty()||email.isEmpty()||tel.isEmpty()){
                Toast.makeText(getContext(),"请重新输入！",Toast.LENGTH_SHORT).show();
            }else{
                if(!checkEmail(email)){
                    Toast.makeText(getContext(),"邮箱格式错误！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!checkMobiles(tel)){
                    Toast.makeText(getContext(),"手机格式错误！",Toast.LENGTH_SHORT).show();
                    return;
                }

                Bundle bundle=new Bundle();
                bundle.putString("username",username);
                bundle.putString("pwd",pwd);
                bundle.putString("email",email);
                bundle.putString("tel",tel);
                NavController controller=Navigation.findNavController(v);
                controller.navigate(R.id.action_registerFragment_to_register2Fragment,bundle);
            }

        });
    }


    //验证邮箱格式
    private  boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }


    //验证手机号格式
    private  boolean checkMobiles(String mobiles){
        boolean flag = false;
        try{
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
