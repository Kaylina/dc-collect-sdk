package com.bingo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bingo.entity.AppContent;
import com.bingo.util.ElasticsearchUtils;
import com.bingo.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;


/**
 * @Author: lemon
 * @Date: 2018/10/23 5:53 PM
 */
@Component
public class LogHandle {

    private static Logger logger = LoggerFactory.getLogger(LogHandle.class);
    public final static String INDEX_APP = "lemon";
    public final static String ES_TYPE = "app";

    /**
     * @author lemon
     * @create 2018/11/1 11:19 AM
     * @desc 日志字段解析
     */
    public static void analyzeLog(String log) throws Exception {
        String[] spiltLogs = log.split(" - ");
        String ip = spiltLogs[0].replaceAll("\"", "");
        // 时间戳
        String t = spiltLogs[2].split("\\[")[1].split("\\]")[0];
        String d = Util.handleTimeStr(t);
        String referer = spiltLogs[6].replaceAll("\"", "");
        // logger.info(referer);
        String url = spiltLogs[3].replaceAll("\"", "");
        //String URL = URLDecoder.decode(url, "UTF-8").replaceAll("\r|\n|\t", " ").trim().substring(11);
        Map<String, String> urlParamsMap = Util.getUrlParamsMap(url);
        String ua = spiltLogs[7].replaceAll("\"", "");
        Set<String> keys = urlParamsMap.keySet();
        for (String key : keys) {
            logger.info(key + " *** " + urlParamsMap.get(key));
        }

        // 解析完成之后给对象赋值
        AppContent appContent = new AppContent();
        appContent.setAction_time(d);
        appContent.setUa(Util.strHandle(ua));
        appContent.setRefer(Util.strHandle(referer));
        appContent.setIp(Util.strHandle(ip));
        appContent.setLogId(Util.strHandle(urlParamsMap.get("logId")));
        appContent.setDescription(Util.strHandle(urlParamsMap.get("description")));
        appContent.setAction_type("click");
        appContent.setContent(Util.strHandle(urlParamsMap.get("content")));
        //登陆
        appContent.setMobile_login(Util.strHandle(urlParamsMap.get("mobile_login")));
        appContent.setWeChat_login(Util.strHandle(urlParamsMap.get("WeChat_login")));
        appContent.setAli_login(Util.strHandle(urlParamsMap.get("Ali_login")));
        appContent.setBinding_mobile(Util.strHandle(urlParamsMap.get("Binding_mobile")));
        //首页
        appContent.setMy_center(Util.strHandle(urlParamsMap.get("My_ center")));
        appContent.setHome_search(Util.strHandle(urlParamsMap.get("home_search")));
        appContent.setHome_Scan(Util.strHandle(urlParamsMap.get("home_Scan")));
        appContent.setHome_banner(Util.strHandle(urlParamsMap.get("home_banner")));
        appContent.setHome_refreshBtn(Util.strHandle(urlParamsMap.get("home_refreshBtn")));
        appContent.setHome_circum(Util.strHandle(urlParamsMap.get("home_circum")));
        appContent.setHome_map(Util.strHandle(urlParamsMap.get("home_map")));
        appContent.setHome_productListBtn(Util.strHandle(urlParamsMap.get("home_productListBtn")));
        appContent.setHome_promotionItem_more(Util.strHandle(urlParamsMap.get("home_promotionItem_more")));
        appContent.setHome_SellingGoods_more(Util.strHandle(urlParamsMap.get("home_SellingGoods_more")));
        appContent.setHome_openBtn(Util.strHandle(urlParamsMap.get("home_openBtn")));
        appContent.setHome_partner(Util.strHandle(urlParamsMap.get("home_partner")));
        appContent.setHome_bottomAD(Util.strHandle(urlParamsMap.get("home_bottomAD")));
        //搜索
        appContent.setSearch_partner(Util.strHandle(urlParamsMap.get("search_partner")));
        //地图详情
        appContent.setMap_goods(Util.strHandle(urlParamsMap.get("map_goods")));
        appContent.setMap_box(Util.strHandle(urlParamsMap.get("map_box")));
        //确认订单
        appContent.setConfirm_goodsMore_Btn(Util.strHandle(urlParamsMap.get("confirm_goodsMore Btn")));
        appContent.setConfirm_VIP(Util.strHandle(urlParamsMap.get("confirm_VIP")));
        appContent.setConfirm_paymentBtn(Util.strHandle(urlParamsMap.get("confirm_paymentBtn")));
        appContent.setConfirm_coinONOFF(Util.strHandle(urlParamsMap.get("confirm_coinONOFF")));
        appContent.setConfirm_discounts(Util.strHandle(urlParamsMap.get("confirm_discounts")));
        appContent.setOrder_cancelOrder(Util.strHandle(urlParamsMap.get("order_cancelOrder")));
        appContent.setConfirm_coupon(Util.strHandle(urlParamsMap.get("confirm_coupon")));
        //支付成功
        appContent.setSuccess_ViewOrder(Util.strHandle(urlParamsMap.get("success_ViewOrder")));
        appContent.setSuccess_backHomepage(Util.strHandle(urlParamsMap.get("success_backHomepage")));
        //盒内商品列表
        appContent.setBox_promotionGoods(Util.strHandle(urlParamsMap.get("box_promotionGoods")));
        appContent.setBox_SellingGoods(Util.strHandle(urlParamsMap.get("box_SellingGoods")));
        appContent.setBox_search(Util.strHandle(urlParamsMap.get("box_ search")));
        //个人中心
        appContent.setMy_oders(Util.strHandle(urlParamsMap.get("My_oders")));
        appContent.setMy_unfinishedOrdersBtn(Util.strHandle(urlParamsMap.get("My_unfinishedOrdersBtn")));
        appContent.setMy_VIPrenew(Util.strHandle(urlParamsMap.get("My_VIPrenew")));
        appContent.setMy_VIP(Util.strHandle(urlParamsMap.get("My_VIP")));
        appContent.setMy_setting(Util.strHandle(urlParamsMap.get("My_setting")));
        appContent.setMy_VIP_daily(Util.strHandle(urlParamsMap.get("My_VIP_daily")));
        appContent.setMessageCenter_notification(Util.strHandle(urlParamsMap.get("messageCenter_notification")));
        appContent.setMessageCenter_service(Util.strHandle(urlParamsMap.get("messageCenter_service")));
        appContent.setMessageCenter_FAQ(Util.strHandle(urlParamsMap.get("messageCenter_FAQ")));
        appContent.setMy_face(Util.strHandle(urlParamsMap.get("My_face")));
        // put to es
        JSONObject data = (JSONObject) JSON.toJSON(appContent);
        ElasticsearchUtils.addData(data, INDEX_APP, ES_TYPE);

    }


}
