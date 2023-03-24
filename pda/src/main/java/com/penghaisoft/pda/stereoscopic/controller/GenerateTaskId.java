package com.penghaisoft.pda.stereoscopic.controller;

import com.penghaisoft.pda.common.Constant;

import java.util.Random;

/**
 * @ClassName GenerateTaskId
 * @Description
 * @Author luo_0
 * @Date 2020/3/6 1:39
 **/
public class GenerateTaskId {
    public static long getTaskId(Constant.TaskType taskType) {
        long taskId = getLongRandom();
        return taskId * 100 + taskType.getTaskType();
    }

    public static long getLongRandom(){
        long min = 1;
        long max = 9999999999l;
        long rangeLong = min + (((long) (new Random().nextDouble() * (max - min))));
        return rangeLong;
    }

    public static void main(String args[]){
        System.out.println(getTaskId(Constant.TaskType.SORT_IN));
        System.out.println(getTaskId(Constant.TaskType.CHECK_IN));
    }
}
