package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

/**
 * NettyTCP回调类
 */
public class NettyTcpByteHandler extends SimpleChannelInboundHandler<Object> {

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("启动发送数据");
//        byte[] b = new byte[]{1,2,3};
//        ctx.writeAndFlush(b);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object packet) throws Exception {
        System.out.println("--读到数据开始--");
        byte[] byteBuf = (byte[]) packet;
        List<Byte> bytes = new LinkedList<>();
        for (int i = 0; i < byteBuf.length; i++) {
            Byte aByte = byteBuf[i];
            bytes.add(aByte);
        }
        System.out.println(bytes);
        System.out.println(bytes.size());
//        String data = bytes2String(byteBuf);
        //删除数据前后空格
//        data = data.trim();
        System.out.println("--读取数据结束--");

//        ctx.writeAndFlush("222".getBytes());
        ctx.fireChannelReadComplete();
    }


//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
//    }

    /**
     * bytes数组转换字符串数组
     *
     * @param  bytes
     * @return String[]
     */
    private String bytes2String(byte[] bytes) {

        //定义解析数据的数据起始位置
        int start = 12;
        int end = bytes.length;

        //截取前十二位解析传感器类型、ID及数据条数
        byte[] byteHead = new byte[12];
        for (int i=0;i<start;i++){
            byteHead[i] = bytes[i];
        }
        String encoded = Base64.getEncoder().encodeToString(byteHead);
        byte[] decoded = Base64.getDecoder().decode(encoded);
        String head = new String(decoded);

        //定义返回结果
        StringBuffer sb = new StringBuffer("");
        sb.append(head);

        int standardNum = Integer.valueOf("800000", 16);
        int max = Integer.valueOf("ffffff", 16);

        //解析数据
        BigDecimal multiplicand = new BigDecimal(4.096);//数值转换时的乘数
        BigDecimal divisor = new BigDecimal(7864320);//数值转换时的被除数
        String result = "";//解析过程中的过度变量
        int i = 0;
        for(int j=start;j<end;j++) {
            String hex = Integer.toHexString(bytes[j] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            //计算数据在结果数组中的下标
            int index = (j-12)/3;
            if(i==index){
                result = hex.toUpperCase() + result;
            }else {
                int num = Integer.valueOf(result, 16);
                if(num>=standardNum) {
                    num = num - max;
                }
                result = String.valueOf((double) num);
                BigDecimal data = new BigDecimal(result);
                result = data.multiply(multiplicand).divide(divisor,2,BigDecimal.ROUND_HALF_UP).toString();
                sb.append(result);
                result = hex.toUpperCase();
                i = index;
            }
        }

        //循环结束最后三位需要转换并追加到结尾
        int num = Integer.valueOf(result, 16);
        if(num>=standardNum) {
            num = num - max;
        }
        result = String.valueOf((double) num);
        BigDecimal data = new BigDecimal(result);
        result = data.multiply(multiplicand).divide(divisor,2,BigDecimal.ROUND_HALF_UP).toString();
        sb.append(result);

        return sb.toString();
    }

}
