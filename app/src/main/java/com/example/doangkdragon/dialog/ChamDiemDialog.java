package com.example.doangkdragon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.doangkdragon.databinding.DialogChamdiemBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Bai;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class ChamDiemDialog extends DialogFragment {

    public DialogChamdiemBinding binding;
    public UpdateListBai updateListBai;
    public Bai bai;
    public ChamDiemDialog(Bai bai){
        this.bai = bai;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_chamdiem, null, false);
        setOnclickSaveDiemBtn();
        builder.setView(binding.getRoot());
        return builder.create();
    }

    public void setOnclickSaveDiemBtn(){
        binding.saveDiemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int diem = Integer.parseInt(binding.diemEditText.getText().toString());
                    if(diem>10 || diem<0){
                        Snackbar.make(binding.getRoot(),"Diem khong hop le( 0-10)",Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        DbHelper db = new DbHelper(getContext());
                        bai.setDiem(diem);
                        bai.setTinhTrang("Da cham");
                        int resul = db.updateBai(bai);
                        Toast.makeText(getContext(), "ket qua update diem: "+resul, Toast.LENGTH_SHORT).show();
                        updateListBai.updateListBai(db.getListBai(bai.getSoPhieu(),bai.getMaMonHoc()));
                        db.close();
                        getDialog().cancel();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Snackbar.make(binding.getRoot(),"Diem khong hop le",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try{
            updateListBai = (UpdateListBai) context;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface UpdateListBai{
        public void updateListBai(Vector<Bai> listBai);
    }
}
