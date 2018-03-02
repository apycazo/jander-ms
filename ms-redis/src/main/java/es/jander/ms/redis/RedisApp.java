package es.jander.ms.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class RedisApp
{
    public static void main (String [] args)
    {
        new SpringApplication(RedisApp.class).run();
    }


}
