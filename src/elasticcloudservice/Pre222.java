package elasticcloudservice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import filetool.util.FileUtil;
import filetool.util.LogUtil;
import filetool.util.Constants;

/**
 * Created by wiyee on 2018/3/21.
 */
public class Pre222 {
    public static void main(String[] args) {
        String inputFilePath = "F:\\dataset\\huawei_ecs\\input_5flavors_cpu_7days.txt";
        String ecsDataPath = "F:\\dataset\\huawei_ecs\\TrainData_2015.1.1_2015.2.19.txt";
        String resultFilePath = "F:\\dataset\\huawei_ecs\\out.txt";
        String[] ecsContent = FileUtil.read(ecsDataPath, null);
        String[] inputContent = FileUtil.read(inputFilePath, null);
        // 功能实现入口
        String[] resultContents = Predict.predictVm(ecsContent, inputContent);
    }

    public static int[] cpu_type = {0, 1, 1, 1, 2, 2, 2, 4, 4, 4, 8, 8, 8, 16, 16, 16};
    public static int[] mem_type = {0, 1, 2, 4, 2, 4, 8, 4, 8, 16, 8, 16, 32, 16, 32, 64};

    public static int[] sort_instances = {0, 15, 12, 9, 6, 3, 14, 11, 8, 5, 2, 13, 10, 7, 4, 1};
    public static int[] sort_instances2 = {0, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    public static int[] sort_instances3 = {0, 3, 6, 9, 12, 15, 2, 5, 8, 11, 14, 1, 4, 7, 10, 13};
    //	public static int[] sort_instances4 = {0,3,6,9,12,15,2,5,8,11,14,1,4,7,10,13};
//	public static int[] sort_instances5 = {0,3,6,9,12,15,2,5,8,11,14,1,4,7,10,13};
    //虚拟机类型列表
    public static ArrayList<String> flavor_type_list = new ArrayList<String>();//需要预测的虚拟机类型列表

    //用二维数组表示每台服务器上每种虚拟机类型的数量,server[i][j]代表第i台服务器上第j类虚拟机的数量
    public static int[][] server = new int[100][20];
    public static int[] flavors_count = new int[Constants.FLAVORS_COUNT];//各个类型虚拟机的数量

    public static int CPU = 0;
    public static int MEM = 0;

    public static String type;

    public static int[][] period_instance = new int[5][16];

    public static String[] predictVm(String[] ecsContent, String[] inputContent) {

        String[] results = new String[ecsContent.length];

        //解析input文件后输出键值对
        Map<String, Object> myMap = new HashMap<String, Object>();
        myMap = parseInputContent(inputContent);

        flavor_type_list = (ArrayList<String>) myMap.get("flavor_type_list");

        CPU = (int) myMap.get(Constants.INPUT_PHYSICAL_CPU);
        MEM = (int) myMap.get(Constants.INPUT_PHYSICAL_MEM);

        int flavor_count = (int) myMap.get("flavor_count");

        type = (String) myMap.get(Constants.INPUT_PHYSICAL_RESOURCES_TYPE);

        String startDate = (String) myMap.get("startDate");
        DateFormat formate2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;
        Date date1 = null;
        try {
            date1 = formate2.parse(startDate);
            date2 = getDate((int) myMap.get("predict_days"), date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date3 = formate2.format(date2);
        String datea = formate2.format(getDate(7, date1));
        String dateb = formate2.format(getDate(14, date1));
        String datec = formate2.format(getDate(21, date1));
        String dated = formate2.format(getDate(28, date1));

        flavorFilter1(ecsContent, flavor_type_list, datea, startDate, 1);
        flavorFilter1(ecsContent, flavor_type_list, dateb, datea, 2);
        flavorFilter1(ecsContent, flavor_type_list, datec, dateb, 3);
        flavorFilter1(ecsContent, flavor_type_list, dated, datec, 4);
        // 预测方式2


        int[] flavors_count2 = new int[Constants.FLAVORS_COUNT];//各个类型虚拟机的数量
        try {
            flavors_count2 = getPredict2Result(ecsContent, date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 15; i++) {
            flavors_count[i] = (int) (0.55 * period_instance[1][i] + 0.30 * period_instance[2][i]
                    + 0.26 * period_instance[3][i] + 0.26 * period_instance[4][i] + 0.5 * flavors_count2[i]);
        }
        int all = 0;
        for (int i = 1; i <= 15; i++) {
            all += flavors_count[i];
        }
//		int all = flavorFilter(ecsContent, flavor_type_list,date3);

        //放置策略
//		int server_count = fillServers(server, flavors_count, CPU, MEM);
        int server_count = fillServers1(server, flavors_count, CPU, MEM);
        server_count = fillServers2_1(CPU, MEM, server_count);

        flavors_count(server, server_count);
        //额外添加虚拟机
        fillFreeServers(server, flavors_count, server_count);
        //results数组赋值
        toResults(results, flavors_count, server, server_count);
        return results;
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
     * 根据预测结果和放置情况对results数组进行复制
     *
     * @param results
     * @param flavors_count 虚拟机数组
     * @param server        服务器数组
     * @param server_count  服务器总数
     */
    private static void toResults(String[] results, int[] flavors_count, int[][] server, int server_count) {
        int k = 1;
        int instances_count = 0;
        for (int i = 1; i < 16; i++) {
            if (flavors_count[i] != 0) {
                results[k] = "flavor" + i + " " + flavors_count[i];
                instances_count += flavors_count[i];
                k++;
            }
        }
        results[0] = String.valueOf(instances_count);

        results[k++] = "";
        results[k] = String.valueOf(server_count);
        for (int i = 1; i <= server_count; i++) {
            results[k + i] = String.valueOf(i);
            for (int j = 1; j < 16; j++) {
                if (server[i][j] != 0) {
                    results[k + i] += " flavor" + j + " " + server[i][j];
                }
            }
        }

    }

    /**
     * 根据server数组 统计flavors_count数组
     *
     * @param server
     * @param server_count
     */
    private static void flavors_count(int[][] server, int server_count) {
        for (int i = 1; i <= 15; i++) {
            flavors_count[i] = 0;
            for (int j = 1; j <= server_count; j++) {
                flavors_count[i] += server[j][i];
            }
        }
    }

    //暂时废弃
    private static String[] setVirtual(int phy_cpu, int phy_mem, int[] flavors_count, int total) {
        String phy_result = null;
        String[] phy_results = new String[20];
        int k = 1, count = 0;
        int cpu = phy_cpu, mem = phy_mem;
        phy_results[k] = String.valueOf(k);
        for (int i = 15; i >= 1; i--) {
            count = 0;
            while (flavors_count[i] != 0) {
                while (flavors_count[i] > 0 && phy_cpu >= cpu_type[i] && phy_mem >= mem_type[i]) {
                    count++;
                    total--;
                    flavors_count[i]--;
                    phy_cpu -= cpu_type[i];
//					System.out.println("CPU:"+ phy_cpu+"count:"+count);
                    phy_mem -= mem_type[i];
                }
                if (count != 0)
                    phy_results[k] += " " + "flavor" + i + " " + count;
                if (total != 0 && flavors_count[i] != 0) {
                    k++;
                    phy_results[k] = String.valueOf(k);
                    phy_cpu = cpu;
                    phy_mem = mem;
                    count = 0;
                }

            }
        }
        return phy_results;

    }

    /**
     * 根据文件内容预测虚拟机数量
     *
     * @param ecsContent       文件内容
     * @param flavor_type_list 待预测虚拟机类型
     * @param startDate        预测开始时间
     * @return 返回所有虚拟机总数, 以及预测结果:数组flavors_count[]是全局变量  flavors_count[i]代表第i个虚拟机的数量
     */
    public static int flavorFilter(String[] ecsContent, ArrayList<String> flavor_type_list, String startDate) {
        int flavor_id = 0;
        int flavors_all_count = 0;
        String flavor = null, filter_string;
        try {
            for (int i = 0; i < ecsContent.length; i++) {
                if (ecsContent[i].split("\t").length == 3) {
                    filter_string = ecsContent[i].split(" ")[0].substring(14);

                    flavor = filter_string.split("\t")[0];

                    String str_time = filter_string.split("\t")[1];
                    if (str_time.compareTo(startDate) >= 0) {
//						System.out.println("strtime"+str_time);
                        if (flavor_type_list.contains(flavor)) {
                            flavor_id = flavor.charAt(6) - '0';
                            if (flavor.length() == 8) {
                                flavor_id = 10 * flavor_id + (flavor.charAt(7) - '0');
                            }
                            flavors_count[flavor_id]++;
                            flavors_all_count++;
                        }
                    }
                }
                if (flavor_id < 1 || flavor_id > 15)
                    continue;
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return flavors_all_count;
    }

    public static int flavorFilter1(String[] ecsContent, ArrayList<String> flavor_type_list, String startDate, String endDate, int num) {
        int flavor_id = 0;
        int flavors_all_count = 0;

        String flavor = null, filter_string;
        try {
            for (int i = 0; i < ecsContent.length; i++) {
                if (ecsContent[i].split("\t").length == 3) {
                    filter_string = ecsContent[i].split(" ")[0].substring(14);

                    flavor = filter_string.split("\t")[0];

                    String str_time = filter_string.split("\t")[1];
                    if (str_time.compareTo(startDate) >= 0 && str_time.compareTo(endDate) < 0) {
//						System.out.println("strtime"+str_time);
                        if (flavor_type_list.contains(flavor)) {
                            flavor_id = flavor.charAt(6) - '0';
                            if (flavor.length() == 8) {
                                flavor_id = 10 * flavor_id + (flavor.charAt(7) - '0');
                            }
                            period_instance[num][flavor_id]++;
                            flavors_all_count++;
                        }
                    }
                }
                if (flavor_id < 1 || flavor_id > 15)
                    continue;
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return flavors_all_count;
    }

    /**
     * 解析input.txt文件的内容 返回map
     *
     * @param inputContent 被解析input文件的内容
     * @return 返回map:map中的"key"包含(pyh_cpu、pyh_mem、flavor_count、flavor_type_list、phy_resources_type、predict_days、startDate、endDate)
     * 分别代表(物理机CPU、物理机内存、需要预测的虚拟机类型的数量、需要预测的虚拟机的类型列表、物理资源类型、预测天数、预测开始时间、预测结束时间)
     * @author XRH
     */
    public static Map<String, Object> parseInputContent(String[] inputContent) {
        Map<String, Object> map = new HashMap<String, Object>();

        String[] phy = inputContent[0].split(" ");
        map.put("phy_cpu", Integer.parseInt(phy[0]));
        map.put("phy_mem", Integer.parseInt(phy[1]));

        int flavor_count = Integer.parseInt(inputContent[2]);
        map.put("flavor_count", flavor_count);

        ArrayList<String> flavor_type_list = new ArrayList<String>();
        int i = 3;
        while ((flavor_count--) > 0) {
            String flavor_type = inputContent[i].split(" ")[0];
            flavor_type_list.add(flavor_type);
            i++;
        }
        map.put("flavor_type_list", flavor_type_list);

        i++;
        map.put("phy_resources_type", inputContent[i++]);
        i++;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            map.put("startDate", inputContent[i].substring(0, 10));
            Date startDate = format.parse(inputContent[i++]);
            map.put("endData", inputContent[i].substring(0, 10));
            Date endData = format.parse(inputContent[i]);
            int predict_days = daysBetween(startDate, endData);
            map.put("predict_days", predict_days);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return map;
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
     * 根据预测的开始时间和预测周期,推算n天前的日期
     *
     * @param predict_days
     * @param end
     * @return
     * @author XRH
     */
    private static Date getDate(int predict_days, Date end) {
        long startTime = end.getTime() - 86400000 * predict_days;
        return new Date(startTime);
    }

    private static int getFlavorId(String flavor_type) {
        int length = flavor_type.length();
        int flavor_id = 0;
        if (length == 8 || length == 7) {
            flavor_id = flavor_type.charAt(6) - '0';
            if (length == 8) {
                flavor_id = 10 * flavor_id + (flavor_type.charAt(7) - '0');
            }
        }
        return flavor_id;
    }

    /**
     * 虚拟机放置策略
     *
     * @param server 二维数组记录服务器放置情况 server[i][j]代表第i台服务器上第j种类型的虚拟机的个数 ;i,j从下标1开始
     * @param count  存储虚拟机数量的数组 count[i]代表第i种虚拟机的个数
     * @param cpu    每台服务器的cpu总量
     * @param mem    每台服务器的mem总量
     * @return 所需的服务器数量
     */
    private static int fillServers(int[][] server, int[] count, int cpu, int mem) {
        int server_count = 1;
        int count_copy[] = new int[Constants.FLAVORS_COUNT];
        System.arraycopy(count, 0, count_copy, 0, Constants.FLAVORS_COUNT);
        int flag_server = 1;


        for (int i = 15; i >= 1; i--) {

            while (inFlavorList(i) && count_copy[i] > 0) {
                System.out.println("flavor" + i + " " + count_copy[i]);
                if (flag_server == 1) {
                    server[server_count][Constants.FREE_CPU] = cpu;
                    server[server_count][Constants.FREE_MEM] = mem;
                    flag_server = 0;
                }

                if (cpu_type[i] <= server[server_count][Constants.FREE_CPU]
                        && mem_type[i] <= server[server_count][Constants.FREE_MEM]) {
                    count_copy[i]--;
                    server[server_count][Constants.FREE_CPU] -= cpu_type[i];
                    server[server_count][Constants.FREE_MEM] -= mem_type[i];
                    System.out.println("MEM " + server[server_count][Constants.FREE_MEM] + " CPU " + server[server_count][Constants.FREE_CPU]);
                    server[server_count][i]++;
                } else {
                    //若服务器资源不足,开始放置小型虚拟机
                    small_flavor:
                    for (int j = i - 1; j > 0; j--) {
                        while (count_copy[j] > 0) {
                            if (cpu_type[j] <= server[server_count][Constants.FREE_CPU]
                                    && mem_type[j] <= server[server_count][Constants.FREE_MEM]) {
                                System.out.println("flavor" + j + " " + count_copy[j]);
                                count_copy[j]--;
                                server[server_count][Constants.FREE_CPU] -= cpu_type[j];
                                server[server_count][Constants.FREE_MEM] -= mem_type[j];
                                System.out.println("MEM " + server[server_count][Constants.FREE_MEM] + " CPU " + server[server_count][Constants.FREE_CPU]);
                                server[server_count][j]++;
                            } else {
                                j--;
                            }

                        }
                    }
                    flag_server = 1;
                    server_count++;
                }
            }

        }

        return server_count++;
    }

    /**
     * 用虚拟机填充服务器的空闲资源
     * 输入物理机二维矩阵,遍历每台物理机的剩余资源,从大到小添加新的虚拟机填充空闲资源(针对CPU和MEM不同类型可采用不同策略,本策略更适用于CPU类型)
     *
     * @param server       物理机预测结果的二维数组
     * @param count        虚拟机预测结果的数组
     * @param server_count 预测的物理机总数
     */
    private static int fillFreeServers(int[][] server, int[] count, int server_count) {
        int add_count = 0;
        for (int j = 1; j <= server_count; j++) {//遍历每台物理机
//			if(type.equals("CPU")){
            for (int i = 1; i <= 15; i++) {//遍历每种类型的虚拟机;从小到大
//				for(int i=15;i>=1;i-=3) {//遍历每种类型的虚拟机;从大到小
                if (inFlavorList(i)) {
                    if (cpu_type[i] <= server[j][Constants.FREE_CPU] && mem_type[i] <= server[j][Constants.FREE_MEM]) {
                        count[i]++;
                        add_count++;
                        server[j][Constants.FREE_CPU] -= cpu_type[i];
                        server[j][Constants.FREE_MEM] -= mem_type[i];
                        server[j][i]++;
                        i--;//
                    }
                }
            }
//			}
//			else if(type.equals("MEM")){
//				for(int i=3;i<=15;i+=3) {//遍历每种类型的虚拟机;从小到大
////				for(int i=15;i>=1;i-=3) {//遍历每种类型的虚拟机;从大到小
//					if(inFlavorList(i)) {
//						if(cpu_type[i]<=server[j][Constants.FREE_CPU] && mem_type[i]<=server[j][Constants.FREE_MEM]) {
//							count[i]++;
//							add_count++;
//							server[j][Constants.FREE_CPU] -= cpu_type[i];
//							server[j][Constants.FREE_MEM] -= mem_type[i];
//							server[j][i]++;
//							i+=3;//可加可不加 需要重新评估
//						}
//					}
//				}
//			}
        }
        return add_count;
    }

    private static int fillFreeServers2(int[][] server, int[] count, int server_count) {
        int add_count = 0;
        for (int j = 1; j <= server_count; j++) {
            if (type.equals("CPU")) {
                for (int i = 15; i >= 1; i--) {
                    if (fill_one_instance2(count, server, sort_instances[i], j)) {//{0,15,12,9,6,3,14,11,8,5,2,13,10,7,4,1};
                        add_count++;
                        i++;
                    }
                }
            } else if (type.equals("MEM")) {
                for (int i = 1; i <= 15; i++) {//{0,3,6,9,12,15,2,5,8,11,14,1,4,7,10,13};
                    if (fill_one_instance2(count, server, sort_instances3[i], j)) {
                        add_count++;
                        i--;
                    }

                }
                System.out.println("di" + j + "tai:" + server[j][16] + "," + server[j][17]);
            }
        }

        return add_count;
    }


    /**
     * 判断该类型虚拟机是否需要预测
     *
     * @param flavor_id 虚拟机id(范围1-15)
     * @return 若存在返回true, 否则false
     */
    private static boolean inFlavorList(int flavor_id) {
        String flavorName = "flavor" + flavor_id;
        if (flavor_type_list.contains(flavorName))
            return true;
        else
            return false;
    }

    /**
     * 根据虚拟机id判断次类型数量是否为0,
     *
     * @param flavor_id
     * @return 若为0返回false, 非0返回true
     */
    private static boolean notZero(int flavor_id) {
        if (flavors_count[flavor_id] != 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据虚拟机数组计算cpu和mem的比例  需要重写此函数
     *
     * @param count 虚拟机数组
     * @return 所有虚拟机CPU总量和内存总量的比值
     */
    private static double instance_rate(int[] count) {
        int cpu = 0, mem = 0;
        for (int i = 1; i < 16; i++) {
            switch (i) {
                case 1:
                    cpu += count[i];
                    mem += count[i];
                    break;
                case 2:
                    cpu += count[i];
                    mem += count[i] * 2;
                    break;
                case 3:
                    cpu += count[i];
                    mem += count[i] * 4;
                    break;
                case 4:
                    cpu += count[i] * 2;
                    mem += count[i] * 2;
                    break;
                case 5:
                    cpu += count[i] * 2;
                    mem += count[i] * 4;
                    break;
                case 6:
                    cpu += count[i] * 2;
                    mem += count[i] * 8;
                    break;
                case 7:
                    cpu += count[i] * 4;
                    mem += count[i] * 4;
                    break;
                case 8:
                    cpu += count[i] * 4;
                    mem += count[i] * 8;
                    break;
                case 9:
                    cpu += count[i] * 4;
                    mem += count[i] * 12;
                    break;
                case 10:
                    cpu += count[i] * 8;
                    mem += count[i] * 8;
                    break;
                case 11:
                    cpu += count[i] * 8;
                    mem += count[i] * 16;
                    break;
                case 12:
                    cpu += count[i] * 8;
                    mem += count[i] * 32;
                    break;
                case 13:
                    cpu += count[i] * 16;
                    mem += count[i] * 16;
                    break;
                case 14:
                    cpu += count[i] * 16;
                    mem += count[i] * 32;
                    break;
                case 15:
                    cpu += count[i] * 16;
                    mem += count[i] * 64;
                    break;
                default:
                    break;
            }
        }
        return (double) cpu / (double) mem;
    }


    private static double[] instance_rate1(int[] count) {
        int cpu = 0, mem = 0;
        double instance_resources[] = new double[3];
        for (int i = 1; i < 16; i++) {
            switch (i) {
                case 1:
                    cpu += count[i];
                    mem += count[i];
                    break;
                case 2:
                    cpu += count[i];
                    mem += count[i] * 2;
                    break;
                case 3:
                    cpu += count[i];
                    mem += count[i] * 4;
                    break;
                case 4:
                    cpu += count[i] * 2;
                    mem += count[i] * 2;
                    break;
                case 5:
                    cpu += count[i] * 2;
                    mem += count[i] * 4;
                    break;
                case 6:
                    cpu += count[i] * 2;
                    mem += count[i] * 8;
                    break;
                case 7:
                    cpu += count[i] * 4;
                    mem += count[i] * 4;
                    break;
                case 8:
                    cpu += count[i] * 4;
                    mem += count[i] * 8;
                    break;
                case 9:
                    cpu += count[i] * 4;
                    mem += count[i] * 12;
                    break;
                case 10:
                    cpu += count[i] * 8;
                    mem += count[i] * 8;
                    break;
                case 11:
                    cpu += count[i] * 8;
                    mem += count[i] * 16;
                    break;
                case 12:
                    cpu += count[i] * 8;
                    mem += count[i] * 32;
                    break;
                case 13:
                    cpu += count[i] * 16;
                    mem += count[i] * 16;
                    break;
                case 14:
                    cpu += count[i] * 16;
                    mem += count[i] * 32;
                    break;
                case 15:
                    cpu += count[i] * 16;
                    mem += count[i] * 64;
                    break;
                default:
                    break;
            }
        }
        instance_resources[Constants.INSTANCES_TOTAL_RATE] = (double) cpu / mem;
        instance_resources[Constants.INSTANCES_TOTAL_CPU] = cpu;
        instance_resources[Constants.INSTANCES_TOTAL_MEM] = mem;

        return instance_resources;
    }

    /**
     * 根据虚拟机总需求量和每台服务器提供的资源,计算需要的最小服务器数量
     *
     * @param instance_resources 虚拟机数组,包含所有虚拟机cpu/mem的比例,cpu、mem的总量
     * @param cpu                单台服务器的cpu
     * @param mem                单台服务器的mem
     * @return 需要的最小的服务器数量
     */
    private static int need_servers_count(double[] instance_resources, int cpu, int mem) {
        int cpu_count = (int) Math.ceil(instance_resources[Constants.INSTANCES_TOTAL_CPU] / cpu);
        int mem_count = (int) Math.ceil(instance_resources[Constants.INSTANCES_TOTAL_MEM] / mem);
        return cpu_count > mem_count ? cpu_count : mem_count;
    }

    /**
     * 判断是否还有虚拟机未放置完毕
     *
     * @param count 虚拟机数组
     * @return 若虚拟机有剩余返回true, 否则返回false
     */
    private static boolean exist(int[] count) {
        for (int i = 1; i < 16; i++) {
            if (count[i] != 0)
                return true;
        }
        return false;
    }

    /**
     * 统计虚拟机数组中虚拟机的总数
     *
     * @param count 虚拟机数组
     * @return 虚拟机的数量
     */
    private static int number_instance(int[] count) {
        int number = 0;
        for (int i = 1; i < 16; i++) {
            number += count[i];
        }
        return number;
    }

    /**
     * 分类填充虚拟机 返回服务器总数
     *
     * @param server 服务器数组
     * @param count  虚拟机数组
     * @param cpu    单台服务器CPU个数
     * @param mem    单台服务器mem个数
     * @return
     */
    private static int fillServers1(int[][] server, int[] count, int cpu, int mem) {
        int server_count = 1;
        int count_copy[] = new int[Constants.FLAVORS_COUNT];
        System.arraycopy(count, 0, count_copy, 0, Constants.FLAVORS_COUNT);
        int flag_server = 1;
        double server_rate = (double) cpu / mem;
        int flag_one = 0;
        int tempi = 0;
        System.out.println("begin fillServers1");
        while (exist(count_copy)) {/* 虚拟机的总数不为0*/
            System.out.println("剩余虚拟机数量:" + number_instance(count_copy));
            if (flag_server == 1) {
                server[server_count][Constants.FREE_CPU] = cpu;
                server[server_count][Constants.FREE_MEM] = mem;
                flag_server = 0;
                System.out.println("增加一台服务器");
            }
            System.out.println("主机数量:" + server_count);
            System.out.println("虚拟机比例:" + instance_rate(count_copy));
            System.out.println("主机比例:" + server_rate);
            if (instance_rate(count_copy) >= server_rate) {
                flag_one = 0;
                label1:
                for (int i = 13; i >= 1; i -= 3) {
                    tempi = i;
                    while (inFlavorList(i) && count_copy[i] > 0) {
                        System.out.println("进入while,取到非0虚拟机:" + i);
                        System.out.println("取到非0虚拟机:CPU:" + server[server_count][Constants.FREE_CPU]);
                        System.out.println("取到非0虚拟机:MEM:" + server[server_count][Constants.FREE_MEM]);
                        if (cpu_type[i] <= server[server_count][Constants.FREE_CPU]
                                && mem_type[i] <= server[server_count][Constants.FREE_MEM]) {
                            count_copy[i]--;
                            System.out.println("减少一台虚拟机:" + i);
                            server[server_count][Constants.FREE_CPU] -= cpu_type[i];
                            server[server_count][Constants.FREE_MEM] -= mem_type[i];

                            server[server_count][i]++;
                            flag_one = 1;
                            break label1;
                        } else {
                            break;
                        }
                    }
                }
                if (flag_one == 0 && tempi == 1) {
                    label2:
                    for (int i = 14; i >= 1; i -= 3) {
                        tempi = i;
                        while (inFlavorList(i) && count_copy[i] > 0) {
                            if (cpu_type[i] <= server[server_count][Constants.FREE_CPU]
                                    && mem_type[i] <= server[server_count][Constants.FREE_MEM]) {
                                count_copy[i]--;
                                server[server_count][Constants.FREE_CPU] -= cpu_type[i];
                                server[server_count][Constants.FREE_MEM] -= mem_type[i];

                                server[server_count][i]++;
                                flag_one = 1;
                                break label2;
                            } else {
                                break;
                            }
                        }
                    }
                }
                //重复上面步骤i初始值变为14
                if (flag_one == 0 && tempi == 2) {
                    label3:
                    for (int i = 15; i >= 1; i -= 3) {
                        while (inFlavorList(i) && count_copy[i] > 0) {
                            if (cpu_type[i] <= server[server_count][Constants.FREE_CPU]
                                    && mem_type[i] <= server[server_count][Constants.FREE_MEM]) {
                                count_copy[i]--;
                                server[server_count][Constants.FREE_CPU] -= cpu_type[i];
                                server[server_count][Constants.FREE_MEM] -= mem_type[i];

                                server[server_count][i]++;
                                flag_one = 1;
                                break label3;
                            } else {
                                break;
                            }
                        }
                    }
                    //重复上面步骤i初始值变为15
                }
                if (flag_one == 0) {
                    flag_server = 1;
                    server_count++;
                }
            } else if (instance_rate(count_copy) < server_rate) {
                flag_one = 0;
                label3:
                for (int i = 15; i >= 1; i -= 3) {
                    tempi = i;
                    while (inFlavorList(i) && count_copy[i] > 0) {
                        if (cpu_type[i] <= server[server_count][Constants.FREE_CPU]
                                && mem_type[i] <= server[server_count][Constants.FREE_MEM]) {
                            count_copy[i]--;
                            server[server_count][Constants.FREE_CPU] -= cpu_type[i];
                            server[server_count][Constants.FREE_MEM] -= mem_type[i];

                            server[server_count][i]++;
                            flag_one = 1;
                            break label3;
                        } else {
                            break;
                        }
                    }
                }
                //交换上面3个if的顺序

                if (flag_one == 0 && tempi == 3) {
                    label2:
                    for (int i = 14; i >= 1; i -= 3) {
                        tempi = 2;
                        while (inFlavorList(i) && count_copy[i] > 0) {
                            if (cpu_type[i] <= server[server_count][Constants.FREE_CPU]
                                    && mem_type[i] <= server[server_count][Constants.FREE_MEM]) {
                                count_copy[i]--;
                                server[server_count][Constants.FREE_CPU] -= cpu_type[i];
                                server[server_count][Constants.FREE_MEM] -= mem_type[i];

                                server[server_count][i]++;
                                flag_one = 1;
                                break label2;
                            } else {
                                break;
                            }
                        }
                    }
                }
                //重复上面步骤i初始值变为14
                if (flag_one == 0 && tempi == 2) {
                    label1:
                    for (int i = 13; i >= 1; i -= 3) {
                        while (inFlavorList(i) && count_copy[i] > 0) {
                            if (cpu_type[i] <= server[server_count][Constants.FREE_CPU]
                                    && mem_type[i] <= server[server_count][Constants.FREE_MEM]) {
                                count_copy[i]--;
                                server[server_count][Constants.FREE_CPU] -= cpu_type[i];
                                server[server_count][Constants.FREE_MEM] -= mem_type[i];

                                server[server_count][i]++;
                                flag_one = 1;
                                break label1;
                            } else {
                                break;
                            }
                        }
                    }
                    //重复上面步骤i初始值变为15
                }
                if (flag_one == 0) {
                    flag_server = 1;
                    server_count++;
                }
            }
        }//while end

        //======================分割线===================

        return server_count;
    }

    private static int fillServers2_1(int cpu, int mem, int fill1_count) {
        double[] instance_resources = instance_rate1(flavors_count);
        int server_count = need_servers_count(instance_resources, cpu, mem);
        for (int i = server_count; i <= fill1_count; i++) {
            System.out.println("服务器的递增个数:" + i);
            if (fillServers2_2(flavors_count, i, cpu, mem)) {
                System.out.println("此时服务器个数:" + i);
                return i;
            }
        }
        return fill1_count;
    }

    private static boolean fillServers2_2(int[] flavors_count, int servers_count, int cpu, int mem) {
        int count_copy[] = new int[Constants.FLAVORS_COUNT];
        System.arraycopy(flavors_count, 0, count_copy, 0, Constants.FLAVORS_COUNT);
        int[][] servers_copy = new int[100][20];
        for (int i = 1; i <= servers_count; i++) {
            servers_copy[i][Constants.FREE_CPU] = cpu;
            servers_copy[i][Constants.FREE_MEM] = mem;
        }
        int end_flag = 1;
        while (number_instance(count_copy) != 0) {
            end_flag = 1;
            for (int j = 1; j <= servers_count; j++) {
                System.out.println("servers_count:" + j);
                for (int i = 1; i <= 15; i++) {
                    if (fill_one_instance(count_copy, servers_copy, sort_instances[i], j)) {
                        end_flag = 0;//放置成功,说明可以继续
                        break;
                    }
                }
            }
            if (end_flag == 1)//遍历服务器 没有空余的资源可以继续放置虚拟机
                break;
        }
        if (number_instance(count_copy) == 0) {
            //拷贝server数组
            for (int i = 0; i <= servers_count; i++) {
                for (int j = 0; j <= 17; j++) {
                    Predict.server[i][j] = servers_copy[i][j];
                }
            }
            return true;
        }
        return false;

    }

    /**
     * 将i类型的虚拟机放到第j台服务器上
     *
     * @param count           虚拟机数组
     * @param servers_count   服务器数组
     * @param flavor_sequence 第flavor_sequence个类型的虚拟机
     * @param server_sequence 第server_sequence台服务器
     * @return 若放置成功, 减少一台虚拟机, 改变服务器数组相应的值(已放置虚拟机和剩余资源), 并返回true;否则返回false
     */
    private static boolean fill_one_instance(int[] count, int[][] servers_count, int flavor_sequence, int server_sequence) {
        if (count[flavor_sequence] > 0
                && cpu_type[flavor_sequence] <= servers_count[server_sequence][Constants.FREE_CPU]
                && mem_type[flavor_sequence] <= servers_count[server_sequence][Constants.FREE_MEM]) {
            count[flavor_sequence]--;
            servers_count[server_sequence][Constants.FREE_CPU] -= cpu_type[flavor_sequence];
            servers_count[server_sequence][Constants.FREE_MEM] -= mem_type[flavor_sequence];

            servers_count[server_sequence][flavor_sequence]++;
            return true;
        }
        return false;
    }

    private static boolean fill_one_instance2(int[] count, int[][] servers_count, int flavor_sequence, int server_sequence) {
        if (count[flavor_sequence] > 0
                && cpu_type[flavor_sequence] <= servers_count[server_sequence][Constants.FREE_CPU]
                && mem_type[flavor_sequence] <= servers_count[server_sequence][Constants.FREE_MEM]) {
            System.out.println("cpu:" + cpu_type[flavor_sequence] + "mem" + mem_type[flavor_sequence]);
            System.out.println("ser_cpu:" + servers_count[server_sequence][Constants.FREE_CPU]);
            System.out.println("ser_mem:" + servers_count[server_sequence][Constants.FREE_MEM]);
            count[flavor_sequence]++;
            servers_count[server_sequence][Constants.FREE_CPU] -= cpu_type[flavor_sequence];
            servers_count[server_sequence][Constants.FREE_MEM] -= mem_type[flavor_sequence];

            servers_count[server_sequence][flavor_sequence]++;
            return true;
        }
        return false;
    }

}
