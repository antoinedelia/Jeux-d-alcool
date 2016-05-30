package com.antoinedelia.lebarbu_versionalcool;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

    private String name;
    private int numberSips;
    private String specialTrait;

    public Player(){
        name = "";
        numberSips = 0;
        specialTrait = "";
    }

    public Player(String namePlayer){
        name = namePlayer;
        numberSips = 0;
        specialTrait = "";
    }

    private Player(Parcel in) {
        // This order must match the order in writeToParcel()
        name = in.readString();
        numberSips = in.readInt();
        specialTrait = in.readString();
        // Continue doing this for the rest of your member data
    }

    public int getNumberSips(){
        return numberSips;
    }

    public void setNumberSips(int number){
        numberSips = number;
    }

    public String getSpecialTrait(){
        return specialTrait;
    }

    public void setSpecialTrait(String trait){
        specialTrait = trait;
    }

    public String getName(){
        return name;
    }

    public void setName(String namePlayer){
        name = namePlayer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(numberSips);
        dest.writeString(specialTrait);
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public String toString() {
        return name;
    }
}
