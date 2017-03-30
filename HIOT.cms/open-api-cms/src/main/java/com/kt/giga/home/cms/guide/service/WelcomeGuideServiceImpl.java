package com.kt.giga.home.cms.guide.service;

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
import com.kt.giga.home.cms.guide.dao.*;

@Service
public class WelcomeGuideServiceImpl implements WelcomeGuideService {

	@Autowired
	private WelcomeGuideDao welcomeGuideDao;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String separator = System.getProperty("file.separator");
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return welcomeGuideDao.getCount(searchInfo);
	}
	
	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		int pk = Integer.valueOf(searchInfo.get("pk").toString());
		searchInfo.put("pk", pk);
		
		Map<String, Object> guide = welcomeGuideDao.get(searchInfo);
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setTable("tb_guide_welcome");
		fileInfo.setUnique(pk);
		
		List<FileInfo> imageList = fileInfoService.getList(fileInfo);
		guide.put("imageList", imageList);
		
		return guide; 
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return welcomeGuideDao.getList(searchInfo);
	}

	@Override
	public void updateOpenYN(Map<String, Object> guide) {
		guide.put("pk", Integer.valueOf(guide.get("pk").toString()));
		welcomeGuideDao.updateOpenYN(guide);
		if(guide.get("openYN").equals("Y")){
			welcomeGuideDao.updateAllOpenYN(guide);
		}
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}
	
	@Override
	public List<Map<String, Object>> getVersionList(Map<String, Object> searchInfo) {
		return welcomeGuideDao.getVersionList(searchInfo);
	}
	
	@Override
	public String checkWelcomeFormData(Map<String, Object> searchInfo) {
		String rtn = "";
		String lastVerStr = "";
		int chkVer, lastVer;
		boolean chkProcess = false;
		
		try {
			//체크할 버전
			String[] chkVerArryStr = searchInfo.get("verNum").toString().split("\\.");
			
			//마지막 등록된 버전
			lastVerStr = welcomeGuideDao.getLastVer(searchInfo);
			
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
	@Transactional
	public void remove(Map<String, Object> guide) {
		int pk = Integer.valueOf(guide.get("pk").toString());
		guide.put("pk", pk);		
		welcomeGuideDao.remove(guide);
		
		FileInfo fileInfo = new FileInfo();
		
		fileInfo.setTable("tb_guide_welcome");
		fileInfo.setUnique(pk);
		
		fileInfoService.remove(fileInfo);		
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}

	@Override
	@Transactional
	public void register(Map<String, Object> guide) throws IllegalStateException, IOException {
		
		guide.put("targetCpcode", guide.get("cpCode"));
		
		if(guide.get("openYN").equals("Y")){
			welcomeGuideDao.updateAllOpenYN(guide);
		}
		welcomeGuideDao.register(guide);
		registerFile(guide);
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}

	@Override
	@Transactional
	public void modify(Map<String, Object> guide) throws IllegalStateException, IOException {
		int pk = Integer.valueOf(guide.get("pk").toString());
		boolean existsImage = false;
		
		guide.put("pk", pk);
		welcomeGuideDao.modify(guide);
		if(guide.get("openYN").equals("Y")){
			welcomeGuideDao.updateAllOpenYN(guide);
		}
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setTable("tb_guide_welcome");
		fileInfo.setUnique(pk);
		
		@SuppressWarnings("unchecked")
		Map<String, MultipartFile> images = (Map<String, MultipartFile>)guide.get("images");
		for(Map.Entry<String, MultipartFile> image : images.entrySet()) {
			if(!image.getValue().isEmpty()) {
				existsImage = true;
			}
		}
		
		if(existsImage) {
			fileInfoService.remove(fileInfo);
		}
		
		registerFile(guide);
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}
	
	private void registerFile(Map<String, Object> guide) throws IllegalStateException, IOException {
		
		String orgName, saveName, size, ext;
		String urlPath, virtualPath, realPath;
		
		int pk = (Integer)guide.get("pk");
		
		SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String dateStr = ft.format(new Date());				
		
		File targetDirectory = new File(guide.get("realPath") + separator + dateStr);
		if(!targetDirectory.exists()) {
			targetDirectory.mkdirs();
		}
		
		int count = 1;
		@SuppressWarnings("unchecked")
		Map<String, MultipartFile> images = (Map<String, MultipartFile>)guide.get("images");
		
		for(Map.Entry<String, MultipartFile> image : images.entrySet()) {
			
			MultipartFile file = image.getValue();
			
			if(!file.isEmpty()) {
				size = String.valueOf(file.getSize());
				orgName = file.getOriginalFilename();
				ext = orgName.substring(orgName.lastIndexOf(".") + 1);
				saveName = UUID.randomUUID().toString() + "." + ext;
				
				virtualPath = guide.get("virtualPath").toString() + "/" + dateStr;
				realPath = targetDirectory.getAbsolutePath();
				
				FileInfo fileInfo = new FileInfo();
				
				fileInfo.setTable("tb_guide_welcome");
				fileInfo.setUnique(pk); 
				fileInfo.setDescription("1901");
				fileInfo.setUrlPath("");
				fileInfo.setVirtualPath(virtualPath);
				fileInfo.setRealPath(realPath);
				fileInfo.setOrgName(orgName);
				fileInfo.setSaveName(saveName);
				fileInfo.setSize(size);
				fileInfo.setExt(ext);
				fileInfo.setSortNo(count);
				
				File outputFile = new File(guide.get("realPath") + separator + dateStr + separator + saveName);
				
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
}
