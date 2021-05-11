package com.example.doangkdragon.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.ItemBaiRecycleviewBinding;
import com.example.doangkdragon.db.models.Bai;

import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public class BaiAdapter extends RecyclerView.Adapter<BaiAdapter.BaiViewHolder> {

    public Vector<Bai> listBai;
    public OnItemClickListener listener;

    public BaiAdapter(Vector<Bai> listBai) {
        this.listBai = listBai;
    }

    @NonNull
    @NotNull
    @Override
    public BaiViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemBaiRecycleviewBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_bai_recycleview, parent, false);
        return new BaiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BaiViewHolder holder, int position) {
        holder.setUpBinding(listBai.get(position));
    }

    @Override
    public int getItemCount() {
        if (listBai == null) return 0;
        return listBai.size();
    }

    class BaiViewHolder extends RecyclerView.ViewHolder {
        private ItemBaiRecycleviewBinding binding;

        public BaiViewHolder(ItemBaiRecycleviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setUpBinding(Bai bai) {
            binding.maBaiTextView.setText("MABAI: " + bai.getMaBai());
            binding.soPhieuTextView.setText("SOPHIEU: " + bai.getSoPhieu());
            binding.maMonTextView.setText("MAMON: " + bai.getMaMonHoc());
            binding.diemTextView.setText("DIEM: " + bai.getDiem());
            binding.tinhTrangTextView.setText("TINHTRANG: " + bai.getTinhTrang());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(bai);
                }
            });
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(Bai bai);
    }
}
