package com.example.doangkdragon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.example.doangkdragon.databinding.ActivityMainBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.GiaoVien;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        db = new DbHelper(this);
        loadSpinner();
    }

    public void loadSpinner(){
        Vector<GiaoVien>listGv = db.getListGv();
        Vector<Integer> listMaGv = new Vector<>();
        if(listGv != null){
            for(GiaoVien x: listGv){
                listMaGv.add(x.getMaGv());
            }
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,listMaGv);
        binding.maGvspinner.setAdapter(adapter);
    }
}