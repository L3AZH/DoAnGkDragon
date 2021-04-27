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
import com.example.doangkdragon.databinding.DialogAddMonhocBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Mon;

import java.util.Vector;

public class UpdateMonHocDialog extends DialogFragment {

    public DialogAddMonhocBinding binding;
    public Mon monHocUpdate;
    public UpdateMonHocDialogListener listener;
    public UpdateMonHocDialog(Mon monHocUpdate){
        this.monHocUpdate = monHocUpdate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_monhoc, null, false);
        builder.setView(binding.getRoot());
        setUpInfoMonHoc();
        setOnclickCancelBtn();
        setOnclickSaveBtn();
        return builder.create();
    }

    public void setUpInfoMonHoc(){
        binding.tenMonHocEditText.setText(monHocUpdate.getTenMh());
        binding.chiPhiEditText.setText(String.valueOf(monHocUpdate.getChiPhi()));
    }
    public void setOnclickCancelBtn(){
        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
    }

    public void setOnclickSaveBtn(){
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(getContext());
                Mon mon = new Mon(monHocUpdate.getMaMh(),binding.tenMonHocEditText.getText().toString(),
                        Double.parseDouble(binding.chiPhiEditText.getText().toString()));
                int re = db.updateMonHoc(mon);
                Toast.makeText(getContext(), "Update result "+re, Toast.LENGTH_SHORT).show();
                listener.update_updateMonHocDialog(db.getListMonHoc());
                db.close();
                getDialog().cancel();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateMonHocDialogListener) context;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface UpdateMonHocDialogListener{
        public void update_updateMonHocDialog(Vector<Mon> listMon);
    }
}
