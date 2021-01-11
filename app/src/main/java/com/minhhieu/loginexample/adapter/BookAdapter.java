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
import com.minhhieu.loginexample.interfaces.ItemClickListener;
import com.minhhieu.loginexample.model.Book;
import java.util.ArrayList;



public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Book> bookList;
    DatabaseBook databaseBook;
    private static ItemClickListener itemClickListener;



    public BookAdapter(Context context, ArrayList<Book> bookList, DatabaseBook databaseBook, ItemClickListener itemClickListener) {
        this.context = context;
        this.bookList = bookList;
        this.databaseBook = databaseBook;
        this.itemClickListener = itemClickListener;

    }

    public void setData(ArrayList<Book> list){
        this.bookList = list;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.book_row,parent,false);


        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

       holder.txtTenSach.setText(bookList.get(position).getTenSach());
       holder.txtTacGia.setText(bookList.get(position).getTacGia());
       holder.txtTheLoai.setText(bookList.get(position).getTheLoai());
       holder.txtSoTrang.setText(String.valueOf(bookList.get(position).getTrang()));

       BitmapFactory.Options options = new BitmapFactory.Options();
       options.inSampleSize = 8;
       Bitmap bitmapPicture = BitmapFactory.decodeFile(bookList.get(position).getAnh());
       holder.imgSach.setImageBitmap(bitmapPicture);




    }


    @Override
    public int getItemCount() {
        return bookList !=null ? bookList.size():0;
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{
      TextView txtTenSach;
      TextView txtTacGia;
      TextView txtTheLoai;
      TextView txtSoTrang;
      ImageView imgSach,container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
              txtTenSach = itemView.findViewById(R.id.txtTenSach);
              txtTacGia =  itemView.findViewById(R.id.txtTacGia);
              txtTheLoai = itemView.findViewById(R.id.txt_theloai);
              txtSoTrang = itemView.findViewById(R.id.txt_soTrang);
              imgSach = itemView.findViewById(R.id.imgv_book);
              container = itemView.findViewById(R.id.container);


            itemView.setOnClickListener(v -> itemClickListener.onClick(getAdapterPosition(),container,imgSach,txtTenSach,txtTacGia,txtSoTrang));

            itemView.setOnLongClickListener(v -> {
                itemClickListener.onLongClick(getAdapterPosition(),v);
                return true;
            });

        }


    }
    public void setFilter(ArrayList<Book> arrayBook) {

        bookList = new ArrayList<>();
        bookList.addAll(arrayBook);
        notifyDataSetChanged();
    }



}
