package com.example.doangkdragon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.DialogInfoChitietthongtinphieuBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Bai;
import com.example.doangkdragon.db.models.ThongTinPhieu;
import com.google.android.material.snackbar.Snackbar;

import java.util.Vector;

public class InfoChiTietThongTinPhieuDialog extends DialogFragment {

    public DialogInfoChitietthongtinphieuBinding binding;
    public ThongTinPhieu thongTinPhieu;
    public UpdateInfoChiTietThongTinPhieuDialogListener listener;
    public InfoChiTietThongTinPhieuDialog(ThongTinPhieu thongTinPhieu){
        this.thongTinPhieu = thongTinPhieu;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_info_chitietthongtinphieu, null, false);
        builder.setView(binding.getRoot());
        setUpInfoChiTietThongTinPhieu();
        setOnclickCancelBtn();
        setOnclickDeleteBtn();
        setOnclickEditBtn();
        setOnclickAddBaiBtn();
        //setOnclickSaveBtn();
        return builder.create();
    }
    public void setUpInfoChiTietThongTinPhieu(){
        binding.soPhieuInfoTextView.setText("So phieu: "+thongTinPhieu.getMaPhieu());
        binding.maMonInfoTextView.setText("Ma mon: "+thongTinPhieu.getMaMon());
        binding.soBaiInfoTextView.setText("So bai: "+thongTinPhieu.getSoBai());
    }
    public void setOnclickCancelBtn(){
        binding.cancelChiTietThongTinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
    }
    public void setOnclickEditBtn(){
        binding.editChiTietThongTinPhieuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateChiTietThongTinPhieuDialog dialog = new UpdateChiTietThongTinPhieuDialog(thongTinPhieu);
                dialog.show(getActivity().getSupportFragmentManager(),"update thong tin phieu dialog");
                getDialog().cancel();
            }
        });
    }
    public void setOnclickDeleteBtn(){
        binding.deleteChiTietThongTinPhieuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(getContext());
                db.deleteThongTinPhieu(thongTinPhieu);
                listener.UpdateListChiTietThonTinPhieu_dialogInfoChiTietThongTinPhieu(
                        db.getListThongTinPhieu(thongTinPhieu.getMaPhieu()));
                db.close();
                getDialog().cancel();
            }
        });
    }
    public void setOnclickAddBaiBtn(){
        binding.addBaiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(getContext());
                int re = db.addBai(new Bai(
                                thongTinPhieu.getMaPhieu(),
                                thongTinPhieu.getMaMon(),0,"Chua cham"));
                db.close();
                switch (re){
                    case -2:{
                        Snackbar.make(binding.getRoot(),"Loi search db vui long check lai",Snackbar.LENGTH_LONG).show();
                        break;
                    }
                    case -1:{
                        Snackbar.make(binding.getRoot(),"So luong bai da vuot qua so luong bai dang ki !!",Snackbar.LENGTH_LONG).show();
                        break;
                    }
                    case 0:{
                        Snackbar.make(binding.getRoot(),"Add bai that bai",Snackbar.LENGTH_LONG).show();
                        break;
                    }
                    case 1:{
                        Snackbar.make(binding.getRoot(),"Add bai thanh cong",Snackbar.LENGTH_LONG).show();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateInfoChiTietThongTinPhieuDialogListener) context;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface UpdateInfoChiTietThongTinPhieuDialogListener{
        public void UpdateListChiTietThonTinPhieu_dialogInfoChiTietThongTinPhieu(Vector<ThongTinPhieu> listThongTinPhieu);
    }
}
