package com.penghaisoft.wms;

import onbon.bx06.Bx6GEnv;
import onbon.bx06.Bx6GScreenClient;
import onbon.bx06.area.TextCaptionBxArea;
import onbon.bx06.area.page.TextBxPage;
import onbon.bx06.file.ProgramBxFile;
import onbon.bx06.series.Bx6E;
import onbon.bx06.utils.DisplayStyleFactory;

import java.awt.*;

/**
 * @Description
 * @Author zhangxin
 * @Date 2022-09-29
 **/

public class LEDTest {
    public static void main(String[] args) throws Exception {
        /**
         * 默认启动LED服务
         */
            System.out.println("=========LED服务启动==========");
            try {
                Bx6GEnv.initial(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("=========LED服务启动完成==========");

        System.out.println("==========");
        //sendOut("172.32.2.28","test","1001","test2",100,100);

        SendProgramIn("172.32.2.28","", "", "1001001419","1234567890987", "托盘码不一致！", "LED显示内容:");
        System.out.println("-----------");
    }



    /**
     * 诺华入库模板调用
     * @param IP ip地址
     * @param batch 批次号
     * @param materialCode 物料编码
     * @param motherTray 母托盘码
     * @param taskId 任务id
     * @param warning 问题表述
     * @param title 标题
     * @throws Exception
     */
    public static void SendProgramIn(String IP
            ,String batch
            ,String materialCode
            ,String motherTray
            ,String taskId
            ,String warning
            ,String title) {

        System.out.println("ip地址"+IP+"批次号："+batch +"物料编码"+materialCode+"母托盘码:"+motherTray+"任务id:"+taskId+"问题表述:"+warning+"标题:"+title );

        DisplayStyleFactory.DisplayStyle[] styles = DisplayStyleFactory.getStyles().toArray(
                new DisplayStyleFactory.DisplayStyle[0]);

        // 创建screen对象,用于与控制卡的交互
        // 第二个参数是控制卡型号，只有型号对才能正常通讯，否则会出现逾时未回应，如果使用的型号API中未定义，用new Bx6M()替代
        Bx6GScreenClient screen = new Bx6GScreenClient( "MyScreen",new Bx6E());
        System.out.println("====连接=======");
        // 连接控制器
        try {
            screen.connect(IP,5005);
        }catch (Exception e){
            System.out.println("连接控制器-异常"+e);
        }

        System.out.println("====连接结束=======");
        // 创建节目 一个节目相当于一屏显示内容
        ProgramBxFile pf = new ProgramBxFile( "P000",screen.getProfile());

        // 创建一个分区
        // 分别输入X，Y，width，heigth
        // 注意区域坐标和宽度高度不要越界
        TextCaptionBxArea area1 = new TextCaptionBxArea(820, 20, 100, 15, screen.getProfile());
        TextCaptionBxArea area2 = new TextCaptionBxArea(820, 40, 100, 15, screen.getProfile());
        TextCaptionBxArea area3 = new TextCaptionBxArea(820, 60, 100, 15, screen.getProfile());
        TextCaptionBxArea area4 = new TextCaptionBxArea(820, 80, 100, 15, screen.getProfile());
        TextCaptionBxArea area5 = new TextCaptionBxArea(820, 100, 100, 15, screen.getProfile());


        TextCaptionBxArea area6 = new TextCaptionBxArea(900, 20, 122, 15, screen.getProfile());
        TextCaptionBxArea area7 = new TextCaptionBxArea(900, 40, 122, 15, screen.getProfile());
        TextCaptionBxArea area8 = new TextCaptionBxArea(900, 60, 122, 15, screen.getProfile());
        TextCaptionBxArea area9 = new TextCaptionBxArea(900, 80, 122, 15, screen.getProfile());
        TextCaptionBxArea area10 = new TextCaptionBxArea(900, 100, 122, 15, screen.getProfile());

        TextCaptionBxArea area11 = new TextCaptionBxArea( 0,0,120,15,screen.getProfile());
        TextCaptionBxArea area12 = new TextCaptionBxArea( 70,0,60,15,screen.getProfile());
//        TextCaptionBxArea area13 = new TextCaptionBxArea( 0,25,250,5,screen.getProfile());



        // 创建一个数据页
        // 第一行数据
        TextBxPage page1 = new TextBxPage("当前批次：");
        TextBxPage page2 = new TextBxPage("物料编码：");
        TextBxPage page3 = new TextBxPage("托盘编码：");
        TextBxPage page4 = new TextBxPage("任务编号：");
        TextBxPage page5 = new TextBxPage("问题表述：");


        TextBxPage page6;
        if(batch!=null &&!"".equals(batch)){
            page6 = new TextBxPage(batch);
        }else {
            page6 = new TextBxPage("无");
        }
        TextBxPage page7 ;
        if(materialCode!=null &&!"".equals(materialCode)){
            page7 = new TextBxPage(materialCode);
        }else {
            page7 = new TextBxPage("无");
        }
        TextBxPage page8 ;
        if(motherTray!=null &&!"".equals(motherTray)){
            page8 = new TextBxPage(motherTray);
        }else {
            page8 = new TextBxPage("无");
        }
        TextBxPage page9 ;
        if(taskId!=null &&!"".equals(taskId)){
            page9 = new TextBxPage(taskId);
        }else {
            page9 = new TextBxPage("无");
        }
        TextBxPage page10;
        if(warning!=null &&!"".equals(warning)) {
            page10= new TextBxPage(warning);
        }else {
            page10 = new TextBxPage("成功！");
        }
        TextBxPage page11 = new TextBxPage(title);
//        TextBxPage page12 = new TextBxPage("------------------------------------------------------------");
        // 第二行数据
//        page.newLine("这是第二行数据");
        // 设置字体
        page1.setFont( new Font("宋体", Font.PLAIN,15) );
        page2.setFont( new Font("宋体", Font.PLAIN,15) );
        page3.setFont( new Font("宋体", Font.PLAIN,15) );
        page4.setFont( new Font("宋体", Font.PLAIN,15) );
        page5.setFont( new Font("宋体", Font.PLAIN,15) );
        page6.setFont( new Font("宋体", Font.PLAIN,15) );
        page7.setFont( new Font("宋体", Font.PLAIN,15) );
        page8.setFont( new Font("宋体", Font.PLAIN,15) );
        page9.setFont( new Font("宋体", Font.PLAIN,15) );
        page10.setFont( new Font("宋体", Font.PLAIN,15) );
        page11.setFont( new Font("宋体", Font.PLAIN,15) );
        // 设置显示特技为快速打出
        page1.setDisplayStyle(styles[1]);
        page2.setDisplayStyle(styles[1]);
        page3.setDisplayStyle(styles[1]);
        page4.setDisplayStyle(styles[1]);
        page5.setDisplayStyle(styles[1]);
        page6.setDisplayStyle(styles[1]);
        page7.setDisplayStyle(styles[1]);
        page8.setDisplayStyle(styles[1]);
        page9.setDisplayStyle(styles[1]);
        page10.setDisplayStyle(styles[4]);
        page11.setDisplayStyle(styles[1]);
//        page12.setDisplayStyle(styles[1]);

        // 数据页可以是图片
//        ImageFileBxPage iPage  = new ImageFileBxPage( "/usr/local/software/logo2.png" );

        // 数据页可以是txt文件
//        TextFileBxPage tPage = new TextFileBxPage( "D:a/001.txt" );

        // 将前面的page添加到area中，page不可以是表格，如果需要Led显示表格，请先将表格绘制成图片
        area1.addPage(page1);
        area2.addPage(page2);
        area3.addPage(page3);
        area4.addPage(page4);
        area5.addPage(page5);
        area6.addPage(page6);
        area7.addPage(page7);
        area8.addPage(page8);
        area9.addPage(page9);
        area10.addPage(page10);
//        area11.addPage(iPage);
        area12.addPage(page11);
//        area13.addPage(page12);
        // 将area添加到节目中，节目中可以添加多个area
        pf.addArea(area1);
        pf.addArea(area2);
        pf.addArea(area3);
        pf.addArea(area4);
        pf.addArea(area5);
        pf.addArea(area6);
        pf.addArea(area7);
        pf.addArea(area8);
        pf.addArea(area9);
        pf.addArea(area10);
        pf.addArea(area11);
        pf.addArea(area12);
//        pf.addArea(area13);

        // 更新节目
        try {
            screen.writeProgram(pf);
        }catch (Exception e){
            System.out.println("更新节目-异常！"+e);
        }
        // 断开连接
        screen.disconnect();
    }

    public static void sendOut(String IP,
                               String batch,
                               String code,
                               String name,
                               int count,
                               int splitCount) {
        try {
            DisplayStyleFactory.DisplayStyle[] styles = DisplayStyleFactory.getStyles().toArray(
                    new DisplayStyleFactory.DisplayStyle[0]);

            // 创建screen对象,用于与控制卡的交互
            // 第二个参数是控制卡型号，只有型号对才能正常通讯，否则会出现逾时未回应，如果使用的型号API中未定义，用new Bx6M()替代
            Bx6GScreenClient screen = new Bx6GScreenClient("MyScreen", new Bx6E());

            // 连接控制器
            screen.connect(IP, 5005);

            // 创建节目 一个节目相当于一屏显示内容
            ProgramBxFile pf = new ProgramBxFile("P000", screen.getProfile());

            // 创建一个分区
            // 分别输入X，Y，width，heigth
            // 注意区域坐标和宽度高度不要越界
            TextCaptionBxArea area1 = new TextCaptionBxArea(820, 30, 100, 20, screen.getProfile());
            TextCaptionBxArea area2 = new TextCaptionBxArea(820, 55, 100, 20, screen.getProfile());
            TextCaptionBxArea area3 = new TextCaptionBxArea(820, 80, 100, 20, screen.getProfile());
            TextCaptionBxArea area4 = new TextCaptionBxArea(820, 105, 100, 20, screen.getProfile());
            TextCaptionBxArea area5 = new TextCaptionBxArea(820, 130, 100, 20, screen.getProfile());


            TextCaptionBxArea area6 = new TextCaptionBxArea(900, 30, 60, 15, screen.getProfile());
            TextCaptionBxArea area7 = new TextCaptionBxArea(900, 55, 60, 15, screen.getProfile());
            TextCaptionBxArea area8 = new TextCaptionBxArea(900, 80, 60, 15, screen.getProfile());
            TextCaptionBxArea area9 = new TextCaptionBxArea(900, 105, 60, 15, screen.getProfile());
            TextCaptionBxArea area10 = new TextCaptionBxArea(1200, 130, 60, 15, screen.getProfile());

            TextCaptionBxArea area11 = new TextCaptionBxArea(838, 0, 120, 20, screen.getProfile());
            TextCaptionBxArea area12 = new TextCaptionBxArea(900, 0, 60, 20, screen.getProfile());
//        TextCaptionBxArea area13 = new TextCaptionBxArea( 0,25,250,5,screen.getProfile());

            // 创建一个数据页
            // 第一行数据
            TextBxPage page1 = new TextBxPage("当前批次：");
            TextBxPage page2 = new TextBxPage("物料编码：");
            TextBxPage page3 = new TextBxPage("物料名称：");
            TextBxPage page4 = new TextBxPage("出库数量：");
            TextBxPage page5 = new TextBxPage("拆托数量：");


            TextBxPage page6 = new TextBxPage(batch);
            TextBxPage page7 = new TextBxPage(code);
            TextBxPage page8 = new TextBxPage(name);
            TextBxPage page9 = new TextBxPage("" + count);
            TextBxPage page10 = new TextBxPage("" + splitCount + " ");
            TextBxPage page11 = new TextBxPage("");
//        TextBxPage page12 = new TextBxPage("------------------------------------------------------------");
            // 第二行数据
//        page.newLine("这是第二行数据");
            // 设置字体
            page1.setFont(new Font("宋体", Font.PLAIN, 15));
            page2.setFont(new Font("宋体", Font.PLAIN, 15));
            page3.setFont(new Font("宋体", Font.PLAIN, 15));
            page4.setFont(new Font("宋体", Font.PLAIN, 15));
            page5.setFont(new Font("宋体", Font.PLAIN, 15));
            page6.setFont(new Font("宋体", Font.PLAIN, 15));
            page7.setFont(new Font("宋体", Font.PLAIN, 15));
            page8.setFont(new Font("宋体", Font.PLAIN, 15));
            page9.setFont(new Font("宋体", Font.PLAIN, 15));
            page10.setFont(new Font("宋体", Font.PLAIN, 15));
            page11.setFont(new Font("宋体", Font.PLAIN, 15));
            // 设置显示特技为快速打出
            page1.setDisplayStyle(styles[1]);
            page2.setDisplayStyle(styles[1]);
            page3.setDisplayStyle(styles[1]);
            page4.setDisplayStyle(styles[1]);
            page5.setDisplayStyle(styles[1]);
            page6.setDisplayStyle(styles[1]);
            page7.setDisplayStyle(styles[1]);
            page8.setDisplayStyle(styles[1]);
            page9.setDisplayStyle(styles[1]);
            page10.setDisplayStyle(styles[1]);
            page11.setDisplayStyle(styles[1]);
//        page12.setDisplayStyle(styles[1]);

            // 数据页可以是图片
//        ImageFileBxPage iPage  = new ImageFileBxPage( "/usr/local/software/logo2.png" );

            // 数据页可以是txt文件
//        TextFileBxPage tPage = new TextFileBxPage( "D:a/001.txt" );

            // 将前面的page添加到area中，page不可以是表格，如果需要Led显示表格，请先将表格绘制成图片
            area1.addPage(page1);
            area2.addPage(page2);
            area3.addPage(page3);
            area4.addPage(page4);
            area5.addPage(page5);
            area6.addPage(page6);
            area7.addPage(page7);
            area8.addPage(page8);
            area9.addPage(page9);
            area10.addPage(page10);
//        area11.addPage(iPage);
            area12.addPage(page11);
//        area13.addPage(page12);

            // 将area添加到节目中，节目中可以添加多个area
            pf.addArea(area1);
            pf.addArea(area2);
            pf.addArea(area3);
            pf.addArea(area4);
            pf.addArea(area5);
            pf.addArea(area6);
            pf.addArea(area7);
            pf.addArea(area8);
            pf.addArea(area9);
            pf.addArea(area10);
            pf.addArea(area11);
            pf.addArea(area12);
//        pf.addArea(area13);

            // 更新节目
            screen.writeProgram(pf);

            // 断开连接
            screen.disconnect();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送到LED失败:" + e.getMessage());
        }
    }
}
