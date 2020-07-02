package com.example.dockermusic.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListAlbum implements Parcelable {
    private int id;
    private String nameAlbum, nameAlbumSinger, imageAlbum;
    private String nameSong;
    private String nameSinger;
    private String numberLove;

    public ListAlbum(Parcel in) {
        id = in.readInt();
        nameAlbum = in.readString();
        nameAlbumSinger = in.readString();
        imageAlbum = in.readString();
        nameSong = in.readString();
        nameSinger = in.readString();
        numberLove = in.readString();
        urlSong = in.readString();
    }

    public static final Creator<ListAlbum> CREATOR = new Creator<ListAlbum>() {
        @Override
        public ListAlbum createFromParcel(Parcel in) {
            return new ListAlbum(in);
        }

        @Override
        public ListAlbum[] newArray(int size) {
            return new ListAlbum[size];
        }
    };

    public ListAlbum() {

    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public String getNumberLove() {
        return numberLove;
    }

    public void setNumberLove(String numberLove) {
        this.numberLove = numberLove;
    }

    public String getUrlSong() {
        return urlSong;
    }

    public void setUrlSong(String urlSong) {
        this.urlSong = urlSong;
    }

    private String urlSong;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAlbum() {
        return nameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.nameAlbum = nameAlbum;
    }

    public String getNameAlbumSinger() {
        return nameAlbumSinger;
    }

    public void setNameAlbumSinger(String nameAlbumSinger) {
        this.nameAlbumSinger = nameAlbumSinger;
    }

    public String getImageAlbum() {
        return imageAlbum;
    }

    public void setImageAlbum(String imageAlbum) {
        this.imageAlbum = imageAlbum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nameAlbum);
        dest.writeString(nameAlbumSinger);
        dest.writeString(imageAlbum);
        dest.writeString(nameSong);
        dest.writeString(nameSinger);
        dest.writeString(numberLove);
        dest.writeString(urlSong);
    }
}
