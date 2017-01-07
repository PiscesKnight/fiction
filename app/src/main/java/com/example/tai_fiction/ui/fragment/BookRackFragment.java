package com.example.tai_fiction.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.example.tai_fiction.DB.BookDB;
import com.example.tai_fiction.R;
import com.example.tai_fiction.adapter.BookRackAdapter;
import com.example.tai_fiction.entity.MyCollectEntity;
import com.example.tai_fiction.novelview.NovelViewActivity;

import java.util.List;



/**
 * Created by 泰子 on 2015/10/17.
 */
public class BookRackFragment extends Fragment {


    private GridView gridView;
    private FloatingActionButton menu_fabtn;
    private PopupWindow bookrackPpw;

    private BookDB bookDB;

    private List<MyCollectEntity> bookData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookrack_fragment, container, false);

        bookDB = BookDB.getInstance(getContext(),"Book.db");

        gridView = (GridView) view.findViewById(R.id.bookrack_gridview);
        menu_fabtn = (FloatingActionButton) view.findViewById(R.id.bookrack_menu_fabtn);

        bookData = bookDB.getBookRackData();
        BookRackAdapter bookRackAdapter = new BookRackAdapter(getContext(),
                R.layout.bookrack_gridview_item,bookData);
        gridView.setAdapter(bookRackAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NovelViewActivity.class);
                startActivity(intent);
            }
        });



        menu_fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniPopupWindow(v);
            }
        });
        return view;
    }


    public void iniPopupWindow(View v) {

        View bookrackMenuView = LayoutInflater.from(getActivity()).inflate(R.layout.bookrack_menu_popupwindow,null);
        bookrackPpw = new PopupWindow(bookrackMenuView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);

        bookrackPpw.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件
        bookrackPpw.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
        bookrackPpw.setBackgroundDrawable(new ColorDrawable(0xb00000));// 设置背景图片，不能在布局中设置，要通过代码来设置
        bookrackPpw.setAnimationStyle(R.style.bookrack_menu_anim_style);
        bookrackPpw.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        bookrackPpw.update();
    }

    @Override
    public void onResume() {
        super.onResume();
        bookData = bookDB.getBookRackData();
        BookRackAdapter bookRackAdapter = new BookRackAdapter(getContext(),
                R.layout.bookrack_gridview_item,bookData);
        gridView.setAdapter(bookRackAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NovelViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
