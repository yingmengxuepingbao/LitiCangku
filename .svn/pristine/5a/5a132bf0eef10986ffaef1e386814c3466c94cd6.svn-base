//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IBasePdaVersionService;
import com.penghaisoft.wms.basicmanagement.model.entity.BasePdaVersion;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/expose/basePdaVersion"})
public class BasePdaVersionQRCodeController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BasePdaVersionQRCodeController.class);
    @Autowired
    private IBasePdaVersionService basePdaVersionService;

    public BasePdaVersionQRCodeController() {
    }

    @GetMapping({"qrCode/{id}"})
    public void qrCode(@PathVariable String id, HttpServletResponse response) {
        BufferedImage image = this.getImage(id);
        if (image == null) {
            log.info("二维码生成失败");
        }

        try {
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    private BufferedImage getImage(String id) {
        BasePdaVersion basePdaVersion = this.basePdaVersionService.findById(id);
        if (basePdaVersion == null) {
            return null;
        } else {
            String logoPath = "";
            BufferedImage image = null;

            try {
                image = QRCodeUtil.encodeImg(basePdaVersion.getUrl(), logoPath, true);
            } catch (Exception var6) {
                var6.printStackTrace();
            }

            return image;
        }
    }
}
