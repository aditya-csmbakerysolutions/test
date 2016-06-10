package com.csmbs.exceptions;

import java.io.StringWriter;

import org.apache.commons.lang.exception.ExceptionUtils;


public class CSMBSCustomException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	private String errorStackTrace; 

	public CSMBSCustomException() {}

    public CSMBSCustomException(Exception e)
    {
       super(e.getMessage());
       
       //set error stack trace to variable.
       StringWriter sw = new StringWriter();
      String[] rootCauseArr =  ExceptionUtils.getRootCauseStackTrace(e);
      for(String err:rootCauseArr){
    	  sw.append(err);
      }
      errorStackTrace = sw.toString();
      
    }

	public String getErrorStackTrace() {
		return errorStackTrace;
	}
    
}
