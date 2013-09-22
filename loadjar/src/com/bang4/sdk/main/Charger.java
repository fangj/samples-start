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
		Charger charger=new Charger();
		sendUpdateCmd(charger);
		Thread.sleep(5000);
	}
	
	private static void sendUpdateCmd(Charger charger){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("url", "http://localhost/abc.jar");
		charger.call("update",params,null);
	}
	
	public Charger() throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException{
		ClassLoader loader=Charger.class.getClassLoader();		
		sdk=this.getSDK(loader,"com.bang4.sdk.lib.V1");
		sdk.init(this);
		System.out.println(sdk.version());
	}
	
	public void call(String cmd, Map<String, Object> param, ICallback callback){
		sdk.call(cmd, param, callback);
	}
	
	private ISDK getSDK(ClassLoader loader) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException{
		
		Class<?> clsConst = loader.loadClass("com.bang4.sdk.lib.Const");
		Field fcn=clsConst.getField("classname");
		String classname=(String) fcn.get(clsConst);
		System.out.println(classname);
		return getSDK(loader,classname);		
	}
	
	private ISDK getSDK(ClassLoader loader,String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		//get class
		Class<?> clsSDK = loader.loadClass(className);
		ISDK sdk=(ISDK)clsSDK.newInstance();
		return sdk;
	}

	@Override
	public void loadJar(String jarPath) {
		System.out.println("load jar");
		File myjar=new File(jarPath);
		System.out.println(myjar.exists());
		URL[] urls;
		try {
			urls = new URL[]{myjar.toURL()};
			URLClassLoader urlLoader = new URLClassLoader(urls);
			Class<?> clsConst = urlLoader.loadClass("com.bang4.sdk.lib.Const");
			//destroy old jar
			sdk.destory();
			//load new jar
			sdk=getSDK(urlLoader);
			System.out.println(version());
			sdk.init(this);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Integer version(){
		return (null==sdk)?null:sdk.version();
	}
}
