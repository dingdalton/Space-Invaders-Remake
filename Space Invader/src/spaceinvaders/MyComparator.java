package spaceinvaders;

import java.util.Comparator;

public class MyComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
    	int s1 = Integer.parseInt(o1.split(":")[1]);
    	int s2 = Integer.parseInt(o2.split(":")[1]);
        return s2-s1;
    }
}