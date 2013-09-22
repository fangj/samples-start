package com.bang4.sdk.i;

import java.util.Map;

public interface ISDK {
	public void call(String cmd,Map<String,Object>param,ICallback callback);
	public int version();
	public void init(IHolder holder);
	public void destory();
}
