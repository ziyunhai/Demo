package com.iglens.文件.读取Jar文件;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import org.springframework.core.io.ClassPathResource;

public class 读取Jar包内resource下文件 {

  public static void main(String[] args) {
    String imageFileName =
        File.separator + "example" + File.separator + "windDirection_2022071623.txt";
    String outPath = "C:\\Users\\zhouhuilin\\Desktop\\新建 文本文档.txt";

    getJarContextFile(imageFileName, outPath);

    // String windDirectionPath =
    //     File.separator + "example" + File.separator + "windDirection_2022071623.txt";
    // String windDirectionString = null;
    // try {
    //   String decode = URLDecoder.decode(windDirectionPath, "UTF-8");
    //   ClassPathResource resource = new ClassPathResource(decode);
    //   InputStream inputStream = resource.getInputStream();
    //   windDirectionString = readToString(inputStream);
    // } catch (IOException e) {
    //   log.error(e.getMessage(), e);
    // }
  }

  private static void getJarContextFile(String imageFileName, String outPath) {
    try {
      // 转译，避免中文乱码
      String decode = URLDecoder.decode(imageFileName, "UTF-8");
      ClassPathResource resource = new ClassPathResource(decode);
      // 因为 ClassPathResource.getFile 只能读取文件，jar包中已经不是文件，不适用所以只能使用 getInputStream 来获取文件流
      InputStream inputStream = resource.getInputStream();
      // outPath 不能为文件夹，否则会出现拒绝访问，应为具体文件才能写
      FileOutputStream out = new FileOutputStream(outPath);
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);

      byte[] buff = new byte[1024];
      int n;
      while ((n = inputStream.read(buff)) != -1) {
        bufferedOutputStream.write(buff, 0, n);
      }
      bufferedOutputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
