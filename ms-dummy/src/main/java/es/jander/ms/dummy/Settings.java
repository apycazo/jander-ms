package es.jander.ms.dummy;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "dummy")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Settings
{
    private String id;
    private int servicePort = 0;
    private String version = "default";
    private boolean global = false;

    @Data
    public static class SettingsPoolConfig
    {
        private int core = 8;
        private int max = 16;
        private int queue = 100;
    }

    private SettingsPoolConfig pool = new SettingsPoolConfig();

    @PostConstruct
    private void logProperties ()
    {
        log.info("{} Configuration: {}", DummyApp.class.getSimpleName(), this);
    }
}
