package com.penghaisoft.wcs.task.connect;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsPathService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsPath;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.expose.dto.PalletInDto;
import com.penghaisoft.wcs.modbus.ModbusUtil;
import com.penghaisoft.wcs.operation.model.PalletizerInfo;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;
import com.penghaisoft.wcs.operation.model.WcsBindingInfoPlane;
import com.penghaisoft.wcs.operation.service.BindingPlaneService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskSplitService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description PLC返回老码垛线托盘绑定状态
 * @ClassName TcpByteHandler
 * @Author luot
 * @Date 2020/7/28 17:43
 **/
@Slf4j
@ChannelHandler.Sharable
@Component
public class PalletizerOtherTcpByteHandler extends SimpleChannelInboundHandler<Object> {

    @Autowired
    private BindingPlaneService bindingPlaneService;

    @Autowired
    private ITaskSplitService taskSplitService;

    @Autowired
    private IWcsCommonService wcsCommonService;

    @Autowired
    private IWcsPathService wcsPathService;

//    /**
//     * 连接激活
//     * @param ctx
//     * @throws Exception
//     */
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("连接成功");
//        byte[] b = new byte[]{1,2,3};
//        ctx.writeAndFlush(b);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object packet) throws Exception {
        byte[] data = (byte[]) packet;

//        检验位【前两个字节是校验位】
        short chk1 = ModbusUtil.twoBytes2Short(data[0], data[1]);
        if (chk1 != -1) {
            log.info("校验位1不合法");
            return;
        }

        dealData(data);

//       各个口的处理，放入ConveyorDispatchTask
        ctx.fireChannelReadComplete();
    }


//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
//    }

    /**
     * @return
     * @Description 解析数据，若没有插入数据库，则插入数据库
     * @Author luot
     * @Date 2020/7/29 10:56
     * @Param
     **/
    private void dealData(byte[] data) {
//        16进制的测试数据ffff000141413030303130000141413030303230
        String operator = this.getClass().getSimpleName();
//        等待绑定的数据
        List<WcsBindingInfoPlane> waitBindInfoList = new ArrayList<WcsBindingInfoPlane>();
        int channelCount = 2;
        int channel = 6;
        for (int i = 0; i < channelCount; i++) {
            PalletizerInfo palletizerInfo = new PalletizerInfo();
            int start = i * 46 + 2;
            log.info("解析{}号通道数据", channel + i);
//            short reqTrans = data[start];
            short reqTrans = ModbusUtil.twoBytes2Short(data[start], data[start + 1]);

//           码垛机请求传送，这时候进行绑定
            if (reqTrans == 1) {
                log.info("请求传送");
//                商品编码
                StringBuffer goodsCodeBuf = new StringBuffer();
                for (int j = 0; j < 16; j++) {
                    char s = (char) data[start + 4 + j];
                    goodsCodeBuf.append(s);
                }
                String goodsCode = goodsCodeBuf.toString();

//                批次号
                StringBuffer batchNoBuf = new StringBuffer();
//                plc托盘码提供7字节数据，目前只用前6字节
                for (int j = 0; j < 16; j++) {
                    char s = (char) data[start + 20 + j];
                    batchNoBuf.append(s);
                }
                String batchNo = batchNoBuf.toString();

//                托盘号
                StringBuffer palletCodeBuf = new StringBuffer();
//                plc托盘码提供7字节数据，目前只用前6字节
                for (int j = 0; j < 7; j++) {
                    char s = (char) data[start + 36 + j];
                    palletCodeBuf.append(s);
                }
                String palletCode = palletCodeBuf.toString();

//                数量
                short amount = ModbusUtil.twoBytes2Short(data[start + 44], data[start + 45]);

                WcsBindingInfoPlane wcsBindingInfoPlane = new WcsBindingInfoPlane();
                wcsBindingInfoPlane.setPalletizerId(channel + i);
                wcsBindingInfoPlane.setAddressId(channel + i);
                wcsBindingInfoPlane.setPalletCode(palletCode);
                wcsBindingInfoPlane.setGoodsCode(goodsCode);
                wcsBindingInfoPlane.setAmount((int)amount);
                wcsBindingInfoPlane.setBatchNo(batchNo);
                wcsBindingInfoPlane.setCreateBy(operator);
                wcsBindingInfoPlane.setGmtCreate(new Date());
                waitBindInfoList.add(wcsBindingInfoPlane);
            }else{
                log.info("未请求传送");
            }
        }

        if(!waitBindInfoList.isEmpty()){
            List<WcsBindingInfoPlane> list = bindingPlaneService.queryAll();
            for(WcsBindingInfoPlane tmp : waitBindInfoList){
                String palletCode = tmp.getPalletCode() == null ? "" : tmp.getPalletCode();
                Boolean flag = false;
                for(WcsBindingInfoPlane tpp : list){
                    String palletCodeCompare = tpp.getPalletCode() == null ? "" : tpp.getPalletCode();
                    if(palletCode.equals(palletCodeCompare)){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    bindingPlaneService.insertSelective(tmp);

                    Long taskId = (long)wcsCommonService.getTaskIds(Constant.TaskType.PRODUCT_IN, 1)[0];
                    splitInTaskWithoutLoc(tmp,taskId,operator);
                }
            }
        }
    }

    /**
     * 入库任务拆分
     * @param havaBindInfo
     * @param taskId
     * @param operator
     */
    private void splitInTaskWithoutLoc(WcsBindingInfoPlane havaBindInfo,Long taskId,String operator){
        Integer channelId = havaBindInfo.getAddressId();
        //        校验任务有没有重复下发
        boolean isDuplicate = taskSplitService.isDuplicateTask(taskId);
        if (isDuplicate){
            log.info("任务{}重复！",taskId);
            return;
        }

//        根据库位转获取入库线
//        Integer inBufferAddress = wcsCommonService.getInBufferAddressByLocation(0);
        Integer inBufferAddress = 1006;

//      判断地址是否合法,从码垛线（绑定时获取）到立库入库口（写死） 理论上这不可能不合法
        Resp pathResult = wcsPathService.isPathLegal(channelId,inBufferAddress);
        if ("0".equals(pathResult.getCode())){
            log.info(pathResult.getMsg());
            return;
        }
        WcsPath wcsPath = (WcsPath) pathResult.getData();
        Integer pathId = wcsPath.getPathId();

//        拆分
        PalletInDto palletInDto = new PalletInDto();
        palletInDto.setTaskId(taskId);
        palletInDto.setPalletCode(havaBindInfo.getPalletCode());
        palletInDto.setFromAddress(channelId);
        palletInDto.setOperator(operator);
//        现在还没有目的地库位
        taskSplitService.splitInTaskPlane(inBufferAddress,pathId,palletInDto,null);
    }
}
