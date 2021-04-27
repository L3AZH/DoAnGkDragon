package com.example.doangkdragon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.ItemPhieuchambaiRecycleviewBinding;
import com.example.doangkdragon.db.models.Phieu;

import java.util.Vector;

public class PhieuChamBaiAdapter extends RecyclerView.Adapter<PhieuChamBaiAdapter.MyViewHolder> {

    public Vector<Phieu> listPhieu;
    public onClickItemListener listener;

    public PhieuChamBaiAdapter(Vector<Phieu> listPhieu){
        this.listPhieu = listPhieu;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPhieuchambaiRecycleviewBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_phieuchambai_recycleview, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setUpBinding(listPhieu.get(position));
    }

    @Override
    public int getItemCount() {
        if (listPhieu == null) return 0;
        else return listPhieu.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemPhieuchambaiRecycleviewBinding binding;

        public MyViewHolder(ItemPhieuchambaiRecycleviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setUpBinding(Phieu phieu) {
            binding.maPhieuTextView.setText("MP: " + String.valueOf(phieu.getMaPhieu()));
            binding.ngayGiaoBaiTextView.setText("Ngay: " + phieu.getNgay().toString());
            binding.maGvFkTextView.setText("MaGV: " + String.valueOf(phieu.getMaGv()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickItem(phieu);
                }
            });
        }
    }

    public interface onClickItemListener{
        public void onClickItem(Phieu phieu);
    }
}
