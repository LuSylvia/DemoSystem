package com.example.demosystem.View.ui.reportinfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.demosystem.R;
import com.example.demosystem.bean.ReportBean;
import com.example.demosystem.databinding.FragmentReportDataBinding;


public class ReportDataFragment extends Fragment {
    private FragmentReportDataBinding binding;
    DetailReportViewModel viewModel;


    public ReportDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentReportDataBinding.inflate(inflater,container,false);
        View view=binding.getRoot();
        viewModel=new ViewModelProvider(requireActivity()).get(DetailReportViewModel.class);
        //setData();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getReportLiveData().observe(getViewLifecycleOwner(), new Observer<ReportBean>() {
            @Override
            public void onChanged(ReportBean reportBean) {
                setData();
            }
        });
    }

    //填充数据
    void setData(){
      if(viewModel.getReport()!=null){
          binding.tvPeriodRight.setText(String.valueOf(viewModel.getReport().getPeriod()));
          binding.tvStrideRight.setText(String.valueOf(viewModel.getReport().getStride()));
          binding.tvSteplengthRight.setText(String.valueOf(viewModel.getReport().getStep_length()));
          binding.tvStepwidthRight.setText(String.valueOf(viewModel.getReport().getStep_width()));
          binding.tvFrequencyRight.setText(String.valueOf(viewModel.getReport().getStride_frequency()));
          binding.tvPaceRight.setText(String.valueOf(viewModel.getReport().getPace()));
          binding.tvLrRight.setText(viewModel.getReport().getBarycenter_LR());
          binding.tvUdRight.setText(viewModel.getReport().getBarycenter_UD());
          setImage();
      }else{
          Log.d("ERR_VIEWMODEL","你的REPORT还是空的");
      }
    }
    //填充图像
    private void setImage(){
        Glide.with(getContext()).load(R.drawable.index1).into(binding.im01);
        Glide.with(getContext()).load(R.drawable.index2).into(binding.im02);
        Glide.with(getContext()).load(R.drawable.index3).into(binding.im03);
        Glide.with(getContext()).load(R.drawable.index4).into(binding.im04);
    }


}