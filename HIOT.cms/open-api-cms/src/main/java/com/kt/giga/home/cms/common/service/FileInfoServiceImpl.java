package com.kt.giga.home.cms.common.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.dao.*;
import com.kt.giga.home.cms.common.domain.*;

@Service
public class FileInfoServiceImpl implements FileInfoService {
	
	@Autowired
	private FileInfoDao fileInfoDao;

	@Override
	public FileInfo get(FileInfo fileInfo) {
		return fileInfoDao.get(fileInfo);
	}

	@Override
	public List<FileInfo> getList(FileInfo fileInfo) {
		return fileInfoDao.getList(fileInfo);
	}

	@Override
	public void register(FileInfo fileInfo) {
		fileInfoDao.register(fileInfo);
	}

	@Override
	public void remove(FileInfo fileInfo) {
		fileInfoDao.remove(fileInfo);
	}

}
