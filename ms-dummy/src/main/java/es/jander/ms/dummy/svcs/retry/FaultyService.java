package es.jander.ms.dummy.svcs.retry;

import es.jander.ms.dummy.data.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class FaultyService
{
    private AtomicBoolean shouldSucceed = new AtomicBoolean(true);

    @Retryable(
            value = { NullPointerException.class },
            maxAttempts = 2,
            backoff = @Backoff(delay = 500))
    public SimpleResponse<String> safeRequest(boolean forceFailure)
    {
        return tryToDoSomething(forceFailure);
    }

    public SimpleResponse<String> tryToDoSomething(boolean forceFailure)
    {
        if (shouldSucceed.get()) {
            // next try should fail
            shouldSucceed.set(false);
            return SimpleResponse.<String>builder()
                    .source(FaultyService.class.getName())
                    .response("Next time I will fail!")
                    .build();
        } else {
            log.info("Request failed as expected!");
            if (!forceFailure) {
                shouldSucceed.set(true);
            }
            throw new NullPointerException();
        }
    }

    // Note that return values must match the @Retryable method.
    @Recover
    public SimpleResponse<String> onException (NullPointerException ex)
    {
        log.info("Recovering from failure");
        shouldSucceed.set(true);
        // final attempt
        return tryToDoSomething(false);
    }
}
