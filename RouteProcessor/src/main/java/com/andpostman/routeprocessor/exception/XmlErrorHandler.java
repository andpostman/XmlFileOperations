package com.andpostman.routeprocessor.exception;

import java.util.ArrayList;
import java.util.List;

public class XmlErrorHandler  {

    private List<Exception> exceptions;

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public XmlErrorHandler() {
        this.exceptions = new ArrayList<>();
    }

    public <T extends Exception> void error(T exception) {
        exceptions.add(exception);
    }
}
