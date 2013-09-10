package com.yuan.common.brpc;

import java.io.Serializable;

public class InvocationResponse implements Serializable {

	private static final long serialVersionUID = 1L;

    private Throwable exception;
    private Serializable result;

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public Serializable getResult() {
        return result;
    }

    public void setResult(Serializable result) {
        this.result = result;
    }

}
