package com.kt.giga.home.cms.inquiry.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableWorkbook;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.kt.giga.home.cms.util.JxlSpringManager;



public class JxlsExcelViewMonitorHistoryList extends AbstractJExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> map, WritableWorkbook workbook, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataList = (List<Map<String, Object>>)map.get("monitorHistoryList");
		String fileName = createFileName();
		setFileNameToResponse(request, response, fileName);

		JxlSpringManager jxlmng = new JxlSpringManager();
		jxlmng.createSheet(workbook.createSheet("sheet", 0));		
			
		String[][] aaStrHeader = { {"NO", "고객ID", "홈카메라ID", "홈카메라닉네임", "휴대폰번호", "발생위치", "세션아이디", "대역폭", "해상도", "모니터링시간", "회선망", "단말기명", "OS버전", "상세정보",  "일시"} };
		String[][] excelData = new String[dataList.size()][15];

		for (int i = 0; i < dataList.size(); i++) {
			excelData[i][0] = Integer.toString(dataList.size() - i);
			excelData[i][1] = "" + dataList.get(i).get("mbr_id");
			excelData[i][2] = "" + dataList.get(i).get("spot_dev_id");
			excelData[i][3] = "" + dataList.get(i).get("dev_nm");
			excelData[i][4] = "" + dataList.get(i).get("tel_no");
			excelData[i][5] = "" + dataList.get(i).get("description");
			
			if(dataList.get(i).get("menu_code").toString().equals("CL03")){
				
				if(!dataList.get(i).get("sessionId").toString().equals("")){
					excelData[i][6] = "" + dataList.get(i).get("sessionId");
				}
				if(!dataList.get(i).get("bandwidth").toString().equals("")){
					excelData[i][7] = "" + dataList.get(i).get("bandwidth");
				}
				if(!dataList.get(i).get("resolution").toString().equals("")){
					excelData[i][8] = "" + dataList.get(i).get("resolution");
				}
				if(!dataList.get(i).get("monitorTime").toString().equals("")){
					excelData[i][9] = "" + dataList.get(i).get("monitorTime");
				}
				if(!dataList.get(i).get("accNetwork").toString().equals("")){
					excelData[i][10] = "" + dataList.get(i).get("accNetwork");
				}
				if(!dataList.get(i).get("deviceName").toString().equals("")){
					excelData[i][11] = "" + dataList.get(i).get("deviceName");
				}
				if(!dataList.get(i).get("osVer").toString().equals("")){
					excelData[i][12] = "" + dataList.get(i).get("osVer");
				}
				if(dataList.get(i).get("fd_memo")!=null){
					excelData[i][13] = "" + dataList.get(i).get("fd_memo");
				}
				
			}else if(dataList.get(i).get("menu_code").toString().equals("CJ11")){
				
				if(!dataList.get(i).get("sessionId").toString().equals("")){
					excelData[i][6] = "" + dataList.get(i).get("sessionId");
				}
				if(!dataList.get(i).get("accNetwork").toString().equals("")){
					excelData[i][10] = "" + dataList.get(i).get("accNetwork");
				}
				if(!dataList.get(i).get("deviceName").toString().equals("")){
					excelData[i][11] = "" + dataList.get(i).get("deviceName");
				}
				if(!dataList.get(i).get("osVer").toString().equals("")){
					excelData[i][12] = "" + dataList.get(i).get("osVer");
				}
				if(dataList.get(i).get("fd_memo")!=null){
					excelData[i][13] = "" + dataList.get(i).get("fd_memo");
				}
				
			}else{
				if(dataList.get(i).get("fd_memo")!=null){
					excelData[i][13] = "" + dataList.get(i).get("fd_memo");	
				}
				
			}
			
			excelData[i][14] = "" + dataList.get(i).get("amPmDate");
		}

		jxlmng.addHeader(aaStrHeader); // 여러셀 추가 (Header Type)
		jxlmng.addData(excelData); // 여러셀 추가 (일반)

	}

	private void setFileNameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName) {
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.indexOf("MSIE 5.5") >= 0) {
			response.setContentType("doesn/matter");
			response.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		}
	}

	private String createFileName() {
		SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return new StringBuilder("monitorHistoryList").append("_").append(fileFormat.format(new Date())).append(".xls").toString();
	}
}
