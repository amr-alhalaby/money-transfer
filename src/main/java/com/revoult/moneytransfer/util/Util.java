package com.revoult.moneytransfer.util;

import java.util.MissingResourceException;

import org.apache.log4j.Logger;
import com.revoult.moneytransfer.constant.*;

public class Util {

	final static org.apache.log4j.Logger logger = Logger.getLogger(Util.class);

	public static String getErrorMessage(String code) {
		String msg = null;
		try {
			msg = Constant.ERROR_MESSAGES.getString(code);
		} catch (MissingResourceException e) {
			logger.error("Error resolving message", e);
		}
		return msg;
	}

	public static String trim(String text) {
		
		if(text != null) {
			return text.trim();
		}
		return null;
	}
	
	
	

	public static boolean isCorrectNid(String Nid) {
		logger.info("Start isCorrectNid");
		if (Nid == null) {
			logger.debug("Nid is null");
			return false;
		}
		if (Nid.length() != 14) {
			logger.debug("Nid length != 14");
			return false;
		}
		String chkDigitStr = Nid.substring(13, 14);
		int chkDigit = 99;
		try {
			chkDigit = Integer.parseInt(chkDigitStr);
		} catch (Exception ex) {
			logger.error("parseInt exception",ex);
			return false;
		}
		if (chkDigit != getChkDigit(Nid)) {
			logger.debug("wrong chkDigit");
			return false;
		}
		logger.info("End isCorrectNid");

		return true;
	}

	public static int getChkDigit(String id) {
		logger.debug("start getChkDigit id: "+id);

		int i;
		int ci;

		int IdWeights[] = { 2, 7, 6, 5, 4, 3, 2, 7, 6, 5, 4, 3, 2 };
		int IdSum = 0;
		int IdMod11;
		int idcd;
		String c;

		if (id.length() < 13) {
			return -1;
		}

		for (i = 0; i <= 12; i++) {
			c = id.substring(i, i + 1);
			try {
				ci = Integer.parseInt(c);
			} catch (Exception ex) {
				return -2;
			}
			IdSum += ci * IdWeights[i];
		}

		IdMod11 = IdSum % 11;
		if (IdMod11 < 2)
			idcd = 0;
		else
			idcd = 11 - IdMod11;

		logger.debug("end getChkDigit id: "+id);
		return idcd;

	}
	
	public static StringBuilder removeEndingChar(StringBuilder in, char c) {
		if (in.length() > 0 && in.charAt(in.length() - 1) == c) {
			return new StringBuilder(in.substring(0, in.length() - 1));
		}
		return in;
	}

	

	  public static String replaceLast(String text, String regex, String replacement) {
	        return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
	   }
	  
		public static void main(String[] args) {
			String x= "aasd and ff and and";
			x=replaceLast(x,"and"," ");
			System.out.println(x);
		}

}
