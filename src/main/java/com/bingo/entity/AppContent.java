package com.bingo.entity;

import lombok.Data;

/**
 * @Author: lemon
 * @Date: 2018/10/24 11:27 AM
 */

@Data
public class AppContent {

    private String logId;
    private String description;
    private String action_type;
    private String action_time;
    private String content;
    private String refer;
    private String ua;
    private String ip;
    //登陆
    private String mobile_login;
    private String WeChat_login;
    private String Ali_login;
    private String Binding_mobile;
    //首页
    private String My_center;
    private String home_search;
    private String home_Scan;
    private String home_banner;
    private String home_refreshBtn;
    private String home_circum;
    private String home_map;
    private String home_productListBtn;
    private String home_promotionItem_more;
    private String home_SellingGoods_more;
    private String home_openBtn;
    private String home_partner;
    private String home_bottomAD;
    //搜索
    private String search_partner;
    //地图详情
    private String map_goods;
    private String map_box;
    //确认订单
    private String confirm_goodsMore_Btn;
    private String confirm_VIP;
    private String confirm_paymentBtn;
    private String confirm_coinONOFF;
    private String confirm_discounts;
    private String order_cancelOrder;
    private String confirm_coupon;
    //支付成功
    private String success_ViewOrder;
    private String success_backHomepage;
    //盒内商品列表
    private String box_promotionGoods;
    private String box_SellingGoods;
    private String box_search;
    //个人中心
    private String My_oders;
    private String My_unfinishedOrdersBtn;
    private String My_VIPrenew;
    private String My_VIP;
    private String My_setting;
    private String My_VIP_daily;
    private String messageCenter_notification;
    private String messageCenter_service;
    private String messageCenter_FAQ;
    private String My_face;


}
