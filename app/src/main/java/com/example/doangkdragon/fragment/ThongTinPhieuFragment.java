
package com.example.doangkdragon.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doangkdragon.R;
import com.example.doangkdragon.adapter.ThongTinPhieuChamBaiAdapter;
import com.example.doangkdragon.databinding.DialogAddThongtinphieuBinding;
import com.example.doangkdragon.databinding.FragmentGiaoVienBinding;
import com.example.doangkdragon.databinding.FragmentThongKeBinding;
import com.example.doangkdragon.databinding.FragmentThongTinPhieuBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Phieu;
import com.example.doangkdragon.dialog.AddThongTinPhieuDialog;

public class ThongTinPhieuFragment extends Fragment {

    public FragmentThongTinPhieuBinding binding;
    public Phieu phieuGetFromList;
    public ThongTinPhieuChamBaiAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_thong_tin_phieu,container,false);
        phieuGetFromList = ThongTinPhieuFragmentArgs.fromBundle(getArguments()).getPhieu();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpInfoPhieu();
        setUpRecycleViewList();
        setOnclickFloatBtn();
    }

    public void setUpInfoPhieu(){
        binding.maPhieuInfoTextView.setText(String.valueOf("Ma Phieu: " + phieuGetFromList.getMaPhieu()));
        binding.ngayInfoTextView.setText(phieuGetFromList.getNgay());
        binding.maGvFkInfoTextView.setText(String.valueOf(phieuGetFromList.getMaGv()));
    }

    public void setUpRecycleViewList(){
        DbHelper db = new DbHelper(getContext());
        adapter = new ThongTinPhieuChamBaiAdapter(db.getListThongTinPhieu(phieuGetFromList.getMaPhieu()));
        db.close();
        binding.listThongTinPhieu.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.listThongTinPhieu.setAdapter(adapter);
    }
    public void setOnclickFloatBtn(){
        binding.addThongTinPhieuFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(getContext());
                if (db.getListMonHoc() == null){
                    Toast.makeText(getContext(), "List mon hoc rong !!", Toast.LENGTH_SHORT).show();
                }
                else{
                    AddThongTinPhieuDialog dialog = new AddThongTinPhieuDialog(phieuGetFromList.getMaPhieu());
                    dialog.show(getActivity().getSupportFragmentManager(),"dialog add thong tin phieu");
                }
            }
        });
    }
}