package com.example.tai_fiction.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tai_fiction.entity.BookDetailsEntity;
import com.example.tai_fiction.entity.BookIntroEntity;
import com.example.tai_fiction.entity.IndexLablesEntity;
import com.example.tai_fiction.entity.MyCollectEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tai on 2016/4/14.
 */
public class BookDB {

    //数据库版本
    public static final int VERSION = 1;

    private static BookDB bookDB;

    private SQLiteDatabase db;

    private BookDB(Context context, String DB_NAME) {
        BookDatabaseHelper dbHpler = new BookDatabaseHelper(context, DB_NAME, null, VERSION);
        db = dbHpler.getWritableDatabase();
    }

    /**
     * BookDB的实例
     * DB_NAME:数据库名
     */
    public synchronized static BookDB getInstance(Context context, String DB_NAME) {
        if (bookDB == null) {
            bookDB = new BookDB(context, DB_NAME);
        }
        return bookDB;
    }

    /**
     * 判断是否有重复数据插入
     * <p/>
     */

    public Boolean isTureBook(int sid) {
        //先查询是否有重复数据
        Cursor cursor = db.rawQuery("select sid from book", null);
        boolean isTure = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int dbSid = cursor.getInt(cursor.getColumnIndex("sid"));//查询不重复的数据？？？
                    if (dbSid == sid) {
                        isTure = true;
                        break;
                    }
                } while (cursor.moveToNext());
            }
        } else {
            isTure = false;
        }
        cursor.close();
        return isTure;
    }

    public Boolean isTureLabel() {
        //先查询是否有重复数据
        Cursor cursor = db.rawQuery("select sid from label", null);
        boolean isTure = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    isTure = true;
                    break;

                } while (cursor.moveToNext());
            }
        } else {
            isTure = false;
        }
        cursor.close();
        return isTure;
    }

    /**
     * 向sqlite插入book数据
     */
    public void installData(List<BookIntroEntity.ItemsBean> booksData, int sid) {

        ContentValues values = new ContentValues();
        for (int i = 0; i < booksData.size(); i++) {
            values.put("sid", sid);
            values.put("title", booksData.get(i).getTitle());
            values.put("summary", booksData.get(i).getSummary());
            values.put("cover", booksData.get(i).getCover());
            values.put("authors", booksData.get(i).getAuthors());
            values.put("book_id", booksData.get(i).getBook_id());
            db.insert("Book", null, values); //  插入数据
            //  Log.d("Book", "网络请求插入");
        }
        values.clear();
    }

    /**
     * 从sqlite获得小说列表数据
     */
    public List<BookIntroEntity.ItemsBean> getDataForSqlite(int sid) {

        List<BookIntroEntity.ItemsBean> list = new ArrayList<>();
        //  查询Book表中点击的列表的数据
        Cursor cursor = db.rawQuery("select * from book where sid='" + sid + "'", null);
        if (cursor.moveToFirst()) {
            do {
                BookIntroEntity.ItemsBean itemsBean = new BookIntroEntity.ItemsBean();
                //  遍历Cursor 对象，取出数据并打印
                itemsBean.setBook_id(cursor.getString(cursor.
                        getColumnIndex("book_id")));
                itemsBean.setAuthors(cursor.getString(cursor.
                        getColumnIndex("authors")));
                itemsBean.setCover(cursor.getString(cursor.getColumnIndex
                        ("cover")));
                itemsBean.setSummary(cursor.getString(cursor.
                        getColumnIndex("summary")));
                itemsBean.setTitle(cursor.getString(cursor.
                        getColumnIndex("title")));

                list.add(itemsBean);
            } while (cursor.moveToNext());
            Log.d("Book", "从数据库中取出");
        }
        cursor.close();
        return list;
    }

    /**
     * 向数据库插入label数据
     *
     * @param booksData
     */
    //缺插入items
    public void installLabelData(List<IndexLablesEntity.BookBean> booksData) {
        ContentValues values = new ContentValues();
        if (booksData != null) {//不断网时
            for (int i = 0; i < booksData.size(); i++) {
                values.put("cover", booksData.get(i).getNew_image());
                values.put("bookcount", booksData.get(i).getBook_count());
                values.put("label", booksData.get(i).getLabel());
                values.put("sid", booksData.get(i).getSid());
                db.insert("Label", null, values); //  插入数据
                Log.d("Book", "网络请求插入Label");
            }
            values.clear();
        }
    }

    /**
     * 取出label表的数据
     */
    public List<IndexLablesEntity.BookBean> getLabelForSqlite() {
        //  查询Book 表中所有的数据
        Cursor cursor = db.rawQuery("select * from label ", null);
        List<IndexLablesEntity.BookBean> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                IndexLablesEntity.BookBean bookBean = new IndexLablesEntity.BookBean();
                //  遍历Cursor 对象，取出数据并打印
                bookBean.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
                bookBean.setLabel(cursor.getString(cursor.
                        getColumnIndex("label")));
                bookBean.setBook_count(cursor.getInt(cursor.
                        getColumnIndex("bookcount")));
                bookBean.setCover(cursor.getString(cursor.getColumnIndex
                        ("cover")));
                list.add(bookBean);

            } while (cursor.moveToNext());

            Log.d("Book", "数据库取出Label");
        }
        cursor.close();
        return list;
    }

    /**
     * 添加收藏数据
     */

    public boolean addCollect(BookDetailsEntity.ItemBean itemsBeans) {
        ContentValues values = new ContentValues();
        Cursor cursor = db.rawQuery("select book_id from collect where book_id='"+itemsBeans.getBook_id()+"'", null);
        if (cursor.getCount()==0) {
            values.put("title", itemsBeans.getTitle());
            values.put("authors", itemsBeans.getAuthors());
            values.put("cover", itemsBeans.getCover());
            values.put("score_count", itemsBeans.getScore_count());
            values.put("book_id", itemsBeans.getBook_id());
            db.insert("collect", null, values); //  插入数据
            Log.d("Book", "添加我的收藏成功");
            return true;
        }
        cursor.close();
        values.clear();
        return false;
    }

    /**
     * 我的收藏提取数据
     *
     * @return
     */
    public List<MyCollectEntity> getMyCollectData() {
        Cursor cursor = db.rawQuery("select * from collect", null);
        List<MyCollectEntity> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                MyCollectEntity myCollectEntity = new MyCollectEntity();
                //  遍历Cursor 对象，取出数据并打印
                myCollectEntity.setAuthors(cursor.getString(cursor.
                        getColumnIndex("authors")));
                myCollectEntity.setCover(cursor.getString(cursor.
                        getColumnIndex("cover")));
                myCollectEntity.setBookcount(cursor.getInt(cursor.
                        getColumnIndex("score_count")));
                myCollectEntity.setTitle(cursor.getString(cursor.
                        getColumnIndex("title")));
                myCollectEntity.setBookId(cursor.getString(cursor.
                        getColumnIndex("book_id")));
                Log.d("Book",cursor.getString(cursor.getColumnIndex("title")));
                list.add(myCollectEntity);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    /**
     * 添加书架数据
     * @param itemBean
     * @return
     */
    public boolean addBookRack(BookDetailsEntity.ItemBean itemBean){
        ContentValues values = new ContentValues();
        Cursor cursor = db.rawQuery("select book_id from bookrack where book_id='"
                +itemBean.getBook_id()+"'", null);
        if(cursor.getCount()==0){
            values.put("cover", itemBean.getCover());
            values.put("title", itemBean.getTitle());
            values.put("book_id", itemBean.getBook_id());
            db.insert("bookrack",null,values);
            return true;
        }
        cursor.close();
        values.clear();
        return false;
    }

    /**
     * 提取书架数据
     * @return
     */
    public List<MyCollectEntity> getBookRackData() {
        Cursor cursor = db.rawQuery("select * from bookrack", null);
        List<MyCollectEntity> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                MyCollectEntity myCollectEntity = new MyCollectEntity();
                //  遍历Cursor 对象，取出数据并打印

                myCollectEntity.setCover(cursor.getString(cursor.
                        getColumnIndex("cover")));
                myCollectEntity.setTitle(cursor.getString(cursor.
                        getColumnIndex("title")));
                myCollectEntity.setBookId(cursor.getString(cursor.
                        getColumnIndex("book_id")));

                list.add(myCollectEntity);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    /**
     * 删除收藏数据
     */
    public void deleteMycollectData(String book_id){
        db.delete("collect","book_id=?",new String[]{book_id});
    }
    /**
     * 删除所有
     */
    public void deleteAllMycollect(){
        db.delete("collect",null,null);
    }
}
