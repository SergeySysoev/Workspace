package com.sysoev.exam;

import com.sysoev.exam.Juice;

import java.io.*;
import java.util.*;

public class Juicer implements Runnable {
    private ArrayList<String> allComponents;
    private LinkedHashSet<String> components;
    private ArrayList<Juice> allJuices;

    public Juicer() {
        this.allComponents = new ArrayList<String>();
        this.components = new LinkedHashSet<String>();
        this.allJuices = new ArrayList<Juice>();
    }

    public ArrayList<String> getAllComponents() {
        return allComponents;
    }

    public LinkedHashSet<String> getComponents() {
        return components;
    }

    public void read() {
        String path = "juice.in";
        try {
            BufferedReader buf = new BufferedReader(new FileReader(path));
            String line;
            while ((line = buf.readLine()) != null) {
                ArrayList<String> bufComponents = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer(line);
                Juice juice = new Juice();
                while (st.hasMoreTokens()) {
                    String bufToken = st.nextToken();
                    bufComponents.add(bufToken);
                    allComponents.add(bufToken);
                    components.add(bufToken);
                }
                juice.setComponents(bufComponents);
                allJuices.add(juice);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter("juice1.out")));
            PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("juice2.out")));
            Collections.sort(allComponents);
            for(String s: components)
                out1.write(s + " ");
            out1.close();
            for(String s: allComponents)
                out2.write(s + " ");
            out2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class CountCompare implements Comparator<Juice> {
        @Override
        public int compare(Juice j1, Juice j2) {
            int c = 0;
            if (j1.getCount() > j2.getCount())
                c = 1;
            if (j1.getCount() < j2.getCount())
                c = -1;
            return c;
        }
    }

    public void washing() throws IOException {
        int washCount = 0;
        PrintWriter out = new PrintWriter("juice3.out");
        Collections.sort(allJuices, new CountCompare());
        for(Juice j: allJuices) {
            Collections.sort(j.getComponents());
        }
        for (int i = 0; i < allJuices.size() - 1; i++) {
            if (allJuices.get(i).getComponents().equals(allJuices.get(i + 1).getComponents())) {
                allJuices.remove(i + 1);
                i--;
            }
        }
        while (allJuices.size() != 0) {
            int currentIndex = 0;
            ArrayList<String> inJuicer = new ArrayList<String>();
            inJuicer = allJuices.get(currentIndex).getComponents();
            for (int i = 1; i < allJuices.size(); i++) {
                if (allJuices.get(i).getComponents().containsAll(inJuicer)) {
                    allJuices.remove(currentIndex);
                    currentIndex = i - 1;
                    inJuicer = allJuices.get(currentIndex).getComponents();
                    i--;
                }
            }
            allJuices.remove(currentIndex);
            washCount++;
        }
        out.write(Integer.toString(washCount));
        out.close();
    }
}
