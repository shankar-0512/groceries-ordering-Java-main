package com.groceries.app.constants;

public class ErrorConstants {
	
	private ErrorConstants() {
		//To hide the implicit public one.
	}
	
	//Will be used for Modals in screen
	public static final String USER_NOT_AVAILABLE = "USER NOT AVAILABLE";
	public static final String INVALID_PASSWORD = "INVALID PASSWORD";
	public static final String USER_ALREADY_EXISTS = "USER ALREADY EXISTS";
	public static final String PASSWORDS_DO_NOT_MATCH = "PASSWORDS DO NOT MATCH";
	public static final String PROFILE_DETAIL_NOT_AVAILABLE = "DETAILS NOT AVAILABLE, PLEASE TRY AGAIN LATER.";
	public static final String NAME_CANNOT_BE_EMPTY = "NAME CANNOT BE EMPTY!";
	public static final String INVALID_PLANET = "PLEASE SELECT A VALID PLANET.";
	public static final String NO_UPDATES = "NO UPDATES HAS BEEN MADE!";
	public static final String OTP_VERIFICATION_FAILED = "OTP VERIFICATION FAILED!";
	public static final String EMPTY_FIELDS = "PLEASE FILL ALL THE BLANKS!";
	public static final String OLD_PASSWORD_INCORRECT = "OLD PASSWORD IS INCORRECT!";
	public static final String INVALID_PASSWORD_LENGTH = "YOUR PASSWORD SHOULD BE OF MINIMUM 6 CHARACTERS LONG.";
	public static final String PASSWORD_CHANGED_SUCCESSFULLY = "YOUR PASSWORD HAS BEEN CHANGED SUCCESSFULLY.";
	
	//Response or Logger messages
	public static final String FETCH_GROCERIES_ERROR = "FETCH_GROCERIES_ERROR";
	
	

}
