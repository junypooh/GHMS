package com.kt.hiot.homemanager.kafka.simulator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TestCase {

	public static void main(String[] args) {
		int headerLen = 16;
		byte[] header = new byte[headerLen];
		byte[] name = "IOT GW".getBytes(); //6 byte
		int inx = 0;
		
		for (byte b : name) {
			header[inx++] = b;
		}
		header[inx++] = 0x00;
		header[inx++] = 0x00;	//8byte name	
		
		
		header[inx++] = 0x01;	//1byte version
		header[inx++] = 0x01;	//1byte command
		
		
		int randomNumber = 12354;
		header[inx++] = (byte) ((randomNumber>>24)&0xff);
		header[inx++] = (byte) ((randomNumber>>16)&0xff);
		header[inx++] = (byte) ((randomNumber>>8)&0xff);
		header[inx++] = (byte) ((randomNumber)&0xff);	//4byte random number
		
		int dataSize = 4;
		header[inx++] = (byte) ((dataSize>>8)&0xff);
		header[inx++] = (byte) ((dataSize)&0xff);	//2byte data size
		
		byte[] data = new byte[4];	//SSID, Password : Command 값이 0x01이면 생략, Reserved 있음. 
		data[0] = 0x00;
		data[1] = 0x00;
		data[2] = 0x00;
		data[3] = 0x00;
		
		

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		
		try {
			out.write(header);
			out.write(data);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] buffer = out.toByteArray();
		
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);
		
		byte[] header1  = new byte[8];
		byte[] header2  = new byte[1];
		byte[] header3  = new byte[1];
		byte[] header4  = new byte[4];
		byte[] header5  = new byte[2];

        in.read(header1, 0, 8);
        in.read(header2, 0, 1);
        in.read(header3, 0, 1);
        in.read(header4, 0, 4);
        in.read(header5, 0, 2);
        
        int bodySize = (short) (((header5[0] << 8)) | ((header5[1] & 0xff)));	
        byte[] body = new byte[bodySize];
        
		int read = 0;
        while(read < bodySize){
        	int diff = bodySize-read;
            read += in.read(body, read, diff);
        }
        System.out.println(byteArrayToHex(header1));
        System.out.println(byteArrayToHex(header2));
        System.out.println(byteArrayToHex(header3));
        System.out.println(byteArrayToHex(header4));
        System.out.println(byteArrayToHex(header5));
        System.out.println(byteArrayToHex(body));
	}
	
	public static String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a){
		      sb.append(String.format("%02x", b & 0xff));
		      sb.append(" ");
		   }
		   return sb.toString();
		}
}
