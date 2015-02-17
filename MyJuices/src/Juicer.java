import java.io.*;
import java.util.*;

public class Juicer implements Runnable{
    private ArrayList<String> allComponents;
    private LinkedHashSet<String> components;
    private ArrayList<Juice> allJuices;

    public Juicer(){
        this.allComponents = new ArrayList<String>();
        this.components = new LinkedHashSet<String>();
        this.allJuices = new ArrayList<Juice>();
    }

    public ArrayList<String> getAllComponents(){
        return allComponents;
    }

    public LinkedHashSet<String> getComponents(){
        return components;
    }

    public void read(){
        String path = "juice.in";
        try {
            BufferedReader buf = new BufferedReader(new FileReader(path));
            String line;
            while ((line = buf.readLine())!=null)
            {
                ArrayList<String> bufComponents = new ArrayList<String>();
                StringTokenizer st = new StringTokenizer (line);
                Juice juice = new Juice();
                while(st.hasMoreTokens()){
                    String bufToken = st.nextToken();
                    bufComponents.add(bufToken);
                    allComponents.add(bufToken);
                    components.add(bufToken);
                }
                juice.putComponents(bufComponents);
                juice.putCount(bufComponents.size());
                allJuices.add(juice);
            }
        }
        catch (IOException e) {
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
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void write() throws IOException{
        PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter("juice1.out")));
        PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("juice2.out")));
        Collections.sort(allComponents, new Compare());
        Iterator it1 = this.components.iterator();
        while(it1.hasNext())
            out1.write(it1.next()+ " ");
        out1.close();
        Iterator it2 = this.allComponents.iterator();
        while(it2.hasNext())
            out2.write(it2.next()+ " ");
        out2.close();
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
