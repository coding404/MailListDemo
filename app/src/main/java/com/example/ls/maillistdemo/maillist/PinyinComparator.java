package com.example.ls.maillistdemo.maillist;

import java.util.Comparator;

/**
 * Created by LS on 2017/12/29.
 */

public class PinyinComparator implements Comparator<MedicineBean> {

    public int compare(MedicineBean o1, MedicineBean o2) {
        if (o1.getLetter().equals("@") || o2.getLetter().equals("#")) {
            return -1;
        } else if (o1.getLetter().equals("#")
                || o2.getLetter().equals("@")) {
            return 1;
        } else {
            return o1.getLetter().compareTo(o2.getLetter());
        }
    }
}
