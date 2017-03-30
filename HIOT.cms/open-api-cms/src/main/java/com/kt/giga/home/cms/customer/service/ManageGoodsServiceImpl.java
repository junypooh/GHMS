package com.kt.giga.home.cms.customer.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.customer.dao.ManageGoodsDao;

@Service
public class ManageGoodsServiceImpl implements ManageGoodsService {

	@Autowired
	private ManageGoodsDao manageGoodsDao;
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return manageGoodsDao.getCount(searchInfo);
	}
	
	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return manageGoodsDao.get(searchInfo);
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return manageGoodsDao.getList(searchInfo);
	}
	
	@Override
	public void remove(Map<String, Object> manageGoods) {
		manageGoodsDao.remove(manageGoods);
	}

	@Override
	public void register(Map<String, Object> manageGoods) {
		manageGoodsDao.register(manageGoods);
	}

	@Override
	public void modify(Map<String, Object> manageGoods) {
		manageGoods.put("pk", Integer.valueOf(manageGoods.get("pk").toString()));
		manageGoodsDao.modify(manageGoods);
	}
	
	@Override
	public int checkManageGoodsFormData(Map<String, Object> searchInfo){
		if(searchInfo.get("pk") != null) searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return manageGoodsDao.checkManageGoodsFormData(searchInfo);
	}
	

}
