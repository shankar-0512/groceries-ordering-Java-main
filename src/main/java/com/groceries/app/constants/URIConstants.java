package com.groceries.app.constants;

public class URIConstants {
	
	private URIConstants() {
		//To hide the implicit public one.
	}
	
	public static final String LOGIN_AUTH = "/api/protected/loginAuth";
	public static final String FETCH_GROCERIES = "/api/protected/fetchGroceries";
	public static final String ORDER_GROCERIES = "/api/protected/orderGroceries";
	public static final String FETCH_PROFILE = "/api/protected/fetchProfile";

}
