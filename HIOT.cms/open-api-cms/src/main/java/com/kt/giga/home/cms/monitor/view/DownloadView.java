package com.kt.giga.home.cms.monitor.view;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.*;

import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView {
	
	public DownloadView() {
		setContentType("application/download; charset=utf-8");
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String logData = (String)model.get("logData");
		
		response.setContentType(getContentType());
		response.setContentLength(logData.length());
		response.setHeader("Cache-Control", "max-age=0");
		
		SimpleDateFormat df= new SimpleDateFormat("yyyyMMdd-hhmmss.SSS");
		String nowDate = df.format(new Date());
		
		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;
		//String fileName = "log_" + System.currentTimeMillis() + ".txt";
		String fileName = "log_" + nowDate + ".txt";
		
		if(ie) {
			fileName = URLEncoder.encode(fileName, "utf-8");
		} else {
			fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		OutputStream os = response.getOutputStream();
		InputStream is = IOUtils.toInputStream(logData, "UTF-8");
		
		try {
			FileCopyUtils.copy(is, os);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch(IOException ioe) {}
			}
		}
		
		os.flush();
	}
}