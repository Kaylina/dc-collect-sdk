package com.bingo.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Author: lemon
 * @Date: 2018/10/30 4:14 PM
 */

@Service
public class HandleService implements ApplicationRunner {

    /**
     * @author lemon
     * @create 2018/10/30 4:17 PM
     * @desc 解析日志并推入es
     */
    public void putToEs() {
        MyConsumer.handle();
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
        putToEs();

    }
}
