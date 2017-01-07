package com.example.tai_fiction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tai_fiction.R;
import com.example.tai_fiction.entity.BookDetailsEntity;
import com.example.tai_fiction.entity.MyCollectEntity;
import com.example.tai_fiction.entity.TestData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 泰子 on 2015/10/29.
 */
public class BookRackAdapter extends SimpleBaseAdapter<MyCollectEntity> {


    public BookRackAdapter(Context context, int layoutId, List<MyCollectEntity> data) {
        super(context, layoutId, data);
    }

    @Override
    public void getItemView(ViewHolder holder, MyCollectEntity itemBean) {
        holder.setText(R.id.bookrack_bookname,itemBean.getTitle())
                .setImageURL2(R.id.bookrack_cover, itemBean.getCover());
    }
}
