package com.csmbs.core;

import java.util.List;

import com.csmbs.exceptions.CSMBSCustomException;
import com.csmbs.vo.BaseVO;


public interface AppService {

	// change to a VO that represents the common listing data.
	public List<? extends BaseVO> getList(Object parameters) throws CSMBSCustomException;

	// change to proper VO for content
	public BaseVO getContent(Object parameters) throws CSMBSCustomException;

	// change to proper VO for content
	public BaseVO insertContent(Object parameters);

	public BaseVO deleteContent(Object parameters);

}
