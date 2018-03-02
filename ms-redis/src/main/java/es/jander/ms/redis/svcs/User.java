package es.jander.ms.redis.svcs;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@RedisHash
public class User implements Serializable
{
    private int id;
    private String name;
}
