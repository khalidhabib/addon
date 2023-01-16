package com.quarrio.addon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(HerokuController.HEROKU_URI)
public class HerokuController {

    @Autowired
    ObjectMapper mapper;

    static final String HEROKU_URI = "/heroku";
    static final String RESOURCES_URI = "/resources";

    private static final Logger log = LoggerFactory.getLogger(HerokuController.class);


    @PostMapping(RESOURCES_URI)
    public @ResponseBody Map<String, Object> provision(
        @RequestBody Map<String, Object> request
    ) throws IOException, InterruptedException {
        log.info("Incoming Heroku request:\n{}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));
        return Map.of(
                "id",request.get("uuid"),
                "message","addon has been created and is available",
                "config",Map.of("VAR1","VAL1"));
    }

    @PutMapping(RESOURCES_URI + "/{resource_uuid}")
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public @ResponseBody Map<String, String> planChange(
        @PathVariable("resource_uuid") String resourceUuid, @RequestBody Map<String, String> planRequest
    ) {
        return Map.of("message", "plan change not supported");
    }

    @DeleteMapping(RESOURCES_URI + "/{resource_uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deprovision(@PathVariable("resource_uuid") String resourceUuid) {
        log.error("Delete request received for heroku resource: {}. Delete not supported yet.", resourceUuid);
    }
}