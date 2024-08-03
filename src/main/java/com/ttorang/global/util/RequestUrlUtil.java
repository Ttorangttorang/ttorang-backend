package com.ttorang.global.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


/**
 * Request 객체를 이용한 유틸리티 클래스
 * */
@Slf4j
public class RequestUrlUtil {

	public static String getServiceURL(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		String contextPath = request.getContextPath();
		int serverPort = request.getServerPort();
		String url;

		if (serverPort == 443) {
			url = scheme + "://" + "api." + serverName;
		} else {
			url = scheme + "://" + serverName;
		}

		if (serverPort != 80 && serverPort != 443) {
			url += ":" + serverPort;
		}

		url += contextPath;

		return url;
	}
}
