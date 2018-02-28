package es.jander.ms.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.Executor;

@SpringBootApplication
public class DummyApp
{
    public static void main(String[] args)
    {
        new SpringApplicationBuilder(DummyApp.class).build().run();
    }

    @Configuration
    private static class MvcConfig extends WebMvcConfigurerAdapter
    {
        public MvcConfig ()
        {
            super();
        }

        @Override
        public void configureContentNegotiation(ContentNegotiationConfigurer config)
        {
            config
                    // path extensions will always be used when present
                    .favorPathExtension(true)
                    // allow us to define media types manually
                    .useJaf(false)
                    // Note that a browser might send a default xml header, which would override this
                    .defaultContentType(MediaType.APPLICATION_JSON)
                    // Extensions we allow to use (note that using xml requires dependency on 'jackson-dataformat-xml')
                    .mediaType("text", MediaType.TEXT_PLAIN)
                    .mediaType("xml", MediaType.APPLICATION_XML)
                    .mediaType("json", MediaType.APPLICATION_JSON);
        }
    }

    @Configuration
    @EnableAsync
    public class AsyncConfig extends AsyncConfigurerSupport
    {
        @Autowired
        private Settings settings;

        @Override
        public Executor getAsyncExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(settings.getPool().getCore());
            executor.setMaxPoolSize(settings.getPool().getMax());
            executor.setQueueCapacity(settings.getPool().getQueue());
            executor.setThreadNamePrefix("async-");
            executor.initialize();
            return executor;
        }
    }

    @Component
    public class PortListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent>
    {
        @Autowired
        private Settings settings;

        @Override
        public void onApplicationEvent(final EmbeddedServletContainerInitializedEvent event)
        {
            settings.setServicePort(event.getEmbeddedServletContainer().getPort());
        }
    }

}
