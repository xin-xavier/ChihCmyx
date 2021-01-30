package com.chih.mecm.cmyx;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add("e");
        int size = strings.size();
        for (int i = 0; i < size; i++) {
            String s = strings.get(i);
            if (s.equals("a")) {
                strings.add("aa");
                size++;
            }
            System.out.println(":: " + i);
        }
        System.out.println(strings);
    }

}
