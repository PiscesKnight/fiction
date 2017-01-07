package com.example.tai_fiction.entity;

import com.example.tai_fiction.R;

/**
 * Created by 泰子 on 2015/11/3.
 */
public class TestData {

    /**
     * Recommend
     */
    private String title = "遮天";
    private String content = "简介：冰冷与黑暗并存的宇宙深处，九具庞大的龙尸拉着一口青铜古棺，亘古长存。" +
            "这是太空探测器在枯寂的宇宙中捕捉到的一幅极其震撼的画面。 九龙拉棺，究竟是回到了上古，还是来到了星空的彼岸？" +
            "一个浩大的仙侠世界，光怪6离，神秘无尽。热血似火山沸腾，激情若瀚海汹涌，欲望如深渊无止境…… 登天路，踏歌行，" +
            "弹指遮天。";
    private String writer = "天蚕土豆";
    private String collect_num = "123456";//收藏数
    private int coverId = R.mipmap.abv123456;//书籍图
    private int collectId = R.mipmap.iconfont_yishoucang_huise;//收藏数图标
    private int userheadId = R.mipmap.iconfont_userhead;//用户头像

    public TestData(String title, String content, String writer, int coverId
            , int collectId, String collect_num, int userheadId) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.coverId = coverId;
        this.collectId = collectId;
        this.collect_num = collect_num;
        this.userheadId = userheadId;
    }




    /**
     * Classfly
     */
    private String[] array_mtitle={"玄幻","玄幻","玄幻","玄幻","玄幻","玄幻","玄幻","玄幻","玄幻"};
    private String[] array_num ={"1222222","1231311","1354678","2456498","54678212","45678912","654865452","13254884","13215698"};
    private int imgId = R.mipmap.abv123456;

    private String mtitle;
    private String num;

    public TestData(String mtitle,String num){
        this.title = mtitle;
        this.num = num;
    }

    /**
     * Book
     * @param book_name
     * @param coverId
     */
    public TestData(String book_name,int coverId){
        this.title = book_name;
        this.coverId = coverId;
    }

    public TestData(String mBook_name,String mIntro,String mWriter,int coverId){
        this.title = mBook_name;
        this.content = mIntro;
        this.writer = mWriter;
        this.coverId = coverId;
    }




    public TestData() {
    }

    public String getTitle() {
        return title;
    }

    public int getCoverId() {
        return coverId;
    }

    public int getCollectId() {
        return collectId;
    }

    public String getCollect_num() {
        return collect_num;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public String getMtitle() {
        return mtitle;
    }

    public int getImgId() {
        return imgId;
    }

    public String getNum() {
        return num;
    }

    public int getUserheadId() {
        return userheadId;
    }

    public String[] getArray_num() {
        return array_num;
    }

    public String[] getArray_mtitle() {
        return array_mtitle;
    }
    public void setArray_mtitle(String[] array_mtitle) {
        this.array_mtitle = array_mtitle;
    }
}
