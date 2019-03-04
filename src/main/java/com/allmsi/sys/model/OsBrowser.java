package com.allmsi.sys.model;

public class OsBrowser {

	private String os;
	
	private String browser;
	
	private String browserVersion;
	
	public OsBrowser() {
		
	}

	public OsBrowser(String os, String browser, String browserVersion) {
		this.os = os;
		this.browser = browser;
		this.browserVersion = browserVersion;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
}
