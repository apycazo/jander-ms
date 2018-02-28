package es.jander.ms.dummy.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleResponse<T>
{
    private T response;
    @Builder.Default
    private Long timestamp = Instant.now().toEpochMilli();
    @Builder.Default
    private String source = null;
}
