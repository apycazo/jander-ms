package es.jander.ms.dummy.svcs.config;

import es.jander.ms.dummy.Settings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("config")
public class ConfigRestController
{
    @Autowired
    private Settings settings;

    @GetMapping
    public String getSettings()
    {
        log.info("Current settings are: {}", settings);
        return settings.toString();
    }
}
