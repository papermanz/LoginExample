package com.minhhieu.loginexample.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.adapter.BookRecommendAdapter;
import com.minhhieu.loginexample.adapter.SliderAdapter;
import com.minhhieu.loginexample.animator.TranslateAnimationUtil;
import com.minhhieu.loginexample.data.DatabaseBook;
import com.minhhieu.loginexample.interfaces.ItemClickListener;
import com.minhhieu.loginexample.model.Book;
import com.minhhieu.loginexample.model.SliderItem;
import com.minhhieu.loginexample.view.activity.MainActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ItemClickListener {
    SliderView sliderView;
    private SliderAdapter adapter;
    BookRecommendAdapter bookAdapter;
    ScrollView scrollView;
    View layout;
    DatabaseBook databaseBook;
    TextView textView;
    ArrayList<Book> arrayBook= new ArrayList<>();
    RecyclerView rcBookRecommend;
    RecyclerView rcBookTrending;
    BottomNavigationView bottomNavigationView;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseBook = new DatabaseBook(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_home, container, false);
        initLayout();

        setSliderView();

        setRcBookRecommend();
        setRcBookTrending();
        initListener();


       scrollView.setOnTouchListener(new TranslateAnimationUtil(getActivity(),bottomNavigationView));



        return layout;
    }




    private void setRcBookTrending() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        arrayBook = databaseBook.getAllBook();
        bookAdapter = new BookRecommendAdapter(getActivity(),arrayBook,databaseBook);
        rcBookTrending.setLayoutManager(layoutManager);
        rcBookTrending.setAdapter(bookAdapter);
    }

    private void setRcBookRecommend() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        arrayBook = databaseBook.getBookPage1();
        bookAdapter = new BookRecommendAdapter(getActivity(),arrayBook,databaseBook);
        rcBookRecommend.setLayoutManager(layoutManager);
        rcBookRecommend.setAdapter(bookAdapter);
    }

    private void initListener() {
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(intent);
        });
    }

    private void initLayout(){
        sliderView = (SliderView)layout.findViewById(R.id.imageSlider);
        textView = (TextView) layout.findViewById(R.id.seeall);
        rcBookRecommend = (RecyclerView)layout.findViewById(R.id.recyclerView_recommend);
        rcBookTrending = (RecyclerView) layout.findViewById(R.id.recyclerView_trending);
        scrollView = (ScrollView) layout.findViewById(R.id.scroll_view);
        bottomNavigationView = getActivity().findViewById(R.id.navigation_bottom);

    }



    private void setSliderView(){
        adapter = new SliderAdapter(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data

        for (int i = 0; i < 7; i++) {
            SliderItem sliderItem = new SliderItem();
            switch (i){
                case 1:
                    sliderItem.setImageUrl("https://images.squarespace-cdn.com/content/v1/54297deae4b04053ed0cba0b/1513044884087-IASJHNBH3DCE7ILPCZNB/ke17ZwdGBToddI8pDm48kDUmZJPtjcvIHBJxs1Dy_oF7gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z4YTzHvnKhyp6Da-NYroOW3ZGjoBKy3azqku80C789l0plef_PmwB6-3GP4qDbCUv9oJE4pa-KLtGr30--pRapK7ttJsC8W8Y1lJgpl17C6FA/JUNE_mockup.jpg");
                    sliderItemList.add(sliderItem);
                    break;
                case 2:
                    sliderItem.setImageUrl("https://i.pinimg.com/originals/87/33/3e/87333ebcf21ecf689d45a3158b47cdf7.jpg");
                    sliderItemList.add(sliderItem);
                    break;
                case 3:
                    sliderItem.setImageUrl("https://i.pinimg.com/564x/20/a0/e4/20a0e4bc95a0a5db0334b65559dc9d63.jpg");
                    sliderItemList.add(sliderItem);
                    break;
                case 4:
                    sliderItem.setImageUrl("https://i.pinimg.com/564x/b6/57/9a/b6579a6b878cf9daa9fde0c1e7a17a70.jpg");
                    sliderItemList.add(sliderItem);
                    break;
                case 5:
                    sliderItem.setImageUrl("https://i.pinimg.com/564x/2f/2d/88/2f2d889990914840dae37e188abc1620.jpg");
                    sliderItemList.add(sliderItem);
                    break;
                    case 6:
                    sliderItem.setImageUrl("https://i.pinimg.com/564x/b4/61/11/b4611106ff770a43a0ea439f2c518d1b.jpg");
                    sliderItemList.add(sliderItem);
                    break;

                default:
                    break;


            }

        }

        adapter.renewItems(sliderItemList);
    }


    @Override
    public void onClick(int position, ImageView imgContainer, ImageView imgBook, TextView title, TextView authorName, TextView pages) {

    }

    @Override
    public void onLongClick(int position, View v) {

    }
}