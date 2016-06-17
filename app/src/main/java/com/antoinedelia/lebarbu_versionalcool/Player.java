package com.antoinedelia.lebarbu_versionalcool;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Player implements Parcelable {

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
    private String name;
    private int numberSips;
    private String specialTrait;
    private List<Card> listCards;

    public Player(String namePlayer) {
        name = namePlayer;
        numberSips = 0;
        specialTrait = "";
        listCards = new ArrayList<>();
    }

    private Player(Parcel in) {
        // This order must match the order in writeToParcel()
        name = in.readString();
        numberSips = in.readInt();
        specialTrait = in.readString();
        //TODO handle that
        //in.readTypedList(listCards, Card.CREATOR);
        // Continue doing this for the rest of your member data
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberSips() {
        return numberSips;
    }

    public void setNumberSips(int number) {
        numberSips = number;
    }

    public String getSpecialTrait() {
        return specialTrait;
    }

    public void setSpecialTrait(String trait) {
        specialTrait = trait;
    }

    public List<Card> getCards() {
        if(listCards == null) listCards = new ArrayList<>();
        return listCards;
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
        //TODO handle that
        //dest.writeList(listCards);
    }

    @Override
    public String toString() {
        return name;
    }
}
