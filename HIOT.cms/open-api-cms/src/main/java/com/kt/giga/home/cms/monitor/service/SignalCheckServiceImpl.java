package com.kt.giga.home.cms.monitor.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kt.giga.home.cms.monitor.dao.SignalCheckDao;

@Service
public class SignalCheckServiceImpl implements SignalCheckService{
	@Autowired
	private SignalCheckDao signalCheckDao;
	
	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return signalCheckDao.getList(searchInfo);
	}
	
	@Override
	public List<Map<String, Object>> getListExcel(List<String> macAddrList) {
		return signalCheckDao.getListExcel(macAddrList);
	}
    
    @Override
    public List<Map<String, Object>> getManagerList(Map<String, Object> searchInfo) {
        return signalCheckDao.getManagerList(searchInfo);
    }
    
    @Override
    public List<Map<String, Object>> getDeviceList(Map<String, Object> searchInfo) {
        return signalCheckDao.getDeviceList(searchInfo);
    }
}
