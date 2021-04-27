package com.example.doangkdragon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import com.example.doangkdragon.db.models.ThongTinPhieu;

import java.util.Vector;

public class UpdateChiTietThongTinPhieuDialog extends DialogFragment {

    public DialogAddThongtinphieuBinding binding;
    public ThongTinPhieu thongTinPhieu;
    public UpdateChiTietThongTinPhieuListener listener;
    public UpdateChiTietThongTinPhieuDialog(ThongTinPhieu thongTinPhieu){
        this.thongTinPhieu = thongTinPhieu;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_thongtinphieu, null, false);
        builder.setView(binding.getRoot());
        setUpInfoThongTinPhieu();
        setOnclickCancelBtn();
        setOnclickSaveBtn();
        return builder.create();
    }

    public void setUpInfoThongTinPhieu(){
        Vector<String> list = new Vector<>();
        list.add(String.valueOf(thongTinPhieu.getMaMon()));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,list);
        binding.spinnerMaMon.setAdapter(adapter);
        binding.soBaiEditText.setText(String.valueOf(thongTinPhieu.getSoBai()));
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
                    Toast.makeText(getContext(), "Vui long nhap so bai", Toast.LENGTH_SHORT).show();
                }
                else{
                    DbHelper db = new DbHelper(getContext());
                    ThongTinPhieu thongTinPhieuUpdate = new ThongTinPhieu(thongTinPhieu.getMaPhieu(),
                            thongTinPhieu.getMaMon(),Integer.parseInt(binding.soBaiEditText.getText().toString()));
                    db.updateThongTinPhieu(thongTinPhieuUpdate);
                    listener.updateListThongTinPhieu_dialogUpdateChiTietThongTinPhieu(db.getListThongTinPhieu(thongTinPhieuUpdate.getMaPhieu()));
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
            listener = (UpdateChiTietThongTinPhieuListener) context;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface UpdateChiTietThongTinPhieuListener{
        public void updateListThongTinPhieu_dialogUpdateChiTietThongTinPhieu(Vector<ThongTinPhieu> listThongTinPhieu);
    }

}
