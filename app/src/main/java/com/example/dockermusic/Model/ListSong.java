package com.example.dockermusic.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListSong implements Parcelable {
    private int id;
    private String nameSong;
    private String nameSinger;
    private String numberLove;
    private String imageSong;
    private String urlSong;

    public ListSong(Parcel in) {
        id = in.readInt();
        nameSong = in.readString();
        nameSinger = in.readString();
        numberLove = in.readString();
        imageSong = in.readString();
        urlSong = in.readString();
    }

    public static final Creator<ListSong> CREATOR = new Creator<ListSong>() {
        @Override
        public ListSong createFromParcel(Parcel in) {
            return new ListSong(in);
        }

        @Override
        public ListSong[] newArray(int size) {
            return new ListSong[size];
        }
    };

    public ListSong() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImageSong() {
        return imageSong;
    }

    public void setImageSong(String imageSong) {
        this.imageSong = imageSong;
    }

    public String getUrlSong() {
        return urlSong;
    }

    public void setUrlSong(String urlSong) {
        this.urlSong = urlSong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nameSong);
        dest.writeString(nameSinger);
        dest.writeString(numberLove);
        dest.writeString(imageSong);
        dest.writeString(urlSong);
    }
}
