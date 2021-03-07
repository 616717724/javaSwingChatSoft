package com.ui.test.json;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class Testjson {
	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("name", "json");
		map.put("bool", Boolean.TRUE);
		map.put("int", new Integer(1));
		map.put("arr", new String[] { "a", "b" });
		map.put("func", "function(i){return this.arr[i]; }");
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json);
		map=(Map)json;
		System.out.println(map);
		System.out.println(map.get("arr"));
	}
}
