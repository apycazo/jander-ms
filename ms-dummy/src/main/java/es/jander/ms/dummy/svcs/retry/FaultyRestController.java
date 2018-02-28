package es.jander.ms.dummy.svcs.retry;

import es.jander.ms.dummy.data.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("faulty")
public class FaultyRestController
{
    @Autowired
    private FaultyService faultyService;

    @GetMapping("unsafe")
    public SimpleResponse<String> faultyRequest ()
    {
        return faultyService.tryToDoSomething(false);
    }

    @GetMapping("safe")
    public SimpleResponse<String> safeRequest ()
    {
        return faultyService.safeRequest(false);
    }

    @GetMapping("recovery")
    public SimpleResponse<String> recoveryRequest ()
    {
        return faultyService.safeRequest(true);
    }
}
