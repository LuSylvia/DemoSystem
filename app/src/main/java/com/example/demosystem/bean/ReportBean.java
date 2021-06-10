package com.example.demosystem.bean;

import android.os.Parcel;
import android.os.Parcelable;

//报表详细的步态参数
public class ReportBean implements Parcelable {
    //报表编号
    private int R_ID;
    //步态周期
    private Double Period;
    //步幅
    private Double Stride;
    //步长
    private Double Step_length;
    //步宽
    private Double Step_width;
    //步频
    private Double Stride_frequency;
    //步速
    private Double Pace;
    //质心左右移动范围
    private String Barycenter_LR;
    //质心上下移动范围
    private String Barycenter_UD;
    //髋关节夹角
    private String Hip_Angle;
    //膝关节角度
    private String Knee_Angle;
    //被测人ID，即病历号
    private int T_ID;
    //报表创建时间
    private String Create_time;

    public ReportBean(){

    }

    public ReportBean(int r_ID, Double period, Double stride,
                      Double step_length, Double step_width,
                      Double stride_frequency, Double pace,
                      String barycenter_LR, String barycenter_UD,
                      String hip_Angle, String knee_Angle,
                      int t_ID, String create_time) {
        R_ID = r_ID;
        Period = period;
        Stride = stride;
        Step_length = step_length;
        Step_width = step_width;
        Stride_frequency = stride_frequency;
        Pace = pace;
        Barycenter_LR = barycenter_LR;
        Barycenter_UD = barycenter_UD;
        Hip_Angle = hip_Angle;
        Knee_Angle = knee_Angle;
        T_ID = t_ID;
        Create_time = create_time;
    }

    protected ReportBean(Parcel in) {
        R_ID = in.readInt();
        if (in.readByte() == 0) {
            Period = null;
        } else {
            Period = in.readDouble();
        }
        if (in.readByte() == 0) {
            Stride = null;
        } else {
            Stride = in.readDouble();
        }
        if (in.readByte() == 0) {
            Step_length = null;
        } else {
            Step_length = in.readDouble();
        }
        if (in.readByte() == 0) {
            Step_width = null;
        } else {
            Step_width = in.readDouble();
        }
        if (in.readByte() == 0) {
            Stride_frequency = null;
        } else {
            Stride_frequency = in.readDouble();
        }
        if (in.readByte() == 0) {
            Pace = null;
        } else {
            Pace = in.readDouble();
        }
        Barycenter_LR = in.readString();
        Barycenter_UD = in.readString();
        Hip_Angle = in.readString();
        Knee_Angle = in.readString();
        T_ID = in.readInt();
        Create_time = in.readString();
    }

    public static final Creator<ReportBean> CREATOR = new Creator<ReportBean>() {
        @Override
        public ReportBean createFromParcel(Parcel in) {
            return new ReportBean(in);
        }

        @Override
        public ReportBean[] newArray(int size) {
            return new ReportBean[size];
        }
    };

    public String  getAllData(){
        String msg= "RID="+getR_ID()+",TID="+getT_ID()+",周期="+getPeriod()+",time="+getCreate_time();
        return msg;
    }

    public int getR_ID() {
        return R_ID;
    }

    public void setR_ID(int r_ID) {
        R_ID = r_ID;
    }

    public Double getPeriod() {
        return Period;
    }

    public void setPeriod(Double period) {
        Period = period;
    }

    public Double getStride() {
        return Stride;
    }

    public void setStride(Double stride) {
        Stride = stride;
    }

    public Double getStep_length() {
        return Step_length;
    }

    public void setStep_length(Double step_length) {
        Step_length = step_length;
    }

    public Double getStep_width() {
        return Step_width;
    }

    public void setStep_width(Double step_width) {
        Step_width = step_width;
    }

    public Double getStride_frequency() {
        return Stride_frequency;
    }

    public void setStride_frequency(Double stride_frequency) {
        Stride_frequency = stride_frequency;
    }

    public Double getPace() {
        return Pace;
    }

    public void setPace(Double pace) {
        Pace = pace;
    }

    public String getBarycenter_LR() {
        return Barycenter_LR;
    }

    public void setBarycenter_LR(String barycenter_LR) {
        Barycenter_LR = barycenter_LR;
    }

    public String getBarycenter_UD() {
        return Barycenter_UD;
    }

    public void setBarycenter_UD(String barycenter_UD) {
        Barycenter_UD = barycenter_UD;
    }

    public String getHip_Angle() {
        return Hip_Angle;
    }

    public void setHip_Angle(String hip_Angle) {
        Hip_Angle = hip_Angle;
    }

    public String getKnee_Angle() {
        return Knee_Angle;
    }

    public void setKnee_Angle(String knee_Angle) {
        Knee_Angle = knee_Angle;
    }

    public int getT_ID() {
        return T_ID;
    }

    public void setT_ID(int t_ID) {
        T_ID = t_ID;
    }

    public String getCreate_time() {
        return Create_time;
    }

    public void setCreate_time(String create_time) {
        Create_time = create_time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(R_ID);
        if (Period == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Period);
        }
        if (Stride == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Stride);
        }
        if (Step_length == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Step_length);
        }
        if (Step_width == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Step_width);
        }
        if (Stride_frequency == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Stride_frequency);
        }
        if (Pace == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Pace);
        }
        dest.writeString(Barycenter_LR);
        dest.writeString(Barycenter_UD);
        dest.writeString(Hip_Angle);
        dest.writeString(Knee_Angle);
        dest.writeInt(T_ID);
        dest.writeString(Create_time);
    }
}
