package com.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.stereotype.Service;

@Service
public class MyObjectMapper extends ObjectMapper {

    /**
     * For joda time in JSON
     */
    public MyObjectMapper() {
        this.registerModule(new JodaModule());
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
