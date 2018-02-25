package es.jander.ms.vertx;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VertxLogger extends AbstractVerticle
{
    private static final Logger log = LoggerFactory.getLogger(VertxLogger.class);

    @Override
    public void start() throws Exception
    {
        super.start();
        vertx.eventBus().<String>consumer(VertxApp.TOPIC).handler(x -> {
            log.info("Received body: '{}'", x.body());
            x.reply("{\"status\":\"success on VertxLogger\"}");
        });

        vertx.eventBus().consumer(VertxApp.TOPIC_BROADCAST, x -> {
            log.info("Got published content [1]: {}", x.body());
        });

        vertx.eventBus().consumer(VertxApp.TOPIC_BROADCAST, x -> {
            log.info("Got published content [2]: {}", x.body());
        });
    }
}
