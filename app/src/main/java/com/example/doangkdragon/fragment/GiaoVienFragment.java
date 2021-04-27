package com.example.doangkdragon.fragment;

import android.graphics.BitmapFactory;
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
import com.example.doangkdragon.adapter.PhieuChamBaiAdapter;
import com.example.doangkdragon.databinding.FragmentGiaoVienBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.GiaoVien;
import com.example.doangkdragon.db.models.Phieu;
import com.example.doangkdragon.dialog.AddGiaoVienDialog;
import com.example.doangkdragon.dialog.AddPhieuDialog;
import com.example.doangkdragon.dialog.UpdateGiaoVienDialog;


public class GiaoVienFragment extends Fragment{


    public FragmentGiaoVienBinding binding;
    public GiaoVien gvGetFormList ;
    public PhieuChamBaiAdapter phieuAdapter;
    public GiaoVienFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_giao_vien,container,false);
        gvGetFormList = GiaoVienFragmentArgs.fromBundle(getArguments()).getGiaovien();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setInformationGv();
        setUpRecycleView();
        setOnclickFloatBtn();
        setOnlickEditBtn();
        setOnclickDeleteBtn();
    }

    public void setInformationGv(){
        binding.maGvInfoTextView.setText("ID: " +  String.valueOf(gvGetFormList.getMaGv()));
        binding.tenGvInfoTextView.setText("Ho va Ten: " + gvGetFormList.getHoTenGv());
        binding.sdtGvInfoTextView.setText("SDT: " + String.valueOf(gvGetFormList.getSDT()));
        binding.gvImgView.setImageBitmap(BitmapFactory.decodeByteArray(gvGetFormList.getHinh(),0,gvGetFormList.getHinh().length));
    }
    public void setUpRecycleView(){
        DbHelper db = new DbHelper(getContext());
        phieuAdapter = new PhieuChamBaiAdapter(db.getListPhieu(gvGetFormList.getMaGv()));
        phieuAdapter.listener = new PhieuChamBaiAdapter.onClickItemListener() {
            @Override
            public void onClickItem(Phieu phieu) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("phieu",phieu);
                NavHostFragment.findNavController(GiaoVienFragment.this).navigate(R.id.action_giaoVienFragment_to_thongTinPhieuFragment,bundle);
            }
        };
        db.close();
        binding.listPhieu.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.listPhieu.setAdapter(phieuAdapter);
    }
    public void setOnclickFloatBtn(){
        binding.addPhieuFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPhieuDialog dialog = new AddPhieuDialog(gvGetFormList.getMaGv());
                dialog.show(getActivity().getSupportFragmentManager(),"add phieu dialog");
            }
        });
    }
    public void setOnlickEditBtn(){
        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateGiaoVienDialog dialog = new UpdateGiaoVienDialog(gvGetFormList);
                dialog.show(getActivity().getSupportFragmentManager(),"update giao vien dialog");
            }
        });
    }
    public void setOnclickDeleteBtn(){
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(getContext());
                int re = db.deleteGv(gvGetFormList);
                Toast.makeText(getContext(), "delete result : "+re, Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(GiaoVienFragment.this).navigate(R.id.action_giaoVienFragment_to_quanLyFragment);
            }
        });
    }
}