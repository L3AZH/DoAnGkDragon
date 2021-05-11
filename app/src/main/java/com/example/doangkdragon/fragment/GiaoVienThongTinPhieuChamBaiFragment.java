package com.example.doangkdragon.fragment;

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
import android.widget.Toast;

import com.example.doangkdragon.R;
import com.example.doangkdragon.adapter.ThongTinPhieuChamBaiAdapter;
import com.example.doangkdragon.databinding.FragmentGiaoVienThongTinPhieuChamBaiBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Phieu;
import com.example.doangkdragon.db.models.ThongTinPhieu;
import com.example.doangkdragon.dialog.InfoChiTietThongTinPhieuDialog;

import org.jetbrains.annotations.NotNull;

public class GiaoVienThongTinPhieuChamBaiFragment extends Fragment {

    public FragmentGiaoVienThongTinPhieuChamBaiBinding binding;
    public Phieu phieu;
    public ThongTinPhieuChamBaiAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_giao_vien_thong_tin_phieu_cham_bai, container, false);
        phieu = GiaoVienThongTinPhieuChamBaiFragmentArgs.fromBundle(getArguments()).getPhieu();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecycleView();
        setOnclickBackBtn();
    }

    public void setUpRecycleView(){
        DbHelper db = new DbHelper(getContext());
        adapter = new ThongTinPhieuChamBaiAdapter(db.getListThongTinPhieu(phieu.getMaPhieu()));
        adapter.listener = new ThongTinPhieuChamBaiAdapter.onClickItemListener() {
            @Override
            public void onClickItem(ThongTinPhieu thongTinPhieu) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("thongtinphieu",thongTinPhieu);
                bundle.putSerializable("phieu",phieu);
                NavHostFragment.findNavController(GiaoVienThongTinPhieuChamBaiFragment.this)
                        .navigate(R.id.action_giaoVienThongTinPhieuChamBaiFragment_to_giaoVienChamBaiFragment,bundle);
            }
        };
        db.close();
        binding.listThongTinPhieuChamBai.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.listThongTinPhieuChamBai.setAdapter(adapter);
    }

    public void setOnclickBackBtn(){
        binding.backGvThongTinPhieuChamBaiFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(GiaoVienThongTinPhieuChamBaiFragment.this)
                        .navigate(R.id.action_giaoVienThongTinPhieuChamBaiFragment_to_giaoVienPhieuChamBaiFragment);
            }
        });
    }
}