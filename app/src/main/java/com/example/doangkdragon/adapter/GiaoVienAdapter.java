package com.example.doangkdragon.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.ItemGiaovienRecycleviewBinding;
import com.example.doangkdragon.db.models.GiaoVien;

import java.util.Vector;

public class GiaoVienAdapter extends RecyclerView.Adapter<GiaoVienAdapter.MyViewHolder> {

    public Vector<GiaoVien> listGv;
    public onClickItemListener listener;

    public GiaoVienAdapter(Vector<GiaoVien> listGv){
        this.listGv = listGv;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemGiaovienRecycleviewBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_giaovien_recycleview,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setUpBinding(listGv.get(position));
    }

    @Override
    public int getItemCount() {
        if(listGv == null) return 0;
        else{
            return listGv.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemGiaovienRecycleviewBinding binding;

        public MyViewHolder (ItemGiaovienRecycleviewBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setUpBinding(GiaoVien giaoVien){
            binding.maGvTextView.setText(String.valueOf(giaoVien.getMaGv()));
            binding.tenGvTextView.setText(giaoVien.getHoTenGv());
            binding.sdtTextView.setText(String.valueOf(giaoVien.getSDT()));
            binding.imageView.setImageBitmap(BitmapFactory.decodeByteArray(giaoVien.getHinh(),0,giaoVien.getHinh().length));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(giaoVien);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return listener.onLongClickListener(giaoVien);

                }
            });
        }
    }

    interface onClickItemListener{
        public void onClickListener(GiaoVien giaoVien);
        public boolean onLongClickListener(GiaoVien giaoVien);
    }
}
