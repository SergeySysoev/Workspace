package com.sysoev.exam;

import java.util.*;
import java.io.*;

public class Juice {
    private ArrayList<String> components;

    public Juice() {
        this.components = new ArrayList<String>();
    }

    public ArrayList<String> getComponents() {
        return components;
    }

    public int getCount() {
        return components.size();
    }

    public void setComponents(ArrayList<String> components) {
        this.components = components;
    }
}


