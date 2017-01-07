package com.example.tai_fiction.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Tai on 2016/4/11.
 */
public class IndexLablesEntity {


    private String hash;
    private int result;
    /**
     * name : 图书分类
     * key : book
     */

    private List<SectionBean> section;
    /**
     * count : 12
     * description :
     * image : http://image.read.duokan.com/mfsv2/download/fdsc3/p01C6YSjqLAd/ogn8JYlF081XiC.jpg
     * label : 新闻人物
     * sid : 1
     * category_id : a9e148c8d57311e1b8f300163e0060da
     */


    private List<BookBean> book;


    public List<BookBean> getBook() {
        return book;
    }

    public void setBook(List<BookBean> book) {
        this.book = book;
    }



    public static class SectionBean {
        private String name;
        private String key;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }


    public static class BookBean {
        private int count;
        private String description;
        private String image;
        private String cover;
        private String label;
        private String new_image;
        private String titles;
        private int sid;
        private String category_id;
        private int book_count;
        /**
         * description :
         * image :
         * cover : http://cover.read.duokan.com/mfsv2/download/fdsc3/p01cwWBnGzP4/cAI6taAT6zQrTD.jpg!s
         * label : 言情
         * new_image :
         * titles : 十五年等待候鸟、我不喜欢这世界，我只喜欢你、闫小姐...
         * sid : 57
         * category_id : 72e87d42cb4a11e1b8f300163e0060da
         * book_count : 631
         */

        private List<ItemsBean> items;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }


        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getNew_image() {
            return new_image;
        }

        public void setNew_image(String new_image) {
            this.new_image = new_image;
        }


        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public int getBook_count() {
            return book_count;
        }

        public void setBook_count(int book_count) {
            this.book_count = book_count;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Parcelable {
            private String description;
            private String image;
            private String cover;
            private String label;
            private String new_image;
            private String titles;
            private int sid;
            private String category_id;
            private int book_count;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getNew_image() {
                return new_image;
            }

            public void setNew_image(String new_image) {
                this.new_image = new_image;
            }

            public String getTitles() {
                return titles;
            }

            public void setTitles(String titles) {
                this.titles = titles;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public int getBook_count() {
                return book_count;
            }

            public void setBook_count(int book_count) {
                this.book_count = book_count;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.description);
                dest.writeString(this.image);
                dest.writeString(this.cover);
                dest.writeString(this.label);
                dest.writeString(this.new_image);
                dest.writeString(this.titles);
                dest.writeInt(this.sid);
                dest.writeString(this.category_id);
                dest.writeInt(this.book_count);
            }

            public ItemsBean() {
            }

            protected ItemsBean(Parcel in) {
                this.description = in.readString();
                this.image = in.readString();
                this.cover = in.readString();
                this.label = in.readString();
                this.new_image = in.readString();
                this.titles = in.readString();
                this.sid = in.readInt();
                this.category_id = in.readString();
                this.book_count = in.readInt();
            }

            public static final Parcelable.Creator<ItemsBean> CREATOR = new Parcelable.Creator<ItemsBean>() {
                @Override
                public ItemsBean createFromParcel(Parcel source) {
                    return new ItemsBean(source);
                }

                @Override
                public ItemsBean[] newArray(int size) {
                    return new ItemsBean[size];
                }
            };
        }
    }



}
