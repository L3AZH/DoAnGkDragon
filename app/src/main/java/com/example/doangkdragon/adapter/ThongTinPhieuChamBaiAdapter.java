package com.example.doangkdragon.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.ItemThongtinphieuchambaiRecycleviewBinding;
import com.example.doangkdragon.db.models.ThongTinPhieu;

import java.util.Vector;

public class ThongTinPhieuChamBaiAdapter extends RecyclerView.Adapter<ThongTinPhieuChamBaiAdapter.MyViewHolder> {

    public Vector<ThongTinPhieu> listThongTinPhieu;
    public onClickItemListener listener;

    public ThongTinPhieuChamBaiAdapter(Vector<ThongTinPhieu> listThongTinPhieu){
        this.listThongTinPhieu = listThongTinPhieu;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemThongtinphieuchambaiRecycleviewBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_thongtinphieuchambai_recycleview,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setUpBinding(listThongTinPhieu.get(position));
    }

    @Override
    public int getItemCount() {
        if(listThongTinPhieu == null) return 0;
        else return listThongTinPhieu.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemThongtinphieuchambaiRecycleviewBinding binding;
        public MyViewHolder(ItemThongtinphieuchambaiRecycleviewBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
        public void setUpBinding(ThongTinPhieu thongTinPhieu){
            binding.maPhieuInforFkTextView.setText(String.valueOf(thongTinPhieu.getMaPhieu()));
            binding.maMonHocFkTextView.setText(String.valueOf(thongTinPhieu.getMaMon()));
            binding.soBaiTextView.setText(String.valueOf(thongTinPhieu.getSoBai()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener.onClickItem(thongTinPhieu);
                }
            });
        }
    }
    public interface onClickItemListener{
        public void onClickItem(ThongTinPhieu thongTinPhieu);
    }
}
