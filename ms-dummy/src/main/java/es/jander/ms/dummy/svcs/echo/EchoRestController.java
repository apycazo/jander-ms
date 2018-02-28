package es.jander.ms.dummy.svcs.echo;

import es.jander.ms.dummy.data.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("echo")
public class EchoRestController
{
    @Autowired
    private EchoService echoService;

    @GetMapping("{content}")
    public SimpleResponse<String> echoFromPath (@PathVariable String content)
    {
        return echoService.generateSimpleResponse(content);
    }

    @GetMapping(params = { "content" })
    public SimpleResponse<String> echoFromParam (@RequestParam String content)
    {
        return echoService.generateSimpleResponse(content);
    }

    @PostMapping
    public SimpleResponse<String> echoFromBody (@RequestBody String content)
    {
        return echoService.generateSimpleResponse(content);
    }

    // -------------------------------------------------------------------------
    // Asynchronous versions
    // -------------------------------------------------------------------------

    @GetMapping(path = "{content}", params = { "delay" })
    public SimpleResponse<String> echoDelayedFromPath (
            @PathVariable String content,
            @RequestParam int delay) throws InterruptedException, ExecutionException
    {
        return echoService.generateSimpleResponse(content, delay).get();
    }

    @GetMapping(params = { "content", "delay" })
    public SimpleResponse<String> echoDelayedFromParam (
            @RequestParam String content,
            @RequestParam int delay) throws InterruptedException, ExecutionException
    {
        return echoService.generateSimpleResponse(content, delay).get();
    }

    @PostMapping(params = { "delay" })
    public SimpleResponse<String> echoDelayedFromBody (
            @RequestBody String content,
            @RequestParam int delay) throws InterruptedException, ExecutionException
    {
        return echoService.generateSimpleResponse(content, delay).get();
    }

}
