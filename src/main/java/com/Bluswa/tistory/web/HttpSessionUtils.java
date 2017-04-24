package com.Bluswa.tistory.web;

import javax.servlet.http.HttpSession;

import com.Bluswa.tistory.domain.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "sessionedUser";//4-4 컨벤션(관습,관레)으로 대문자, 이변 수는 상수로 판단
	
	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if (sessionedUser == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}
		
		return (User)session.getAttribute(USER_SESSION_KEY); //4-4 7:50
	}

}
