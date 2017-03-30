/*******************************************************************************
 * Copyright(c) 2015 KT. All rights reserved.
 * This software is the proprietary information of KT.
 *******************************************************************************/
package com.kt.giga.home.openapi.ghms.user.domain.key;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kt.giga.home.openapi.ghms.util.json.ObjectConverter;

/**
 * 
 * @author junypooh ( kyungjun.park@ceinside.co.kr )
 * @since 2015. 2. 3.
 */
public class PrintJsonVo {

	/**
	 * @param args
	 * @throws APIException 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DeviceMasterKey masterKey = new DeviceMasterKey();
		
		DeviceAuthKey key = new DeviceAuthKey();
		masterKey.setDeviceAuthVo(key);
		
		DeviceCntrKey cntrKey = new DeviceCntrKey();
		List<DeviceCntrKey> deviceCntrList = new ArrayList<DeviceCntrKey>();
		deviceCntrList.add(cntrKey);
		
		DeviceKey deviceKey = new DeviceKey();
//		deviceKey.setDeviceCntrList(deviceCntrList);
		
		List<DeviceKey> deviceList = new ArrayList<DeviceKey>();
		deviceList.add(deviceKey);
		
		key.setDeviceList(deviceList);
		
		masterKey.setDeviceList(deviceList);
		
		System.out.println(ObjectConverter.toJSONString(masterKey, Include.ALWAYS));
	}

}
