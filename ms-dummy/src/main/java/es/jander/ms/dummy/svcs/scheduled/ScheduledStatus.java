package es.jander.ms.dummy.svcs.scheduled;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledStatus
{
    private int currentValue;
    private Long lastUpdate;
}
