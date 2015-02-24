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
                juice.putComponents(bufComponents);
                juice.putCount(bufComponents.size());
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
            Collections.sort(allComponents, new Compare());
            Iterator it1 = this.components.iterator();
            while (it1.hasNext())
                out1.write(it1.next() + " ");
            out1.close();
            Iterator it2 = this.allComponents.iterator();
            while (it2.hasNext())
                out2.write(it2.next() + " ");
            out2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() throws IOException {
        PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter("juice1.out")));
        PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("juice2.out")));
        Collections.sort(allComponents, new Compare());
        Iterator it1 = this.components.iterator();
        while (it1.hasNext())
            out1.write(it1.next() + " ");
        out1.close();
        Iterator it2 = this.allComponents.iterator();
        while (it2.hasNext())
            out2.write(it2.next() + " ");
        out2.close();
    }

    class Compare implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            int c = minimum(s1.length(), s2.length());
            for (int i = 0; i < c; i++) {
                if (s1.charAt(i) < s2.charAt(i)) {
                    c = -1;
                    break;
                }
                if (s1.charAt(i) > s2.charAt(i)) {
                    c = 1;
                    break;
                }
                if (s1.charAt(i) == s2.charAt(i))
                    c = 0;
            }
            return c;
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

    public int minimum(int l1, int l2) {
        int min = 0;
        if (l1 <= l2) min = l1;
        else min = l2;
        return min;
    }

    public void washing() throws IOException {
        int washCount = 0;
        PrintWriter out = new PrintWriter("juice3.out");
        Collections.sort(allJuices, new CountCompare());
        Iterator<Juice> itJuices = allJuices.iterator();
        while (itJuices.hasNext()) {
            Collections.sort(itJuices.next().getComponents(), new Compare());
        }
        Iterator<Juice> itJuices2 = allJuices.iterator();

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