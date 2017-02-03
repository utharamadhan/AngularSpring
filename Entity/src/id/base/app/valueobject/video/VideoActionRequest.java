package id.base.app.valueobject.video;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VideoActionRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2576464949062984429L;

	@JsonProperty(value="token_id")
	private String tokenId;
	
	@JsonProperty(value="action")
	private String action;
	
	@JsonProperty(value="minute")
	private String minute;

	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	
}
