package com.iglens.压缩包.zip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Zip4j加解密1 {
  public static void main(String[] args) {
    String zipFilePath = "C:\\Users\\T480S\\Desktop\\zip4j.zip";
    String fileFolder = "C:\\Users\\T480S\\Desktop\\zip4jtest";
    try {
      unzip4J(zipFilePath, fileFolder, "1234");
    } catch (ZipException e) {
      e.printStackTrace();
    }
  }

  /**
   * 压缩单个文件
   *
   * @throws ZipException
   */
  public static void dozip1(String zipFilePath) throws ZipException {
    File file = new File(zipFilePath);
    // 需要判断压缩父路径是否存在
    if (!file.getParentFile().exists()) {
      boolean mkdirs = file.getParentFile().mkdirs();
      System.out.println("压缩包文件父路径不存在，自动创建：" + (mkdirs ? "成功" : "失败"));
    }

    ZipFile zip = new ZipFile(zipFilePath);
    // 设置编码
    zip.setFileNameCharset("GBK");
    // 设置zip压缩参数
    ZipParameters para = new ZipParameters();
    // 设置压缩方式,默认是COMP_DEFLATE
    para.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    // 设置压缩级别,默认为0（即不压缩）
    para.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
    // 设置压缩密码（至少3步）
    // 设置加密文件
    para.setEncryptFiles(true);
    // 设置加密方式（必须要有加密算法）
    para.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
    para.setPassword("test111");
    File srcFile = new File("src//com//学习路线.png");
    zip.addFile(srcFile, para);
    System.out.println("压缩成功！");
  }

  /**
   * 压缩多个文件
   *
   * @throws ZipException
   */
  public static void dozip2() throws ZipException {
    ZipFile zip = new ZipFile("D://压缩//test.zip");
    // 要紧跟设置编码
    zip.setFileNameCharset("GBK");
    ArrayList<File> list = new ArrayList<>();
    list.add(new File("src//com//压缩解压//abc.txt"));
    list.add(new File("src//com//压缩解压//abcUTF-8.txt"));
    list.add(new File("src//com//压缩解压//解压之后.txt"));

    ZipParameters para = new ZipParameters();
    para.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    para.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
    // 设置密码：
    para.setEncryptFiles(true);
    // 设置AES加密方式
    para.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
    // 必须设置长度
    para.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
    para.setPassword("111");
    zip.addFiles(list, para);
    System.out.println("压缩成功！");
  }

  /**
   * 压缩文件夹
   *
   * @throws ZipException
   */
  public static void dozip3() throws ZipException {
    ZipFile zip = new ZipFile("D:\\压缩\\test.zip");
    File file = zip.getFile();
    if (!file.getParentFile().exists()) {
      file.getParentFile().mkdirs();
    }
    // 为了不被原有文件干扰,保证每次重新生成
    if (file.exists()) {
      file.delete();
    }
    ZipParameters para = new ZipParameters();
    // 设置压缩级别，压缩方法默认
    para.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
    zip.addFolder("E:\\测试压缩", para);
    System.out.println("压缩完成！");
  }

  /**
   * 解压方法1
   *
   * @param zipFile
   * @throws ZipException
   */
  public static void unzip4J(String zipFile, String unZipPath, String passwd) throws ZipException {
    long startTime = System.currentTimeMillis();
    ZipFile zip = new ZipFile(zipFile);
    // 第一时间设置编码格式
    zip.setFileNameCharset("GBK");
    // 用自带的方法检测一下zip文件是否合法，包括文件是否存在、是否为zip文件、是否被损坏等
    if (!zip.isValidZipFile()) {
      throw new ZipException("文件不合法或不存在");
    }
    // 检查是否需要密码
    if (zip.isEncrypted()) {
      zip.setPassword(passwd.toCharArray());
    }
    // 跟java自带相比，这里文件路径会自动生成，不用判断
    zip.extractAll(unZipPath);
    System.out.println("解压成功！");
    long endTime = System.currentTimeMillis();
    System.out.println("耗时：" + (endTime - startTime) + "ms");
  }

  /**
   * 解压方法2
   *
   * @param zipFile
   * @throws ZipException
   */
  public static void Unzip4j1(String zipFile, String passwd) throws ZipException {
    long startTime = System.currentTimeMillis();
    ZipFile zip = new ZipFile(zipFile);
    // 设置编码格式
    zip.setFileNameCharset("GBK");
    if (!zip.isValidZipFile()) {
      throw new ZipException("文件不合法或不存在");
    }
    // 检查是否需要密码
    if (zip.isEncrypted()) {
      zip.setPassword(passwd.toCharArray());
    }
    List<FileHeader> fileHeaderList = zip.getFileHeaders();
    for (int i = 0; i < fileHeaderList.size(); i++) {
      FileHeader fileHeader = fileHeaderList.get(i);
      zip.extractFile(fileHeader, "D:\\压缩\\test");
    }
    System.out.println("解压成功！");
    long endTime = System.currentTimeMillis();
    System.out.println("耗时：" + (endTime - startTime) + "ms");
  }
}
