package com.example.doangkdragon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.DialogAddPhieuBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Phieu;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class AddPhieuDialog  extends DialogFragment {

    private DialogAddPhieuBinding binding;
    public UpdateDiaLogListener listener;
    private int maGv;

    public AddPhieuDialog(int maGv){
        this.maGv = maGv;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_phieu, null, false);
        builder.setView(binding.getRoot());
        setOnclickCancelBtn();
        setOnclickSaveBtn();
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
                String dateString = "";
                dateString = String.valueOf(binding.datePicker.getDayOfMonth()) + "-"+
                        String.valueOf(binding.datePicker.getMonth())+"-"+
                        String.valueOf(binding.datePicker.getYear());
                Phieu phieu = new Phieu(dateString,maGv);
                DbHelper db = new DbHelper(getContext());
                db.addPhieuChamBai(phieu);
                listener.updateListPhieu(db.getListPhieu(maGv));
                db.close();
                getDialog().cancel();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateDiaLogListener) context;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface UpdateDiaLogListener{
        public void updateListPhieu(Vector<Phieu> listUpdate);
    }
}
