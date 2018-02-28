package es.jander.ms.dummy.svcs.echo;

import es.jander.ms.dummy.Settings;
import es.jander.ms.dummy.data.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@Service
@EnableAsync
public class EchoService
{
    @Autowired
    private Settings settings;

    public SimpleResponse<String> generateSimpleResponse(String content)
    {
        log.info("Generated response.");
        return SimpleResponse.<String>builder()
                .response(content)
                .source("dummy-" + settings.getServicePort())
                .build();
    }

    @Async
    public Future<SimpleResponse<String>> generateSimpleResponse (String content, int delay) throws InterruptedException
    {
        if (delay > 0) {
            log.info("Waiting {} milliseconds", delay);
            Thread.sleep(delay);
        }

        return new AsyncResult<>(generateSimpleResponse(content));
    }

}
