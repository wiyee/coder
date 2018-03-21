package elasticcloudservice;

import java.beans.EventSetDescriptor;
import java.lang.reflect.Array;
import java.rmi.server.ServerCloneException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.naming.InitialContext;

import filetool.util.FileUtil;
import filetool.util.LogUtil;
import org.omg.CORBA.FREE_MEM;
import org.omg.CORBA.PolicyHelper;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;


public class Predict {

    public static void main(String[] args) {

        String ecsDataPath = "F:\\dataset\\huawei_ecs\\TrainData_2015.1.1_2015.2.19.txt";
        String inputFilePath = "F:\\dataset\\huawei_ecs\\input_5flavors_cpu_7days.txt";
        String resultFilePath = "F:\\dataset\\huawei_ecs\\out.txt";

        LogUtil.printLog("Begin");

        // 读取输入文件
        String[] ecsContent = FileUtil.read(ecsDataPath, null);
        String[] inputContent = FileUtil.read(inputFilePath, null);

        // 功能实现入口
        String[] resultContents = Predict.predictVm(ecsContent, inputContent);

        // 写入输出文件
        if (hasResults(resultContents)) {
            FileUtil.write(resultFilePath, resultContents, false);
        } else {
            FileUtil.write(resultFilePath, new String[]{"NA"}, false);
        }
        LogUtil.printLog("End");
    }

    private static boolean hasResults(String[] resultContents) {
        if (resultContents == null) {
            return false;
        }
        for (String contents : resultContents) {
            if (contents != null && !contents.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static int[] cpu_type = {0, 1, 1, 1, 2, 2, 2, 4, 4, 4, 8, 8, 8, 16, 16, 16};
    public static int[] mem_type = {0, 1, 2, 4, 2, 4, 8, 4, 8, 16, 8, 16, 32, 16, 32, 64};
    //虚拟机类型列表
    public static ArrayList<String> flavor_type_list = new ArrayList<String>();//需要预测的虚拟机类型列表

    //用二维数组表示每台服务器上每种虚拟机类型的数量,server[i][j]代表第i台服务器上第j类虚拟机的数量
    public static int[][] server = new int[100][20];
    public static int[] flavors_count = new int[Constants.FLAVORS_COUNT];//各个类型虚拟机的数量

    public static int CPU = 0;
    public static int MEM = 0;

    public static String type;

    public static String[] predictVm(String[] ecsContent, String[] inputContent) {

        /** =========do your work here========== **/

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
        try {
            Date date1 = formate2.parse(startDate);
            date2 = getDate((int) myMap.get("predict_days"), date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date3 = formate2.format(date2);

        int all = flavorFilter(ecsContent, flavor_type_list, date3);

//		int server_count = fillServers(server, flavors_count, CPU, MEM);
        int server_count = fillServers1(server, flavors_count, CPU, MEM);
        System.out.println("CPU:" + CPU + "MEM:" + MEM);


        int add_count = 0;
        add_count = fillFreeServers(server, flavors_count, server_count);

        //results数组赋值
        int k = 1;
        for (int i = 1; i < 16; i++) {
            if (flavors_count[i] != 0) {
                results[k] = "flavor" + i + " " + flavors_count[i];
                k++;
            }
        }
        results[0] = String.valueOf(all + add_count);
        results[k++] = "";
        int temp = k;
        results[temp] = String.valueOf(server_count);
        for (int i = 1; i <= server_count; i++) {
            results[temp + i] = String.valueOf(i);
            for (int j = 1; j < 16; j++) {
                if (server[i][j] != 0) {
                    results[temp + i] += " flavor" + j + " " + server[i][j];
                }
            }
        }


        return results;
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
     * 过滤EcsContent中的虚拟机类型(flavor)
     *
     * @param ecsContent
     * @param flavor_type_list 要统计的虚拟机类型列表
     * @return
     * @author XRH
     */
    public static int flavorFilter(String[] ecsContent, ArrayList<String> flavor_type_list, String startDate) {
        int flavor_id = 0;
        int flavors_all_count = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, int[]> vmMap = new LinkedHashMap<>();
        try {
            Date start = df.parse(startDate);
            for (String s : ecsContent) {
                String[] vm = s.split("\t");
                int weekBetween = daysBetween(df.parse(vm[2].substring(0, 10)), start) / 7;
                if (flavor_type_list.contains(vm[1])) {
                    if (weekBetween < 4) {
                        if (vmMap.containsKey(vm[1])) {
                            int[] tmp = vmMap.get(vm[1]);
                            tmp[weekBetween]++;
                            vmMap.put(vm[1], tmp);
                        } else {
                            int[] tmp = new int[]{0, 0, 0, 0};
                            tmp[weekBetween]++;
                            vmMap.put(vm[1], tmp);
                        }
                    }
                }
            }
            double[] K = new double[]{-0.5809000038687591, 1.6949169039579777, 1.031615945471744, -1.207981070367037, -0.9457439462589448};
            for (String str : vmMap.keySet()) {
                int[] x = vmMap.get(str);
                int y = (int) (x[0] * K[0] + x[1] * K[1] + x[2] * K[2] + x[3] * K[3] + K[4]);
                flavors_count[Integer.parseInt(str.replaceFirst("flavor", ""))] = y;
                flavors_all_count += y;
            }
            for (int i = 0; i < flavors_count.length; i++) {
                System.out.println(i + "  " + flavors_count[i]);
            }
        } catch (ParseException e) {
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
                flavor_id = 10 * flavor_id + (flavor_type.charAt(6) - '0');
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
//				for(int i=1;i<=15;i++) {//遍历每种类型的虚拟机;从小到大
            for (int i = 15; i >= 1; i--) {//遍历每种类型的虚拟机;从大到小
                if (inFlavorList(i)) {
                    if (cpu_type[i] <= server[j][Constants.FREE_CPU] && mem_type[i] <= server[j][Constants.FREE_MEM]) {
                        count[i]++;
                        add_count++;
                        server[j][Constants.FREE_CPU] -= cpu_type[i];
                        server[j][Constants.FREE_MEM] -= mem_type[i];
                        server[j][i]++;
                        i++;//可加可不加 需要重新评估
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
     * 根据虚拟机数组计算cpu和mem的比例
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

    //分界线采用比例填充物理机


    //判断虚拟机是否存在
    private static boolean exist(int[] count) {
        for (int i = 1; i < 16; i++) {
            if (count[i] != 0)
                return true;
        }
        return false;
    }

    private static int number_instance(int[] count) {
        int number = 0;
        for (int i = 1; i < 16; i++) {
            number += count[i];
        }
        return number;
    }

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
            if (instance_rate(count_copy) >= server_rate) {
                System.out.println("剩余虚拟机资源比例" + instance_rate(count_copy));
                System.out.println("服务器资源比例" + server_rate);
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
                System.out.println("结束最小类型查找");
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

        return server_count++;
    }


}

class Constants {

    //input文件读取的内容
    public static final String INPUT_PHYSICAL_CPU = "phy_cpu";
    public static final String INPUT_PHYSICAL_MEM = "phy_mem";
    public static final String INPUT_FLAVORS_COUNT = "flavor_count";
    public static final String INPUT_FLAVORS_LIST = "flavor_type_list";
    public static final String INPUT_PHYSICAL_RESOURCES_TYPE = "phy_resources_type";
    public static final String INPUT_DAYS = "predict_days";
    public static final String INPUT_START_DATE = "startDate";
    public static final String INPUT_END_DATE = "endDate";

    public static final int FLAVOR1 = 1;
    public static final int FLAVOR2 = 2;
    public static final int FLAVOR3 = 3;
    public static final int FLAVOR4 = 4;
    public static final int FLAVOR5 = 5;
    public static final int FLAVOR6 = 6;
    public static final int FLAVOR7 = 7;
    public static final int FLAVOR8 = 8;
    public static final int FLAVOR9 = 9;
    public static final int FLAVOR10 = 10;
    public static final int FLAVOR11 = 11;
    public static final int FLAVOR12 = 12;
    public static final int FLAVOR13 = 13;
    public static final int FLAVOR14 = 14;
    public static final int FLAVOR15 = 15;

    public static final int FREE_CPU = 16;
    public static final int FREE_MEM = 17;

    public static final int FLAVORS_COUNT = 16;

}
