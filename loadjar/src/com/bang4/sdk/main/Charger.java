package com.bang4.sdk.main;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.bang4.sdk.i.ISDK;

public class Charger {
	public  static void main(String args[]) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException, MalformedURLException{
		System.out.println("hello");
		Charger charger=new Charger();
		//get class name
		ClassLoader loader=Charger.class.getClassLoader();		
	//	ISDK sdk=charger.getSDK(loader);

		//	Module module = (Module) cls.newInstance();
		File myjar=new File("/Users/fangjian/try/v2.jar");
		URL[] urls=new URL[]{myjar.toURL()};
		URLClassLoader urlLoader = new URLClassLoader(urls);
		ISDK sdk=charger.getSDK(urlLoader);
		System.out.println(sdk.version());

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
}
