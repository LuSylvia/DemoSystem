package com.example.demosystem.NetService;

import com.example.demosystem.bean.AbstractReportBean;
import com.example.demosystem.bean.CommentsBean;
import com.example.demosystem.bean.ProfileBean2;
import com.example.demosystem.bean.ReportBean;
import com.example.demosystem.bean.TestedPersonInfoBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitApiService {
    //注册
    @GET("register")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Observable<ResponseBody> register(@Query("U_ID")String U_ID, @Query("Password")String Password,
                                      @Query("Gender")String Gender,@Query("IDcard")String IDcard,
                                      @Query("Birthday")String Birthday,@Query("Age")int Age,
                                      @Query("Email")String Email,@Query("Name")String Name,
                                      @Query("Home")String Home, @Query("Tel")String Tel);

    //登录
    @GET("login")
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Observable<ResponseBody> login(@Query("U_ID")String U_ID, @Query("Password")String Password);

    //重置密码
    @GET("forgetPassword")
    Observable<Integer> resetPwd(@Query("U_ID")String U_ID,@Query("NewPassword")String NewPassword,
                                 @Query("IDcard")String IDcard,@Query("Tel")String Tel);

    //根据R_ID，也就是报表ID来获取唯一的报表
    @GET("getReportByR_ID")
    Observable<ReportBean> getReportByR_ID2(@Query("R_ID")int R_ID);


    //根据T_ID，也就是被测人ID来获取被测人的基本信息
    @GET("FindTestedPersonByT_ID")
    Observable<TestedPersonInfoBean> FindTestedPersonByT_ID(@Query("T_ID")int T_ID);


    //根据R_ID（报表ID）来获取评论数据
    @GET("getCommentByR_ID")
    Observable<List<CommentsBean>> getCommentByR_ID(@Query("R_ID")int R_ID);


    /*
     *修改评论接口
     *101.200.167.112:8080/changeComments
     *传入 C_ID,Content,Doctor
     *返回 1或0.
     */
    @GET("changeComments")
    Observable<ResponseBody>  changeComments(@Query("C_ID") int C_ID, @Query("Content")String Content, @Query("Doctor")String Doctor);


    /*
     *增加评论接口
     *101.200.167.112:8080/AddComments
     *传入 T_ID,Doctor,Content,R_ID,Time
     *返回 1或0
     */
    @GET("AddComments")
    Observable<ResponseBody> addComments(@Query("T_ID")int T_ID,@Query("Doctor")String Doctor,
                                         @Query("Content")String Content,@Query("R_ID")int R_ID,@Query("Time")String Time);

    //根据病人手机号，
    //查找一个病人的所有报表
    @GET("SelectReportByPhone")
    Observable<List<AbstractReportBean>> searchStatement(@Query("Phone_Number")String Phone_Number);

    //修改密码
    @GET("changepassword")
    Observable<ResponseBody> updatePwd(@Query("U_ID")String  U_ID,
                                       @Query("NewPassword")String NewPassword,
                                       @Query("OriginalPassword")String OriginalPassword);

    //获取个人中心
    @GET("getAttribute")
    Observable<List<ProfileBean2>> getAttribute(@Query("U_ID")String U_ID);

    //上传图片
    //返回的是string类型的0和1
    //返回1成功，0失败
    @Multipart
    @POST("upload/files")
    Observable<String> uploadImg(@Part MultipartBody.Part file, @Part("U_ID") String U_ID);

    //上传U_ID
    //返回图片
    @GET("getPicByU_ID")
    Observable<ResponseBody> getPicByU_ID(@Query("U_ID")String U_ID);

    //查找所有未评论的报表
    //返回RID集合
    @GET("SelectPeportWithNoComment")
    Observable<List<String>> SelectPeportWithNoComment();


}
