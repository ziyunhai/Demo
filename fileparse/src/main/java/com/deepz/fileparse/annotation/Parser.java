package com.deepz.fileparse.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解析类注解
 *
 * @author xming
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Parser {

  /**
   * 文件类型
   *
   * @return
   */
  String[] fileType();
}
