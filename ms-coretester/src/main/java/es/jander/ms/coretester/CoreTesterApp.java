package es.jander.ms.coretester;

import es.jander.ms.core.optional.EnableTestEndpoints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableTestEndpoints
@SpringBootApplication
public class CoreTesterApp
{
    public static void main(String[] args)
    {
        new SpringApplication(CoreTesterApp.class).run();
    }
}
