package com.bang4.sdk.lib;

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
		//TODO: download the jar to local
		String jarPath="";
		if(holder==null)return;
		holder.loadJar(jarPath);
	}


}
