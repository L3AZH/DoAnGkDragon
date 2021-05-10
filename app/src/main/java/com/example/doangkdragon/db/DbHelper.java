package com.example.doangkdragon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.doangkdragon.db.models.Bai;
import com.example.doangkdragon.db.models.GiaoVien;
import com.example.doangkdragon.db.models.Mon;
import com.example.doangkdragon.db.models.Phieu;
import com.example.doangkdragon.db.models.SinhVien;
import com.example.doangkdragon.db.models.ThongTinPhieu;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MARKING.db";
    //Table GiaoVien
    private static final String TABLE_GV = "GIAOVIEN";
    private static final String COLUMN_MAGV = "MAGV";
    private static final String COLUMN_HOTENGV = "HOTENGV";
    private static final String COLUMN_SDT = "SDT";
    private static final String COLUMN_HINHGV = "HINHGV";
    //Table PhieuChamBai
    private static final String TABLE_PHIEUCHAMBAI = "PHIEUCHAMBAI";
    private static final String COLUMN_SOPHIEU = "SOPHIEU";
    private static final String COLUMN_NGAYGIAO = "NGAYGIAO";
    private static final String COLUMN_MAGV_FK = "MAGV_FK";
    //Table ThongTinChamBai
    private static final String TABLE_THONGTINCHAMBAI = "THONGTINCHAMBAI";
    private static final String COLUMN_SOPHIEU_FK = "SOPHIEU_FK";
    private static final String COLUMN_MAMH_FK = "MAMH_FK";
    private static final String COLUMN_SOBAI = "SOBAI";
    //Table MonHoc
    private static final String TABLE_MONHOC = "MONHOC";
    private static final String COLUMN_MAMH = "MAMH";
    private static final String COLUMN_TENMH = "TENMH";
    private static final String COLUMN_CHIPHI = "CHIPHI";
    //Table Bai
    private static final String TABLE_BAI = "BAI";
    public static final String COLUMN_MABAI = "MABAI";
    private static final String COLUMN_SOPHIEU_FK_BAI = "SOPHIEU_FK_BAI";
    private static final String COLUMN_MAMH_FK_BAI = "MAMH_FK_BAI";
    public static final String COLUMN_DIEM = "DIEM";
    public static final String COLUMN_TINHTRANG = "TINHTRANG";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG, "DbHelper: contruction");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: Tao table " + TABLE_GV);
        String query = "CREATE TABLE " + TABLE_GV + " ( " +
                COLUMN_MAGV + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HOTENGV + " TEXT, " +
                COLUMN_SDT + " INTEGER,  " +
                COLUMN_HINHGV + " BLOB );";
        db.execSQL(query);
        Log.i(TAG, "onCreate: Tao table " + TABLE_PHIEUCHAMBAI);
        query = "CREATE TABLE " + TABLE_PHIEUCHAMBAI + " ( " +
                COLUMN_SOPHIEU + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NGAYGIAO + " TEXT, " +
                COLUMN_MAGV_FK + " INTEGER, " +
                "FOREIGN KEY ( " + COLUMN_MAGV_FK + ") REFERENCES " + TABLE_GV + "(" + COLUMN_MAGV + ") );";
        db.execSQL(query);
        query = "CREATE TABLE " + TABLE_MONHOC + " ( " +
                COLUMN_MAMH + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TENMH + " TEXT, " +
                COLUMN_CHIPHI + " DOUBLE );";
        db.execSQL(query);
        query = "CREATE TABLE " + TABLE_THONGTINCHAMBAI + " ( " +
                COLUMN_SOPHIEU_FK + " INTEGER, " +
                COLUMN_MAMH_FK + " INTEGER, " +
                COLUMN_SOBAI + " INTEGER, " +
                "PRIMARY KEY (" + COLUMN_SOPHIEU_FK + ", " + COLUMN_MAMH_FK + "), " +
                "FOREIGN KEY ( " + COLUMN_SOPHIEU_FK + ") REFERENCES " + TABLE_PHIEUCHAMBAI + "(" + COLUMN_MAGV + "), " +
                "FOREIGN KEY ( " + COLUMN_MAMH_FK + ") REFERENCES " + TABLE_MONHOC + "(" + COLUMN_MAMH + ")); ";
        db.execSQL(query);
        query = "CREATE TABLE " + TABLE_BAI+ " ( " +
                COLUMN_MABAI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SOPHIEU_FK_BAI + " INTEGER, " +
                COLUMN_MAMH_FK_BAI + " INTEGER, " +
                COLUMN_DIEM + " INTEGER, " +
                COLUMN_TINHTRANG + " TEXT, " +
                "FOREIGN KEY ( " + COLUMN_SOPHIEU_FK_BAI + ") " +
                "REFERENCES " + TABLE_THONGTINCHAMBAI + "(" + COLUMN_SOPHIEU_FK + "), " +
                "FOREIGN KEY ( " + COLUMN_MAMH_FK_BAI + ") " +
                "REFERENCES " + TABLE_THONGTINCHAMBAI + "(" + COLUMN_MAMH_FK + ")); ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrade Database...........");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GV);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONHOC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHIEUCHAMBAI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THONGTINCHAMBAI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAI);
        onCreate(db);
    }

    public int addGiaoVien(GiaoVien giaoVien) {
        Log.i(TAG, "Adding Giao vien: " + giaoVien.getHoTenGv() + " into Database");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTENGV, giaoVien.getHoTenGv());
        values.put(COLUMN_SDT, giaoVien.getSDT());
        values.put(COLUMN_HINHGV, giaoVien.getHinh());
        try {
            db.insert(TABLE_GV, null, values);
            db.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addMonHoc(Mon monHoc) {
        Log.i(TAG, "Adding Mon hoc: " + monHoc.getTenMh() + " into Database");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENMH, monHoc.getTenMh());
        values.put(COLUMN_CHIPHI, monHoc.getChiPhi());
        try {
            db.insert(TABLE_MONHOC, null, values);
            db.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addPhieuChamBai(Phieu phieu){
        Log.i(TAG, "addPhieuChamBai: "+phieu.getMaPhieu()+" intoDatabase");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NGAYGIAO,phieu.getNgay());
        values.put(COLUMN_MAGV_FK,phieu.getMaGv());
        try {
            db.insert(TABLE_PHIEUCHAMBAI, null, values);
            db.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addBai(Bai bai){
        Log.i(TAG, "addBai: soPhieu-"+bai.getSoPhieu()+" va maMh-"+bai.getMaMonHoc()+" intoDatabase");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + COLUMN_SOBAI +
                " from " + TABLE_THONGTINCHAMBAI +
                " where " + COLUMN_SOPHIEU_FK + " = ? AND " +
                COLUMN_MAMH_FK + " = ? ;",new String[]{String.valueOf(bai.getSoPhieu()),String.valueOf(bai.getMaMonHoc())});
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount()==0){
                return -2;
            }
            else{
                int soBai = cursor.getInt(0);
                cursor = db.rawQuery("select * from "+TABLE_BAI+
                        " where "+COLUMN_SOPHIEU_FK_BAI+" = ? AND "+
                        COLUMN_MAMH_FK_BAI+" = ? ",new String[]{String.valueOf(bai.getSoPhieu()),String.valueOf(bai.getMaMonHoc())});
                if(cursor != null){
                    cursor.moveToFirst();
                    if (cursor.getCount()>=soBai){
                        return -1;
                    }
                    db = this.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_SOPHIEU_FK_BAI,bai.getSoPhieu());
                    values.put(COLUMN_MAMH_FK_BAI,bai.getMaMonHoc());
                    values.put(COLUMN_DIEM,bai.getDiem());
                    values.put(COLUMN_TINHTRANG,bai.getTinhTrang());
                    try {
                        db.insert(TABLE_BAI ,null, values);
                        db.close();
                        return 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
                return -2;
            }
        }
        return -2;
    }

    public boolean checkThongTinPhieuChamBai(ThongTinPhieu thongTinPhieu){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_THONGTINCHAMBAI + " WHERE "+COLUMN_MAMH_FK+" = ? AND "+COLUMN_SOPHIEU_FK+" = ?",
                new String[]{String.valueOf(thongTinPhieu.getMaMon()),String.valueOf(thongTinPhieu.getMaPhieu())});
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount() == 0){
                return true;
            }
            return false;
        }
        return true;
    }
    public int addThongTinPhieuChamBai(ThongTinPhieu thongTinPhieu){
        Log.i(TAG, "addThongTinPhieuChamBai: "+thongTinPhieu.getMaPhieu()+" intoDatabase");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOPHIEU_FK,thongTinPhieu.getMaPhieu());
        values.put(COLUMN_MAMH_FK,thongTinPhieu.getMaMon());
        values.put(COLUMN_SOBAI,thongTinPhieu.getSoBai());
        if(checkThongTinPhieuChamBai(thongTinPhieu)){
            try {
                db.insert(TABLE_THONGTINCHAMBAI, null, values);
                db.close();
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        else{
            return -1;
        }
    }

    public int updateGiaoVien(GiaoVien giaoVienUpdate){
        Log.i(TAG, "Updating Giao vien: " + giaoVienUpdate.getMaGv() + " into Database");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTENGV, giaoVienUpdate.getHoTenGv());
        values.put(COLUMN_SDT, giaoVienUpdate.getSDT());
        values.put(COLUMN_HINHGV, giaoVienUpdate.getHinh());
        try {
            int re = db.update(TABLE_GV,values,COLUMN_MAGV+" = ? " ,
                    new String[]{String.valueOf(giaoVienUpdate.getMaGv())});
            db.close();
            return re;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int updateMonHoc(Mon monHocUpdate){
        Log.i(TAG, "Updating Mon hoc: " + monHocUpdate.getMaMh() + " into Database");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENMH, monHocUpdate.getTenMh());
        values.put(COLUMN_CHIPHI, monHocUpdate.getChiPhi());
        try {
            int re = db.update(TABLE_MONHOC,values,COLUMN_MAMH+" = ? " ,
                    new String[]{String.valueOf(monHocUpdate.getMaMh())});
            db.close();
            return re;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int updatePhieu(Phieu phieu){
        Log.i(TAG, "Updating phieu: " + phieu.getMaPhieu() + " into Database");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NGAYGIAO, phieu.getNgay());
        try {
            int re = db.update(TABLE_PHIEUCHAMBAI,values,COLUMN_SOPHIEU+" = ? " ,
                    new String[]{String.valueOf(phieu.getMaPhieu())});
            db.close();
            return re;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int updateThongTinPhieu(ThongTinPhieu thongTinPhieu){
        Log.i(TAG, "Updating thong tin phieu: " + thongTinPhieu.getMaPhieu() + " into Database");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_BAI+
                " where "+COLUMN_SOPHIEU_FK_BAI+" = ? AND "+
                COLUMN_MAMH_FK_BAI+" = ? ",
                new String[]{String.valueOf(thongTinPhieu.getMaPhieu()),
                        String.valueOf(thongTinPhieu.getMaMon())});
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount() == 0){
                db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_SOBAI, thongTinPhieu.getSoBai());
                try {
                    int re = db.update(TABLE_THONGTINCHAMBAI,values,COLUMN_SOPHIEU_FK+" = ? AND "+COLUMN_MAMH_FK+" = ?" ,
                            new String[]{String.valueOf(thongTinPhieu.getMaPhieu()),String.valueOf(thongTinPhieu.getMaMon())});
                    db.close();
                    return re;
                } catch (Exception e) {
                    e.printStackTrace();
                    return -1;
                }
            }
            return -2;
        }
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOBAI, thongTinPhieu.getSoBai());
        try {
            int re = db.update(TABLE_THONGTINCHAMBAI,values,COLUMN_SOPHIEU_FK+" = ? AND "+COLUMN_MAMH_FK+" = ?" ,
                    new String[]{String.valueOf(thongTinPhieu.getMaPhieu()),String.valueOf(thongTinPhieu.getMaMon())});
            db.close();
            return re;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Vector<GiaoVien> getListGv() {
        Log.i(TAG, "getListMaGv ...");
        Vector<GiaoVien> getListGv = new Vector<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GV + ";", null);
        if (cursor != null) {
            cursor.moveToFirst();
            if(cursor.getCount() == 0){
                Log.i(TAG, "null");
                return null;
            }
            do {
                GiaoVien gvAdd = new GiaoVien(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getBlob(3));
                getListGv.add(gvAdd);
            } while (cursor.moveToNext());
            return getListGv;
        }
        Log.i(TAG, "null");
        return null;
    }

    public Vector<Mon> getListMonHoc(){
        Log.i(TAG, "getListMonHoc ...");
        Vector<Mon> getListMonHoc = new Vector<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MONHOC + ";", null);
        if (cursor != null) {
            cursor.moveToFirst();
            if(cursor.getCount() == 0){
                return null;
            }
            do {
                Mon monAdd = new Mon(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2));
                getListMonHoc.add(monAdd);
            } while (cursor.moveToNext());
            return getListMonHoc;
        }
        return null;
    }
    public Vector<Phieu> getListPhieu(int maGv){
        Log.i(TAG, "getListPhieu ...");
        Vector<Phieu> getListPhieu = new Vector<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_PHIEUCHAMBAI + " WHERE "+COLUMN_MAGV_FK+" = ?;",
                new String[]{String.valueOf(maGv)});
        if (cursor != null) {
            cursor.moveToFirst();
            if(cursor.getCount() == 0){
                return null;
            }
            do {
                Phieu phieuAdd = new Phieu(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2));
                getListPhieu.add(phieuAdd);
            } while (cursor.moveToNext());
            return getListPhieu;
        }
        return null;
    }
    public Vector<ThongTinPhieu> getListThongTinPhieu(int soPhieu){
        Log.i(TAG, "getListThongTinPhieu ...");
        Vector<ThongTinPhieu> getListThongTinPhieu = new Vector<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_THONGTINCHAMBAI + " WHERE "+COLUMN_SOPHIEU_FK+" = ?;",
                new String[]{String.valueOf(soPhieu)});
        if (cursor != null) {
            cursor.moveToFirst();
            if(cursor.getCount() == 0){
                return null;
            }
            do {
                ThongTinPhieu thongTinPhieuAdd = new ThongTinPhieu(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2));
                Log.i(TAG, "getListThongTinPhieu: sobai"+cursor.getInt(2));
                getListThongTinPhieu.add(thongTinPhieuAdd);
            } while (cursor.moveToNext());
            return getListThongTinPhieu;
        }
        return null;
    }
    public boolean checkDeleteGv(int maGv){
        Log.i(TAG, "Check giao vien da ton tai phieu  ...");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_PHIEUCHAMBAI + " WHERE "+COLUMN_MAGV_FK+" = ?;",
                new String[]{String.valueOf(maGv)});
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount() == 0){
                return true;
            }
            return false;
        }
        return true;
    }
    public int deleteGv(GiaoVien giaoVienDel){
        Log.i(TAG, "deleteGv: "+giaoVienDel.getMaGv()+" ....");
        SQLiteDatabase db = this.getWritableDatabase();
        if(checkDeleteGv(giaoVienDel.getMaGv())){
            db.delete(TABLE_GV,COLUMN_MAGV+"=?", new String[] {String.valueOf(giaoVienDel.getMaGv())});
            db.close();
            return 1;
        }
        else{
            return -1;
        }
    }
    public boolean checkDeleteMonHoc(int maMh){
        Log.i(TAG, "Check Mon hoc da ton tai thong tin phieu  ...");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_THONGTINCHAMBAI + " WHERE "+COLUMN_MAMH_FK+" = ?;",
                new String[]{String.valueOf(maMh)});
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount() == 0){
                return true;
            }
            return false;
        }
        return true;
    }
    public int deleteMonHoc(Mon mon){
        Log.i(TAG, "deleteMon: "+mon.getMaMh()+" ....");
        SQLiteDatabase db = this.getWritableDatabase();
        if(checkDeleteMonHoc(mon.getMaMh())){
            db.delete(TABLE_MONHOC,COLUMN_MAMH+"=?", new String[] {String.valueOf(mon.getMaMh())});
            db.close();
            return 1;
        }
        else{
            return -1;
        }
    }
    public boolean checkDeletePhieu(int maPhieu){
        Log.i(TAG, "Check phieu hoc da ton tai chi tiet thong tin phieu  ...");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_THONGTINCHAMBAI + " WHERE "+COLUMN_SOPHIEU_FK+" = ?;",
                new String[]{String.valueOf(maPhieu)});
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount() == 0){
                return true;
            }
            return false;
        }
        return true;
    }
    public int deletePhieu(Phieu phieu){
        Log.i(TAG, "deletePhieu: "+phieu.getMaPhieu()+" ....");
        SQLiteDatabase db = this.getWritableDatabase();
        if(checkDeletePhieu(phieu.getMaPhieu())){
            db.delete(TABLE_PHIEUCHAMBAI,COLUMN_SOPHIEU+"=?", new String[] {String.valueOf(phieu.getMaPhieu())});
            db.close();
            return 1;
        }
        else{
            return -1;
        }
    }
    public boolean checkDeleteThongTinPhieu(ThongTinPhieu thongTinPhieu){
        Log.i(TAG, "Check thong tin phieu da ton tai bai  ...");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_BAI+
                        " where "+COLUMN_SOPHIEU_FK_BAI+" = ? AND "+
                        COLUMN_MAMH_FK_BAI+" = ? ",
                new String[]{String.valueOf(thongTinPhieu.getMaPhieu()),
                        String.valueOf(thongTinPhieu.getMaMon())});
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount() == 0){
                return true;
            }
            return false;
        }
        return true;
    }
    public int deleteThongTinPhieu(ThongTinPhieu thongTinPhieu){
        Log.i(TAG, "deleteThongTinPhieu: "+thongTinPhieu.getMaPhieu()+" ....");
        SQLiteDatabase db = this.getWritableDatabase();
        if(checkDeleteThongTinPhieu(thongTinPhieu)){
            int re = db.delete(TABLE_THONGTINCHAMBAI,COLUMN_SOPHIEU_FK+"=? AND "+COLUMN_MAMH_FK+" = ?",
                    new String[] {String.valueOf(thongTinPhieu.getMaPhieu()),String.valueOf(thongTinPhieu.getMaMon())});
            db.close();
            return re;
        }
        else{
            return -1;
        }
    }
}
