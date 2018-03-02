package es.jander.ms.core.optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("data-store")
public class DataStoreService
{
    private Map<Integer, ObjectEntry> data = new ConcurrentHashMap<>();
    private AtomicInteger idGenerator = new AtomicInteger(1);

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ObjectEntry
    {
        private int id;
        private String record;
    }

    @PostConstruct
    private void init ()
    {
        saveRecord("Sample record - 1");
        saveRecord("Sample record - 2");
        saveRecord("Sample record - 3");
    }

    @PostMapping
    public ObjectEntry saveRecord (@RequestBody String body)
    {
        int id = idGenerator.getAndIncrement();
        ObjectEntry objectEntry = ObjectEntry.builder().id(id).record(body).build();
        data.put(id, objectEntry);
        return objectEntry;
    }

    @GetMapping
    public Collection<ObjectEntry> findRecords ()
    {
        return data.values();
    }

    @GetMapping("{id}")
    public String findRecordById (@PathVariable int id, HttpServletResponse response) throws IOException
    {
        ObjectEntry objectEntry = data.getOrDefault(id, null);
        if (objectEntry == null) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "ID " + id + " not found");
            return null;
        }

        return objectEntry.getRecord();
    }

    @PutMapping("{id}")
    public String updateRecord(@PathVariable int id, @RequestBody String body, HttpServletResponse response) throws IOException
    {
        ObjectEntry objectEntry = data.getOrDefault(id, null);
        if (objectEntry == null) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "ID " + id + " not found");
            return null;
        } else {
            objectEntry.setRecord(body);
        }

        return objectEntry.getRecord();
    }

    @DeleteMapping("{id}")
    public String deleteRecord(@PathVariable int id,  HttpServletResponse response) throws IOException
    {
        ObjectEntry objectEntry = data.getOrDefault(id, null);
        if (objectEntry == null) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "ID " + id + " not found");
            return null;
        } else {
            data.remove(id);
        }

        return objectEntry.getRecord();
    }
}
