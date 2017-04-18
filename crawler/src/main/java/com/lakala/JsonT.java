package com.lakala;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		    String str="[{\"StdStg\":6899, \"StdStl\":8, \"_update_time\":\"1456091470\"}]";
	        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	        try {
	            Gson gson = new Gson();
	            list = gson.fromJson(str,
	                    new TypeToken<List<Map<String, String>>>() {
	                    }.getType());
	            System.out.println(list.size());
	            for(int i=0;i<list.size();i++){
	            	System.out.println(list.get(i).get("StdStg"));
	            	System.out.println(list.get(i).get("StdStl"));
	            }
	        } catch (Exception e) {
	        	e.printStackTrace();
	            // TODO: handle exception
	        }
	       
	    
	}

}
