package com.example.demosystem.View.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.demosystem.R;
import com.example.demosystem.Repository.SearchReportRepository;
import com.example.demosystem.ShowReportActivity;
import com.example.demosystem.adapter.MyAdapter;
import com.example.demosystem.bean.AbstractReportBean;
import com.example.demosystem.databinding.StatementListFragmentBinding;
import com.example.demosystem.helper.RetrofitManager;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

//用于处理经病历号查询后显示的患者T的所有报表
public class StatementListFragment extends Fragment {
    StatementListFragmentBinding binding;
    MyAdapter adapter;
    String R_ID;
    Observable<List<AbstractReportBean>> observable;

    public StatementListFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=StatementListFragmentBinding.inflate(inflater,container,false);
        View view= binding.getRoot();
        adapter=new MyAdapter();
        binding.recyclerview.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(binding.recyclerview.getContext(),manager.getOrientation());
        binding.recyclerview.setLayoutManager(manager);
        binding.recyclerview.addItemDecoration(dividerItemDecoration);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: Use the ViewModel
        //拿到病历号
        R_ID=getArguments().getString("病历号");
        observable=RetrofitManager.getApiService().searchStatement(R_ID);
        getStatementList(R_ID);

        binding.refreshLayout.setOnRefreshListener(refreshLayout ->
        {
            //实现下拉刷新
            getStatementList(R_ID);
            refreshLayout.finishRefresh(2000);
        });
        binding.refreshLayout.setOnLoadmoreListener(refreshLayout -> refreshLayout.finishLoadmore(2000));

    }

    void getStatementList(String phone){
        SearchReportRepository.getInstance().searchReportByPhone(phone, new Observer<List<AbstractReportBean>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<AbstractReportBean> abstractReportBeans) {
                List<AbstractReportBean> list=abstractReportBeans;
//                        adapter=MyAdapter.getInstance();
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                if(list.size()==0){
                    Toast.makeText(getContext(),"查找失败，不存在该用户的报表！",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.d("ERR_错误",e.getMessage());
                Toast.makeText(getContext(),"网络异常！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                adapter.setOnItemClickListener( (view, position) ->{
                    //跳转到ReportInfoActivity
                    Intent intent=new Intent(getContext(), ShowReportActivity.class);
                    intent.putExtra("R_ID",adapter.getBean(position).getR_ID());
                    intent.putExtra("T_ID",adapter.getBean(position).getT_ID());
                    startActivity(intent);
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
                controller.navigate(R.id.action_statementListFragment2_to_navigation_dashboard);
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