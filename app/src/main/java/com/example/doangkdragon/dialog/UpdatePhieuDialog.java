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
import com.example.doangkdragon.databinding.DialogAddPhieuBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Phieu;

public class UpdatePhieuDialog extends DialogFragment {

    private DialogAddPhieuBinding binding;
    private Phieu phieuUpdate;
    public UpdatePhieuDialogListenter listener;
    public UpdatePhieuDialog(Phieu phieu){
        this.phieuUpdate = phieu;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_phieu,null,false);
        setOnclickCancelBtn();
        setOnclickSaveBtn();
        builder.setView(binding.getRoot());
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public void setOnclickCancelBtn(){
        binding.cancelDialogAddDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
    }
    public void setOnclickSaveBtn(){
        binding.saveDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(getContext());
                String dateString = "";
                dateString = String.valueOf(binding.datePicker.getDayOfMonth()) + "-"+
                        String.valueOf(binding.datePicker.getMonth())+"-"+
                        String.valueOf(binding.datePicker.getYear());
                Phieu phieu = new Phieu(phieuUpdate.getMaPhieu(),dateString,phieuUpdate.getMaGv());
                db.updatePhieu(phieu);
                listener.updateNgayPhieu(phieu);
                db.close();
                getDialog().cancel();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdatePhieuDialogListenter) context;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface UpdatePhieuDialogListenter{
        public void updateNgayPhieu(Phieu phieu);
    }
}
