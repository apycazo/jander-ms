package es.jander.ms.dummy.svcs.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("quotes-svc")
public interface QuotesClient
{
    @GetMapping("/quotes")
    List<String> getQuotes ();
}
