package com.example.doangkdragon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.DialogInfoMonhocBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Mon;

import java.util.Vector;

public class InfoMonHocDialog extends DialogFragment {

    public DialogInfoMonhocBinding binding;
    public Mon monHocInfo;
    public UpdateInfoMonHocListener listener;
    public InfoMonHocDialog(Mon monHocInfo){
        this.monHocInfo = monHocInfo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_info_monhoc, null, false);
        builder.setView(binding.getRoot());
        setUpInfoMonHoc();
        setOnclickCancelBtn();
        setOnclickDeleteBtn();
        setOnclickEditBtn();
        //setOnclickSaveBtn();
        return builder.create();
    }

    public void setUpInfoMonHoc(){
        binding.tenMonHocInfoTextView.setText("Ten MH: "+monHocInfo.getTenMh());
        binding.chiPhiMonHocInfoTextView.setText(String.valueOf("Chi phi: "+monHocInfo.getChiPhi()));
    }

    public void setOnclickCancelBtn(){
        binding.cancelMonHocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
    }

    public void setOnclickEditBtn(){
        binding.editMonHocInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateMonHocDialog dialog = new UpdateMonHocDialog(monHocInfo);
                dialog.show(getActivity().getSupportFragmentManager(),"update mon hoc dialog");
                getDialog().cancel();
            }
        });
    }
    public void setOnclickDeleteBtn(){
        binding.deleteMonHocInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(getContext());
                int re = db.deleteMonHoc(monHocInfo);
                Toast.makeText(getContext(), "Delete result = "+re, Toast.LENGTH_SHORT).show();
                listener.updateListMonHoc_infoDialog(db.getListMonHoc());
                db.close();
                getDialog().cancel();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateInfoMonHocListener) context;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface UpdateInfoMonHocListener{
        public void updateListMonHoc_infoDialog(Vector<Mon> listMon);
    }
}
