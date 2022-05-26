package com.java.config;

import java.util.HashMap;

public class ShoppingConfig {
	public static boolean validationWithHashMap(String keys[], HashMap<String, String> request) throws Exception{
		boolean status = false;
		try {
			for(String key : keys) {
				if(request.containsKey(key)) {//not exist
					if(request.get(key).equals(""))//if empty
						throw new Exception(key+" should not be empty");
				}else {
					throw new Exception(key + " is missing");
				}
			}
		} catch (Exception e) {
			throw new Exception("Error is " + e.getMessage());
		}
		return status;
	}
}
