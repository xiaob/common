package com.yuan.common.util;

import java.util.Currency;
import java.util.Locale;

public class CurrencyUtil {
	
	public static String getDefaultCurrency(){
		return Currency.getInstance(Locale.getDefault()).getSymbol();
	}
	
	public static String getEnglishCurrency(){
		return Currency.getInstance(Locale.US).getSymbol();
	}

	public static void main(String args[]){
		System.out.println(getDefaultCurrency());
	}
}
