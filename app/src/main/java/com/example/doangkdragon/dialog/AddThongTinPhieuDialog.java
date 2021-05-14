package com.example.doangkdragon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.DialogAddThongtinphieuBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Bai;
import com.example.doangkdragon.db.models.Mon;
import com.example.doangkdragon.db.models.Phieu;
import com.example.doangkdragon.db.models.ThongTinPhieu;

import java.util.Vector;

public class AddThongTinPhieuDialog extends DialogFragment {
    private DialogAddThongtinphieuBinding binding;
    public UpdateDiaLogListener listener;
    private int soPhieu;

    public AddThongTinPhieuDialog(int soPhieu){
        this.soPhieu = soPhieu;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_thongtinphieu, null, false);
        builder.setView(binding.getRoot());
        setUpSpinner();
        setOnclickCancelBtn();
        setOnclickSaveBtn();
        return builder.create();
    }
    public void setUpSpinner(){
        DbHelper db = new DbHelper(getContext());
        Vector<String> listMonString = new Vector<>();
        Vector<Mon> listMon = db.getListMonHoc();
        if(listMon == null){
            listMonString.add("List Mon hoc rong !!");
        }
        else{
            for(Mon monHoc: listMon){
                listMonString.add(String.valueOf(monHoc.getMaMh())+"-"+monHoc.getTenMh());
            }
        }
        db.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,listMonString);
        binding.spinnerMaMon.setAdapter(adapter);
    }
    public void setOnclickCancelBtn(){
        binding.cancelThongTinPhieuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
    }
    public void setOnclickSaveBtn(){
        binding.saveThongTinPhieuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.soBaiEditText.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Vui long dien so bai", Toast.LENGTH_SHORT).show();
                }
                else{
                    DbHelper db = new DbHelper(getContext());
                    String thongTinPhieuAddString[] = binding.spinnerMaMon.getSelectedItem().toString().split("-");
                    ThongTinPhieu thongTinPhieuAdd = new ThongTinPhieu(soPhieu,
                            Integer.parseInt(thongTinPhieuAddString[0]),Integer.parseInt(binding.soBaiEditText.getText().toString()));
                    int re = db.addThongTinPhieuChamBai(thongTinPhieuAdd);
                    Toast.makeText(getContext(), "Result add = "+re, Toast.LENGTH_SHORT).show();
                    for(int i=0;i<Integer.parseInt(binding.soBaiEditText.getText().toString());i++){
                        db.addBai(new Bai(
                                thongTinPhieuAdd.getMaPhieu(),
                                thongTinPhieuAdd.getMaMon(),0,"Chua cham"));
                    }
                    listener.updateListThongTinPhieu(db.getListThongTinPhieu(soPhieu));
                    db.close();
                    getDialog().cancel();
                }
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
        public void updateListThongTinPhieu(Vector<ThongTinPhieu> listUpdate);
    }
}
