package es.jander.ms.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;
import java.time.Instant;

@SpringBootApplication
public class VertxApp
{
    private static final Logger log = LoggerFactory.getLogger(VertxApp.class);
    public static final String TOPIC = "testTopic";
    public static final String TOPIC_BROADCAST = "topicForBroadcasting";

    @Autowired
    private VertxReceiver vertxReceiver;
    @Autowired
    private VertxConsumer vertxConsumer;
    @Autowired
    private VertxLogger vertxLogger;

    public static void main(String[] args)
    {
        new SpringApplicationBuilder(VertxApp.class).web(false).build().run();
    }

    @PostConstruct
    private void initVertx ()
    {
        log.info("Deploying verticle");
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(vertxReceiver);
        vertx.deployVerticle(vertxConsumer);
        vertx.deployVerticle(vertxLogger);
    }

    public static class Verticle extends AbstractVerticle
    {
        @Override
        public void start(Future<Void> future) {
            vertx
                    .createHttpServer()
                    .requestHandler(r -> {
                        String body = String.format("<h1>Date: %d</h1>", Instant.now().toEpochMilli());
                        r.response().end(body);
                    })
                    .listen(8093, result -> {
                        if (result.succeeded()) {
                            log.info("Server up");
                            future.complete();
                        } else {
                            log.info("Server failed");
                            future.fail(result.cause());
                        }
                    });
        }
    }

}
