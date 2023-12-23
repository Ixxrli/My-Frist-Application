package com.jnu.student.data;

import com.jnu.student.content.MyBill;
import com.jnu.student.content.MyReward;
import com.jnu.student.content.MyTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class BillUtil {
    private static final int[] BEFORE_HOUR = {4, 8, 12, 16, 20, 24};
    private static final int[] BEFORE_DAY = {1, 2, 3, 4, 5, 6, 7};
    private static final int[] BEFORE_SOME_DAY = {3, 6, 9, 12, 15, 18, 21, 24, 27};
    private static final int[] MONTH = {12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    public static List<MyBill> getBillListAllSorted(List<MyTask> task_All, List<MyReward> reward_All) {
        List<MyBill> billList = new ArrayList<>();

        for (MyTask task : task_All) {
            for (String time : task.getTTurnover()){
                billList.add(new MyBill(task.getTitle(), task.getPoint(), time, "任务"));
            }
        }

        for (MyReward reward : reward_All) {
            for (String time : reward.getRTurnover()) {
                billList.add(new MyBill(reward.getTitle(), reward.getPoint(), time, "奖励"));
            }
        }

        Collections.sort(billList, Comparator.comparing(MyBill::getbTime).reversed());

        return billList;
    }

    public static Map<String, int[]> getBillTurnover(List<MyBill> billList, String statisticType) {
        Map<String, int[]> resultMap = new LinkedHashMap<>();

        if (statisticType.equals("日")) {
            int nowHour = Integer.parseInt(TimeUtil.getCurrentHour());

            int[] hours = new int[BEFORE_HOUR.length];
            for (int i = 0; i < hours.length; i++) {
                hours[i] = (nowHour - 4 * i + 24) % 24;
                resultMap.put(hours[i] + "时", new int[]{0, 0, 0});
            }

            for (MyBill bill : billList) {
                for (int i = 0; i < hours.length; i++) {
                    String beforeTime = TimeUtil.getFormattedTimeAgoHour(BEFORE_HOUR[i]);
                    if (bill.getbTime().compareTo(beforeTime) >= 0) {
                        if (bill.getbType().equals("任务")) {
                            resultMap.get(hours[i] + "时")[0] += bill.getbPoint();
                            resultMap.get(hours[i] + "时")[2] += bill.getbPoint();
                        } else {
                            resultMap.get(hours[i] + "时")[1] += bill.getbPoint();
                            resultMap.get(hours[i] + "时")[2] -= bill.getbPoint();
                        }
                        break;
                    }
                }
            }
        } else if (statisticType.equals("周")) {
            int[] days = new int[BEFORE_DAY.length];
            for (int i = 0; i < days.length; i++) {
                String beforeTime = TimeUtil.getFormattedTimeAgoDay(BEFORE_DAY[i] - 1);
                String result = TimeUtil.formatMonthAndDay(beforeTime, "dd");
                days[i] = Integer.parseInt(result);
                resultMap.put(days[i] + "日", new int[]{0, 0, 0});
            }

            for (MyBill bill : billList) {
                for (int i = 0; i < days.length; i++) {
                    String beforeTime = TimeUtil.getFormattedTimeAgoDay(BEFORE_DAY[i]);
                    if (bill.getbTime().compareTo(beforeTime) >= 0) {
                        if (bill.getbType().equals("任务")) {
                            resultMap.get(days[i] + "日")[0] += bill.getbPoint();
                            resultMap.get(days[i] + "日")[2] += bill.getbPoint();
                        } else {
                            resultMap.get(days[i] + "日")[1] += bill.getbPoint();
                            resultMap.get(days[i] + "日")[2] -= bill.getbPoint();
                        }
                        break;
                    }
                }
            }
        } else if (statisticType.equals("月")) {
            String[] dates = new String[BEFORE_SOME_DAY.length];
            for (int i = 0; i < dates.length; i++) {
                String beforeTime = TimeUtil.getFormattedTimeAgoDay(BEFORE_SOME_DAY[i] - 3);
                String result = TimeUtil.formatMonthAndDay(beforeTime, "MM-dd");
                dates[i] = result;
                resultMap.put(dates[i], new int[]{0, 0, 0});
            }

            for (MyBill bill : billList) {
                for (int i = 0; i < dates.length; i++) {
                    String beforeTime = TimeUtil.getFormattedTimeAgoDay(BEFORE_SOME_DAY[i]);
                    if (bill.getbTime().compareTo(beforeTime) >= 0) {
                        if (bill.getbType().equals("任务")) {
                            resultMap.get(dates[i])[0] += bill.getbPoint();
                            resultMap.get(dates[i])[2] += bill.getbPoint();
                        } else {
                            resultMap.get(dates[i])[1] += bill.getbPoint();
                            resultMap.get(dates[i])[2] -= bill.getbPoint();
                        }
                        break;
                    }
                }
            }
        } else {
            String currentYear = TimeUtil.formatMonthAndDay(TimeUtil.getBeijingTime(), "yyyy");

            for (int i = 0; i < MONTH.length; i++) {
                resultMap.put(MONTH[i] + "月", new int[]{0, 0, 0});
            }

            for (MyBill bill : billList) {
                for (int i = 0; i < MONTH.length; i++) {
                    String billYear = TimeUtil.formatMonthAndDay(bill.getbTime(), "yyyy");
                    String billMonth = TimeUtil.formatMonthAndDay(bill.getbTime(), "MM");
                    if (billYear.equals(currentYear) && billMonth.equals(String.valueOf(MONTH[i]))) {
                        if (bill.getbType().equals("任务")) {
                            resultMap.get(MONTH[i] + "月")[0] += bill.getbPoint();
                            resultMap.get(MONTH[i] + "月")[2] += bill.getbPoint();
                        } else {
                            resultMap.get(MONTH[i] + "月")[1] += bill.getbPoint();
                            resultMap.get(MONTH[i] + "月")[2] -= bill.getbPoint();
                        }
                        break;
                    }
                }
            }
        }

        return resultMap;
    }
}
