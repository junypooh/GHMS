package com.kt.giga.home.cms.common.service;

import java.util.*;

import com.kt.giga.home.cms.common.domain.*;

public interface FileInfoService {
	
	FileInfo get(FileInfo fileInfo);
	
	List<FileInfo> getList(FileInfo fileInfo);
	
	void register(FileInfo fileInfo);
	
	void remove(FileInfo fileInfo);
}
