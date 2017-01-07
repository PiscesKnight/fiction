package com.example.tai_fiction.tool;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Json解析工具
 * */
public class GsonUtil {
	/** Gson解析框架 */
	private static Gson gson;

	private static GsonUtil gsonUtil;

	public static GsonUtil getInstance(){
		if (gsonUtil == null){
			return  new GsonUtil();
		}else{
			return  gsonUtil;
		}
	}

	public GsonUtil() {

		gson = new GsonBuilder().create();
	}

	/**
	 * 获取个Json数组
	 * 
	 * @param jsonStr
	 *            Json字符
	 * @param type
	 *            转换的实体集
	 * 
	 * @return 集合
	 * */
	public <T> List<T> getDatas(String jsonStr, Type type) {
		List<T> objList = null;
		objList = gson.fromJson(jsonStr, type);
		return objList;
	}

	/**
	 * 获取一个Json对象
	 * 
	 * @param jsonStr
	 *            Json字符
	 * @param cls
	 *            转换的实体对象
	 * 
	 * @return 对象
	 * */
	public <T> T getData(String jsonStr, Class<T> cls) {
		T obj = gson.fromJson(jsonStr, cls);
		return obj;
	}
	
	public String getMsg(String jsonStr){
		return getContent(jsonStr, "msg");
	}
	
	public String getContent(String jsonStr, String key){
		try {
			JSONObject object = new JSONObject(jsonStr);
			return object.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 把对象转换成Json字符
	 * 
	 * @param object
	 *            实体对象
	 * 
	 * @return Json字符串
	 * */
	public String toJson(Object object) {
		return gson.toJson(object);
	}
	
	public static void writeFileSdcardFile(String write_str){
		try {
			
			File file = new File(Environment.getExternalStorageDirectory() + "/droid4xshare/haha.txt");

			file.delete();
			
			if (file.isDirectory()) {
				
			}
			
			
			FileOutputStream fout = new FileOutputStream(file);
			byte[] bytes = write_str.getBytes();

			fout.write(bytes);
			fout.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
