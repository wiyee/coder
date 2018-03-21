package elasticcloudservice;

import filetool.util.FileUtil;
import modeltool.Regression;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wiyee on 2018/3/16.
 */
public class Pre {
    public static void main(String[] args) throws ParseException {
        String startTime = "2015-02-20";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
        String inputFilePath = "F:\\dataset\\huawei_ecs\\TrainData_2015.1.1_2015.2.19.txt";
        String[] ecsContent = FileUtil.read(inputFilePath, null);

//        try {
//            predict_flavor1(df.parse(startTime),inputFilePath);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        int[][] test = new int[][]{{20,10,70,50,250,200,100},{26,18,66,50,180,140,80},{15,8,67,60,270,160,120}};
//        int a = predict2(test);
//        System.out.println(a);


        // 线性回归
//        Map<String,int[]> vmMap = new LinkedHashMap<>();
//        try {
//            predict_flavor(df.parse(startTime));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        int[] d = getPredict2Result(ecsContent, df.parse(startTime));
        for (int i : d)
            System.out.println(i);


    }

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

    private static void predict_flavor1(Date startDate, String inputFilePath) {
        //计算平均数 众数  异常数
        double[] mean = new double[16];

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Map<String, int[]> vmMap = new LinkedHashMap<>();
        try {
            String[] inputContent = FileUtil.read(inputFilePath, null);
            for (String s : inputContent) {
                String[] vm = s.split("\t");
                int day = daysBetween(df.parse(vm[2]), startDate);
                if (day < 30 && Integer.parseInt(vm[1].replace("flavor", "")) < 16) {
                    if (vmMap.containsKey(vm[1])) {
                        int[] tmp = vmMap.get(vm[1]);
                        tmp[day]++;
                        vmMap.put(vm[1], tmp);
                    } else {
                        int[] tmp = new int[30];
                        for (int i : tmp)
                            i = 0;
                        tmp[day]++;
                        vmMap.put(vm[1], tmp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String key : vmMap.keySet()) {
            int[] ints = vmMap.get(key);
            Map<Integer, Integer> map = new HashMap<>();
            int sum = 0;
            for (int i : ints) {
                sum += i;
                if (map.containsKey(i)) {
                    int count = map.get(i);
                    map.put(i, count + 1);
                } else {
                    map.put(i, 1);
                }
            }
            sortMapByValue(map);//对value排序
            int modeTmp = 0;//众数
            for (int i : map.keySet()) {//将异常值替换为众数
                if (i > 0 && modeTmp == 0) {
                    modeTmp = i;
                } else if (i > (modeTmp * 2 + 1)) {
                    map.put(i, modeTmp);
                }
            }
            mean[Integer.parseInt(key.replace("flavor", ""))] = sum / 30;
        }
    }

    /**
     * 根据value排序
     *
     * @param map
     * @return
     */
    public static LinkedHashMap<?, Integer> sortMapByValue(Map<?, Integer> map) {
        List<Map.Entry<?, Integer>> list = new ArrayList<Map.Entry<?, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<?, Integer>>() {
            public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        LinkedHashMap<Object, Integer> resultMap = new LinkedHashMap<Object, Integer>();
        for (Map.Entry<?, Integer> t : list) {
            resultMap.put(t.getKey(), t.getValue());
        }
        return resultMap;
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
     * 线性回归
     *
     * @param startDate
     */
    public static void predict_flavor(Date startDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<String, int[]> vmMap = new LinkedHashMap<>();
        try {
            String inputFilePath = "F:\\dataset\\huawei_ecs\\TrainData_2015.1.1_2015.2.19.txt";
            String[] inputContent = FileUtil.read(inputFilePath, null);
            for (String s : inputContent) {
                String[] vm = s.split("\t");
                int weekBetween = daysBetween(df.parse(vm[2]), startDate) / 7;
                if (weekBetween < 5 && Integer.parseInt(vm[1].replace("flavor", "")) < 16) {
                    if (vmMap.containsKey(vm[1])) {
                        int[] tmp = vmMap.get(vm[1]);
                        tmp[weekBetween]++;
                        vmMap.put(vm[1], tmp);
                    } else {
                        int[] tmp = new int[]{0, 0, 0, 0, 0};
                        tmp[weekBetween]++;
                        vmMap.put(vm[1], tmp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        double[][] X = new double[15][4];
        double[] Y = new double[15];
        int i = 0;
        for (int[] x : vmMap.values()) {
            ;
            for (int j = 1; j < 5; j++) {
                X[i][j - 1] = x[j];
            }
            Y[i] = x[0];
            i++;
        }
        int[] lossPoint = new int[15];
        double[] K = new double[5];
        double[] K1 = new double[5];
        Regression regression = new Regression();
        regression.LineRegression(X, Y, K, 4, 15);
        regression.optLineRegression(X, Y, K1, 4, 15, 0.5, lossPoint);
        for (double k : K)
            System.out.println("k:" + k);
        for (double k : K1)
            System.out.println("k1:" + k);
    }

    /*
     *
     *    @param  double[][]  X  自变量样本集
     *    @param  double[]    Y  变量结果集
     *    @param  double[]   K  回归系数
     *    @param  int  n  回归变量个数
     *    @param  int  m  样本个数
     * @return
     */
    private static double lineRegression(double[][] X, double[] Y, double[] K, int n, int m) {
        Regression regression = new Regression();
        return regression.LineRegression(X, Y, K, n, m);
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
//        System.out.println("median");
//        for (double i:median) {
//            System.out.print(i + ",");
//            System.out.println();
//        }
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
//        System.out.println(base);
        double tmp = 0;
        for (int i = 0; i < 7; i++) {
            tmp += base * median[i];
        }
        result = (int) tmp;
        return result;
    }

    public static void print(double[][] data) {
        for (int i = 0; i < data.length; i++) { // 每天的数据除以周均值,得到一个比例。
            System.out.println();
            System.out.print("[");
            ;
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + ",");
            }
            System.out.println("]");
        }
    }

    public static void print(int[][] data) {
        for (int i = 0; i < data.length; i++) { // 每天的数据除以周均值,得到一个比例。
            System.out.println();
            System.out.print("[");
            ;
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + ",");
            }
            System.out.println("]");
        }
    }


}
