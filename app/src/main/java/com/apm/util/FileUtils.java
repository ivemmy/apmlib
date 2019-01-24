package com.apm.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class FileUtils {
    /**
     * 获取对象的字符串描述信息
     *
     * @param source 要获取信息的对象
     * @return 如果对象为null就返回空字符串，否则返回toString信息
     */
    public static String getString(Object source) {
        if (source == null) {
            return "";
        } else {
            return source.toString();
        }
    }


    /**
     * 关闭字符输出流，如果关闭过程中出现异常再次尝试关闭输出流
     *
     * @param os 输出流
     */
    public static void close(OutputStream os) {
        if (os != null) {
            try {
                os.flush(); // 强制刷新将内容输出到文件
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 进行配置文件读取操作
     */
    public static void config() {
        Properties properties = new Properties();
        try {
            properties.load(FileUtils.class.getResourceAsStream(//
                    "/assets/amp-config.properties"));

            Config config = new Config();
            Field[] fields = config.getClass().getFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                if (properties.containsKey(name)) {
                    String value = properties.getProperty(name);

                    if (value != null && !value.trim().equals("")) {
                        try {
                            // 如果字符串
                            if (field.getType().getName().equals("java.lang.String")) {
                                field.set(config, value);

                            }
                            // 如果是长整数
                            else if (field.getType().getName().equals("long")) {
                                field.setLong(config, Long.parseLong(value));
                            }
                            // 如果是整数
                            else if (field.getType().getName().equals("int")) {
                                field.setInt(config, Integer.parseInt(value));
                            }
                            // 如果是布尔值
                            else if (field.getType().getName().equals("boolean")) {
                                field.setBoolean(config, Boolean.parseBoolean(value));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
