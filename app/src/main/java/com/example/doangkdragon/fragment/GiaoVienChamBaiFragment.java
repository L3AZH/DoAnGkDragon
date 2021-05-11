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

import com.example.doangkdragon.R;
import com.example.doangkdragon.adapter.BaiAdapter;
import com.example.doangkdragon.databinding.FragmentGiaoVienChamBaiBinding;
import com.example.doangkdragon.databinding.FragmentGiaoVienPhieuChamBaiBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Bai;
import com.example.doangkdragon.db.models.Phieu;
import com.example.doangkdragon.db.models.ThongTinPhieu;
import com.example.doangkdragon.dialog.AddMonHocDialog;
import com.example.doangkdragon.dialog.ChamDiemDialog;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;


public class GiaoVienChamBaiFragment extends Fragment {


    public FragmentGiaoVienChamBaiBinding binding;
    public BaiAdapter baiAdapter;
    public ThongTinPhieu thongTinPhieup;
    public Phieu phieu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_giao_vien_cham_bai, container, false);
        thongTinPhieup = GiaoVienChamBaiFragmentArgs.fromBundle(getArguments()).getThongtinphieu();
        phieu = GiaoVienChamBaiFragmentArgs.fromBundle(getArguments()).getPhieu();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecycleView();
        loadingProgress();
        setOnClickGvChamBaiBackBtn();
    }

    public void setUpRecycleView(){
        DbHelper db = new DbHelper(getContext());
        baiAdapter = new BaiAdapter(db.getListBai(thongTinPhieup.getMaPhieu(),thongTinPhieup.getMaMon()));
        db.close();
        baiAdapter.listener = new BaiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bai bai) {
                ChamDiemDialog dialog = new ChamDiemDialog(bai);
                dialog.show(requireActivity().getSupportFragmentManager(),"dialog cham diem");
            }
        };
        binding.lisBai.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.lisBai.setAdapter(baiAdapter);
    }

    public void setOnClickGvChamBaiBackBtn(){
        binding.backGvChamBaiFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("phieu",phieu);
                NavHostFragment.findNavController(GiaoVienChamBaiFragment.this)
                        .navigate(R.id.action_giaoVienChamBaiFragment_to_giaoVienThongTinPhieuChamBaiFragment,bundle);
            }
        });
    }

    public void loadingProgress(){
        DbHelper db = new DbHelper(getContext());
        int max=0,progess=0;
        Vector<Bai> listBai = db.getListBai(thongTinPhieup.getMaPhieu(),thongTinPhieup.getMaMon());
        Vector<Bai> listBaiDaCham = db.getListBaiDaCham(thongTinPhieup.getMaPhieu(),thongTinPhieup.getMaMon());
        if(listBai != null){
            max = listBai.size();
        }
        if(listBaiDaCham != null){
            progess = listBaiDaCham.size();
        }
        binding.tienTrinhProgressBar.setMax(max);
        binding.tienTrinhProgressBar.setProgress(progess);
        binding.tienTrinhTextView.setText("Tiến độ chấm bài: "+progess+" / "+max);
    }
}