//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Hashtable;
import javax.imageio.ImageIO;

public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT = "png";
    private static final int QRCODE_SIZE = 250;
    private static final int LOGO_WIDTH = 50;
    private static final int LOGO_HEIGHT = 50;

    public QRCodeUtil() {
    }

    private static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = (new MultiFormatWriter()).encode(content, BarcodeFormat.QR_CODE, 250, 250, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, 1);

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? -16777216 : -1);
            }
        }

        if (logoPath != null && !"".equals(logoPath)) {
            insertImage(image, logoPath, needCompress);
            return image;
        } else {
            return image;
        }
    }

    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws Exception {
        File file = new File(logoPath);
        if (file.exists()) {
            Image src = ImageIO.read(new File(logoPath));
            int width = ((Image)src).getWidth((ImageObserver)null);
            int height = ((Image)src).getHeight((ImageObserver)null);
            if (needCompress) {
                if (width > 50) {
                    width = 50;
                }

                if (height > 50) {
                    height = 50;
                }

                Image image = ((Image)src).getScaledInstance(width, height, 4);
                BufferedImage tag = new BufferedImage(width, height, 1);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, (ImageObserver)null);
                g.dispose();
                src = image;
            }

            Graphics2D graph = source.createGraphics();
            int x = (250 - width) / 2;
            int y = (250 - height) / 2;
            graph.drawImage((Image)src, x, y, width, height, (ImageObserver)null);
            Shape shape = new Float((float)x, (float)y, (float)width, (float)width, 6.0F, 6.0F);
            graph.setStroke(new BasicStroke(3.0F));
            graph.draw(shape);
            graph.dispose();
        }
    }

    public static Boolean encode(String content, String logoPath, String filePath, String fileName, boolean needCompress) throws Exception {
        Boolean success = false;
        File file = new File(filePath + File.separator + fileName);
        if (file.exists()) {
            file.delete();
        }

        BufferedImage image = createImage(content, logoPath, needCompress);
        ImageIO.write(image, "png", new File(filePath + File.separator + fileName));
        if (file.exists()) {
            success = true;
        }

        return success;
    }

    public static BufferedImage encodeImg(String content, String logoPath, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        return image;
    }
}
