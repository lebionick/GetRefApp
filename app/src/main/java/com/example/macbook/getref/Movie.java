package com.example.macbook.getref;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by macbook on 04.06.16.
 */
public class Movie implements Comparable<Movie>, Parcelable, Serializable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    protected Movie(Parcel source){
        mId = source.readInt();
        mName = source.readString();
        mDescription = source.readString();
        mActors = source.readString();
        mPicture = source.readString();
    }

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("actors")
    private String mActors;
    @SerializedName("picture")
    private String mPicture;

    public int getId(){
        return mId;
    }
    public String getName(){
        return mName;
    }
    public String getDescription(){
        return mDescription;
    }
    public String getActors(){
        return mActors;
    }
    public String getPicture(){
        return mPicture;
    }
    @Override
    public int compareTo(@NonNull Movie next){
        return getName().compareTo(next.getName());
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(mId);
        out.writeString(mName);
        out.writeString(mDescription);
        out.writeString(mActors);
        out.writeString(mPicture);
    }
}
