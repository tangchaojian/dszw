package net.yundingwei.dszw.app.net.resmsg;

/**
 * 响应消息的消息实体类
 */
public class ResMessage {

	private String version;
	private Object data;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
