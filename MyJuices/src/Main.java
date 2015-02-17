import java.util.*;
import java.io.*;

public class Main {
    public static void main (String[] args){
        Juicer juicer = new Juicer();
        juicer.read();
        Thread myThread = new Thread(juicer);
        myThread.start();
        //juicer.write();

    }
}
