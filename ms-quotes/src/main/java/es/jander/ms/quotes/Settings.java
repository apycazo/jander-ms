package es.jander.ms.quotes;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "quotes")
public class Settings
{
    private String testString = "default";
}
