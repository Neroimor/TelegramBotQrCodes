package com.neroimor.Generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenerateQrCode {

    private final int WIDHT = 400;
    private final int HEIGHT = 400;

    public InputFile generateQR(String data) throws WriterException, IOException {
        Map<EncodeHintType, Object> hintMap = new HashMap<EncodeHintType, Object>();
        hintMap.put(EncodeHintType.MARGIN, 1);
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix matrix = new MultiFormatWriter()
                .encode(data, BarcodeFormat.QR_CODE, WIDHT,HEIGHT ,hintMap);

        BufferedImage image = new BufferedImage(WIDHT, HEIGHT, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < WIDHT; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                image.setRGB(i, j, matrix.get(i, j) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }
        return convertBufferedImageToInputFile(image);
    }

    public InputFile convertBufferedImageToInputFile(BufferedImage image) throws IOException {
        File tempFile = File.createTempFile("QRCode", ".png");
        ImageIO.write(image, "jpg", tempFile);
        return new InputFile(tempFile);
    }
}
