package es.jander.ms.dummy.svcs.error;

import es.jander.ms.dummy.data.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This service demonstrates multiple ways to deal with errors
 */
@Slf4j
@RestController
@RequestMapping("fail")
public class ErrorService
{
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public class TeapotException extends RuntimeException {
        public TeapotException()
        {
            super();
        }

        public TeapotException(String message)
        {
            super(message);
        }
    }

    /**
     * sample return value
     * {
     *      "response": "Entity error",
     *      "timestamp": 1519984611930
     * }
     * @return The entity with the error
     */
    @GetMapping("entity")
    public ResponseEntity<SimpleResponse<String>> failWithResponseEntity ()
    {
        SimpleResponse<String> response = SimpleResponse.<String>builder().response("Entity error").build();
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(response);
    }

    /**
     * Sample return value:
     * {
     *      "timestamp": 1519984726923,
     *      "status": 418,
     *      "error": "I'm a teapot",
     *      "message": "Servlet error",
     *      "path": "/fail/servlet"
     * }
     * @param servletResponse The injected response object.
     * @return Actually nothing, an error will override value.
     * @throws IOException when to response can not be accessed.
     */
    @GetMapping("servlet")
    public SimpleResponse<String> failWithServletResponse (HttpServletResponse servletResponse) throws IOException
    {
        servletResponse.sendError(HttpStatus.I_AM_A_TEAPOT.value(), "Servlet error");
        return null;
    }

    /**
     * Sample return value:
     * {
     *      "timestamp": 1519984775817,
     *      "status": 418,
     *      "error": "I'm a teapot",
     *      "exception": "es.jander.ms.dummy.svcs.error.ErrorService$TeapotException",
     *      "message": "I am a teapot!",
     *      "path": "/fail/exception"
     * }
     * @return nothing, and exception is forced.
     */
    @GetMapping("exception")
    public SimpleResponse<String> failWithCustomException()
    {
        throw new TeapotException("I am a teapot!");
    }

}
