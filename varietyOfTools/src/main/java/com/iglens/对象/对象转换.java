package com.iglens.对象;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.cglib.beans.BeanMap;

/** @author T480S */
public class 对象转换 {
  /**
   * 将对象装换为map
   *
   * @param bean 对象
   * @return
   */
  public static <T> Map<String, Object> beanToMap(T bean) {
    Map<String, Object> map = Maps.newHashMap();
    if (bean != null) {
      BeanMap beanMap = BeanMap.create(bean);
      for (Object key : beanMap.keySet()) {
        map.put(key + "", beanMap.get(key));
      }
    }
    return map;
  }
  /**
   * 将map装换为javabean对象
   *
   * @param map
   * @param bean 对象
   * @return
   */
  public static <T> T mapToBean(Map<String, Object> map, T bean) {
    BeanMap beanMap = BeanMap.create(bean);
    beanMap.putAll(map);
    return bean;
  }
  /**
   * 将List<T>转换为List<Map<String, Object>>
   *
   * @param objList
   * @return
   */
  public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
    List<Map<String, Object>> list = Lists.newArrayList();
    if (objList != null && objList.size() > 0) {
      Map<String, Object> map;
      T bean;
      for (T t : objList) {
        bean = t;
        map = beanToMap(bean);
        list.add(map);
      }
    }
    return list;
  }
  /**
   * 将List<Map<String,Object>>转换为List<T>
   *
   * @param maps
   * @param clazz
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz)
      throws InstantiationException, IllegalAccessException {
    List<T> list = Lists.newArrayList();
    if (maps != null && maps.size() > 0) {
      Map<String, Object> map;
      T bean;
      for (Map<String, Object> stringObjectMap : maps) {
        map = stringObjectMap;
        bean = clazz.newInstance();
        mapToBean(map, bean);
        list.add(bean);
      }
    }
    return list;
  }
}
