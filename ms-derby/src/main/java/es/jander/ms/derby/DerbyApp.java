package es.jander.ms.derby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DerbyApp
{
    public static void main(String[] args)
    {
        new SpringApplication(DerbyApp.class).run();
    }
}
