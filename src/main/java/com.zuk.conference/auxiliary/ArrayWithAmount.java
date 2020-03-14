package com.zuk.conference.auxiliary;

import com.zuk.conference.model.Conference;

import java.util.ArrayList;

public class ArrayWithAmount {
    private ArrayList arrayList;
    private int amount;

    public ArrayList getArrayList() {
        return arrayList;
    }

    public int getAmount() {
        return amount;
    }

    public ArrayWithAmount() {
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
        this.amount = arrayList.size();
    }
}
