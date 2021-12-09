package com.example.pizzastore.service.exception;

public abstract class BaseException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private int statusCode;

  public BaseException(int statusCode) {
    this.statusCode = statusCode;
  }

  public BaseException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public BaseException(String message, Throwable cause, int statusCode) {
    super(message, cause);
    this.statusCode = statusCode;
  }

  public BaseException(Throwable cause, int statusCode) {
    super(cause);
    this.statusCode = statusCode;
  }

  public BaseException(String message, Throwable cause, boolean enableSuppression,
                       boolean writableStackTrace, int statusCode) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.statusCode = statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
