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


public class AddMonHocDialog extends DialogFragment {

    private DialogAddMonhocBinding binding;
    public UpdateDiaLogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_monhoc, null, false);
        builder.setView(binding.getRoot());
        setOnclickCancelBtn();
        setOnclickSaveBtn();
        return builder.create();
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
                if(binding.tenMonHocEditText.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền tên môn học", Toast.LENGTH_SHORT).show();
                }
                else if(binding.chiPhiEditText.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền chi phí", Toast.LENGTH_SHORT).show();
                }
                else{
                    DbHelper db = new DbHelper(getContext());
                    db.addMonHoc(new Mon(binding.tenMonHocEditText.getText().toString(),
                            Double.parseDouble(binding.chiPhiEditText.getText().toString())));
                    listener.updateListMonHoc(db.getListMonHoc());
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
        public void updateListMonHoc(Vector<Mon> listUpdate);
    }
}
