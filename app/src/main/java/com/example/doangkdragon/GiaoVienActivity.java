package com.example.doangkdragon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.doangkdragon.databinding.ActivityGiaoVienBinding;
import com.example.doangkdragon.db.models.Bai;
import com.example.doangkdragon.dialog.ChamDiemDialog;
import com.example.doangkdragon.fragment.GiaoVienChamBaiFragment;
import com.example.doangkdragon.fragment.QuanLyFragment;

import java.util.Vector;

public class GiaoVienActivity extends AppCompatActivity implements ChamDiemDialog.UpdateListBai {

    public ActivityGiaoVienBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_giao_vien);
    }

    @Override
    public void updateListBai(Vector<Bai> listBai) {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_gv);
        GiaoVienChamBaiFragment giaoVienChamBaiFragment =
                (GiaoVienChamBaiFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        giaoVienChamBaiFragment.baiAdapter.listBai = listBai;
        giaoVienChamBaiFragment.baiAdapter.notifyDataSetChanged();
        giaoVienChamBaiFragment.loadingProgress();
    }
}