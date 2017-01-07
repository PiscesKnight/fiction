package com.example.tai_fiction.ui.fragment.bookrack;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.tai_fiction.R;

import com.example.tai_fiction.adapter.BookRackAdapter;
import com.example.tai_fiction.base.mvp.MvpFragment;
import com.example.tai_fiction.novelview.NovelViewActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by 泰子 on 2015/10/17.
 */
public class BookRackFragment extends MvpFragment<BookRackPresenter> implements BookRackView{

    @Bind(R.id.bookrack_gridview)
    GridView gridView;
    @Bind(R.id.bookrack_menu_fabtn)
    FloatingActionButton menu_fabtn;
    @Bind(R.id.relative)
    RelativeLayout relative;

    private PopupWindow bookrackPpw;

    private BookRackAdapter bookRackAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.bookrack_fragment;
    }

    @Override
    public void initView() {
         bookRackAdapter = new BookRackAdapter(getContext(),
                R.layout.bookrack_gridview_item,  mPresenter.getBookRackData());
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
    }

    @Override
    protected BookRackPresenter createPresenter() {
        return new BookRackPresenter(this,getContext());
    }


    /**
     * 书架按钮菜单
     * @param v
     */
    @Override
    public void iniPopupWindow(View v) {

        View bookrackMenuView = LayoutInflater.from(getActivity()).inflate(R.layout.bookrack_menu_popupwindow, null);
        bookrackPpw = new PopupWindow(bookrackMenuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

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
        BookRackAdapter bookRackAdapter = new BookRackAdapter(getContext(),
                R.layout.bookrack_gridview_item, mPresenter.getBookRackData());
        gridView.setAdapter(bookRackAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NovelViewActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
