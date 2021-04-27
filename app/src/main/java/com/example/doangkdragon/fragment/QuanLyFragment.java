package com.example.doangkdragon.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.doangkdragon.R;
import com.example.doangkdragon.adapter.ViewPaggerAdapter;
import com.example.doangkdragon.databinding.FragmentQuanLyBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.GiaoVien;
import com.example.doangkdragon.dialog.AddGiaoVienDialog;
import com.example.doangkdragon.dialog.AddMonHocDialog;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;
import java.util.Vector;


public class QuanLyFragment extends Fragment {

    private FragmentQuanLyBinding binding;
    private DbHelper db;
    public ViewPaggerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quan_ly, container, false);
        db = new DbHelper(getContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Vector<String> listObject = new Vector<>();
        listObject.add("Giáo Viên");
        listObject.add("Môn Học");
        adapter = new ViewPaggerAdapter(listObject,this.getContext());
        adapter.listGiaoVien = db.getListGv();
        adapter.listMonHoc = db.getListMonHoc();
        adapter.handlerListener = new ViewPaggerAdapter.handlerOnclickItemGv() {
            @Override
            public void handlerOnclick(GiaoVien giaoVien) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("giaovien",giaoVien);
                Toast.makeText(getContext(), "Giao vien Id: "+giaoVien.getMaGv(), Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(QuanLyFragment.this).navigate(R.id.action_quanLyFragment_to_giaoVienFragment,bundle);
            }

            @Override
            public boolean handlerLongclick(GiaoVien giaoVien) {
                return true;
            }
        };
        binding.viewPage.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPage, ((tab, position) -> {
            tab.setText(listObject.get(position));
        })).attach();
        setOnclickAddFloatBtn();
    }

    public void setOnclickAddFloatBtn(){
        binding.addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (binding.tabLayout.getSelectedTabPosition()){
                    case 0:{
                        AddGiaoVienDialog dialog = new AddGiaoVienDialog();
                        dialog.show(getActivity().getSupportFragmentManager(),"Add Giao Vien Dialog");
                        break;
                    }
                    case 1:{
                        AddMonHocDialog dialog = new AddMonHocDialog();
                        dialog.show(getActivity().getSupportFragmentManager(),"Add Mon Hoc Dialog");
                        break;
                    }
                }
            }
        });
    }

}