package com.bloomberg.fxdeals.exception;

public class DuplicateDealException extends RuntimeException {
  public DuplicateDealException(String message) {
    super(message);
  }
}