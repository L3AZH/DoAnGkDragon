package com.example.doangkdragon.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doangkdragon.GiaoVienActivity;
import com.example.doangkdragon.LoginActivity;
import com.example.doangkdragon.R;
import com.example.doangkdragon.adapter.PhieuChamBaiAdapter;
import com.example.doangkdragon.databinding.FragmentGiaoVienPhieuChamBaiBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Phieu;

import org.jetbrains.annotations.NotNull;

public class GiaoVienPhieuChamBaiFragment extends Fragment {

    public FragmentGiaoVienPhieuChamBaiBinding binding;
    public int maGv;
    public PhieuChamBaiAdapter phieuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_giao_vien_phieu_cham_bai, container, false);
        maGv = getActivity().getIntent().getIntExtra("magv",0);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecycleView();
        setOnclickLogoutFloatBtn();
    }

    public void setUpRecycleView(){
        DbHelper db = new DbHelper(getContext());
        phieuAdapter = new PhieuChamBaiAdapter(db.getListPhieu(maGv));
        phieuAdapter.listener = new PhieuChamBaiAdapter.onClickItemListener() {
            @Override
            public void onClickItem(Phieu phieu) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("phieu",phieu);
                NavHostFragment.findNavController(GiaoVienPhieuChamBaiFragment.this)
                        .navigate(R.id.action_giaoVienPhieuChamBaiFragment_to_giaoVienThongTinPhieuChamBaiFragment,bundle);
            }
        };
        db.close();
        binding.listPhieuChamBai.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.listPhieuChamBai.setAdapter(phieuAdapter);
    }
    public void  setOnclickLogoutFloatBtn(){
        binding.logoutFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToActivityLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(goToActivityLogin);
            }
        });
    }
}