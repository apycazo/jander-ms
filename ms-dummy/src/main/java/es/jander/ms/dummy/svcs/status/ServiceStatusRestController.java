package es.jander.ms.dummy.svcs.status;

import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("status")
public class ServiceStatusRestController
{
    @Autowired
    private CustomHealthStatus customHealthStatus;

    @GetMapping
    public StatusInfo getStatus ()
    {
        return new StatusInfo(customHealthStatus.getStatus(null));
    }

    @PutMapping("{newStatus}")
    public StatusInfo setStatus (@PathVariable InstanceInfo.InstanceStatus newStatus)
    {
        log.info("Setting status to {}", newStatus.name());
        customHealthStatus.setInstanceStatus(newStatus);
        return getStatus();
    }
}
