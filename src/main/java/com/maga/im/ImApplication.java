package com.maga.im;

import cn.jmessage.api.JMessageClient;
import com.maga.im.constant.SystemConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ImApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImApplication.class, args);
    }

    @Bean
    public JMessageClient getJMessageClient() {
        return new JMessageClient(SystemConstant.APP_KEY, SystemConstant.MASTER_SECRET);
    }
}
