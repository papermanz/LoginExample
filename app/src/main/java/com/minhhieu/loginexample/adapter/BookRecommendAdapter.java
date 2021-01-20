package com.minhhieu.loginexample.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.data.DatabaseBook;
import com.minhhieu.loginexample.model.Book;

import java.util.ArrayList;

public class BookRecommendAdapter extends RecyclerView.Adapter<BookRecommendAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Book> bookList;
    DatabaseBook databaseBook;

    public BookRecommendAdapter(Context context, ArrayList<Book> bookList, DatabaseBook databaseBook) {
        this.context = context;
        this.bookList = bookList;
        this.databaseBook = databaseBook;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.recommend_book_row,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.txtTenSachHome.setText(bookList.get(position).getTenSach());
        holder.txtTacGiaHome.setText(bookList.get(position).getTacGia());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bitmapPicture = BitmapFactory.decodeFile(bookList.get(position).getAnh());
        holder.imgSachHome.setImageBitmap(bitmapPicture);


    }

    @Override
    public int getItemCount() {
        return bookList !=null ? bookList.size():0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenSachHome;
        TextView txtTacGiaHome;
        ImageView imgSachHome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSachHome = itemView.findViewById(R.id.book_name_home);
            txtTacGiaHome =  itemView.findViewById(R.id.author_book_home);
            imgSachHome = itemView.findViewById(R.id.img_book_home);
        }


    }
}
