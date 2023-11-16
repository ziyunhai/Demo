package com.iglens.json.使用JSON字符串生成Java实体类;

import com.iglens.爬虫.基金.test;

public class MainClass {
    public static void main(String[] args) {
        JsonToEntity jsonToEntity = new JsonToEntity();

        //可选:按需要在字段属性上追加的注解
        jsonToEntity.appendInterfaceAdd("@ExcelImport(value = \"{key}\",required = true,unique = true)");
         //可选:按需要重写属性名
        jsonToEntity.fieldMapPut("身份证号码","a");
    // User.class这个类需要手动先创建,创建后执行即可写入字段属性
    jsonToEntity.toEntityOrUpdateFile(
        test.class,
        "用户信息",
        "{\"no\":{\"0\":\"Interval 1\",\"1\":\"Interval 2\",\"2\":\"Interval 3\",\"3\":\"Interval 4\",\"4\":\"Interval 5\",\"5\":\"Interval 6\",\"6\":\"Interval 7\"},\"StartDepth\":{\"0\":0,\"1\":1940,\"2\":2046,\"3\":2376,\"4\":4443,\"5\":6706,\"6\":6753},\"EndDepth\":{\"0\":1940,\"1\":2046,\"2\":2376,\"3\":4443,\"4\":6706,\"5\":6753,\"6\":6942},\"PIPE_1OD\":{\"0\":7.0,\"1\":4.5,\"2\":4.5,\"3\":4.5,\"4\":4.5,\"5\":3.5,\"6\":7.0},\"PIPE_1NomThkin\":{\"0\":0.362,\"1\":0.271,\"2\":0.271,\"3\":0.271,\"4\":0.271,\"5\":0.254,\"6\":0.362},\"PIPE_1LbFt\":{\"0\":26.0,\"1\":12.6,\"2\":12.6,\"3\":12.6,\"4\":12.6,\"5\":9.3,\"6\":26.0},\"PIPE_1Type\":{\"0\":\"Tubing\",\"1\":\"Tubing\",\"2\":\"Tubing\",\"3\":\"Tubing\",\"4\":\"Tubing\",\"5\":\"Tubing\",\"6\":\"Liner\"},\"PIPE_2OD\":{\"0\":9.625,\"1\":9.625,\"2\":7.0,\"3\":7.0,\"4\":7.0,\"5\":7.0,\"6\":null},\"PIPE_2NomThkin\":{\"0\":0.395,\"1\":0.395,\"2\":0.362,\"3\":0.362,\"4\":0.362,\"5\":0.362,\"6\":null},\"PIPE_2LbFt\":{\"0\":40.0,\"1\":40.0,\"2\":26.0,\"3\":26.0,\"4\":26.0,\"5\":26.0,\"6\":null},\"PIPE_2Type\":{\"0\":\"Casing1\",\"1\":\"Casing1\",\"2\":\"Liner\",\"3\":\"Liner\",\"4\":\"Liner\",\"5\":\"Liner\",\"6\":null},\"PIPE_3OD\":{\"0\":13.375,\"1\":13.375,\"2\":9.625,\"3\":9.625,\"4\":null,\"5\":null,\"6\":null},\"PIPE_3NomThkin\":{\"0\":0.43,\"1\":0.43,\"2\":0.395,\"3\":0.395,\"4\":null,\"5\":null,\"6\":null},\"PIPE_3LbFt\":{\"0\":61.0,\"1\":61.0,\"2\":40.0,\"3\":40.0,\"4\":null,\"5\":null,\"6\":null},\"PIPE_3Type\":{\"0\":\"Casing2\",\"1\":\"Casing2\",\"2\":\"Casing1\",\"3\":\"Casing1\",\"4\":null,\"5\":null,\"6\":null},\"PIPE_1ThkChannel\":{\"0\":\"ADEC[10]\",\"1\":\"ADEC[10]\",\"2\":\"ADEC[10]\",\"3\":\"ADEC[10]\",\"4\":\"ADEC[12]\",\"5\":\"ADEC[12]\",\"6\":\"ADEC[26]\"},\"PIPE_2ThkChannel\":{\"0\":\"ADEC[32]\",\"1\":\"ADEC[32]\",\"2\":\"ADEC[32]\",\"3\":\"ADEC[32]\",\"4\":\"ADEC[36]\",\"5\":\"ADEC[36]\",\"6\":null},\"PIPE_3ThkChannel\":{\"0\":\"ADEC[52]\",\"1\":\"ADEC[52]\",\"2\":\"ADEC[52]\",\"3\":\"ADEC[52]\",\"4\":null,\"5\":null,\"6\":null},\"PIPE_1ChannelContrast\":{\"0\":0.2,\"1\":0.2,\"2\":0.2,\"3\":0.2,\"4\":0.2,\"5\":0.2,\"6\":0.2},\"PIPE_2ChannelContrast\":{\"0\":0.2,\"1\":0.2,\"2\":0.2,\"3\":0.2,\"4\":0.2,\"5\":0.2,\"6\":null},\"PIPE_3ChannelContrast\":{\"0\":0.2,\"1\":0.2,\"2\":0.2,\"3\":0.2,\"4\":null,\"5\":null,\"6\":null},\"PIPE_1Offset\":{\"0\":0,\"1\":0,\"2\":0,\"3\":0,\"4\":0,\"5\":0,\"6\":0},\"PIPE_2Offset\":{\"0\":0.0,\"1\":0.0,\"2\":0.0,\"3\":0.0,\"4\":0.0,\"5\":0.0,\"6\":null},\"PIPE_3Offset\":{\"0\":0.0,\"1\":0.0,\"2\":0.0,\"3\":0.0,\"4\":null,\"5\":null,\"6\":null},\"PIPE_4OD\":{\"0\":null,\"1\":null,\"2\":13.375,\"3\":null,\"4\":null,\"5\":null,\"6\":null},\"PIPE_4NomThkin\":{\"0\":null,\"1\":null,\"2\":0.43,\"3\":null,\"4\":null,\"5\":null,\"6\":null},\"PIPE_4LbFt\":{\"0\":null,\"1\":null,\"2\":61.0,\"3\":null,\"4\":null,\"5\":null,\"6\":null},\"PIPE_4Type\":{\"0\":null,\"1\":null,\"2\":\"Casing2\",\"3\":null,\"4\":null,\"5\":null,\"6\":null}}");
    }
}

