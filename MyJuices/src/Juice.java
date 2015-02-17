import java.util.*;
import java.io.*;


public class Juice  {
    private ArrayList<String> components;
    private int count;

    public Juice(){
        this.components = new ArrayList<String>();
    }

    public ArrayList<String> getComponents() {
        return components;
    }

    public int getCount() {
        return count;
    }

    public void putComponents(ArrayList<String> components) {
        this.components = components;
    }

    public void putCount(int count) {
        this.count = count;
    }

    class Compare implements Comparator<String>{
        @Override
        public int compare(String s1, String s2){
            int c = minimum(s1.length(),s2.length());
            for(int i = 0; i< c; i++){
                if(s1.charAt(i) < s2.charAt(i))
                {
                    c = -1;
                    break;
                }
                if(s1.charAt(i) > s2.charAt(i)){
                    c = 1;
                    break;
                }
                if(s1.charAt(i) == s2.charAt(i))
                    c = 0;
            }
            return c;
        }
    }

    public int minimum(int l1, int l2) {
        int min = 0;
        if (l1 <= l2) min = l1;
        else min = l2;
        return min;
    }
}

