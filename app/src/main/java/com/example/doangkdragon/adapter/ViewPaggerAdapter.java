package com.example.doangkdragon.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.ItemViewPagerBinding;
import com.example.doangkdragon.db.models.GiaoVien;
import com.example.doangkdragon.db.models.Mon;
import com.example.doangkdragon.dialog.AddGiaoVienDialog;
import com.example.doangkdragon.dialog.InfoMonHocDialog;

import java.util.List;
import java.util.Vector;

public class ViewPaggerAdapter extends RecyclerView.Adapter<ViewPaggerAdapter.MyViewHolder> {

    private List<String> listQuanLy;
    public Vector<GiaoVien> listGiaoVien;
    public Vector<Mon> listMonHoc;
    public Context context;
    public handlerOnclickItemGv handlerListener;
    public handerOnclickItemMonHoc handlerListenerMonHoc;

    public ViewPaggerAdapter(List<String> listQuanLy,Context context) {
        this.listQuanLy = listQuanLy;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemViewPagerBinding itemViewPagerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_view_pager, parent, false);
        return new MyViewHolder(itemViewPagerBinding,this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setUpBinding(listQuanLy.get(position),listGiaoVien,listMonHoc);
    }

    @Override
    public int getItemCount() {
        return this.listQuanLy.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ItemViewPagerBinding binding;
        private Context context;

        public MyViewHolder(ItemViewPagerBinding binding,Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        public void setUpBinding(String nameObject,Vector<GiaoVien> giaoViens,Vector<Mon> mons){
            switch (nameObject){
                case "Giáo Viên":{
                    Log.i("MyViewHolder", "setUpBinding: Run case Giao vien");
                    binding.itemRecycleView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                    GiaoVienAdapter gvAdapter = new GiaoVienAdapter(giaoViens);
                    gvAdapter.listener = new GiaoVienAdapter.onClickItemListener() {
                        @Override
                        public void onClickListener(GiaoVien giaoVien) {
                            handlerListener.handlerOnclick(giaoVien);
                        }

                        @Override
                        public boolean onLongClickListener(GiaoVien giaoVien) {
                            return handlerListener.handlerLongclick(giaoVien);
                        }
                    };
                    binding.itemRecycleView.setAdapter(gvAdapter);
                    break;
                }
                case  "Môn Học":{
                    Log.i("MyViewHolder", "setUpBinding: Run case Mon Hoc");
                    binding.itemRecycleView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                    MonHocAdater mhAdapter = new MonHocAdater(mons);
                    mhAdapter.listener = new MonHocAdater.onClickItemListener() {
                        @Override
                        public void onClickItem(Mon mon) {
                            handlerListenerMonHoc.handlerOnclickMonHoc(mon);
                        }
                    };
                    binding.itemRecycleView.setAdapter(mhAdapter);
                    break;
                }
            }
        }
    }
    public interface handlerOnclickItemGv{
        public void handlerOnclick(GiaoVien giaoVien);
        public boolean handlerLongclick(GiaoVien giaoVien);
    }
    public interface handerOnclickItemMonHoc{
        public void handlerOnclickMonHoc(Mon mon);
    }
}
