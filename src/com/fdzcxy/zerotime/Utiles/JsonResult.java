package com.fdzcxy.zerotime.Utiles;

import java.util.ArrayList;

public class JsonResult {

	/**
	 * 操作结果
	 */
	private String result; // ok,fail
	/**
	 * 操作结果描述
	 */
	private String desc; // 可空，描述

	/**
	 * 返回的记录数据
	 */
	private Object record;

	public JsonResult() {
		super();
		this.result = "fail";
		this.desc = "";
		this.record = new ArrayList();
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getRecord() {
		return record;
	}

	public void setRecord(Object record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "JsonResult [result=" + result + ", desc=" + desc + ", record="
				+ record + "]";
	}

}
