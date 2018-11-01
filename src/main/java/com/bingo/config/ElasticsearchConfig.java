package com.bingo.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * @Author: lemon
 * @Date: 2018/9/3 上午10:52
 * 初始化es连接
 */

@Configuration
public class ElasticsearchConfig {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    /**
     * elk集群地址
     */
    @Value("${elasticsearch.ip}")
    private String hostName;
    /**
     * 端口
     */
    @Value("${elasticsearch.port}")
    private String port;
    /**
     * 集群名称
     */
    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    /**
     * 连接池
     */
    @Value("${elasticsearch.pool}")
    private String poolSize;

    TransportClient transportClient = null;

    @Bean
    public TransportClient init() {
        try {
            // 配置信息
            Settings esSetting = Settings.builder()
                    .put("cluster.name", clusterName)
                    //增加嗅探机制，找到ES集群
                    //.put("client.transport.sniff", false)  //报错的咋回事这个
                    //增加线程池个数，暂时设为5
                    .put("thread_pool.search.size", Integer.parseInt(poolSize))
                    .build();
            transportClient = new PreBuiltTransportClient(esSetting).addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port)));
            logger.error(hostName + "  " + clusterName);
        } catch (Exception e) {
            logger.error("elasticsearch TransportClient create error!!!", e);
        }

        return transportClient;
    }

}
