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
    private List<Trait> specialTrait;
    private List<Card> listCards;

    public Player(String namePlayer) {
        name = namePlayer;
        numberSips = 0;
        specialTrait = new ArrayList<>();
        listCards = new ArrayList<>();
    }

    private Player(Parcel in) {
        // This order must match the order in writeToParcel()
        name = in.readString();
        numberSips = in.readInt();
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

    public List<Trait> getSpecialTrait() {
        return specialTrait;
    }

    public void setSpecialTrait(List<Trait> trait) {
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
        //TODO handle that
        //dest.writeList(listCards);
    }

    @Override
    public String toString() {
        return name;
    }

    public enum Trait{
        MASTER(0),
        QUEEN(1),
        SNAKE(2),
        BISCUIT(3);

        private int numVal;

        Trait(int num) {
            this.numVal = num;
        }

        public int getNumVal() {
            return numVal;
        }
    }

    public boolean HasTrait()
    {
        return specialTrait.contains(Trait.MASTER) || specialTrait.contains(Trait.QUEEN) || specialTrait.contains(Trait.SNAKE) || specialTrait.contains(Trait.BISCUIT);
    }
}
