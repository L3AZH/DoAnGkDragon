package com.example.doangkdragon.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.DialogAddGvBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.GiaoVien;

import java.io.ByteArrayOutputStream;
import java.util.Vector;

public class AddGiaoVienDialog extends DialogFragment {

    private DialogAddGvBinding binding;
    public UpdateDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_gv,null,false);
        setOnclickCancelBtn();
        setOnclickSelectImg();
        setOnclickSaveBtn();
        builder.setView(binding.getRoot());
        // Create the AlertDialog object and return it
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
    public void setOnclickSelectImg(){
        binding.hinhGvImgView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "run this shit", Toast.LENGTH_SHORT).show();
                if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,1);
                }
            }
        });
    }
    public void setOnclickSaveBtn(){
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.hoTenGvEditText.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền họ tên", Toast.LENGTH_SHORT).show();
                }
                else if(binding.sdtGvEditText.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền sdt", Toast.LENGTH_SHORT).show();
                }
                else{
                    DbHelper db = new DbHelper(getActivity());
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.hinhGvImgView.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.PNG ,100,bos);
                    byte[] byteArray = bos.toByteArray();
                    db.addGiaoVien(new GiaoVien(binding.hoTenGvEditText.getText().toString(),
                            Integer.parseInt(binding.sdtGvEditText.getText().toString()),byteArray));
                    listener.updateListGv(db.getListGv());
                    db.close();
                    getDialog().cancel();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 2){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null){
            Uri img = data.getData();
            try{
                Bitmap selectImg = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(),img);
                binding.hinhGvImgView.setImageBitmap(selectImg);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateDialogListener) context;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface UpdateDialogListener{
        public void updateListGv(Vector<GiaoVien> listUpdate);
    }

}