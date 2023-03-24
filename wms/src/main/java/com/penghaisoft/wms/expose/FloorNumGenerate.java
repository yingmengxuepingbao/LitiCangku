//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.expose;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloorNumGenerate {
    private static final Logger log = LoggerFactory.getLogger(FloorNumGenerate.class);

    public FloorNumGenerate() {
    }

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        log.info(getRandomFloorList().toString());
        long t2 = System.currentTimeMillis();
        log.info("##################耗时：" + (t2 - t1) + "ms！#########################");
    }

    public static List<Integer> getRandomFloorList() {
        List<Integer> floorNumList = new LinkedList();
        Map<Integer, Double> map = new HashMap();
        map.put(1, 2.0D);
        map.put(2, 2.0D);
        map.put(4, 1.0D);
        map.put(3, 1.0D);
        Integer floorNumber = getRandomFloorNum(map);

        while(true) {
            if (!floorNumList.contains(floorNumber)) {
                floorNumList.add(floorNumber);
            }

            if (floorNumList.size() == 4) {
                return floorNumList;
            }

            map.remove(floorNumber);
            log.info(map.toString());
            floorNumber = getRandomFloorNum(map);
        }
    }

    public static Integer getRandomFloorNum(Map<Integer, Double> map) {
        Integer floorNumber = 1;
        double ran = Math.random();
        log.info("随机数：" + String.valueOf(ran));
        double allNum = 0.0D;

        Integer key;
        for(Iterator var6 = map.keySet().iterator(); var6.hasNext(); allNum += (Double)map.get(key)) {
            key = (Integer)var6.next();
        }

        double tmpNum = 0.0D;
        Iterator var8 = map.keySet().iterator();

        while(var8.hasNext()) {
            key = (Integer)var8.next();
            tmpNum += (Double)map.get(key);
            if (ran < tmpNum / allNum) {
                floorNumber = key;
                break;
            }
        }

        log.info("返回：" + String.valueOf(floorNumber));
        return floorNumber;
    }
}
