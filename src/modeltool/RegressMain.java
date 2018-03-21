package modeltool;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wiyee on 2018/3/13.
 */
public class RegressMain {

    private static int daysBetween(Date start, Date end) {
        int difference = (int) ((end.getTime() - start.getTime()) / 86400000);
        return difference;
    }

    public static void main(String[] args) {
        String testData = "F:\\dataset\\练习数据\\data_2015_2.txt";
        DateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        String start = "2015-12-20";
        String end = "2015-12-26";


        File file = new File(testData);
        Map<String, Integer> map = new LinkedHashMap<>();
        try {
            Date startDate = formate.parse(start);
            Date endDate = formate.parse(end);
            int day = daysBetween(startDate, endDate);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String input = reader.readLine();
            while (input != null) {
                String[] splitString = input.split("\t");
                if (splitString[1].equals("flavor8")) {
                    String time = splitString[2].substring(0, 10);
                    if (map.containsKey(time)) {
                        int count = map.get(time);
                        map.put(time, count + 1);
                    } else
                        map.put(time, 1);
                }
                input = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map);
    }

//    double[][] x = new double[][]{};
//    double[] y = new double[]{};
//    double[] k = new double[8];
//    int n = 8;
//    int length = 5;
}
