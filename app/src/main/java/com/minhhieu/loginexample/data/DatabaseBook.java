package com.minhhieu.loginexample.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import com.minhhieu.loginexample.model.Book;

import java.util.ArrayList;


public class DatabaseBook extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    //DatabaseLogin name
    private final static String DATABASE_NAME ="LGAPP.sqlite";
    //Table name
    private final static String TABLE_NAME = "Book";

    private final static String KEY_ID = "Id";
    private final static String KEY_TENSACH = "TenSach";
    private final static String KEY_TACGIA = "TacGia";
    private final static String KEY_NHASANXUAT = "NhaSX";
    private final static String KEY_THELOAI = "TheLoai";
    private final static String KEY_NGAYXUATBAN = "NgayXB";
    private final static String KEY_TRANG = "Trang";
    private final static String KEY_GIA = "Gia";
    private final static String KEY_NOIDUNG = "NoiDung";
    private final static String KEY_ANH = "Anh";
    private ArrayList<Book> list = new ArrayList<>();




    public DatabaseBook(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TENSACH + " TEXT,"
            + KEY_TACGIA + " TEXT,"
            + KEY_NHASANXUAT + " TEXT,"
            + KEY_THELOAI + " TEXT,"
            + KEY_NGAYXUATBAN + " DATE,"
            + KEY_TRANG + " INT,"
            + KEY_GIA + " FLOAT,"
            + KEY_NOIDUNG + " TEXT,"
            + KEY_ANH + " TEXT" + ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        Log.d("BBB", "Tạo DataBook thành công");
    }
    //insert book
    public void addBook(Book book){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID,book.getId());
        values.put(KEY_TENSACH,book.getTenSach());
        values.put(KEY_TACGIA,book.getTacGia());
        values.put(KEY_NHASANXUAT,book.getNhaSX());
        values.put(KEY_THELOAI,book.getTheLoai());
        values.put(KEY_NGAYXUATBAN,book.getNgayXB());
        values.put(KEY_TRANG,book.getTrang());
        values.put(KEY_GIA,book.getGia());
        values.put(KEY_NOIDUNG,book.getNoiDung());
        values.put(KEY_ANH,book.getAnh());
        sqLiteDatabase.insert(TABLE_NAME,null,values);
        sqLiteDatabase.close();
    }

    //update book
    public void updateBook(Book book, String id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID,id);
        values.put(KEY_TENSACH,book.getTenSach());
        values.put(KEY_TACGIA,book.getTacGia());
        values.put(KEY_NHASANXUAT,book.getNhaSX());
        values.put(KEY_THELOAI,book.getTheLoai());
        values.put(KEY_NGAYXUATBAN,book.getNgayXB());
        values.put(KEY_TRANG,book.getTrang());
        values.put(KEY_GIA,book.getGia());
        values.put(KEY_NOIDUNG,book.getNoiDung());
        values.put(KEY_ANH,book.getAnh());
        sqLiteDatabase.update(TABLE_NAME,values,KEY_ID + " = ?",new String[]{id});
        sqLiteDatabase.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    public ArrayList<Book> getAllBook() {
        ArrayList<Book> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" LIMIT 10,8";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                book.setTenSach(cursor.getString(cursor.getColumnIndex(KEY_TENSACH)));
                book.setTacGia(cursor.getString(cursor.getColumnIndex(KEY_TACGIA)));
                book.setNhaSX(cursor.getString(cursor.getColumnIndex(KEY_NHASANXUAT)));
                book.setTheLoai(cursor.getString(cursor.getColumnIndex(KEY_THELOAI)));
                book.setNgayXB(cursor.getString(cursor.getColumnIndex(KEY_NGAYXUATBAN)));
                book.setTrang(cursor.getInt(cursor.getColumnIndex(KEY_TRANG)));
                book.setGia(cursor.getFloat(cursor.getColumnIndex(KEY_GIA)));
                book.setNoiDung(cursor.getString(cursor.getColumnIndex(KEY_NOIDUNG)));
                book.setAnh(cursor.getString(cursor.getColumnIndex(KEY_ANH)));
                list.add(book);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }


    public ArrayList<Book> getBookPage1() {

        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" LIMIT 0,8" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                book.setTenSach(cursor.getString(cursor.getColumnIndex(KEY_TENSACH)));
                book.setTacGia(cursor.getString(cursor.getColumnIndex(KEY_TACGIA)));
                book.setNhaSX(cursor.getString(cursor.getColumnIndex(KEY_NHASANXUAT)));
                book.setTheLoai(cursor.getString(cursor.getColumnIndex(KEY_THELOAI)));
                book.setNgayXB(cursor.getString(cursor.getColumnIndex(KEY_NGAYXUATBAN)));
                book.setTrang(cursor.getInt(cursor.getColumnIndex(KEY_TRANG)));
                book.setGia(cursor.getFloat(cursor.getColumnIndex(KEY_GIA)));
                book.setNoiDung(cursor.getString(cursor.getColumnIndex(KEY_NOIDUNG)));
                book.setAnh(cursor.getString(cursor.getColumnIndex(KEY_ANH)));
                list.add(book);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }



    public ArrayList<Book> getLoadMoreBook(int limit ) {

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " LIMIT " + limit + " ,8" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                book.setTenSach(cursor.getString(cursor.getColumnIndex(KEY_TENSACH)));
                book.setTacGia(cursor.getString(cursor.getColumnIndex(KEY_TACGIA)));
                book.setNhaSX(cursor.getString(cursor.getColumnIndex(KEY_NHASANXUAT)));
                book.setTheLoai(cursor.getString(cursor.getColumnIndex(KEY_THELOAI)));
                book.setNgayXB(cursor.getString(cursor.getColumnIndex(KEY_NGAYXUATBAN)));
                book.setTrang(cursor.getInt(cursor.getColumnIndex(KEY_TRANG)));
                book.setGia(cursor.getFloat(cursor.getColumnIndex(KEY_GIA)));
                book.setNoiDung(cursor.getString(cursor.getColumnIndex(KEY_NOIDUNG)));
                book.setAnh(cursor.getString(cursor.getColumnIndex(KEY_ANH)));
                list.add(book);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }

}
