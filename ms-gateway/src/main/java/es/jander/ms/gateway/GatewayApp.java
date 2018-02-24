package es.jander.ms.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class GatewayApp
{
    public static void main(String[] args)
    {
        new SpringApplication(GatewayApp.class).run();
    }
}
