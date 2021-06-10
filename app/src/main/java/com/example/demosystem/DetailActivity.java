package com.example.demosystem;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;

import com.example.demosystem.databinding.ActivityDetailBinding;
import com.example.demosystem.helper.BaseActivity;
import com.example.demosystem.View.search.SharedViewModel;

//不要使用该类！
@Deprecated
public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);


        boolean changeFragment=getIntent().getBooleanExtra("changeFragment",false);
        if(changeFragment==true){

            NavController controller=Navigation.findNavController(this,R.id.fragment_detail);
            controller.navigate(R.id.action_statementListFragment_to_detailStatementFragment2);
        }




    }





}