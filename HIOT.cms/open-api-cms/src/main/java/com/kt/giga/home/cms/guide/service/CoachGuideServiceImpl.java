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
public class CoachGuideServiceImpl implements CoachGuideService {

	@Autowired
	private CoachGuideDao coachGuideDao;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String separator = System.getProperty("file.separator");
	
	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return coachGuideDao.getCount(searchInfo);
	}
	
	@Override
	public Map<String, Object> get(Map<String, Object> searchInfo) {
		int pk = Integer.valueOf(searchInfo.get("pk").toString());
		searchInfo.put("pk", pk);
		
		Map<String, Object> guide = coachGuideDao.get(searchInfo);
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setTable("tb_guide_coach");
		fileInfo.setUnique(pk);
		
		List<FileInfo> imageList = fileInfoService.getList(fileInfo);
		guide.put("imageList", imageList);
		
		return guide; 
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return coachGuideDao.getList(searchInfo);
	}

	@Override
	public void updateOpenYN(Map<String, Object> guide) {
		guide.put("pk", Integer.valueOf(guide.get("pk").toString()));
		coachGuideDao.updateOpenYN(guide);
		if(guide.get("openYN").equals("Y")){
			coachGuideDao.updateAllOpenYN(guide);
		}
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}
	
	@Override
	public List<Map<String, Object>> getVersionList(Map<String, Object> searchInfo) {
		return coachGuideDao.getVersionList(searchInfo);
	}
	
	@Override
	public String checkCoachFormData(Map<String, Object> searchInfo) {
		String rtn = "";
		
		if(coachGuideDao.getCheckDataCount(searchInfo) > 0){
			rtn = "fail";
		}else{
			rtn = "success";
		}
		
		return rtn;
	}

	@Override
	@Transactional
	public void remove(Map<String, Object> guide) {
		int pk = Integer.valueOf(guide.get("pk").toString());
		guide.put("pk", pk);		
		coachGuideDao.remove(guide);
		
		FileInfo fileInfo = new FileInfo();
		
		fileInfo.setTable("tb_guide_coach");
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
		guide.put("targetPositionCode", guide.get("positionCode"));
		
		if(guide.get("openYN").equals("Y")){
			coachGuideDao.updateAllOpenYN(guide);
		}
		coachGuideDao.register(guide);
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
		coachGuideDao.modify(guide);
		if(guide.get("openYN").equals("Y")){
			coachGuideDao.updateAllOpenYN(guide);
		}
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setTable("tb_guide_coach");
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
		
		guide.put("positionCode", guide.get("targetPositionCode"));
		registerFile(guide);
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}
	
	private void registerFile(Map<String, Object> guide) throws IllegalStateException, IOException {
		
		String targetDescription = "";
		String orgName, saveName, description, size, ext;
		String urlPath, virtualPath, realPath;
		
		int pk = (Integer)guide.get("pk");
		
		SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String dateStr = ft.format(new Date());				
		String position = guide.get("positionCode").toString();
		
		File targetDirectory = new File(guide.get("realPath") + separator + dateStr + separator + position);
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
				
				virtualPath = guide.get("virtualPath").toString() + separator + dateStr + separator + position;
				realPath = targetDirectory.getAbsolutePath();
				
				targetDescription = image.getKey().replace("image", "description");
				description = guide.get(targetDescription).toString();
				
				FileInfo fileInfo = new FileInfo();
				
				fileInfo.setTable("tb_guide_coach");
				fileInfo.setUnique(pk); 
				fileInfo.setDescription(description);
				fileInfo.setUrlPath("");
				fileInfo.setVirtualPath(virtualPath);
				fileInfo.setRealPath(realPath);
				fileInfo.setOrgName(orgName);
				fileInfo.setSaveName(saveName);
				fileInfo.setSize(size);
				fileInfo.setExt(ext);
				fileInfo.setSortNo(count);
				
				File outputFile = new File(guide.get("realPath") + separator + dateStr + separator + position + separator + saveName);
				
				file.transferTo(outputFile);		
				fileInfoService.register(fileInfo);
				
				count++;
			}
		}
	}
}
