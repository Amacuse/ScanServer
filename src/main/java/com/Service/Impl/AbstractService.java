package com.Service.Impl;

import com.Exception.ResourceNotFoundException;
import com.Service.Interface.HeaderService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Locale;

public abstract class AbstractService<T> implements HeaderService {

    @Resource
    protected MessageSource ms;

    @Override
    public HttpHeaders buildPath(UriComponentsBuilder builder, Long... id) {
        UriComponents uriComponents = builder.path(setPath()).buildAndExpand(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Template method
     */
    protected abstract String setPath();

    protected void isValid(T t, Long id) {
        if (t == null) {
            throw new ResourceNotFoundException(
                    ms.getMessage("exception.resourceNotFound", new Object[]{id}, getLocale()));
        }
    }

    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
