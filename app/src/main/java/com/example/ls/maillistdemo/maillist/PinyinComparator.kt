package com.example.ls.maillistdemo.maillist

import java.util.*

/**
 * Created by LS on 2017/12/29.
 */

class PinyinComparator : Comparator<MedicineBean> {

    override fun compare(o1: MedicineBean, o2: MedicineBean): Int {
        return if (o1.letter == "@" || o2.letter == "#") {
            -1
        } else if (o1.letter == "#" || o2.letter == "@") {
            1
        } else {
            o1.letter!!.compareTo(o2.letter!!)
        }
    }
}
