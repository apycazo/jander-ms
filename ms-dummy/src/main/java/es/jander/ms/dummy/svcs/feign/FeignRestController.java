package es.jander.ms.dummy.svcs.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("feign")
public class FeignRestController
{
    @Autowired
    private HttpBinClient httpBinClient;
    @Autowired
    private QuotesClient quotesClient;

    @GetMapping("httpbin")
    public String sendGetRequestToHttpBin ()
    {
        return httpBinClient.getRequestInfo("test:" + Instant.now().toEpochMilli());
    }

    @PostMapping("httpbin")
    public String sendPostRequestToHttpBin ()
    {
        return httpBinClient.sendRequestInfo("test:" + Instant.now().toEpochMilli());
    }

    @GetMapping("quotes")
    public List<String> getQuotes ()
    {
        return quotesClient.getQuotes();
    }
}
