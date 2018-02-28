package es.jander.ms.dummy.svcs.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class ScheduledService
{
    private ScheduledStatus status = new ScheduledStatus(0, Instant.now().getEpochSecond());

    public ScheduledStatus getStatus()
    {
        return status;
    }

    @Scheduled(fixedRate = 10_000, initialDelay = 10_000)
    private void updateStatus()
    {
        int currentValue = status.getCurrentValue();
        status.setCurrentValue(currentValue + 1);
        Long now = Instant.now().getEpochSecond();
        status.setLastUpdate(now);

        log.debug("Scheduler has run an update");
    }
}
