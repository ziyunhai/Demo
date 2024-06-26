package com.iglens.文件;

import java.io.File;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class 删除目录及子文件 {
  private static final Logger LOGGER = LoggerFactory.getLogger(删除目录及子文件.class);

  /**
   * 删除目录及目录下的文件
   *
   * @param dir 要删除的目录的文件路径
   * @return 目录删除成功返回true，否则返回false
   */
  public static boolean deleteDirectory(String dir) {
    // 如果dir不以文件分隔符结尾，自动添加文件分隔符
    if (!dir.endsWith(File.separator)) {
      dir = dir + File.separator;
    }
    File dirFile = new File(dir);
    // 如果dir对应的文件不存在，或者不是一个目录，则退出
    if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
      LOGGER.info("删除目录失败：" + dir + "不存在！");
      return false;
    }
    boolean flag = true;
    // 删除文件夹中的所有文件包括子目录
    File[] files = dirFile.listFiles();
    for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
      // 删除子文件
      if (files[i].isFile()) {
        flag = deleteFileByPath(files[i].getAbsolutePath());
        if (!flag) {
          break;
        }
      } else if (files[i].isDirectory()) {
        // 删除子目录
        flag = deleteDirectory(files[i].getAbsolutePath());
        if (!flag) {
          break;
        }
      }
    }

    if (!dirFile.delete() || !flag) {
      LOGGER.info("删除目录失败！");
      return false;
    }
    return true;
  }

  /**
   * 删除单个文件
   *
   * @param filePath 要删除的文件的文件路径
   * @return 单个文件删除成功返回true，否则返回false
   */
  public static boolean deleteFileByPath(String filePath) {
    File file = new File(filePath);
    // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
    if (file.exists() && file.isFile()) {
      if (file.delete()) {
        LOGGER.info("删除单个文件" + filePath + "成功！");
        return true;
      } else {
        System.gc();
        System.runFinalization();
        boolean delete = file.delete();
        LOGGER.info("删除单个文件" + filePath + "：" + delete + " ！");
        return delete;
      }
    } else {
      LOGGER.info("删除单个文件失败：" + filePath);
      return false;
    }
  }
}
