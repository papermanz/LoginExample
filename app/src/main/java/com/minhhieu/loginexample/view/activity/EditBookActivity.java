package com.minhhieu.loginexample.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.data.DatabaseBook;
import com.minhhieu.loginexample.model.Book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class EditBookActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerTheLoai;

    private ImageView imgNXB,imgBook;
    private Button btnAddBook;
    private EditText edtNXB,edtThemSach,edtTacGia,edtNhaSX,edtSoTrang,edtGia,edtNoiDung;
    private ArrayList<String> arrTheLoai;
    private boolean editMode = false;
    DatabaseBook databaseBook;
    private ArrayAdapter arrayAdapter;
    private Book book;

    int REQUEST_CODE_FOLDER = 123;
    String imagePath;

    private String id, tenSach, tacGia, nhaSX, theLoai, ngayXB, trang,noiDung, gia,anh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        setTitle(null);
        databaseBook = new DatabaseBook(this);
        initLayout();

        setSpinner();
        getDataBook();
        imagePath = anh;

        initListener();



    }

    // Nhận dữ liệu
    private void getDataBook(){
        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editMode",editMode);
        id = intent.getStringExtra("ID");
        tenSach = intent.getStringExtra("TENSACH");
        tacGia = intent.getStringExtra("TACGIA");
        nhaSX = intent.getStringExtra("NHASX");
        theLoai = intent.getStringExtra("THELOAI");
        ngayXB = intent.getStringExtra("NGAYXB");
        trang = intent.getStringExtra("TRANG");
        noiDung = intent.getStringExtra("NOIDUNG");
        gia = intent.getStringExtra("GIA");
        anh = intent.getStringExtra("ANH");


        //set text
        edtThemSach.setText(tenSach);
        edtTacGia.setText(tacGia);
        edtNhaSX.setText(nhaSX);
        edtNXB.setText(ngayXB);
        edtSoTrang.setText(trang);
        edtGia.setText(gia);
        edtNoiDung.setText(noiDung);
        arrTheLoai = new ArrayList<>();
        spinnerTheLoai.setSelection(arrayAdapter.getPosition(theLoai));
        imgBook.setImageURI(Uri.parse(anh));


    }

    /***************************************
     * Định dạng ngày, khi chọn Date Picker*
     ***************************************/

    private void SelectDay(){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        final int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(this), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                calendar.set(mYear,mMonth,mDay);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtNXB.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }


    /********************
     *Tối ưu String nhập*
     ********************/

    private String getStringInput(EditText editText){
        return editText.getText().toString().trim();

    }

    /**********************
     * Check lỗi nhập sách*
     **********************/

    private boolean Validate(){
        boolean valid = true;
        String tenSach = getStringInput(edtThemSach);
        String tacGia = getStringInput(edtTacGia);
        String nhaSX = getStringInput(edtNhaSX);
        String soTrang = getStringInput(edtSoTrang);
        String giaSach = getStringInput(edtGia);
        String noiDung = getStringInput(edtNoiDung);

        if(tenSach.isEmpty()){
            valid = false;
            edtThemSach.setError(getResources().getString(R.string.empty_ten_sach));
        }
        if(tacGia.isEmpty()){
            valid = false;
            edtTacGia.setError(getResources().getString(R.string.empty_tac_gia));
        }
        if(nhaSX.isEmpty()){
            valid = false;
            edtNhaSX.setError(getResources().getString(R.string.empty_nha_san_xuat));
        }
        if(soTrang.isEmpty()){
            valid = false;
            edtSoTrang.setError(getResources().getString(R.string.empty_so_trang));
        }
        if(giaSach.isEmpty()){
            valid = false;
            edtGia.setError(getResources().getString(R.string.empty_gia_sach));
        }
        if(noiDung.isEmpty()){
            valid = false;
            edtNoiDung.setError(getResources().getString(R.string.empty_noi_dung));
        }
        return valid;
    }

    /*********************
     * Bắt id các control*
     *********************/
    private void initLayout(){
        spinnerTheLoai = findViewById(R.id.spTheLoai);
        imgNXB = findViewById(R.id.imgNXB);
        edtNXB = findViewById(R.id.edtNXB);
        edtThemSach = findViewById(R.id.edtThemSach);
        edtTacGia = findViewById(R.id.edtTacGia);
        edtNhaSX = findViewById(R.id.edtNhaSX);
        edtSoTrang = findViewById(R.id.edtTrang);
        edtGia = findViewById(R.id.edtGia);
        edtNoiDung = findViewById(R.id.edtND);
        btnAddBook = findViewById(R.id.btn_add_book);
        imgBook = findViewById(R.id.imgBook);

    }


    private void initListener(){
        btnAddBook.setOnClickListener(this);

        imgNXB.setOnClickListener(this);
        imgBook.setOnClickListener(this);

    }

    //set spinner
    private void setSpinner(){
        arrTheLoai = new ArrayList<>();
        arrTheLoai.add("Trinh Thám");
        arrTheLoai.add("Tiểu Thuyết");
        arrTheLoai.add("Manga");
        arrTheLoai.add("Ngôn Tình");
        arrTheLoai.add("Kinh Dị");
        arrTheLoai.add("Văn Học");
        arrTheLoai.add("Khác");

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrTheLoai);
        spinnerTheLoai.setAdapter(arrayAdapter);
    }

    private void inputBook(){
        book.setTenSach(getStringInput(edtThemSach));
        book.setTacGia(getStringInput(edtTacGia));
        book.setNhaSX(getStringInput(edtNhaSX));
        book.setTheLoai(spinnerTheLoai.getSelectedItem().toString());
        book.setNgayXB(getStringInput(edtNXB));
        book.setTrang(Integer.parseInt(getStringInput(edtSoTrang)));
        book.setGia(Float.parseFloat(getStringInput(edtGia)));
        book.setNoiDung(getStringInput(edtNoiDung));
        book.setAnh(imagePath);
    }

    private void setBtnaddBook(){
        if(Validate()) {
            book = new Book();
            inputBook();
            try {
                databaseBook.updateBook(book,id);
                Toast.makeText(this, "Sửa sách thành công!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }catch (NullPointerException ex){
                Log.e("lỗi", "onClick: " + ex);
            }
        }else{
            Toast.makeText(this, "Sửa sách thất bại!", Toast.LENGTH_LONG).show();
        }
    }


    private void setbtnNXB(){
        SelectDay();
    }

    private void setbtnAddImgBook(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE_FOLDER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == Activity.RESULT_OK && data!=null){
            Uri uri = data.getData();


            imagePath = createCopyAndReturnRealPath(this,uri);

            Bitmap bitmapImage = null;
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imgBook.setImageBitmap(bitmapImage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /***********************
     *Get filePath from URI*
     ***********************/
    public static String createCopyAndReturnRealPath(
            @NonNull Context context, @NonNull Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        // Create file path inside app's data dir
        String filePath = context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis();

        File file = new File(filePath);
        try {
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);

            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }

        return file.getAbsolutePath();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_book:
                setBtnaddBook();
                break;
            case R.id.imgBook:
                setbtnAddImgBook();
                break;
            case R.id.imgNXB:
                setbtnNXB();
                break;
        }
    }
}