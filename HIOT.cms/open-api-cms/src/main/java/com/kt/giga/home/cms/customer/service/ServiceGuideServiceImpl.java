package com.kt.giga.home.cms.customer.service;

import java.io.*;
import java.text.*;
import java.util.*;

import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.*;

import com.kt.giga.home.cms.customer.dao.*;
import com.kt.giga.home.cms.common.domain.*;
import com.kt.giga.home.cms.common.service.*;

@Service
public class ServiceGuideServiceImpl implements ServiceGuideService {
	
	@Autowired
	private ServiceGuideDao serviceGuideDao;	
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private OpenAPICallService openAPICallService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String separator = System.getProperty("file.separator");

	@Override
	public int getCount(Map<String, Object> searchInfo) {
		return serviceGuideDao.getCount(searchInfo);
	}
	
	@Override
	public Map<String, Object> get(Map<String, Object> serviceGuide) {
		
		int pk = Integer.valueOf(serviceGuide.get("pk").toString());
		serviceGuide.put("pk", pk);
		
		Map<String, Object> guide = serviceGuideDao.get(serviceGuide);
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setTable("tb_faq");
		fileInfo.setUnique(pk);
		
		List<FileInfo> imageList = fileInfoService.getList(fileInfo);
		guide.put("imageList", imageList);
		
		return guide; 
	}	

	@Override
	public List<Map<String, Object>> getList(Map<String, Object> searchInfo) {
		return serviceGuideDao.getList(searchInfo);
	}

	@Override
	public void updateOpenYN(Map<String, Object> serviceGuide) {
		int pk = Integer.valueOf(serviceGuide.get("pk").toString());
		serviceGuide.put("pk", pk);
		
		serviceGuideDao.updateOpenYN(serviceGuide);
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}

	@Override
	@Transactional
	public void remove(Map<String, Object> serviceGuide) {
		
		int pk = Integer.valueOf(serviceGuide.get("pk").toString());
		serviceGuide.put("pk", pk);		
		serviceGuideDao.remove(serviceGuide);
		
		FileInfo fileInfo = new FileInfo();
		
		fileInfo.setTable("tb_faq");
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
	public void register(Map<String, Object> serviceGuide) throws IllegalStateException, IOException {
		serviceGuideDao.register(serviceGuide);
		if(serviceGuide.get("contentsType").toString().equals("image")) {
			registerFile(serviceGuide);
		}
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
	}

	@Override
	@Transactional
	public void modify(Map<String, Object> serviceGuide) throws IllegalStateException, IOException  {
		int pk = Integer.valueOf(serviceGuide.get("pk").toString());
		serviceGuide.put("pk", pk);
		serviceGuideDao.modify(serviceGuide);
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setTable("tb_faq");
		fileInfo.setUnique(pk);
		
		if(serviceGuide.get("contentsType").toString().equals("image")) {
			boolean existsImage = false;
			
			Map<String, MultipartFile> images = (Map<String, MultipartFile>)serviceGuide.get("images");
			for(Map.Entry<String, MultipartFile> image : images.entrySet()) {
				if(!image.getValue().isEmpty()) {
					existsImage = true;
				}
			}
			
			if(existsImage) {
				fileInfoService.remove(fileInfo);
			}
			
			registerFile(serviceGuide);
		}else{
			fileInfoService.remove(fileInfo);		
		}
		
		/*try {
			openAPICallService.updateOpenAPIInit();
		} catch (Exception e) {
			e.printStackTrace();
		}	*/
		
	}
	
	private void registerFile(Map<String, Object> serviceGuide) throws IllegalStateException, IOException {
		
		String targetDescription = "";
		String orgName, saveName, description, size, ext;
		String urlPath, virtualPath, realPath;
		
		int pk = (Integer)serviceGuide.get("pk");
		
		SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String dateStr = ft.format(new Date());				
		
		File targetDirectory = new File(serviceGuide.get("realPath") + separator + dateStr);
		if(!targetDirectory.exists()) {
			targetDirectory.mkdirs();
		}
		
		int count = 1;
		Map<String, MultipartFile> images = (Map<String, MultipartFile>)serviceGuide.get("images");
		
		for(Map.Entry<String, MultipartFile> image : images.entrySet()) {
			
			MultipartFile file = image.getValue();
			
			if(!file.isEmpty()) {
				size = String.valueOf(file.getSize());
				orgName = file.getOriginalFilename();
				ext = orgName.substring(orgName.lastIndexOf(".") + 1);
				saveName = UUID.randomUUID().toString() + "." + ext;
				
				virtualPath = serviceGuide.get("virtualPath").toString() + "/" + dateStr;
				realPath = targetDirectory.getAbsolutePath();
				
				targetDescription = image.getKey().replace("image", "description");
				description = serviceGuide.get(targetDescription).toString();
				
				FileInfo fileInfo = new FileInfo();
				
				fileInfo.setTable("tb_faq");
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
				
				File outputFile = new File(serviceGuide.get("realPath") + separator + dateStr + separator + saveName);
				
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
