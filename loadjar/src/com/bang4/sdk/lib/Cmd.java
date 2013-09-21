package com.bang4.sdk.lib;

import java.util.Map;

import com.bang4.sdk.i.ICallback;

public class Cmd {
	String cmd;
	Map<String, Object> param;
	ICallback callback;
	public Cmd(String cmd, Map<String, Object> param, ICallback callback) {
		super();
		this.cmd = cmd;
		this.param = param;
		this.callback = callback;
	}
	public String getCmd() {
		return cmd;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public ICallback getCallback() {
		return callback;
	}
	
}
