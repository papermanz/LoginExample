package com.minhhieu.loginexample.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.animator.TranslateAnimationUtil;
import com.minhhieu.loginexample.data.DatabaseBook;
import com.minhhieu.loginexample.model.Book;
import com.minhhieu.loginexample.utils.asynctask.CheckBookTask;
import com.minhhieu.loginexample.utils.asynctask.SetBookTask;
import com.minhhieu.loginexample.view.activity.HomeActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;



public class AddBookFragment extends Fragment implements View.OnClickListener {

    Spinner spinnerTheLoai;

    private ImageView imgNXB,imgBook;
    private Button btnAddBook;
    private EditText edtNXB,edtThemSach,edtTacGia,edtNhaSX,edtSoTrang,edtGia,edtNoiDung;
    private View layout;
    DatabaseBook databaseBook;
    private Book book;
    ScrollView scrollView;
    BottomNavigationView bottomNavigationView;

    int REQUEST_CODE_FOLDER = 123;
    String imagePath;

    /*******************************
     * Check lỗi nhập ngày xuất bản*
     *******************************/
    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            if (working.length() == 2 && before == 0) {
                if (Integer.parseInt(working) < 1 || Integer.parseInt(working) > 31) {
                    isValid = false;
                } else {
                    working += "/";
                    edtNXB.setText(working);
                    edtNXB.setSelection(working.length());
                }
            } else if (working.length() == 5 && before == 0) {
                String enterMonth = working.substring(3);
                if (Integer.parseInt(enterMonth) < 1 || Integer.parseInt(enterMonth) > 12) {
                    isValid = false;
                } else {
                    working += "/";
                    edtNXB.setText(working);
                    edtNXB.setSelection(working.length());
                }
            } else if (working.length() == 10 && before == 0) {
                String enteredYear = working.substring(6);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                if (Integer.parseInt(enteredYear) > currentYear || Integer.parseInt(enteredYear) < 1900) {
                    isValid = false;
                }
            } else if (working.length() != 10) {
                isValid = false;
            }

            if (!isValid) {
                edtNXB.setError(getResources().getString(R.string.sai_dinh_dang_ngay));
            } else {
                edtNXB.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

    };

    /***************************************
     * Định dạng ngày, khi chọn Date Picker*
     ***************************************/

    private void SelectDay(){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        final int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), (view, mYear, mMonth, mDay) -> {
            calendar.set(mYear,mMonth,mDay);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            edtNXB.setText(simpleDateFormat.format(calendar.getTime()));
        }, year, month, day);
        datePickerDialog.show();
    }


    /********************
     *Tối ưu String nhập*
     ********************/

    private String getStringInput(EditText editText){
            return editText.getText().toString().trim();

    }


    private void addBook(){
        inputBook();
        SetBookTask setBookTask = new SetBookTask(getActivity(),book);
        setBookTask.setGetBookListener(s -> Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show());
        setBookTask.execute();
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
        spinnerTheLoai = layout.findViewById(R.id.spTheLoai);
        imgNXB = layout.findViewById(R.id.imgNXB);
        edtNXB = layout.findViewById(R.id.edtNXB);
        edtThemSach = layout.findViewById(R.id.edtThemSach);
        edtTacGia = layout.findViewById(R.id.edtTacGia);
        edtNhaSX = layout.findViewById(R.id.edtNhaSX);
        edtSoTrang = layout.findViewById(R.id.edtTrang);
        edtGia = layout.findViewById(R.id.edtGia);
        edtNoiDung = layout.findViewById(R.id.edtND);

        btnAddBook = layout.findViewById(R.id.btn_add_book);
        imgBook = layout.findViewById(R.id.imgBook);
        scrollView = layout.findViewById(R.id.scroll_view_addbook);
        bottomNavigationView = getActivity().findViewById(R.id.navigation_bottom);

    }


    private void initListener(){
        btnAddBook.setOnClickListener(this);

        imgNXB.setOnClickListener(this);
        imgBook.setOnClickListener(this);

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        databaseBook = new DatabaseBook(getActivity());
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout=inflater.inflate(R.layout.fragment_add_book, container, false);
        initLayout();

        initListener();
        setSpinner();

        edtNXB.addTextChangedListener(mDateEntryWatcher);
        scrollView.setOnTouchListener(new TranslateAnimationUtil(getActivity(),bottomNavigationView));




        return layout;
    }

    //set spinner
    private void setSpinner(){
        ArrayList<String> arrTheLoai = new ArrayList<>();
        arrTheLoai.add("Trinh Thám");
        arrTheLoai.add("Tiểu Thuyết");
        arrTheLoai.add("Manga");
        arrTheLoai.add("Ngôn Tình");
        arrTheLoai.add("Kinh Dị");
        arrTheLoai.add("Văn Học");
        arrTheLoai.add("Khác");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,arrTheLoai);
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
            String tenSach = getStringInput(edtThemSach);
            book = new Book();
            inputBook();

            CheckBookTask task = new CheckBookTask(getActivity(), tenSach);
            task.setGetBookListener(book -> {
                if (book.getTenSach() != null) {
                    Toast.makeText(getActivity(), "Sách đã tồn tại!", Toast.LENGTH_SHORT).show();
                } else {
                    addBook();
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }
            });
            task.execute();
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

            imagePath = createCopyAndReturnRealPath(getActivity(),uri);

            Bitmap bitmapImage = null;
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
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
            case R.id.imgNXB:
                setbtnNXB();
                break;
            case R.id.imgBook:
                setbtnAddImgBook();
                break;
        }
    }





}