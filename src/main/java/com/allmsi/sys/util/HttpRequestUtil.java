package com.allmsi.sys.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.allmsi.sys.model.OsBrowser;

public class HttpRequestUtil {

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if ((ip != null && !ip.equals("")) && !"unKnown".equalsIgnoreCase(ip)) {
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if ((ip != null && !ip.equals("")) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}

		String ipAddress = request.getRemoteAddr();
		if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
			// 根据网卡取本机配置的IP
			InetAddress inet = null;
			try {
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			ipAddress = inet.getHostAddress();
		}
		return ipAddress;
	}

	private static String[] getUA(HttpServletRequest request) {
		String agent = request.getHeader("User-Agent");
		String[] ua = agent.trim().split("\\)\\s");
		if (ua == null || ua.length < 1) {
			return null;
		}
		String[] u = ua[ua.length - 1].split(" ")[0].split("\\/");
		return u;
	}

	public static String getBrower(HttpServletRequest request) {
		String[] ua = getUA(request);
		if (ua != null && ua.length > 0) {
			return ua[0];
		}
		return null;
	}

	public static String getBrowserVersion(HttpServletRequest request) {
		String[] ua = getUA(request);
		if (ua != null && ua.length > 1) {
			return ua[1];
		}
		return null;
	}
	
	public static OsBrowser getOsAndBrowserInfo(HttpServletRequest request) {
		String browserDetails = request.getHeader("User-Agent");
		String userAgent = browserDetails;
		String user = userAgent.toLowerCase();

		String os = "";
		String browserInfo = "";

		// =================OS Info=======================
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		// ===============Browser===========================
		if (user.contains("edge")) {
			browserInfo = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browserInfo = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browserInfo = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera")) {
				browserInfo = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			} else if (user.contains("opr")) {
				browserInfo = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
						.replace("OPR", "Opera");
			}

		} else if (user.contains("chrome")) {
			browserInfo = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)
				|| (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1)
				|| (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
			browserInfo = "Netscape-?";

		} else if (user.contains("firefox")) {
			browserInfo = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
			browserInfo = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
		} else {
			browserInfo = "UnKnown, More-Info: " + userAgent;
		}
		String[] browserArray = browserInfo.split("-");
		String browser = "";
		String browserVersion = "";
		if (2 == browserArray.length) {
			browser = browserInfo.split("-")[0];
			browserVersion = browserInfo.split("-")[1];
		}
		return new OsBrowser(os, browser, browserVersion);
	}
}
