package com.example.demosystem.View.userSetting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.demosystem.LoginRegisterActivity;
import com.example.demosystem.R;
import com.example.demosystem.Repository.ProfileRepository;
import com.example.demosystem.databinding.FragmentUserSettingBinding;
import com.example.demosystem.helper.GlideEnginee;
import com.example.demosystem.helper.SystemHelper;
import com.example.demosystem.helper.UserHelper;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@RequiresApi(api = Build.VERSION_CODES.O)
public class userSettingFragment extends Fragment {
    private static final String TAG = "ERR";
    private FragmentUserSettingBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentUserSettingBinding.inflate(inflater,container,false);
        View view =binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        autoGetProfilePicture();

        //跳转到上传头像界面
        binding.btnChangeProfilePicture.setOnClickListener(v->{
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .isWeChatStyle(true)
                    .imageEngine(GlideEnginee.createGlideEngine())//必传
                    .selectionMode(PictureConfig.SINGLE)//单选
                    .isZoomAnim(true)
                    .queryMimeTypeConditions(PictureMimeType.ofJPEG())
                    .filterMaxFileSize(5120)//过滤最大资源，单位为KB，此处代表过滤5M以上的图片（服务器限制）
                    .minSelectNum(1)//最少选择1个图片
                    .maxSelectNum(1)//最多选择1个图片
                    .forResult(new OnResultCallbackListener() {
                        @Override
                        public void onResult(List result) {
                            List<LocalMedia> selectList = result;
                            for(LocalMedia media:selectList){
                                //上传头像
                                //loadWithGlide(media.getRealPath());
                                uploadProfile(media.getRealPath());
//                                Log.i(TAG, "是否压缩:" + media.isCompressed());
//                                Log.i(TAG, "压缩:" + media.getCompressPath());
//                                Log.i(TAG, "原图:" + media.getPath());
                                Log.i(TAG, "绝对路径:" + media.getRealPath());
//                                Log.i(TAG, "是否裁剪:" + media.isCut());
//                                Log.i(TAG, "裁剪:" + media.getCutPath());
//                                Log.i(TAG, "是否开启原图:" + media.isOriginal());
//                                Log.i(TAG, "原图路径:" + media.getOriginalPath());
//                                Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
//                                Log.i(TAG, "宽高: " + media.getWidth() + "x" + media.getHeight());
//                                Log.i(TAG, "Size: " + media.getSize());
                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    });

        });


        //跳转到修改密码界面
        binding.btnChangePwd.setOnClickListener(v->{
            NavController controller= Navigation.findNavController(view);
            controller.navigate(R.id.action_navigation_notifications_to_settingFragment22);
        });
        //跳转到个人资料页面
        binding.btnShowProfile.setOnClickListener(v->{
            NavController controller=Navigation.findNavController(view);
            controller.navigate(R.id.action_navigation_notifications_to_profileFragment);
        });


        //实现退出登录功能
        binding.btnQuitLogin.setOnClickListener(v->{
            //1.修改userHelper中U_ID为null
            UserHelper userHelper=UserHelper.newInstance();
            userHelper.setuId(null);
            //2.准备跳转到LoginRegisterActivity
            Intent intent=new Intent(getContext(), LoginRegisterActivity.class);
            /*
            * (1)FLAG_ACTIVITY_CLEAR_TASK :如果在调用Context.startActivity时传递这个标记，
            * 将会导致任何用来放置该activity的已经存在的task里面的已经存在的activity先清空，然后该activity再在该task中启动，
            * 也就是说，这个新启动的activity变为了这个空tas的根activity.所有老的activity都结束掉。该标志必须和FLAG_ACTIVITY_NEW_TASK一起使用.
              (2)FLAG_ACTIVITY_NEW_TASK: 首先会查找是否存在和被启动的Activity具有相同的亲和性的任务栈（即taskAffinity，
              * 注意同一个应用程序中的activity的亲和性一样，所以下面的a情况会在同一个栈中，前面这句话有点拗口，请多读几遍），
              * 如果有，刚直接把这个栈整体移动到前台，并保持栈中的状态不变，即栈中的activity顺序不变，如果没有，则新建一个栈来存放被启动的activity.
            * */
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    //传入图片路径，上传图片
    private void uploadProfile(String path){
        File tmpfile=new File(path);
        String cachePath=getContext().getFilesDir().getPath();
        //Log.d("ERR_cachePath",cachePath);
        SharedPreferences sharedPref=getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        //获取系统时间，格式为年-月-日 时:分:秒，并记录到本地
        LocalDateTime dateTime=LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time=dateTime.format(formatter);
        editor.putString("PictureTime",time);
        //Log.d("ERR_时间","time是"+time);
        editor.apply();
        //Log.d("ERR_文件名1",UserHelper.newInstance().getuId()+"-"+time+".jpg");
        //复制图片，重命名
        File file= new File(cachePath+"/"+UserHelper.newInstance().getuId()+"-"+time+".jpg");
        //Log.d("ERR_测试",file.getName());
        try {
            Files.copy(tmpfile.toPath(),file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置图片上传格式，别动
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        //开始上传头像
        ProfileRepository.getInstance().uploadFile(body, UserHelper.newInstance().getuId(), new Observer<String>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
            }
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull String s) {
                if(s.equals("1")){
                    //成功
                    Toast.makeText(getContext(),"头像上传成功！",Toast.LENGTH_SHORT).show();
                    //Log.d("ERR_上传头像1","成功,消息是"+s);
                    //loadWithGlide(path);
                    autoGetProfilePicture();
                }else if(s.equals("0")){
                    //失败
                    Toast.makeText(getContext(),"头像上传失败！",Toast.LENGTH_SHORT).show();
                    //Log.d("ERR_上传头像2","失败,消息是"+s);
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Toast.makeText(getContext(),"网络异常！",Toast.LENGTH_SHORT).show();
                //Log.d("ERR_上传头像3",e.getMessage());
                file.delete();
            }

            @Override
            public void onComplete() {
                file.delete();//删除复制后的文件
            }
        });


        

    }

    //使用Glide读取图片，具体属性请自行查阅Glide官方文档
    private void loadWithGlide(String path) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.doctor)//占位符，当尚未请求完时用该图片代替
                .error(R.drawable.error)//异常符，网络异常时显示该图片
                .centerCrop()
                //.skipMemoryCache(true)//禁止使用内存缓存
                //.diskCacheStrategy(DiskCacheStrategy.NONE)//禁止使用磁盘缓存
                .fitCenter()
                ;
        Glide.with(getContext()).load(path)
                .apply(options)
                .into(binding.ivProfilePicture);
    }



    //打开界面后自动获取用户头像
    //本功能暂时无法解决当系统重装APP后，头像的显示问题
    //假如用户设置了头像后，卸载并重装了APP，那么APP依然会显示系统默认头像
    private void autoGetProfilePicture(){
        SharedPreferences preferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        String time=preferences.getString("PictureTime", "默认时间");
        Log.d("ERR_时间2","时间是"+time);
        if(!time.equals("默认时间")){
            //11指的是账号
            //loadWithGlide("http://101.200.167.112:8080/img/11-2021-06-07 20:14:35.jpg");
            Log.d("ERR_文件名2",UserHelper.newInstance().getuId()+"-"+time+".jpg");
            loadWithGlide(SystemHelper.getInstance().getApiUrl()+"img/"+UserHelper.newInstance().getuId()+"-"+time+".jpg");
        }else{
            //显示默认头像
            Glide.with(getContext()).load(R.drawable.doctor).into(binding.ivProfilePicture);
        }




    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}