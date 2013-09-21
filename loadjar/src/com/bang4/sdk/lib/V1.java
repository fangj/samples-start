package com.bang4.sdk.lib;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.bang4.sdk.i.ICallback;
import com.bang4.sdk.i.ISDK;

public class V1 implements ISDK {
	private BlockingQueue<Cmd> cmdList=new LinkedBlockingQueue<Cmd>();
	private V1Executer workerThread=null;

	@Override
	public void call(String cmd, Map<String, Object> param, ICallback callback) {
		Cmd command=new Cmd(cmd,param,callback);
		cmdList.offer(command);
	}

	@Override
	public int version() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void init() {
		workerThread=new V1Executer();
		workerThread.start();		
	}

	@Override
	public void destory() {
		if(null==workerThread){
			return;
		}
		workerThread.stopLater();
		workerThread=null;
	}

}
