package com.minhhieu.loginexample.interfaces;


import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;

public interface ItemClickListener {
    void onClick(int position,
                 ImageView imgContainer,
                 ImageView imgBook,
                 TextView title,
                 TextView authorName,
                 TextView pages

                 );
    void onLongClick(int position, View v);

}
