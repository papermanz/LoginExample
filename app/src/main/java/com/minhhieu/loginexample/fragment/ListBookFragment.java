package com.minhhieu.loginexample.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.adapter.BookAdapter;
import com.minhhieu.loginexample.interfaces.ItemClickListener;
import com.minhhieu.loginexample.data.DatabaseBook;
import com.minhhieu.loginexample.model.Book;
import com.minhhieu.loginexample.view.activity.DetailBookActivity;
import com.minhhieu.loginexample.view.activity.EditBookActivity;
import com.minhhieu.loginexample.view.activity.LoginActivity;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;



public class ListBookFragment extends Fragment implements SearchView.OnQueryTextListener, ItemClickListener {
    DatabaseBook databaseBook;
    RecyclerView rcBook;

    BookAdapter bookAdapter;

    private boolean isLoading = false;

    ProgressBar progressBar;

    ArrayList<Book> arrayBook= new ArrayList<>();
    private View layout;

    private void initLayout(){
        rcBook = layout.findViewById(R.id.rc_book);

        progressBar = layout.findViewById(R.id.progress_bar1);




    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseBook = new DatabaseBook(getActivity());
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout=inflater.inflate(R.layout.fragment_list_book, container, false);
        initLayout();
        setLoading();
        getBookList();

        return layout;
    }

    private void setLoading(){
        rcBook.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(!isLoading){
                    if(layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == arrayBook.size() - 1)
                    {
                        loadMoreBookList();
                        isLoading = true;
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }



    private void setUpRecycleView(){
        bookAdapter = new BookAdapter(getActivity(),arrayBook,databaseBook,this);
        rcBook.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcBook.setAdapter(bookAdapter);

    }




      private void getBookList()
      {
        try
        {

            arrayBook = databaseBook.getBookPage1();
            if(arrayBook != null){
               setUpRecycleView();
            }

        }
        catch (Exception e)
        {
            Log.e("lỗi", "getBookList: exception");
            e.printStackTrace();
        }

      }

      //get more book
      private void loadMoreBookList(){
          try
          {
              int nextLimit = arrayBook.size();
              arrayBook = databaseBook.getLoadMoreBook(nextLimit);
              new Handler(Looper.getMainLooper()).postDelayed(() -> {
                  bookAdapter.notifyDataSetChanged();
                  isLoading = false;
                  progressBar.setVisibility(View.GONE);
              },2000);

          }
          catch (Exception e)
          {
              Log.e("lỗi", "getBookList: exception");
              e.printStackTrace();
          }
      }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.search_sort, menu);
        final MenuItem searchItem = menu.findItem(R.id.search_option);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);


    }

    /****************
     *Sort by author*
     ****************/
    private void sortListByAuthor(ArrayList<Book> arrayBook){
        Collections.sort(arrayBook, new EventDetailSortByAuthor());
    }

    @Override
    public void onClick(int position, ImageView container, ImageView imgBook, TextView title,
                        TextView authorName,
                        TextView pages) {
        Book book = arrayBook.get(position);

        final String tenSach = book.getTenSach();
        final String tacGia = book.getTacGia();

        final String theLoai = book.getTheLoai();

        final int trang = book.getTrang();

        final String noiDung = book.getNoiDung();
        final String anh = book.getAnh();

//        putDetailBook( +position,
//                ""+id,
//                ""+tenSach,
//                ""+tacGia,
//                ""+nhaSX,
//                ""+theLoai,
//                ""+ngayXB,
//                ""+trang,
//                ""+gia,
//                ""+noiDung,
//                ""+anh);

        Intent intent = new Intent(getActivity(), DetailBookActivity.class);

        intent.putExtra("TENSACH",tenSach);
        intent.putExtra("TACGIA",tacGia);

        intent.putExtra("THELOAI",theLoai);

        intent.putExtra("TRANG",trang+"");
        intent.putExtra("NOIDUNG",noiDung);
        intent.putExtra("ANH",anh);
        intent.putExtra("editMode",false);

        Pair<View,String> p1 = Pair.create((View)container,"containerTN");
        Pair<View,String> p2 = Pair.create((View)imgBook,"bookTN");
        Pair<View,String> p3 = Pair.create((View)title,"booktitleTN");
        Pair<View,String> p4 = Pair.create((View)authorName,"authorTN");
        Pair<View,String> p5 = Pair.create((View)pages,"bookpagesTN");



        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),p1,p2,p3,p4,p5);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getActivity().startActivity(intent,optionsCompat.toBundle());
        }
        else
            getActivity().startActivity(intent);

    }

//    private void putDetailBook(int position, final String id, final String tenSach, final String tacGia, final String nhaSX, final String theLoai, final String ngayXB, final String trang, final String gia, final String noiDung, final String anh) {
//        Intent intent = new Intent(getActivity(), DetailBookActivity.class);
//        intent.putExtra("ID",id);
//        intent.putExtra("TENSACH",tenSach);
//        intent.putExtra("TACGIA",tacGia);
//        intent.putExtra("NHASX",nhaSX);
//        intent.putExtra("THELOAI",theLoai);
//        intent.putExtra("NGAYXB",ngayXB);
//        intent.putExtra("TRANG",trang);
//        intent.putExtra("GIA",gia);
//        intent.putExtra("NOIDUNG",noiDung);
//        intent.putExtra("ANH",anh);
//        intent.putExtra("editMode",false);
//
//
//        getActivity().startActivity(intent);
//    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onLongClick(int position, View v) {
        Book book = arrayBook.get(position);
        final int id = book.getId();
        final String tenSach = book.getTenSach();
        final String tacGia = book.getTacGia();
        final String nhaSX = book.getNhaSX();
        final String theLoai = book.getTheLoai();
        final String ngayXB = book.getNgayXB();
        final int trang = book.getTrang();
        final float gia = book.getGia();
        final String noiDung = book.getNoiDung();
        final String anh = book.getAnh();

        PopupMenu popup = new PopupMenu(getActivity(),v, Gravity.END,0,R.style.PopupMenuMoreCentralized);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_sua_xoa, popup.getMenu());


        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            Log.e("error Show Icon", "onLongClick: "+e );
        }
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.edit_book:
                    editDialog(
                            +position,
                            ""+id,
                            ""+tenSach,
                            ""+tacGia,
                            ""+nhaSX,
                            ""+theLoai,
                            ""+ngayXB,
                            ""+trang,
                            ""+gia,
                            ""+noiDung,
                            ""+anh

                    );

                    return true;
                case R.id.delete_book:
                    new MaterialAlertDialogBuilder(getActivity())
                            .setTitle("")
                            .setMessage("Bạn có muốn xoá sách "+arrayBook.get(position).getTenSach()+" không?")
                            .setPositiveButton("Có", (dialogInterface, i) -> {
                                try{
                                    databaseBook.deleteBook(arrayBook.get(position).getId());
                                    Toast.makeText(getActivity(), "Xoá sách "+arrayBook.get(position).getTenSach()+" thành công !", Toast.LENGTH_SHORT).show();
                                    arrayBook.remove(position);
                                    bookAdapter.notifyDataSetChanged();
                                }catch (NullPointerException ex){
                                    Log.e("lỗi", "onClick: " + ex);
                                }
                            })
                            .setNegativeButton("Không", (dialogInterface, i) -> {

                            })
                            .show();

                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }


    private static class EventDetailSortByAuthor implements java.util.Comparator<Book>{

        @Override
        public int compare(Book object1, Book object2) {
            String author1, author2;
            author1 = object1.getTacGia().toLowerCase().trim();
            author2 = object2.getTacGia().toLowerCase().trim();
            return author1.compareTo(author2);
        }
    }

    /****************************
     *Search item on recycleView*
     ****************************/
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        String inputText = newText.toLowerCase();
        ArrayList<Book> arrBook = new ArrayList<>();
        for (Book book : arrayBook) {

            String name = book.getTenSach().toLowerCase();
            String author = book.getTacGia().toLowerCase();
            String theLoai = book.getTheLoai().toLowerCase();
            if (name.contains(inputText) || author.contains(inputText) || theLoai.contains(inputText)){
                arrBook.add(book);
            }
        }
        bookAdapter.setFilter(arrBook);
        return true;
    }



    /**************
     *Sort by name*
     **************/
    private void sortListByName(ArrayList<Book> arrayBook) {

        Collections.sort(arrayBook, new EventDetailSortByName());
    }


    private static class EventDetailSortByName implements java.util.Comparator<Book> {
        @Override
        public int compare(Book object1, Book object2) {
            String name1, name2;
            name1 = object1.getTenSach().toLowerCase().trim();
            name2 = object2.getTenSach().toLowerCase().trim();
            return name1.compareTo(name2);
        }

    }

    /**************
     *Sort by date*
     **************/
    private void sortListByDate(ArrayList<Book> arrayBook) {
        Collections.sort(arrayBook, new EventDetailSortByDate());
    }

    private static class EventDetailSortByDate implements java.util.Comparator<Book> {
        @Override
        public int compare(Book object1, Book object2) {
            Date DateObject1 = StringToDate(object1.getNgayXB());
            Date DateObject2 = StringToDate(object2.getNgayXB());

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(DateObject1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(DateObject2);

            int month1 = cal1.get(Calendar.MONTH);
            int month2 = cal2.get(Calendar.MONTH);

            if (month1 < month2)
                return -1;
            else if (month1 == month2)
                return cal1.get(Calendar.DAY_OF_MONTH) - cal2.get(Calendar.DAY_OF_MONTH);

            else return 1;
        }
    }

    public static Date StringToDate(String theDateString) {
        Date returnDate = new Date();
        if (theDateString.contains("-")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            try {
                returnDate = dateFormat.parse(theDateString);
            } catch (ParseException e) {
                SimpleDateFormat altdateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    returnDate = altdateFormat.parse(theDateString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                returnDate = dateFormat.parse(theDateString);
            } catch (ParseException e) {
                SimpleDateFormat altdateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    returnDate = altdateFormat.parse(theDateString);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return returnDate;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.by_name:
                sortListByName(arrayBook);
                bookAdapter = new BookAdapter(getActivity(),arrayBook,databaseBook,this);
                rcBook.setAdapter(bookAdapter);
                break;

            case R.id.by_date:
                sortListByDate(arrayBook);
                bookAdapter = new BookAdapter(getActivity(),arrayBook,databaseBook,this);
                rcBook.setAdapter(bookAdapter);
                break;

            case R.id.by_author:
                sortListByAuthor(arrayBook);
                bookAdapter = new BookAdapter(getActivity(),arrayBook,databaseBook,this);
                rcBook.setAdapter(bookAdapter);
                break;

            case R.id.by_page:
                Collections.sort(arrayBook);
                bookAdapter = new BookAdapter(getActivity(),arrayBook,databaseBook,this);
                rcBook.setAdapter(bookAdapter);
                break;

            case R.id.item_logout:
                setLogOut();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void setLogOut(){
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle("")
                .setMessage("Bạn có muốn thoát tài khoản không?")
                .setPositiveButton("Có", (dialogInterface, i) -> {
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("Không", (dialogInterface, i) -> {

                })
                .show();

    }

    private void editDialog(int position, final String id, final String tenSach, final String tacGia, final String nhaSX, final String theLoai, final String ngayXB, final String trang, final String gia, final String noiDung, final String anh) {
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle("")
                .setMessage("Bạn có muốn sửa sách "+arrayBook.get(position).getTenSach()+" không?")
                .setPositiveButton("Có", (dialogInterface, i) -> {
                    Intent intent = new Intent(getActivity(), EditBookActivity.class);
                    intent.putExtra("ID",id);
                    intent.putExtra("TENSACH",tenSach);
                    intent.putExtra("TACGIA",tacGia);
                    intent.putExtra("NHASX",nhaSX);
                    intent.putExtra("THELOAI",theLoai);
                    intent.putExtra("NGAYXB",ngayXB);
                    intent.putExtra("TRANG",trang);
                    intent.putExtra("GIA",gia);
                    intent.putExtra("NOIDUNG",noiDung);
                    intent.putExtra("ANH",anh);
                    intent.putExtra("editMode",false);
                    getActivity().startActivity(intent);
                })
                .setNegativeButton("Không", (dialogInterface, i) -> {

                })
                .show();

    }
}