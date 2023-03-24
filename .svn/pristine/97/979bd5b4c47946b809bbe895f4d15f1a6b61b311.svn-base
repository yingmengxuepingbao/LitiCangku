package com.penghaisoft.wms.util;

import org.apache.commons.lang.ObjectUtils;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Description 生成条形码-工具类
 * @Author zhangxin
 * @Date 2022-08-18
 **/
public class BarCodeUtils {
    /**
     * 生成code128条形码
     *
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param message       要生成的文本
     * @param withQuietZone 是否两边留白
     * @param hideText      隐藏可读文本
     * @return 图片对应的字节码
     */
    public static byte[] generateBarCode128(String message, Double height, Double width, boolean withQuietZone, boolean hideText) {
        Code128Bean bean = new Code128Bean();
        // 分辨率
        int dpi = 512;
        // 设置两侧是否留白
        bean.doQuietZone(withQuietZone);

        // 设置条形码高度和宽度
        bean.setBarHeight((double) ObjectUtils.defaultIfNull(height, 9.0D));
        if (width != null) {
            bean.setModuleWidth(width);
        }
        // 设置文本位置（包括是否显示）
        if (hideText) {
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }
        // 设置图片类型
        String format = "image/png";

        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                BufferedImage.TYPE_BYTE_BINARY, false, 0);

        // 生产条形码
        bean.generateBarcode(canvas, message);
        try {
            canvas.finish();
        } catch (IOException e) {

        }
        return ous.toByteArray();
    }
    /**
     *功能描述: 生成一个二维码
     * @params
     * @return void
     */
    public static void creat128yards(HttpServletResponse response, String strA) throws IOException {
        byte[] bytes = BarCodeUtils.generateBarCode128(strA, 10.00, 0.3, true, false);
        response.setContentType("image/png");
        OutputStream output = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(bytes);
        int len;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf)) != -1) {
            output.write(buf, 0, len);
        }
        output.flush();
        //如果没有下面两行，可能出现getOutputStream() has already been called for this response的异常
        /*output.clear();
        out = pageContext.pushBody();
        Result result = new Result();*/
    }

    /**
     * 拼接byte数组
     * @param data1
     * @param data2
     * @return 拼接后数组
     */
    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }
}
