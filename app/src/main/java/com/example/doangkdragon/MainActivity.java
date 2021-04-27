package com.example.doangkdragon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.doangkdragon.databinding.ActivityMainBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.GiaoVien;
import com.example.doangkdragon.db.models.Mon;
import com.example.doangkdragon.db.models.Phieu;
import com.example.doangkdragon.db.models.ThongTinPhieu;
import com.example.doangkdragon.dialog.AddGiaoVienDialog;
import com.example.doangkdragon.dialog.AddMonHocDialog;
import com.example.doangkdragon.dialog.AddPhieuDialog;
import com.example.doangkdragon.dialog.AddThongTinPhieuDialog;
import com.example.doangkdragon.dialog.InfoMonHocDialog;
import com.example.doangkdragon.dialog.UpdateGiaoVienDialog;
import com.example.doangkdragon.dialog.UpdateMonHocDialog;
import com.example.doangkdragon.dialog.UpdatePhieuDialog;
import com.example.doangkdragon.fragment.GiaoVienFragment;
import com.example.doangkdragon.fragment.QuanLyFragment;
import com.example.doangkdragon.fragment.ThongKeFragment;
import com.example.doangkdragon.fragment.ThongTinPhieuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Vector;

public class MainActivity extends AppCompatActivity implements
        AddGiaoVienDialog.UpdateDialogListener,
        AddMonHocDialog.UpdateDiaLogListener,
        AddPhieuDialog.UpdateDiaLogListener,
        AddThongTinPhieuDialog.UpdateDiaLogListener,
        UpdateGiaoVienDialog.UpdateDialogListener,
        InfoMonHocDialog.UpdateInfoMonHocListener,
        UpdateMonHocDialog.UpdateMonHocDialogListener,
        UpdatePhieuDialog.UpdatePhieuDialogListenter {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController);
    }

    @Override
    public void updateListGv(Vector<GiaoVien> listUpdate) {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        QuanLyFragment quanLyFragment = (QuanLyFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        quanLyFragment.adapter.listGiaoVien = listUpdate;
        quanLyFragment.adapter.notifyDataSetChanged();
    }

    @Override
    public void updateListMonHoc(Vector<Mon> listUpdate) {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        QuanLyFragment quanLyFragment = (QuanLyFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        quanLyFragment.adapter.listMonHoc = listUpdate;
        quanLyFragment.adapter.notifyDataSetChanged();
    }

    @Override
    public void updateListPhieu(Vector<Phieu> listUpdate) {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        GiaoVienFragment giaoVienFragment = (GiaoVienFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        giaoVienFragment.phieuAdapter.listPhieu = listUpdate;
        giaoVienFragment.phieuAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateListThongTinPhieu(Vector<ThongTinPhieu> listUpdate) {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        ThongTinPhieuFragment thongTinPhieuFragment = (ThongTinPhieuFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        thongTinPhieuFragment.adapter.listThongTinPhieu = listUpdate;
        thongTinPhieuFragment.adapter.notifyDataSetChanged();
    }

    @Override
    public void updateListGv_update(GiaoVien giaoVienUpdate) {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        GiaoVienFragment giaoVienFragment = (GiaoVienFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        giaoVienFragment.gvGetFormList = giaoVienUpdate;
        giaoVienFragment.setInformationGv();
    }

    @Override
    public void updateListMonHoc_infoDialog(Vector<Mon> listMon) {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        QuanLyFragment quanLyFragment = (QuanLyFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        quanLyFragment.adapter.listMonHoc = listMon;
        quanLyFragment.adapter.notifyDataSetChanged();
    }

    @Override
    public void update_updateMonHocDialog(Vector<Mon> listMon) {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        QuanLyFragment quanLyFragment = (QuanLyFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        quanLyFragment.adapter.listMonHoc = listMon;
        quanLyFragment.adapter.notifyDataSetChanged();
    }

    @Override
    public void updateNgayPhieu(Phieu phieu) {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        ThongTinPhieuFragment thongTinPhieuFragment = (ThongTinPhieuFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        thongTinPhieuFragment.phieuGetFromList = phieu;
        thongTinPhieuFragment.setUpInfoPhieu();
    }
}