package com.bang4.sdk.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.bang4.sdk.i.ICallback;
import com.bang4.sdk.i.IHolder;
import com.bang4.sdk.i.ISDK;

public class V1  extends Thread implements ISDK {
	private BlockingQueue<Cmd> cmdList=new LinkedBlockingQueue<Cmd>();
	private IHolder holder=null;
	private boolean loopFlag=true;

	@Override
	public int version() {
		return 1;
	}

	@Override
	public void init(IHolder holder) {
		this.holder=holder;
		this.start();		
	}

	@Override
	public void destory() {
		this.stopLater();
	}
	
	@Override
	public void call(String cmd, Map<String, Object> param, ICallback callback) {
		Cmd command=new Cmd(cmd,param,callback);
		cmdList.offer(command);
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("V1 Thread running!");
		while(loopFlag){
			processCmd();
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				System.out.println("interrupt");
				break;
			}
		}
		System.out.println("stop");
	}
	
	public void stopLater(){
		loopFlag=false;
	}
	
	private void processCmd(){
		Cmd cmd=cmdList.poll();
		if(cmd!=null&&cmd.getCmd()!=null){
			if(cmd.getCmd().equals("update")){
				cmdUpdate(cmd);
			}
		}
	}
	
	private void cmdUpdate(Cmd cmd){
		if(holder==null)return;
		String jarPath="e:\\try\\v2.jar";
		String sUrl=cmd.getParam().get("url").toString();
		File file=new File(jarPath);
		try {
			saveUrlFile(sUrl,file);
			holder.loadJar(jarPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void saveUrlFile(String sUrl,File file) throws IOException{
		URL url = new URL( sUrl );
		URLConnection connection = url.openConnection();
		InputStream input = connection.getInputStream();
		byte[] buffer = new byte[4096];
		int n = - 1;
		OutputStream output = new FileOutputStream( file );
		while ( (n = input.read(buffer)) != -1)
		{
		    if (n > 0)
		    {
		        output.write(buffer, 0, n);
		    }
		}
		output.close();
	}


}
