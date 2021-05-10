package com.example.doangkdragon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.doangkdragon.databinding.ActivityLoginBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.GiaoVien;
import com.google.android.material.snackbar.Snackbar;

import java.util.Vector;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setOnlickLoginBtn();
    }

    public void setOnlickLoginBtn() {
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.email.getText().toString().isEmpty()) {
                    binding.emailError.setError("vui long nhap id hoac email");
                } else if (binding.password.getText().toString().isEmpty()) {
                    binding.passError.setError("vui long nhap mat khau");
                } else if (binding.email.getText().toString().equals("admin@gmail.com")) {
                    if (binding.password.getText().toString().equals("123456")) {
                        Intent goToAdminActivity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToAdminActivity);
                    } else {
                        Snackbar.make(binding.getRoot(), "sai mat khau", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        int idGv = Integer.parseInt(binding.email.getText().toString());
                        DbHelper dbHelper = new DbHelper(getApplicationContext());
                        Vector<GiaoVien> listGv = dbHelper.getListGv();
                        boolean flag = false;
                        for (GiaoVien gv : listGv) {
                            if (idGv == gv.getMaGv()) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            if (binding.password.getText().toString().equals("123456")){
                                Intent goToGiaoVienActivity = new Intent(LoginActivity.this, GiaoVienActivity.class);
                                goToGiaoVienActivity.putExtra("magv", idGv);
                                startActivity(goToGiaoVienActivity);
                            }
                            else{
                                Snackbar.make(binding.getRoot(), "sai mat khau", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(binding.getRoot(), "Id hoac email khong ton tai", Snackbar.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Snackbar.make(binding.getRoot(), "Id hoac email khong hop le", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}