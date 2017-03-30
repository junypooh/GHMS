package com.kt.giga.home.openapi.cms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kt.giga.home.openapi.cms.dao.CodeDao;
import com.kt.giga.home.openapi.cms.dao.HomesvcPropsDao;
import com.kt.giga.home.openapi.cms.dao.NoticePmDao;
import com.kt.giga.home.openapi.cms.domain.Code;
import com.kt.giga.home.openapi.cms.domain.HomesvcProps;
import com.kt.giga.home.openapi.cms.domain.NoticePm;

/**
 * 앱초기화 테이블 스케쥴
 * 
 * @author 조상현
 *
 */

@Service
public class ScheduleService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CodeDao codeDao;
	
	@Autowired
	private NoticePmDao noticePmDao;
	
	@Autowired
	private HomesvcPropsDao homesvcPropsDao;
	
	SimpleDateFormat df 	= new SimpleDateFormat("yyyyMMddhhHHss");

//	@Scheduled(cron = "0 50 17 * * *")
	@Scheduled(fixedDelay=300000) //60초x5
	public void schedule(){
		syncNoticePm("001");
		syncNoticePm("002");
		syncNoticePm("003");
	}
	
	public void syncNoticePm(String cpCode){
	    Date date 				= new Date();
	    String now 				= df.format(date);

	    Map<String, Object> map	= new HashMap<String, Object> ();
		map.put("now"			, now);	
		map.put("cpCode"		, cpCode);	
//		System.out.println("======================= syncNoticePm()" + now);
		
		Code code				= codeDao.getCode("1406");	//PM공지 url 코드 : 1406
		try {
			HomesvcProps homesvcProps 			= new HomesvcProps();
			homesvcProps.setSvccode( cpCode 	);

			NoticePm noticePm		= noticePmDao.getNoticePmNow(map);
			if(noticePm != null){
				
				homesvcProps.setName(	"init.noticeOption"				);
				homesvcProps.setValue(	"1"	);
				homesvcPropsDao.updateHomesvcProps(homesvcProps);
				
				homesvcProps.setName(	"init.noticeUrl"				);
				homesvcProps.setValue(	code.getFd_name() +"?uid="+ noticePm.getPk_notice_pm()	);// PM공지 url
				homesvcPropsDao.updateHomesvcProps(homesvcProps);
				
			}else{

				homesvcProps.setName(	"init.noticeOption"				);
				homesvcProps.setValue(	"0"	);
				homesvcPropsDao.updateHomesvcProps(homesvcProps);
				
				homesvcProps.setName(	"init.noticeUrl"				);
				homesvcProps.setValue(	""	);// PM공지 url
				homesvcPropsDao.updateHomesvcProps(homesvcProps);
				
			}
		} catch(DataAccessException e) {

			log.error(e.getMessage(), e);
		}
	}
	
}
