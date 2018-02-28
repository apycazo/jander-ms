package es.jander.ms.dummy.svcs.feign;

import feign.Headers;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "httpbin", url = "https://httpbin.org/")
public interface HttpBinClient
{
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @Headers("Content-Type: application/json") // only to show how to send headers
    String getRequestInfo (@RequestParam(value = "param") String param);

    @RequestMapping(value = "post", method = RequestMethod.POST)
    @Headers("Content-Type: application/json")
    String sendRequestInfo(@RequestBody String content);
}
