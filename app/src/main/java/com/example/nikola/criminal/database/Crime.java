package com.example.nikola.criminal.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

@Entity
public class Crime implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private UUID mID;
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
    @ColumnInfo(name = "Suspect")
    private String mSuspect;
    @ColumnInfo(name = "Suspects Number")
    private String mSuspectNumber;

    public Crime() {
        mID = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getID() {
        return mID;
    }

    public void setID(UUID ID) {
        mID = ID;
    }

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

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getSuspectNumber() {
        return mSuspectNumber;
    }

    public void setSuspectNumber(String suspectNumber) {
        mSuspectNumber = suspectNumber;
    }

    public String getPhotoFilename(){
        return "IMG_" + getID().toString() + ".jpg";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.mID);
        dest.writeString(this.mTitle);
        dest.writeLong(this.mDate != null ? this.mDate.getTime() : -1);
        dest.writeByte(this.mSolved ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mRequiredPolice ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mHour);
        dest.writeInt(this.mMinute);
        dest.writeString(this.mSuspect);
        dest.writeString(this.mSuspectNumber);
    }

    protected Crime(Parcel in) {
        this.mID = (UUID) in.readSerializable();
        this.mTitle = in.readString();
        long tmpMDate = in.readLong();
        this.mDate = tmpMDate == -1 ? null : new Date(tmpMDate);
        this.mSolved = in.readByte() != 0;
        this.mRequiredPolice = in.readByte() != 0;
        this.mHour = in.readInt();
        this.mMinute = in.readInt();
        this.mSuspect = in.readString();
        this.mSuspectNumber = in.readString();
    }

    public static final Creator<Crime> CREATOR = new Creator<Crime>() {
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
