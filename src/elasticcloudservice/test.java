package elasticcloudservice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wiyee on 2018/3/21.
 */
public class test {

    public static int[] getPredict2Result(String[] escontent, Date startDate) throws ParseException {
        //得出每周的数据
        int[] flavors_count2_wyy = new int[16];//各个类型虚拟机的数量
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, int[][]> map = new HashMap<>();//<虚拟机名称，每天数量>
        for (String s : escontent) {
            String[] tmp = s.split("\t");
            String name = tmp[1].replace("flavor", "");
            if (Integer.parseInt(name) < 16 && daysBetween(df.parse(tmp[2].substring(0, 10)), startDate) <= 28) {
                int day = daysBetween(df.parse(tmp[2].substring(0, 10)), startDate);
                int x = 3 - (day - 1) / 7;
                int y = 6 - (day - 1) % 7;
                if (map.containsKey(name)) {
                    int[][] tmpInt = map.get(name);
                    tmpInt[x][y]++;
                    map.put(name, tmpInt);
                } else {
                    int[][] tmpInt = getInt(7, 4);
                    tmpInt[x][y]++;
                    map.put(name, tmpInt);
                }
            }
        }
        for (String s : map.keySet()) {
            flavors_count2_wyy[Integer.parseInt(s)] = predict2(map.get(s));
        }
        return flavors_count2_wyy;
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 两个日期之间的天数
     * @author XRH
     */
    private static int daysBetween(Date start, Date end) {
        int difference = (int) ((end.getTime() - start.getTime()) / 86400000);
        return difference;
    }

    /**
     * 去周期性预测
     *
     * @param flavorData
     * @return
     */
    private static int predict2(int[][] flavorData) {
        double[] weekMean = new double[flavorData.length];//周均值。
        double[][] dayProportion = new double[flavorData.length][7];// 每天的数据除以周均值。
        double[] median = new double[]{0, 0, 0, 0, 0, 0, 0};// 中位数
        double[] lastWeekCountWithoutT = new double[]{0, 0, 0, 0, 0, 0, 0};
        double base = 0;
        int result = 0;
        for (int i = 0; i < flavorData.length; i++) {//计算
            int tmp = 0;
            for (int j = 0; j < flavorData[i].length; j++) {
                tmp += flavorData[i][j];
            }
            weekMean[i] = (double) tmp / 7;
        }
        for (int i = 0; i < flavorData.length; i++) { // 每天的数据除以周均值,得到一个比例。
            for (int j = 0; j < flavorData[i].length; j++) {
                if (weekMean[i] != 0) {
                    dayProportion[i][j] = flavorData[i][j] / weekMean[i];
                } else
                    dayProportion[i][j] = 0;
            }
        }

//        // 计算每一列的中位数
//        for (int i = 0; i < flavorData[0].length;i++){
//            List<Double> list = new ArrayList<>();
//            for (int j = 0;j<flavorData.length;j++){
//                list.add(dayProportion[j][i]);
//            }
//            Collections.sort(list);
////            System.out.println(list);
//            Double[] tmpDouble = list.toArray(new Double[0]);
//            median[i] = tmpDouble[(int) tmpDouble.length/2];
//        }
        // 按列取平均值
        for (int i = 0; i < flavorData[0].length; i++) {
            int count = 0;
            for (int j = 0; j < flavorData.length; j++) {
                count += dayProportion[j][i];
            }
            median[i] = (double) count / (flavorData.length + 1);
        }
        System.out.println("median");
        for (double i : median) {
            System.out.print(i + ",");
            System.out.println();
        }
        //计算base值（去周期以后的虚拟机创建数）
//        for (int i=0;i<lastWeekCountWithoutT.length;i++){
//            if (median[i]!=0)
//                lastWeekCountWithoutT[i] = (double) flavorData[flavorData.length-1][i]/median[i];
//            else
//                lastWeekCountWithoutT[i] = 0;
//        }
//        for (int i=4;i<7;i++){
//            base += lastWeekCountWithoutT[i];
//        }
//        base /= 3;
        base = weekMean[flavorData.length - 1];
        System.out.println(base);
        double tmp = 0;
        for (int i = 0; i < 7; i++) {
            tmp += base * median[i];
        }
        result = (int) tmp;
        return result;
    }

    /**
     * 初始化数组
     *
     * @param length 7
     * @param depth  4
     * @return
     */
    private static int[][] getInt(int length, int depth) {
        int[][] data = new int[depth][length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = 0;
            }
        }
        return data;
    }
}
