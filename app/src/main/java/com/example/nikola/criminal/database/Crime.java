package com.example.nikola.criminal.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@Entity
public class Crime implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mID;
    @ColumnInfo(name = "Title")
    private String mTitle;
    @ColumnInfo(name = "Date")
    private Date mDate;
    @ColumnInfo(name = "Solved")
    private boolean mSolved;
    @ColumnInfo(name = "Police")
    private boolean mRequiredPolice;
    @ColumnInfo(name = "Hour")
    private int mHour;
    @ColumnInfo(name = "Minute")
    private int mMinute;

    public Crime() {
//        mID = UUID.randomUUID();
        mDate = new Date();
    }

//    public UUID getID() {
//        return mID;
//    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public int getHour() {
        return mHour;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setHour(int hour) {

        mHour = hour;
    }

    public void setMinute(int minute) {
        mMinute = minute;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public boolean isRequiredPolice() {
        return mRequiredPolice;
    }

    public void setRequiredPolice(boolean requiredPolice) {
        mRequiredPolice = requiredPolice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.mID);
        dest.writeString(this.mTitle);
        dest.writeLong(this.mDate != null ? this.mDate.getTime() : -1);
        dest.writeByte(this.mSolved ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mRequiredPolice ? (byte) 1 : (byte) 0);
    }

    protected Crime(Parcel in) {
//        this.mID = (UUID) in.readSerializable();
        this.mTitle = in.readString();
        long tmpMDate = in.readLong();
        this.mDate = tmpMDate == -1 ? null : new Date(tmpMDate);
        this.mSolved = in.readByte() != 0;
        this.mRequiredPolice = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Crime> CREATOR = new Parcelable.Creator<Crime>() {
        @Override
        public Crime createFromParcel(Parcel source) {
            return new Crime(source);
        }

        @Override
        public Crime[] newArray(int size) {
            return new Crime[size];
        }
    };
}
