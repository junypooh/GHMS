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



public class JxlsExcelViewPnsHistoryList extends AbstractJExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> map, WritableWorkbook workbook, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataList = (List<Map<String, Object>>)map.get("pnsHistoryList");
		String fileName = createFileName();
		setFileNameToResponse(request, response, fileName);

		JxlSpringManager jxlmng = new JxlSpringManager();
		jxlmng.createSheet(workbook.createSheet("sheet", 0));		
			
		String[][] aaStrHeader = { {"NO", "고객ID", "휴대폰번호", "상품명", "푸시", "푸시 일련번호", "발송일시", "발송여부", "수신일시", "수신여부"} };
		String[][] excelData = new String[dataList.size()][10];

		for (int i = 0; i < dataList.size(); i++) {
			excelData[i][0] = Integer.toString(dataList.size() - i);
			excelData[i][1] = "" + dataList.get(i).get("mbrId");
			excelData[i][2] = "" + dataList.get(i).get("telNo");
			excelData[i][3] = "" + dataList.get(i).get("unitSvcNm");
			excelData[i][4] = "" + dataList.get(i).get("statEvetNm");
			excelData[i][5] = "" + dataList.get(i).get("msgSeq");
			
			excelData[i][6] = "" + dataList.get(i).get("msgTrmDt");
			excelData[i][7] = dataList.get(i).get("msgTtrmForwardYn")==null ? "" : dataList.get(i).get("msgTtrmForwardYn").toString().equals("Y") ? "성공" : "실패";
			
			excelData[i][8] = "" + dataList.get(i).get("msgRcvDt");
			excelData[i][9] = dataList.get(i).get("msgRcvYn")==null ? "" : dataList.get(i).get("msgRcvYn").toString().equals("Y") ? "성공" : "실패";
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
		return new StringBuilder("pnsHistoryList").append("_").append(fileFormat.format(new Date())).append(".xls").toString();
	}
}
