package com.bingo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @Author: lemon
 * @Date: 2018/10/23 6:09 PM
 */

public class Util {
    public static final String URL_PREFIX = "http://www.test.com";
    public static final SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
    public static final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Logger logger = LoggerFactory.getLogger(Util.class);

    /**
     * Created with Kaylina
     * Time: 2017-05-14 20:54
     * Description: 解析原始日志的第三号参数 客户端请求信息
     */
    public static Map<String, String> getUrlParamsMap(String url) throws Exception {

        Map<String, String> paramsMap = new LinkedHashMap<>();
        URL orgUrl = new URL(URL_PREFIX + url);
        String orgParams = orgUrl.getQuery();
        String[] orgParamsArr = orgParams.split("&");
        for (int i = 0; i < orgParamsArr.length; i++) {
            String paramPair = orgParamsArr[i];
            int splitLocation = paramPair.indexOf("=");
            if (splitLocation <= 0) {
                continue;
            }

            String keyField = paramPair.substring(0, splitLocation);
            String valueField = paramPair.substring(splitLocation + 1);
            paramsMap.put(keyField, URLDecoder.decode(valueField, "UTF-8").replaceAll("\r|\n|\t", " ").trim());

        }

        return paramsMap;
    }

    /**
     * Created with Kaylina
     * Time: 2017/5/15 11:29
     * Description: 处理时间格式
     */
    public static String handleTimeStr(String timeStr) {
        try {
            return f.format(format.parse(timeStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Created with Kaylina
     * Time: 2017/5/15 13:12
     * Description: 为空转换为 -
     * 不为空 不区分大小写 转换为正常参数
     */
    public static String strHandle(String str) {
        if (PubMethod.isEmpty(str)) {
            str = "-";
        } else {
            str = str.replaceAll("\r|\n|\t", "");
        }
        return str;
    }

    /**
     * Created with Kaylina
     * Time: 2017/5/15 15:46
     * Description: map转string
     */
    public static String transMapToString(Map map) {
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("=").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? ";" : "");
        }
        return sb.toString();
    }

}
