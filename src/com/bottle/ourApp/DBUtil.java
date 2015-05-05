package com.bottle.ourApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Message;


public class DBUtil {
	public ArrayList<String> arrayList = new ArrayList<String>();
	public ArrayList<String> brrayList = new ArrayList<String>();
	public ArrayList<String> crrayList = new ArrayList<String>();
	public HttpConnSoap Soap = new HttpConnSoap();

	public static Connection getConnection() {
		Connection con = null;
		try {
			//Class.forName("org.gjt.mm.mysql.Driver");
		//=DriverManager.getConnection("jdbc:mysql://10.0.2.2/test?useUnicode=true&characterEncoding=UTF-8","root","initial");  		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 获取所有货物的信息
	 * 
	 * @return
	 */
	
	 
	 public List<HashMap<String, String>> getAllInfo() {
         List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
         HashMap<String, String> tempHash = new HashMap<String, String>();
         tempHash.put("Cno", "Cno");
         tempHash.put("Cname", "Cname");
        // tempHash.put("Cnum", "Cnum");
         list.add(tempHash);
           
         for (int j = 0; j < crrayList.size(); j += 2) {
             HashMap<String, String> hashMap = new HashMap<String, String>();
             hashMap.put("Cno", crrayList.get(j));
             hashMap.put("Cname", crrayList.get(j + 1));
         //   hashMap.put("Cnum", crrayList.get(j + 2));
             list.add(hashMap);
         }
         return list;
     }
	/**
	 * 增加一条货物信息
	 * 
	 * @return
	 */
	public void insertCargoInfo(String Cname, String Cnum) {

		arrayList.clear();
		brrayList.clear();
		
		arrayList.add("Cname");
		arrayList.add("Cnum");
		brrayList.add(Cname);
		brrayList.add(Cnum);
		
		new Thread(){
			public void run()
			{
				try
				{
					Soap.GetWebServre("insertCargoInfo", arrayList, brrayList);
				}
				catch(Exception e)
				{
					
				}
			}
			
		}.start();
	
	}
	
	/**
	 * 删除一条货物信息
	 * 
	 * @return
	 */
	/*
	public void deleteCargoInfo(String Cno) {

		arrayList.clear();
		brrayList.clear();
		
		arrayList.add("Cno");
		brrayList.add(Cno);
		
		Soap.GetWebServre("deleteCargoInfo", arrayList, brrayList);
	}
	*/
	
	
}

