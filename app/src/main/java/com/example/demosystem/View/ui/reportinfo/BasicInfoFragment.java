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

import com.example.demosystem.bean.ReportBean;
import com.example.demosystem.bean.TestedPersonInfoBean;
import com.example.demosystem.databinding.FragmentBasicInfoBinding;


public class BasicInfoFragment extends Fragment {

    public FragmentBasicInfoBinding binding;
    DetailReportViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBasicInfoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel=new ViewModelProvider(requireActivity()).get(DetailReportViewModel.class);
        viewModel.getUserInfo();
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getUserInfoLiveData().observe(getViewLifecycleOwner(), new Observer<TestedPersonInfoBean>() {
            @Override
            public void onChanged(TestedPersonInfoBean testedPersonInfoBean) {
                if(testedPersonInfoBean!=null){
                    binding.tvNameRight.setText(viewModel.getUserInfo().getName());
                    binding.tvGenderRight.setText(viewModel.getUserInfo().getGender());
                    binding.tvAgeRight.setText(String.valueOf(viewModel.getUserInfo().getAge()));
                    binding.tvPhoneRight.setText(viewModel.getUserInfo().getPhone_Number());
                    binding.tvSiteRight.setText(viewModel.getUserInfo().getSite());
                }
            }
        });
        viewModel.getReportLiveData().observe(getViewLifecycleOwner(), new Observer<ReportBean>() {
            @Override
            public void onChanged(ReportBean reportBean) {
                String rid= String.valueOf(viewModel.getReport().getR_ID());
                binding.tvRIDRight.setText(rid);
                binding.tvTimeRight.setText(viewModel.getReport().getCreate_time());
            }
        });






    }








}