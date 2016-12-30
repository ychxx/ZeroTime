package com.fdzcxy.zerotime.Utiles;

import org.json.JSONException;
import org.json.JSONObject;



public class JsonResponse {
	
	
	public JsonResult  parseJsonResponse(JSONObject response) throws JSONException {

	    JSONObject resultPkg =  response.getJSONObject("usersdata");
	    JsonResult result = new JsonResult();
	    result.setResult(resultPkg.getString("result"));
	    result.setDesc(resultPkg.getString("desc"));
	    result.setRecord(resultPkg.getJSONArray("record"));
	    return result;

	}


}
