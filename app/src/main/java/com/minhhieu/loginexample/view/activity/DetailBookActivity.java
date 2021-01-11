package com.minhhieu.loginexample.view.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.minhhieu.loginexample.R;

public class DetailBookActivity extends AppCompatActivity {

    TextView txtTenSach, txtTacGia, txtSoTrang, txtNoiDung, txtTheLoai;
    ImageView imgBook;
    private boolean editMode = false;
    String imagePath;

    private String id, tenSach, tacGia, nhaSX, theLoai, ngayXB, trang,noiDung, gia,anh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        initLayout();
        getDataBook();
        imagePath = anh;

    }

    private void initLayout(){
        txtTenSach = findViewById(R.id.txtTenSach);
        txtTacGia = findViewById(R.id.txtTacGia);
        txtSoTrang = findViewById(R.id.txt_soTrang);
        txtNoiDung = findViewById(R.id.txt_noidung);
        imgBook = findViewById(R.id.imgv_book);
        txtTheLoai = (TextView) findViewById(R.id.txtTheloai);


    }
    private void getDataBook() {
        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editMode",editMode);

        tenSach = intent.getStringExtra("TENSACH");
        tacGia = intent.getStringExtra("TACGIA");

        theLoai = intent.getStringExtra("THELOAI");

        trang = intent.getStringExtra("TRANG");
        noiDung = intent.getStringExtra("NOIDUNG");

        anh = intent.getStringExtra("ANH");


        //set text
        txtTenSach.setText(tenSach);
        txtTacGia.setText(tacGia);
        txtSoTrang.setText(trang);
        txtNoiDung.setText(noiDung);
        imgBook.setImageURI(Uri.parse(anh));
        txtTheLoai.setText(theLoai);


    }


}