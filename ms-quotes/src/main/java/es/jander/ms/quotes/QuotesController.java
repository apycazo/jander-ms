package es.jander.ms.quotes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class QuotesController
{
    public static final Logger log = LoggerFactory.getLogger(QuotesController.class);

    @Autowired
    private QuotesService quotesService;
    @Autowired
    private Settings settings;

    @PostConstruct
    private void init ()
    {
        quotesService.init();
    }

    @GetMapping("/quotes")
    public List<String> getQuotes ()
    {
        return quotesService.getQuotes();
    }

    @GetMapping(path = "/testString")
    public String testString ()
    {
        return String.format("{ \"value\": \"%s\"}", settings.getTestString());
    }

    @GetMapping("/quotes/{index}")
    public ResponseEntity<String> getQuote (@PathVariable int index)
    {
        String quote = quotesService.getQuote(index);
        if (quote == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(quote);
        }
    }
}
