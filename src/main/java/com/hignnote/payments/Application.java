package com.hignnote.payments;

import com.hignnote.payments.processor.Orchestrator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.util.ObjectUtils;

@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Application implements CommandLineRunner {

    @Autowired
    private Orchestrator orchestrator;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       try {
           if (ObjectUtils.isEmpty(args)) {
               log.error("Input file required to authorize card transaction.");
               return;
           }
           orchestrator.start(args[0]);
       }catch (Exception exception){
           log.error("Exception occurred {}",exception.getMessage() );
       }finally {
           System.exit(0);
       }
    }
}
