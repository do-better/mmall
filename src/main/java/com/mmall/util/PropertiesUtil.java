package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by yuanli on 2017/9/13.
 */
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;

    //静态代码块里加载mmall.properties文件
    static {
        String fileName = "mmall.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
        } catch (IOException e) {
            logger.error("读取配置文件异常",e);
        }
    }

    public static String getProperty(String key) {
        //这里的trim函数是为了防止配置文件中不小心输入了空格
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue) {
        //这里的trim函数是为了防止配置文件中不小心输入了空格
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)) {
            value= defaultValue;
        }
        return value.trim();
    }
}
