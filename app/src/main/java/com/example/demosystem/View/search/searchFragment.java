package com.example.demosystem.View.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.demosystem.R;
import com.example.demosystem.databinding.FragmentSearchBinding;


public class searchFragment extends Fragment {
    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentSearchBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        binding.btnSearch.setOnClickListener(v->{
            String R_ID="";
            if(binding.edPhone.getText().toString().isEmpty()){
                Toast.makeText(getContext(),"您尚未输入任何信息！",Toast.LENGTH_SHORT).show();
            }else{
                R_ID=binding.edPhone.getText().toString();
                NavController controller= Navigation.findNavController(v);
                Bundle bundle=new Bundle();
                bundle.putString("病历号",R_ID);
                controller.navigate(R.id.action_navigation_dashboard_to_statementListFragment2,bundle);
            }


        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}