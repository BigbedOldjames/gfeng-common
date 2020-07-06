package com.omysoft.common.exception;

public class BusinessException extends RuntimeException{
	/** 
     *  
     */  
    private static final long serialVersionUID = -4788951533205831941L;  
  
    public BusinessException() {  
        super();  
    }  
  
    public BusinessException(String message) {  
        super(message);  
    }  
  
    public BusinessException(String message, Throwable cause) {  
        super(message, cause);  
    }  
  
    public BusinessException(Throwable cause) {  
        super(cause);  
    } 
}
