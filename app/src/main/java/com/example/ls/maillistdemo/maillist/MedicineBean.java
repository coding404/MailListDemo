package com.example.ls.maillistdemo.maillist;

/**
 * Created by LS on 2017/12/29.
 */

public class MedicineBean {
    private String name;
    private String letter;
    private int portId;

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
