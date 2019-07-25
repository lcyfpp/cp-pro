package com.linkedaim.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableTransactionManagement
@tk.mybatis.spring.annotation.MapperScan("com.linkedaim.admin.system.mapper")
@EnableCaching
public class CpAdminApplication {

    private static Logger log = LoggerFactory.getLogger(CpAdminApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CpAdminApplication.class, args);
        log.info("=========== cp-admin started up successfully at {} {} ==========", LocalDate.now(), LocalTime.now());
    }
}

