package es.jander.ms.dummy.svcs.status;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomHealthStatus implements HealthCheckHandler
{
    private InstanceStatus currentStatus = null;

    public void setInstanceStatus (InstanceStatus newStatus)
    {
        currentStatus = newStatus;
    }

    @Override
    public InstanceStatus getStatus(InstanceStatus instanceStatus)
    {
        if (instanceStatus != null) {
            // checking current status
            if (currentStatus == null) {
                log.info("Setting current status to {}", instanceStatus);
                currentStatus = instanceStatus;
            } else if (instanceStatus != currentStatus) {
                log.info("Comparing status {} with forced value: {}", instanceStatus, currentStatus);
                instanceStatus = currentStatus;
            }
            return instanceStatus;
        } else if (currentStatus != null) {
            log.info("Returning existing current status: {}", currentStatus);
            return currentStatus;
        } else {
            log.info("Returning default value: DOWN");
            return InstanceStatus.DOWN;
        }
    }
}
