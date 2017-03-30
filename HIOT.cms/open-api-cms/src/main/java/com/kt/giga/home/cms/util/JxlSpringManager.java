package com.kt.giga.home.cms.util;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

/**
 * jxl을 컨트롤 하는 클래스(특정 디자인을 위한 메소드가 많다)
 * @author 송영석
 * 
 */
 public class JxlSpringManager {

	private int nRowCellCount       = 0;     // 현재 열의 위치
	private WritableSheet ws                = null;
	private WritableCellFormat  formatTitle = null;	
	 
	//====================== Get Methode ========================================
	//
	public int getRowCellCount()	{		return nRowCellCount;	}	
	//
	//====================== Get Methode ========================================
	
	public JxlSpringManager() throws WriteException {
		
		// Create : Header Format
		formatTitle= new WritableCellFormat();		
		formatTitle.setBackground(jxl.format.Colour.GRAY_25 );
		formatTitle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN );
		formatTitle.setAlignment(jxl.format.Alignment.CENTRE);
		
	}
			
	public void createSheet(WritableSheet sheet) {
		this.ws = sheet;		
	}
	
	/**
	 * 2차원 배열의 데이타를 표처럼 엑셀 파일에 쓴다.
	 * 
	 * @param aStrData  : 2차원 배열을의 데이타
	 * @throws Exception
	 */
	public void addData(String[][] aStrData) throws Exception 
	{
		if(aStrData == null) return;
		
		int strLength[] = new int[aStrData[0].length];
 
		for(int i=0; i<aStrData.length; i++, nRowCellCount++)
		{			   				 
			for(int j=0; j<aStrData[i].length; j++)
			{
				ws.addCell( new Label( j, nRowCellCount, aStrData[i][j]) ); 
				if(aStrData[i][j] != null){
					if(strLength[j] < aStrData[i][j].length())
						strLength[j] = aStrData[i][j].length();
					ws.setColumnView(j, strLength[j]+5);
					//System.out.println("addData : " + strLength[j]+5);
				}
			}
			//System.out.println(" ");
		}	
	}
	
	/**
	 * 셀 한개를 삽입한다.
	 * @param lData      : Label (Cell)
	 * @throws Exception
	 */
	public void addData(Label lData) throws Exception 
	{
		ws.addCell(lData);	
	}
	
	/**
	 * 셀 한개를 삽입한다.(셀 스타일을 설정할수 있다.)
	 *  
	 * @param lData   : 셀
	 * @param strFormatType :  들어오는 값에 따라, Cell의 스타일을 설정한다.(현재 'HEADER'만 된다.)
	 * @throws Exception
	 */
	public void addData(Label lData, String strFormatType) throws Exception 
	{
		if(strFormatType!=null && strFormatType.equals("HEADER"))
		{
			lData.setCellFormat(formatTitle);			
		}		
		 
		ws.addCell(lData);
	}
	
	/**
	 * 
	 * 2차원 배열의 데이타를 저장한다. (셀 스타일은 'Header'이다)
	 * 
	 * @param aStrHeaderData
	 * @throws Exception
	 */
	public void addHeader(String[][] aStrHeaderData) throws Exception 
	{
		
		if(aStrHeaderData == null) return;
		int strLength[] = new int[aStrHeaderData[0].length];
 
		for(int i=0; i<aStrHeaderData.length; i++, nRowCellCount++)
		{			   				 
			for(int j=0; j<aStrHeaderData[i].length; j++)
			{
				ws.addCell( new Label( j, nRowCellCount, aStrHeaderData[i][j], formatTitle) );
				if(aStrHeaderData[i][j] != null){
					if(strLength[j] < aStrHeaderData[i][j].length())
						strLength[j] = aStrHeaderData[i][j].length();
					ws.setColumnView(j, strLength[j]+5);
					//System.out.println("addHeader : " + strLength[j]+5);
				}
			}
		}	
	}
	
	/**
	 *  현재 열에 +1을 해준다.
	 */
	public void addRowCellCount()
	{
		nRowCellCount++;
	}
	
	/**
	 * 현재 열에 addCount만큼 더해준다. 
	 * @param addCount 
	 */
	public void addRowCellCount(int addCount)
	{
		nRowCellCount += addCount;
	}
	
	/**
	 * 선택한 열의 사이즈를 조절한다.
	 * @param addCount 
	 */
	public void setColumnView(int nColum, int nSize)
	{
		ws.setColumnView(nColum, nSize); 
	}	
	
}