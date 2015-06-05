package org.ametiste.notification;

/**
 * Created by atlantis on 5/27/15.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration(exclude={ElasticsearchDataAutoConfiguration.class})
@ComponentScan
@ImportResource({"classpath:spring/app-config.xml"})
public class Boot {
    public static void main(String[] args) {

        SpringApplication.run(Boot.class, args);

    }
}
