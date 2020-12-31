package io.pricereader.batch.helpers;

public class NumberHelper {
	private NumberHelper() {}
	public static Float getValue(String value) {
		return (value != null )? Float.valueOf(value) : 0f;
	}
	
	public static Float getCurrencyValue(String value) {
		return Float.valueOf(value.replaceAll("[^0-9\\.]", ""));
	}
}
