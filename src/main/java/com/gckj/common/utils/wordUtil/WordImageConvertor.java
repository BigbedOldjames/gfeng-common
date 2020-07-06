package com.gckj.common.utils.wordUtil;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

/**
 * @Description：文档图片转换器
 * @author：ldc
 * date：2020-06-23
 */
public class WordImageConvertor {

    private static final Logger logger = LoggerFactory.getLogger(WordImageConvertor.class);
    /**
     * 将图片转换成base64编码的字符串
     * @param imageSrc
     * @return
     * @throws IOException
     */
    public static String imageToBase64(String imageSrc) throws IOException{
        //判断文件是否存在
        File file=new File(imageSrc);
        if(!file.exists()){
            throw new FileNotFoundException("WordImageConvertor imageToBase64():file not found！");
        }
        StringBuilder pictureBuffer = new StringBuilder();
        FileInputStream input=new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        //读取文件
        Base64 base64=new Base64();
        BASE64Encoder encoder=new BASE64Encoder();
        byte[] temp = new byte[1024];
        for(int len = input.read(temp); len != -1;len = input.read(temp)){
            out.write(temp, 0, len);
        }
        pictureBuffer.append(new String(base64.encodeBase64Chunked(out.toByteArray())));
        input.close();
        return pictureBuffer.toString();
    }

    /**
     * 拼接文档的图片
     * @param imageFilePath 图片路径
     * @param imageFielShortName 图片短路径名
     * @param imageHeight 图片高度
     * @param imageWidth  图片宽度
     * @param imageStyle  图片样式
     * @param srcLocationShortName doc中图片短路径名
     * @param shapeidPrex 固定值
     * @param spidPrex  固定值
     * @param typeid  固定值
     * @return
     */
    public static String toDocBodyBlock(String imageFilePath,String imageFielShortName,int imageHeight,int imageWidth,String imageStyle,String srcLocationShortName,String shapeidPrex,String spidPrex,String typeid){
        //shapeid
        //mht文件中针对shapeid的生成好像规律，其内置的生成函数没法得知，但是只要保证其唯一就行
        //这里用前置加32位的uuid来保证其唯一性。
        String shapeid=shapeidPrex;
        shapeid+= UUIDUtils.get32UUID();
        //spid ,同shapeid处理
        String spid=spidPrex;
        spid+=UUIDUtils.get32UUID();

        StringBuilder sb1=new StringBuilder();
        sb1.append(" <!--[if gte vml 1]>");
        sb1.append("<v:shape id=3D\"" + shapeid+"\"");
        sb1.append("\n");
        sb1.append(" o:spid=3D\""+ spid +"\"" );
        sb1.append(" type=3D\""+  typeid +"\" alt=3D\"" + imageFielShortName +"\"");
        sb1.append("\n");
        sb1.append( " style=3D' " + generateImageBodyBlockStyleAttr(imageFilePath,imageHeight,imageWidth) + imageStyle +"'");
        sb1.append(">");
        sb1.append("\n");
        sb1.append(" <v:imagedata src=3D\"" + srcLocationShortName +"\""  );
        sb1.append("\n");
        sb1.append(" o:title=3D\"" + imageFielShortName.split("\\.")[0]+"\""  );
        sb1.append("/>");
        sb1.append("</v:shape>");
        sb1.append("<![endif]-->");
        
        return sb1.toString();
    }

    /**
     * 生成图片base64字符串在mht中格式
     * @param nextPartId
     * @param contextLoacation
     * @param fileTypeName
     * @param base64Content
     * @return
     */
    public static String generateImageBase64Block(String nextPartId,String contextLoacation,
                                                  String fileTypeName,String base64Content){
        StringBuilder sb=new StringBuilder();
        sb.append("\n");
        sb.append("\n");
        sb.append("------=_NextPart_"+nextPartId);
        sb.append("\n");
        sb.append("Content-Location: "+ contextLoacation);
        sb.append("\n");
        sb.append("Content-Transfer-Encoding: base64");
        sb.append("\n");
        sb.append("Content-Type: " + FileUtils.getImageContentType(fileTypeName));
        sb.append("\n");
        sb.append("\n");
        sb.append(base64Content);
        return sb.toString();
    }


    /**
     * 创建图片的样式
     * @param imageFilePath
     * @param height
     * @param width
     * @return
     */
    private static String generateImageBodyBlockStyleAttr(String imageFilePath, int height,int width){
        StringBuilder sb=new StringBuilder();
        BufferedImage sourceImg;
        try {
            sourceImg = ImageIO.read(new FileInputStream(imageFilePath));
            if(height==0){
                height=sourceImg.getHeight();
            }
            if(width==0){
                width=sourceImg.getWidth();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将像素转化成pt
        BigDecimal heightValue=new BigDecimal(height*12/16);
        heightValue= heightValue.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal widthValue=new BigDecimal(width*12/16);
        widthValue= widthValue.setScale(2, BigDecimal.ROUND_HALF_UP);

        sb.append("height:"+heightValue +"pt;");
        sb.append("width:"+widthValue +"pt;");
        sb.append("visibility:visible;");
        sb.append("mso-wrap-style:square; ");
        return sb.toString();
    }
}
