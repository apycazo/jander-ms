package es.jander.ms.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class VertxReceiver extends AbstractVerticle
{
    private static final Logger log = LoggerFactory.getLogger(VertxReceiver.class);

    @Value("${server.port:8080}")
    private int port;
    @Value("${spring.application.name:unknown}")
    private String applicationName;

    @Override
    public void start() throws Exception
    {
        super.start();
        log.info("Configuring Vertx server to listen at port {} (app: {})", port, applicationName);
        Router router = Router.router(vertx);
        router.get("/ok").handler(this::ok);
        router.get("/about").handler(this::processEvent);
        router.get("/notify").handler(this::publishEvent);
        router.get("/phase").handler(this::phase1);
        router.get("/phase").handler(this::phase2);
        // interceptor works both ways
        vertx.eventBus().addInterceptor(handler -> {
            log.info("Intercepted message: {}", handler.message().body());
            handler.next();
        });
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .exceptionHandler(ex -> log.warn("Captured exception", ex))
                .listen(port);
    }

    private void ok(RoutingContext routingContext)
    {
        routingContext.response().setStatusCode(200).end(Json.encodePrettily("{\"status\":\"ok\"}"));
    }

    private void phase1 (RoutingContext routingContext)
    {
        log.info("Phase 1");
        HttpServerResponse response = routingContext.response();
        // must be set before anything else, because we do not know the body size yet
        response
                .setChunked(true)
                .write("phase 1");
        routingContext.vertx().setTimer(1000, tid -> routingContext.next());
    }

    private void phase2 (RoutingContext routingContext)
    {
        log.info("Phase 2");
        HttpServerResponse response = routingContext.response();

        response
                .write(" phase 2")
                .end();
    }

    private void publishEvent (RoutingContext routingContext)
    {
        String msg = "Event body ts is " + Instant.now().toEpochMilli();
        vertx.eventBus().<String>publish(VertxApp.TOPIC_BROADCAST, msg);
        routingContext.response().end();
    }

    /**
     * This will send a point-2-point event, that will be handled using round robin to choose
     * between VertxConsumer and VertxLogger
     * @param routingContext
     */
    private void processEvent(RoutingContext routingContext)
    {
        String msg = "Event body ts is " + Instant.now().toEpochMilli();
        vertx.eventBus().<String>send(VertxApp.TOPIC, msg,
                result -> {
                    if (result.succeeded()) {
                        routingContext.response()
                                .putHeader("content-type", "application/json")
                                .setStatusCode(200)
                                .end(result.result().body());
                    } else {
                        log.warn("Failure!: {}", result.cause().getMessage());
                        routingContext.response()
                                .setStatusCode(500)
                                .end(result.cause().getMessage());
                    }
                });
    }

}
