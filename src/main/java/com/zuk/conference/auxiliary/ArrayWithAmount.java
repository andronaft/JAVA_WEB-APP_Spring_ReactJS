package com.zuk.conference.auxiliary;

import java.util.List;

/** Response wrapper for a list: {"arrayList": [...], "amount": n}. */
public class ArrayWithAmount<T> {
    private final List<T> arrayList;

    public ArrayWithAmount(List<T> arrayList) {
        this.arrayList = arrayList;
    }

    public List<T> getArrayList() {
        return arrayList;
    }

    public int getAmount() {
        return arrayList.size();
    }
}
