package com.iglens.地理.瓦片;

/**
 * @link {https://www.cnblogs.com/akaishi/p/7519992.html}
 * @author T480S
 */
public class 经纬度范围与用户指定的瓦片层级计算需要下载的瓦片数量与待拼接结果图像的像素尺寸 {

  public static void main(String[] args) {
    System.out.println("Hello World!");
    double minLat = 39.02734816074371;
    double maxLat = 39.05447065830231;
    double minLon = 117.71792650222778;
    double maxLon = 117.75427579879761;
    int zoom = 17;

    经纬度范围与用户指定的瓦片层级计算需要下载的瓦片数量与待拼接结果图像的像素尺寸 e = new 经纬度范围与用户指定的瓦片层级计算需要下载的瓦片数量与待拼接结果图像的像素尺寸();

    int[] pixSize = new int[] {0, 0};
    int tnum;
    tnum = e.getGeodeticSize(minLon, maxLon, minLat, maxLat, zoom, pixSize);
    System.out.println("经纬度数据瓦片数：" + tnum + "      图像尺寸：" + pixSize[0] + "*" + pixSize[1]);
    tnum = e.getMercatorSize(minLon, maxLon, minLat, maxLat, zoom, pixSize);
    System.out.println("谷歌数据瓦片数：" + tnum + "        图像尺寸：" + pixSize[0] + "*" + pixSize[1]);
  }

  /**
   * 计算谷歌投影输出瓦片数量与待拼接图像像素尺寸
   *
   * @param minLon
   * @param maxLon
   * @param minLat
   * @param maxLat
   * @param zoom 瓦片层级
   * @param pixSize 像素数组，
   * @return 瓦片数量
   */
  public int getMercatorSize(
      double minLon, double maxLon, double minLat, double maxLat, int zoom, int[] pixSize) {

    double[] oULxy = new double[] {0, 0};
    double[] oDRxy = new double[] {0, 0};
    LatLonToMeters(minLon, maxLat, oULxy);
    LatLonToMeters(maxLon, minLat, oDRxy);
    double ominx = oULxy[0];
    double omaxx = oDRxy[0];
    double ominy = oDRxy[1];
    double omaxy = oULxy[1];

    int[] tminxy = new int[] {0, 0};
    int[] tmaxxy = new int[] {0, 0};
    MetersToTile(ominx, ominy, zoom, tminxy);
    MetersToTile(omaxx, omaxy, zoom, tmaxxy);

    pixSize[0] = (1 + Math.abs(tmaxxy[0] - tminxy[0])) * 256;
    pixSize[1] = (1 + Math.abs(tmaxxy[1] - tminxy[1])) * 256;

    int tnum = (1 + Math.abs(tmaxxy[0] - tminxy[0])) * (1 + Math.abs(tmaxxy[1] - tminxy[1]));

    return tnum;
  }

  private void LatLonToMeters(double lon, double lat, double[] mxy) {
    // WGS84椭球的长半轴就为6378137.0
    double m_originShift = 2 * 3.141592653589793 * 6378137 / 2.0;

    mxy[0] = lon * m_originShift / 180.0;
    mxy[1] =
        Math.log(Math.tan((90 + lat) * 3.141592653589793 / 360.0)) / (3.141592653589793 / 180.0);

    mxy[1] = mxy[1] * m_originShift / 180.0;
  }

  private void MetersToTile(double mx, double my, int zoom, int[] txy) {
    double m_initialResolution = 2 * 3.141592653589793 * 6378137 / 256;
    double m_originShift = 2 * 3.141592653589793 * 6378137 / 2.0;

    double res = m_initialResolution / Math.pow(2, zoom);
    double px = (mx + m_originShift) / res;
    double py = (m_originShift - my) / res;

    txy[0] = (int) (Math.ceil(px / (float) (256)) - 1);
    txy[1] = (int) (Math.ceil(py / (float) (256)) - 1);
  }

  /** 计算经纬度输出瓦片数量与待拼接图像像素尺寸 */
  public int getGeodeticSize(
      double minLon, double maxLon, double minLat, double maxLat, int zoom, int[] pixSize) {

    int[] tminxy = new int[] {0, 0};
    int[] tmaxxy = new int[] {0, 0};

    lonLatToTile(minLon, minLat, zoom - 1, tminxy);
    lonLatToTile(maxLon, maxLat, zoom - 1, tmaxxy);

    pixSize[0] = (1 + Math.abs(tmaxxy[0] - tminxy[0])) * 256;
    pixSize[1] = (1 + Math.abs(tmaxxy[1] - tminxy[1])) * 256;

    // 瓦片数量
    int tnum = (1 + Math.abs(tmaxxy[0] - tminxy[0])) * (1 + Math.abs(tmaxxy[1] - tminxy[1]));

    return tnum;
  }

  private void lonLatToTile(double lon, double lat, int zoom, int[] txy) {
    double resFact = 180.0 / 256.0;
    double[] pxy = new double[] {0.0, 0.0};
    double pow = Math.pow(2, zoom);
    double res = resFact / pow;
    pxy[0] = (180.0 + lon) / res;
    pxy[1] = (90.0 - lat) / res;

    //    Math.ceil()  “向上取整”， 即小数部分直接舍去，并向正数部分进1
    txy[0] = (int) (Math.ceil(pxy[0] / 256.0) - 1);
    txy[1] = (int) (Math.ceil(pxy[1] / 256.0) - 1);
  }
}
