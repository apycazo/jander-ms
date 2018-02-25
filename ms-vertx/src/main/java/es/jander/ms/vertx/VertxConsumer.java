package es.jander.ms.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VertxConsumer extends AbstractVerticle
{
    private static final Logger log = LoggerFactory.getLogger(VertxReceiver.class);

    @Override
    public void start() throws Exception
    {
        super.start();
        vertx.eventBus().<String>consumer(VertxApp.TOPIC).handler(process());
    }

    private Handler<Message<String>> process ()
    {
        log.info("{} Consuming topic", getClass().getSimpleName());
        return msg -> vertx.<String> executeBlocking(future -> {
            String content = "{\"status\":\"success on VertxConsumer\"}";
            future.complete(content);
        }, result -> {
            if (result.succeeded()) {
                msg.reply(result.result());
            } else {
                msg.reply(result.cause().toString());
            }
        });
    }
}
