package com.kt.giga.home.cms.inquiry.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.inquiry.dao.*;

@Service
public class ProductCusInfoServiceImpl implements ProductCusInfoService {

	@Autowired
	private ProductCusInfoDao productCusInfoDao;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return productCusInfoDao.getCount(searchInfo);
	}
	
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {		
		return productCusInfoDao.getList(searchInfo);
	}
	
	@Override
	public List<Map<String, Object>> getSpotDevInfoList(long mbrSeq) {
		return productCusInfoDao.getSpotDevInfoList(mbrSeq);
	}	
	
	@Override
	public List<Map<String, Object>> getTermlInfoList(long mbrSeq) {
		return productCusInfoDao.getTermlInfoList(mbrSeq);
	}	
}
