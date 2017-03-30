package com.kt.giga.home.cms.manage.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.common.domain.FileInfo;
import com.kt.giga.home.cms.common.service.FileInfoService;
import com.kt.giga.home.cms.common.service.OpenAPICallService;
import com.kt.giga.home.cms.manage.dao.*;
//
@Service
public class VerAppServiceImpl implements VerAppService {

	@Autowired
	private VerAppDao verAppDao;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String separator = System.getProperty("file.separator");
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return verAppDao.getCount(searchInfo);
	}

	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		/*searchInfo.put("pk", Integer.valueOf(searchInfo.get("pk").toString()));
		return verAppDao.get(searchInfo);*/
		int pk = Integer.valueOf(searchInfo.get("pk").toString());
		searchInfo.put("pk", pk);
		
		Map<String, Object> guide = verAppDao.get(searchInfo);
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setTable("tb_version_app");
		fileInfo.setUnique(pk);
		
		List<FileInfo> imageList = fileInfoService.getList(fileInfo);
		guide.put("fileList", imageList);
		
		return guide; 
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return verAppDao.getList(searchInfo);
	}

	@Override
	@Transactional
	public void register(Map<String, Object> verApp) throws IllegalStateException, IOException {
		verAppDao.register(verApp);
		registerFile(verApp);
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}

	@Override
	@Transactional
	public void modify(Map<String, Object> manage) throws IllegalStateException, IOException {
		int pk = Integer.valueOf(manage.get("pk").toString());
		boolean existsImage = false;
		
		manage.put("pk", pk);
		verAppDao.modify(manage);
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setTable("tb_version_app");
		fileInfo.setUnique(pk);
		
		@SuppressWarnings("unchecked")
		Map<String, MultipartFile> files = (Map<String, MultipartFile>)manage.get("files");
		for(Map.Entry<String, MultipartFile> appFile : files.entrySet()) {
			if(!appFile.getValue().isEmpty()) {
				existsImage = true;
			}
		}
		
		if(existsImage) {
			fileInfoService.remove(fileInfo);
		}
		
		registerFile(manage);
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}
	
	@Override
	public String checkVerAppFormData(Map<String, Object> searchInfo){
		String rtn = "";
		String lastVerStr = "";
		int chkVer, lastVer;
		boolean chkProcess = false;
		
		try {
			//체크할 버전
			String[] chkVerArryStr = searchInfo.get("verNum").toString().split("\\.");
			
			//마지막 등록된 버전
			lastVerStr = verAppDao.getLastVer(searchInfo);
			
			if(lastVerStr != null){
				String[] lastVerArryStr = lastVerStr.split("\\.");
				for(int i=0 ; i<chkVerArryStr.length ; i++){
					chkVer = Integer.parseInt(chkVerArryStr[i]);
					lastVer = Integer.parseInt(lastVerArryStr[i]);
					
					if(chkVer > lastVer){
						chkProcess = true;
						break;
					}else if(chkVer < lastVer){
						chkProcess = false;
						break;
					}
				}
			}else{
				chkProcess = true;
			}
			
			if(chkProcess){
				rtn = "success";
			}else{
				rtn = "fail";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rtn = "fail";
		}
		return rtn;
	}
	
	@Override
	public String verAppUpdateCheck(Map<String, Object> searchInfo){
		return verAppDao.verAppUpdateCheck(searchInfo);
	}
	
	private void registerFile(Map<String, Object> manage) throws IllegalStateException, IOException {
		
		String targetDescription = "";
		String orgName, saveName, description, size, ext;
		String urlPath, virtualPath, realPath;
		
		int pk = (Integer)manage.get("pk");
		
		SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String dateStr = ft.format(new Date());				
		
		File targetDirectory = new File(manage.get("realPath") + separator + dateStr);
		if(!targetDirectory.exists()) {
			targetDirectory.mkdirs();
		}
		
		int count = 1;
		@SuppressWarnings("unchecked")
		Map<String, MultipartFile> files = (Map<String, MultipartFile>)manage.get("files");
		
		for(Map.Entry<String, MultipartFile> appFile : files.entrySet()) {
			
			MultipartFile file = appFile.getValue();
			
			if(!file.isEmpty()) {
				size = String.valueOf(file.getSize());
				orgName = file.getOriginalFilename();
				ext = orgName.substring(orgName.lastIndexOf(".") + 1);
				saveName = UUID.randomUUID().toString() + "." + ext;
				
				virtualPath = manage.get("virtualPath").toString() + "/" + dateStr;
				realPath = targetDirectory.getAbsolutePath();
				
				FileInfo fileInfo = new FileInfo();
				
				fileInfo.setTable("tb_version_app");
				fileInfo.setUnique(pk); 
				fileInfo.setUrlPath("");
				fileInfo.setVirtualPath(virtualPath);
				fileInfo.setRealPath(realPath);
				fileInfo.setOrgName(orgName);
				fileInfo.setSaveName(saveName);
				fileInfo.setSize(size);
				fileInfo.setExt(ext);
				fileInfo.setSortNo(count);
				
				File outputFile = new File(manage.get("realPath") + separator + dateStr + separator + saveName);
				
				file.transferTo(outputFile);		
				fileInfoService.register(fileInfo);
				
				count++;
			}
		}
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}
	
	@Override
	@Transactional
	public void remove(Map<String, Object> notice) throws IllegalStateException, IOException {
		verAppDao.remove(notice);
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}
}
