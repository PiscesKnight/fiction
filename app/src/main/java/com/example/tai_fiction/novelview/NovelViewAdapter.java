package com.example.tai_fiction.novelview;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tai_fiction.R;

import java.util.List;

/**
 * Created by TAI on 2016/1/15.
 */
public class NovelViewAdapter extends PageAdapter {
    Context context;
    List<String> items;
    AssetManager am;
    String text;
    public NovelViewAdapter(Context context, List<String> items)
    {
        this.context = context;
        this.items = items;
        am = context.getAssets();
    }

    public void addContent(View view, int position)
    {

            TextView content = (TextView) view.findViewById(R.id.content);
        TextView tv = (TextView) view.findViewById(R.id.index);
        TextView sum = (TextView)view.findViewById(R.id.sum);
        if ((position - 1) < 0 || (position - 1) >= getCount())
            return;
        content.setText("第一章星空中的青铜巨棺\n" +
                "\n" +
                "    生命是世间最伟大的奇迹。２３Ｕｓ．ｃｏｍ\n" +
                "\n" +
                "    四方上下曰宇。宇虽有实，而无定处可求。往古来今曰宙。宙虽有增长，不知其始之所至。\n" +
                "\n" +
                "    浩瀚的宇宙，无垠的星空，许多科学家推测，地球可能是唯一的生命源地。\n" +
                "\n" +
                "    人类其实很孤独。在苍茫的天宇中，虽然有亿万星辰，但是却很难寻到第二颗生命源星。\n" +
                "\n" +
                "    不过人类从来没有放弃过探索，自上世纪以来已经发射诸多太空探测器。\n" +
                "\n" +
                "    旅行者二号是一艘无人外太空探测器，于一九七七年在美国肯尼迪航天中心发射升空。\n" +
                "\n" +
                "    它上面携带着一张主题为“向宇宙致意”的镀金唱片，里面包含一些流行音乐和用地球五十五种语言录制的问候辞，以冀有一天被可能存在的外星文明拦截和收到。\n" +
                "\n" +
                "    从上世纪七十年代到现在，旅行者二号一直在孤独的旅行，在茫茫宇宙中它如一粒尘埃般渺小。\n" +
                "\n" +
                "    同时代的外太空探测器，大多或已经发生故障，或已经中断讯号联系，永远的消失在了枯寂的宇宙中。\n" +
                "\n" +
                "    三十几年过去了，科技在不断发展，人类已经研制出更加先进的外太空探测器，也许不久的将来对星空的探索会取得更进一步的发展。\n" +
                "\n" +
                "    但纵然如此，在相当长的时间内，新型外太空探测器依然无法追上旅行者二号的步伐。\n" +
                "\n" +
                "    三十三年过去了，旅行者二号距离地球已经有一百四十亿公里。\n" +
                "\n" +
                "    此时此刻，它已经达到第三宇宙速度，轨道再也不能引导其飞返太阳系，成为了一艘星际太空船。\n" +
                "\n" +
                "    黑暗与冰冷的宇宙中，星辰点点，犹如一颗颗晶莹的钻石镶嵌在黑幕上。\n" +
                "\n" +
                "    旅行者二号太空探测器虽然正在极速飞行，但是在幽冷与无垠的宇宙中却像是一只小小的蚁虫在黑暗的大地上缓缓爬行。\n" +
                "\n" +
                "    三十多年过去后，就在今日这一刻，旅行者二号有了惊人的发现！\n" +
                "\n" +
                "    在枯寂的宇宙中，九具庞大的尸体静静的横在那里……\n" +
                "\n" +
                "    二零一零年五月二十二日，美国宇航局接收到旅行者二号传送回的一组神秘的数据信息，经过艰难的破译与还原，他们看到了一幅不可思议的画面。\n" +
                "\n" +
                "    在这一刻宇航局主监控室内所有人同时变色，最后如木雕泥塑般一动不动，他们震惊到了无以复加的地步！\n" +
                "\n" +
                "    直至过了很久众人才回过神来，而后主监控室内一下子沸腾了。\n" +
                "\n" +
                "    “上帝，我看到了什么？”\n" +
                "\n" +
                "    “这怎么可能，无法相信！”\n" +
                "\n" +
                "    ……");
        tv.setText(items.get(position - 1));
        sum.setText(getCount()+"");
    }

    public int getCount()
    {
        return items.size();
    }

    public View getView()
    {
        View view = LayoutInflater.from(context).inflate(R.layout.reader_novel,
                null);
        return view;
    }
}
