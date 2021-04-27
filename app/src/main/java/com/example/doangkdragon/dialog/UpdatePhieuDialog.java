package com.example.doangkdragon.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.DialogAddPhieuBinding;
import com.example.doangkdragon.db.models.Phieu;

public class UpdatePhieuDialog extends DialogFragment {

    private DialogAddPhieuBinding binding;
    private Phieu phieuUpdate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_phieu,null,false);
        //setUpInfoPhieu();
//        setOnclickCancelBtn();
//        setOnclickSelectImg();
//        setOnclickSaveBtn();
        builder.setView(binding.getRoot());
        // Create the AlertDialog object and return it
        return builder.create();
    }
//    public void setUpInfoPhieu(){
//        binding.datePicker.set
//    }
}
