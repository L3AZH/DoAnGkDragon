package com.example.doangkdragon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.doangkdragon.databinding.ActivityGiaoVienBinding;

public class GiaoVienActivity extends AppCompatActivity {

    public ActivityGiaoVienBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_giao_vien);
    }
}