package com.example.doangkdragon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.ItemMonhocRecycleviewBinding;
import com.example.doangkdragon.db.models.Mon;

import java.util.Vector;

public class MonHocAdater extends RecyclerView.Adapter<MonHocAdater.MyViewHolder> {

    public Vector<Mon> listMon ;

    public MonHocAdater(Vector<Mon> listMon){
        this.listMon = listMon;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMonhocRecycleviewBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_monhoc_recycleview,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setUpBinding(listMon.get(position));
    }

    @Override
    public int getItemCount() {
        if(listMon == null) return 0;
        else{
            return listMon.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ItemMonhocRecycleviewBinding binding;

        public MyViewHolder(ItemMonhocRecycleviewBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setUpBinding(Mon monHoc){
            binding.maMhTextView.setText(String.valueOf(monHoc.getMaMh()));
            binding.tenMhTextView.setText(monHoc.getTenMh());
            binding.chiPhiTextView.setText(String.valueOf(monHoc.getChiPhi()));
        }
    }
}
