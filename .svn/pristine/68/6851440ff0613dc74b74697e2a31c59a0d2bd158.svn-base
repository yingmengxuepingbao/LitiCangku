package device;

/**
 * 堆垛机和线体任务号最大10位 <2147450879 int最大值
 * 用月(不够两位补8)+日+4位流水号
 * @Description TaskUtil
 * @Auther zhangxu
 * @Date 2020/3/3 17:26
 **/
public class TaskNoUtil {

    private static int TaskNo = -1;

    public static int getTaskNo()
    {
        int result;
        boolean flag2 = TaskNoUtil.TaskNo == -1;
        if (flag2)
        {
            TaskNoUtil.TaskNo = TaskNoUtil.getTime();
        }
        else
        {
            TaskNoUtil.TaskNo++;
            int num = TaskNoUtil.getTime() - 3600;
            boolean flag3 = num < 0;
            if (flag3)
            {
                num += 8388607;
            }
            boolean flag4 = TaskNoUtil.TaskNo < num;
            if (flag4)
            {
                TaskNoUtil.TaskNo = num;
            }
            boolean flag5 = TaskNoUtil.TaskNo >= 8388607;
            if (flag5)
            {
                TaskNoUtil.TaskNo -= 8388607;
            }
        }
        result = TaskNoUtil.TaskNo + 1000000;
        return result;
    }
    private static int getTime()
    {
        long num = System.currentTimeMillis();
        return (int)(num & 8388607L);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            System.out.println(getTaskNo());
        }
    }
}
