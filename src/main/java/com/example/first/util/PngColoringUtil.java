package com.example.first.util;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class PngColoringUtil {

    public static void changePNGBackgroudColor(String sourceImage, Color backgroudColor) {
        try {
            BufferedImage result = changePNGBackgroudColor2(sourceImage + ".png", backgroudColor);
            File output = new File(sourceImage + ".jpeg");
            output.getParentFile().mkdirs();
            ImageIO.write(result, "png", output);
        } catch (IOException e) {
            System.out.println("有问题了" + e.getMessage());
        }
    }

    public static void handleDpi(File file, int dpi) {
        FileOutputStream fileOutputStream = null;
        try {
            BufferedImage image = ImageIO.read(file);
            fileOutputStream = new FileOutputStream(file);
            saveAsJPEG(dpi, image, 1.0f, fileOutputStream);
//            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
//            JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(image);
//            jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
//            jpegEncoder.setJPEGEncodeParam(jpegEncodeParam);
//            jpegEncodeParam.setQuality(1.0f, false);
//            jpegEncodeParam.setXDensity(dpi);
//            jpegEncodeParam.setYDensity(dpi);
//            jpegEncoder.encode(image, jpegEncodeParam);
            image.flush();

        } catch (IOException e) {
            e.printStackTrace();
            HashMap<String, Object> extend = new HashMap<>(2);
            extend.put("fileName", file.getName());
            extend.put("exception", e.toString());
        } finally {
            try {
                if(fileOutputStream != null)
                    fileOutputStream.flush();
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "/doc/test.jpeg";
        try {
            BufferedImage image = changePNGBackgroudColor2(filePath,null);
            File output = new File("/doc/test2.jpeg");
            output.getParentFile().mkdirs();
            ImageIO.write(image, "jpeg", output);
            handleDpi(output,300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 以JPEG编码保存图片
     * @param dpi 分辨率
     * @param imageToSave 要处理的图像图片
     * @param quality 压缩比
     * @param fos 文件输出流
     * @throws IOException
     */
    public static void saveAsJPEG(Integer dpi, BufferedImage imageToSave,
                                  float quality, FileOutputStream fos) throws IOException {
        //格式化的图片类型
        ImageWriter imageWriter = ImageIO
                .getImageWritersBySuffix("jpeg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);

        //写入压缩的倍数
        ImageWriteParam jpegParams = null;
        if (quality >= 0 && quality <= 1f) {
            //新建压缩比率
            jpegParams = imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(quality);
        }

        // and metadata
        IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(
                new ImageTypeSpecifier(imageToSave), jpegParams);

        //添加具体的分辨率
        if(Objects.nonNull(dpi)) {
//            Element tree = (Element)
//                    imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
//            Element jfif = (
//            k) tree.getElementsByTagName("app0JFIF").item(0);
//            jfif.setAttribute("Xdensity", Integer.toString(dpi));
//            jfif.setAttribute("Ydensity", Integer.toString(dpi));
//           // imageMetaData.setFromTree("javax_imageio_jpeg_image_1.0",tree);
//            imageMetaData.mergeTree("javax_imageio_jpeg_image_1.0",tree);
            IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(imageMetaData.getNativeMetadataFormatName());
            IIOMetadataNode jfif = (IIOMetadataNode) root.getElementsByTagName("app0JFIF").item(0);
            jfif.setAttribute("resUnits", "1");
            jfif.setAttribute("Xdensity", Integer.toString(dpi));
            jfif.setAttribute("Ydensity", Integer.toString(dpi));
            imageMetaData.mergeTree(imageMetaData.getNativeMetadataFormatName(), root);
        }
        imageWriter.write(imageMetaData,
                new IIOImage(imageToSave, null, imageMetaData), jpegParams);
        ios.close();
        imageWriter.dispose();
    }

    /**
     * @Description 给PNG图片增加背景色 返回BufferedImage
     * @param sourceImage 原始图片 最好是PNG透明的
     * @param backgroudColor 背景色
     * @return BufferedImage
     **/
    public static BufferedImage changePNGBackgroudColor2(String sourceImage, Color backgroudColor) {
        try {
            File input = new File(sourceImage);
            BufferedImage image = ImageIO.read(input);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D graphic = result.createGraphics();
            graphic.drawImage(image, 0, 0, backgroudColor, null);
            graphic.dispose();
            return result;
        } catch (IOException e) {
            System.out.println("有问题了" + e.getMessage());
            return null;
        }
    }



    public static void pngToJpg(String filepath) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filepath));
            BufferedImage newBufferedImage = new BufferedImage(
                    bufferedImage.getWidth(), bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            // TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, new Color(0f,0f,0f,0f), null);
            ImageIO.write(newBufferedImage, "png", new File(filepath));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setBgColor(String imgInPath, String bgColor,Integer dpi){
        if(null == dpi){
            dpi = 301;
        }
        int index = imgInPath.lastIndexOf(".");
        int num = imgInPath.lastIndexOf("/");
        String filepath = imgInPath.substring(imgInPath.indexOf("/upload/"),num + 1);
        String imgInName = imgInPath.substring(num + 1, index);
        String imagePath = filepath + imgInName;
        changePNGBackgroudColor(imagePath, new Color(Integer.parseInt(bgColor, 16)));
        handleDpi(new File(imagePath + ".jpeg"),dpi);
        new File(imagePath + ".png").delete();
    }

}
