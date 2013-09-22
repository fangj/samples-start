package com.bang4.sdk.main;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import com.bang4.sdk.i.ICallback;
import com.bang4.sdk.i.IHolder;
import com.bang4.sdk.i.ISDK;

public class Charger implements IHolder{
	private ISDK sdk=null;
	public  static void main(String args[]) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException, MalformedURLException, InterruptedException{
		System.out.println("hello");
		Charger charger=new Charger();

/*
		//v1.jar=com.bang4.sdk.lib/Const.java+V1.java V1Executer.java
		File myjar=new File("/Users/fangjian/try/v1.jar");
		URL[] urls=new URL[]{myjar.toURL()};
		URLClassLoader urlLoader = new URLClassLoader(urls);
		//ISDK sdk=charger.getSDK(urlLoader);
*/		
		System.out.println(charger.version());
		sendUpdateCmd(charger);

		
		//sdk.destory();
		Thread.sleep(5000);
	}
	
	private static void sendUpdateCmd(Charger charger){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("url", "http://localhost/abc.jar");
		charger.call("update",params,null);
	}
	
	public Charger() throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException{
		ClassLoader loader=Charger.class.getClassLoader();		
		sdk=this.getSDK(loader);
		sdk.init(this);
	}
	
	public void call(String cmd, Map<String, Object> param, ICallback callback){
		sdk.call(cmd, param, callback);
	}
	
	private ISDK getSDK(ClassLoader loader) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException{
		Class<?> clsConst = loader.loadClass("com.bang4.sdk.lib.Const");
		Field fcn=clsConst.getField("classname");
		String classname=(String) fcn.get(clsConst);
		System.out.println(classname);
		//get class
		Class<?> clsSDK = loader.loadClass(classname);
		ISDK sdk=(ISDK)clsSDK.newInstance();
		return sdk;
	}

	@Override
	public void loadJar(String jarPath) {
		// TODO Auto-generated method stub
		System.out.println("load jar");
		sdk.destory();
	}
	
	public Integer version(){
		return (null==sdk)?null:sdk.version();
	}
}
