package es.jander.ms.quotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class QuotesApp
{
    public static void main(String[] args)
    {
        new SpringApplication(QuotesApp.class).run();
    }
}
