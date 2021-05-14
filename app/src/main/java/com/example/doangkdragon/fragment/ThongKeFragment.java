package com.example.doangkdragon.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.doangkdragon.R;
import com.example.doangkdragon.databinding.FragmentThongKeBinding;
import com.example.doangkdragon.db.DbHelper;
import com.example.doangkdragon.db.models.Bai;
import com.example.doangkdragon.db.models.GiaoVien;
import com.example.doangkdragon.db.models.Phieu;
import com.example.doangkdragon.db.models.ThongTinPhieu;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Vector;

public class ThongKeFragment extends Fragment {

    public FragmentThongKeBinding binding;
    private Vector<String> listNameThongKe = new Vector<>();
    private Vector<Integer> listSoLieuThongKe= new Vector<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_thong_ke, container, false);
        listNameThongKe.add("Da hoan thanh");
        listNameThongKe.add("Dang cham");
        listNameThongKe.add("Chua Cham");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnclickLoadSpinnerBtn();
        setUpThongKeBtn();
    }

    public void setOnclickLoadSpinnerBtn(){
        binding.loadSpinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setUpSpinner();
                }
                catch (Exception e){
                    Snackbar.make(binding.getRoot(),"Loi nhap lieu",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setUpSpinner(){
        DbHelper db = new DbHelper(getContext());
        Vector<Phieu> listPhieu = db.getListPhieu(Integer.parseInt(binding.maGvTextEdit.getText().toString()));
        String[] listPhieuString = new String[listPhieu.size()];
        for (int i=0;i<listPhieu.size();i++){
            listPhieuString[i] = String.valueOf(listPhieu.get(i).getMaPhieu());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,listPhieuString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);
    }

    public Vector<Integer> analyticTinhTrangPhieu(int maphieu) {
        int tongSoPhieuDaHoanThanh=0;
        int tongSoPhieuDangCham=0;
        int tongSoPhieuChuaCham=0;
        Vector<Integer> result = new Vector<>();
        DbHelper db = new DbHelper(getContext());
        Vector<ThongTinPhieu> listThongTinPhieu = db.getListThongTinPhieu(maphieu);
        if (listThongTinPhieu != null){
            for(ThongTinPhieu thongTinPhieu:listThongTinPhieu){
                if (db.getListBaiDaCham(thongTinPhieu.getMaPhieu(),thongTinPhieu.getMaMon()) == null){
                    tongSoPhieuChuaCham++;
                }
                else if (db.getListBaiDaCham(thongTinPhieu.getMaPhieu(),thongTinPhieu.getMaMon()).size()>0 &&
                        db.getListBaiDaCham(thongTinPhieu.getMaPhieu(),thongTinPhieu.getMaMon()).size()<thongTinPhieu.getSoBai()){
                    tongSoPhieuDangCham++;
                }
                else if(db.getListBaiDaCham(thongTinPhieu.getMaPhieu(),thongTinPhieu.getMaMon()).size()==thongTinPhieu.getSoBai()){
                    tongSoPhieuDaHoanThanh++;
                }
            }
            db.close();
            result.add(tongSoPhieuDaHoanThanh);
            result.add(tongSoPhieuDangCham);
            result.add(tongSoPhieuChuaCham);
            return result;
        }
        return null;
    }

    public void setUpThongKeBtn(){
        binding.thongKeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                DbHelper db = new DbHelper(getContext());
                Vector<GiaoVien> listGv = db.getListGv();
                try {
                    for(int i=0;i<listGv.size();i++){
                        if(Integer.parseInt(binding.maGvTextEdit.getText().toString()) == listGv.get(i).getMaGv()){
                            flag = true;
                            break;
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    flag = false;
                }
                if(flag){
                    int maGv = Integer.parseInt(binding.maGvTextEdit.getText().toString());
                    Vector<Phieu> listPhieu = db.getListPhieu(maGv);
                    if(listPhieu != null){
                        try {
                            showSoDo();
                            setData(3,analyticTinhTrangPhieu(
                                    Integer.parseInt(binding.spinner.getSelectedItem().toString())),
                                    listNameThongKe);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            Snackbar.make(binding.getRoot(),"Giao vien nay chua co thong tin phieu cham bai",Snackbar.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "Giao vien nay chua co list phieu", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Snackbar.make(binding.getRoot(),"Khong tim thay ma giao vien",Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
    private void setData(int count, Vector<Integer> listData,Vector<String> listName) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) listData.get(i),
                    listName.get(i),
                    getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Thong Ke Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(17f);
        data.setValueTextColor(Color.BLACK);
        //data.setValueTypeface(tfLight);
        binding.soDoThongKe.setData(data);

        // undo all highlights
        binding.soDoThongKe.highlightValues(null);

        binding.soDoThongKe.invalidate();
    }
    public void showSoDo(){
        binding.soDoThongKe.setUsePercentValues(true);
        binding.soDoThongKe.getDescription().setEnabled(false);
        binding.soDoThongKe.setExtraOffsets(5, 10, 5, 5);

        binding.soDoThongKe.setDragDecelerationFrictionCoef(0.95f);

        binding.soDoThongKe.setDrawHoleEnabled(true);
        binding.soDoThongKe.setHoleColor(Color.WHITE);

        binding.soDoThongKe.setTransparentCircleColor(Color.WHITE);
        binding.soDoThongKe.setTransparentCircleAlpha(110);

        binding.soDoThongKe.setHoleRadius(58f);
        binding.soDoThongKe.setTransparentCircleRadius(61f);

        binding.soDoThongKe.setDrawCenterText(true);

        binding.soDoThongKe.setRotationAngle(0);
        // enable rotation of the chart by touch
        binding.soDoThongKe.setRotationEnabled(true);
        binding.soDoThongKe.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);


        binding.soDoThongKe.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = binding.soDoThongKe.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        binding.soDoThongKe.setEntryLabelColor(Color.BLACK);
        binding.soDoThongKe.setEntryLabelTextSize(20f);
    }
}