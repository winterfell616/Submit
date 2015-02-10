package com.google.gwt.messenger.shared;

public class FieldVerifier {

	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		return name.length() > 3;
	}
	
	public static boolean isMessageBodyValid(String name) {
		if (name == null) {
			return false;
		}
		return (name.length() > 0 && name.length() < 10);
	}
	
	public static boolean isGroupNumberValid(String groupNumber){
		if (groupNumber.matches( "^[0-9]$" ) ) {
			  return true;
			}
		else return false;
		
	}
}
