package com.milk.redisBucket.service.impl;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @Auther: wanghong
 * @Date: 2020/10/12 09:51
 * @Description:
 */
public class BatchDealImpl {

    // 每批数量
    private final static int batchNum = 100;

    public static void batchDealList(List<String> dataList) {
        List<String> workList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            workList.add(dataList.get(i));
            if (batchNum == workList.size() || i == dataList.size() - 1) {
                printList(workList);
                workList.clear();
                System.out.println("=====");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void printList(List<String> dataList) {
        for (Object str : dataList) {
            System.out.print(str + " -- ");
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1523; i++) {
            list.add(i + "gfdgs");
        }
        //batchDealList(list);
        partition(list);
    }

    public static void partition(List<String> dataList) {
        List<List<String>> subs = ListUtils.partition(dataList, batchNum);
        for (List<String> sub : subs) {
            printList(sub);
            System.out.println("=====");
        }
    }
}
