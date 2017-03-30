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



public class JxlsExcelViewDeviceControlRecordList extends AbstractJExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> map, WritableWorkbook workbook, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataList = (List<Map<String, Object>>)map.get("controlRecordList");
		String fileName = createFileName();
		setFileNameToResponse(request, response, fileName);

		JxlSpringManager jxlmng = new JxlSpringManager();
		jxlmng.createSheet(workbook.createSheet("sheet", 0));		
			
		String[][] aaStrHeader = { {"NO", "고객ID", "단말ID",	"단말닉네임", "휴대폰번호", "상품명", "내용", "적용 날짜"} };
		String[][] excelData = new String[dataList.size()][8];

		for (int i = 0; i < dataList.size(); i++) {
			excelData[i][0] = Integer.toString(dataList.size() - i);
			excelData[i][1] = "" + dataList.get(i).get("customerId");
			excelData[i][2] = "" + dataList.get(i).get("spotDevId");
			excelData[i][3] = "" + dataList.get(i).get("spotDevNm");
			excelData[i][4] = "" + dataList.get(i).get("phoneNum");
			excelData[i][5] = "" + dataList.get(i).get("unitSvcNm");
			//excelData[i][6] = "" + dataList.get(i).get("snsnTagNm") + "/" + dataList.get(i).get("contlInfo");
			
			excelData[i][6] = "" + dataList.get(i).get("snsnTagNm");
			if(dataList.get(i).get("contlInfo")!=null){
				excelData[i][6] += dataList.get(i).get("contlInfo").toString().equals("") ? "" : "/" + dataList.get(i).get("contlInfo");
			}
			
			excelData[i][7] = "" + dataList.get(i).get("occDt");
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
		return new StringBuilder("controlRecordList").append("_").append(fileFormat.format(new Date())).append(".xls").toString();
	}
}
