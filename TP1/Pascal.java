import java.io.*; 
import java.util.*;

public class Pascal { 
    public static void main(String[] args) {
    	if (args.length < 1) {
    		System.out.println("Usage : ./Pascal <Int>");
    		System.exit(1);
    	}
        int noOfRows = Integer.parseInt(args[0]);
        int counter = 1;
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);

        list = itMe(list, counter,noOfRows);
    }

    public static List<Integer> itMe(List<Integer> list, int counter,int noOfRows) {
        System.out.println(list);

        List<Integer> tempList = new ArrayList<Integer>();

        tempList.add(1);
        for (int i = 1; i < list.size(); i++) {
            tempList.add(list.get(i) + list.get(i-1));
        }
        tempList.add(1);

        if(counter != noOfRows)
            itMe(tempList, ++counter,noOfRows);

        return tempList;
    }
}
