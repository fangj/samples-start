package com.bang4.sdk.lib;

public class V1Executer extends Thread {

	private boolean loopFlag=true;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("V1Executer running!");
		while(loopFlag){
			try {
				Thread.sleep(5000);
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

}
