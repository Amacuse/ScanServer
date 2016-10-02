package com.Service.Interface;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

public interface HeaderService {
    HttpHeaders buildPath(UriComponentsBuilder builder, Long... id);
}
