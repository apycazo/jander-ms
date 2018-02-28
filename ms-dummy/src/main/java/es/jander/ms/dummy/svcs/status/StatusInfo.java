package es.jander.ms.dummy.svcs.status;

import com.netflix.appinfo.InstanceInfo;
import lombok.Data;

@Data
public class StatusInfo
{
    private InstanceInfo.InstanceStatus status;
    private InstanceInfo.InstanceStatus [] allowedValues;

    public StatusInfo (InstanceInfo.InstanceStatus status)
    {
        this.status = status;
        allowedValues = new InstanceInfo.InstanceStatus [] {
                InstanceInfo.InstanceStatus.UNKNOWN,
                InstanceInfo.InstanceStatus.STARTING,
                InstanceInfo.InstanceStatus.UP,
                InstanceInfo.InstanceStatus.DOWN,
                InstanceInfo.InstanceStatus.OUT_OF_SERVICE,
        };
    }
}
