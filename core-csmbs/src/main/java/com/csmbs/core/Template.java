package com.csmbs.core;

import com.csmbs.exceptions.CSMBSCustomException;
import com.csmbs.vo.BaseVO;

public interface Template {
	
	public BaseVO populate(Object parameters) throws CSMBSCustomException;

}
