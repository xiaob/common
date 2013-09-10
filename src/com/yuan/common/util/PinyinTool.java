package com.yuan.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinTool {

	public static void main(String[] args) {
		// 汉语拼音
		// String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray('航');
		// 通用拼音
		// String[] pinyinArr = PinyinHelper.toTongyongPinyinStringArray('中');
		// 威氏拼音
		// String[] pinyinArr = PinyinHelper.toGwoyeuRomatzyhStringArray('中');
		// 注音二式
		// String[] pinyinArr = PinyinHelper.toMPS2PinyinStringArray('中');
		// 雅礼
		// String[] pinyinArr = PinyinHelper.toWadeGilesPinyinStringArray('中');
		// 国语罗马字
//		String[] pinyinArr = PinyinHelper.toYalePinyinStringArray('中');
//		for (String pinyin : pinyinArr) {
//			System.out.println(pinyin);
//		}

//		System.out.println(getPinYin("原此航a"));
//		System.out.println(getPinYinHeadChar("原此航a"));
//		System.out.println(getCnASCII("原此航a"));
//		System.out.println(converterToFirstSpell("原此航a"));
//		System.out.println(converterToSpell("原此航a"));
//		printSpell('丄');
		System.out.println(toSpell("李盛焹"));
//		System.out.println(toSpell('丄'));
	}
	
	public static void printSpell(char chinese){
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		try {
			System.out.println("汉语拼音: ");
			String[] spellArr = PinyinHelper.toHanyuPinyinStringArray(chinese, format);
			for(String spell : spellArr){
				System.out.println(spell);
			}
			System.out.println("通用拼音: ");
			spellArr = PinyinHelper.toTongyongPinyinStringArray(chinese);
			for(String spell : spellArr){
				System.out.println(spell);
			}
			System.out.println("威氏拼音: ");
			spellArr = PinyinHelper.toGwoyeuRomatzyhStringArray(chinese);
			for(String spell : spellArr){
				System.out.println(spell);
			}
			System.out.println("注音二式: ");
			spellArr = PinyinHelper.toMPS2PinyinStringArray(chinese);
			for(String spell : spellArr){
				System.out.println(spell);
			}
			System.out.println("雅礼: ");
			spellArr = PinyinHelper.toWadeGilesPinyinStringArray(chinese);
			for(String spell : spellArr){
				System.out.println(spell);
			}
			System.out.println("国语罗马字: ");
			spellArr = PinyinHelper.toYalePinyinStringArray(chinese);
			for(String spell : spellArr){
				System.out.println(spell);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		
	}
	public static String toSpell(char chinese){
		StringBuilder pinyin = new StringBuilder();
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		try {
			if(StringUtil.isChinese(chinese)){
				String[] spellArr = PinyinHelper.toHanyuPinyinStringArray(chinese, format);
				if(spellArr != null){
					List<String> spellList = new ArrayList<String>();
					for(String spell : spellArr){
						if(spellList.contains(spell)){
							continue;
						}
						spellList.add(spell);
						pinyin.append(spell).append(" ");
					}
					pinyin = StringUtil.compareAndDeleteLastChar(pinyin, ' ');
				}
			}else{
				pinyin.append(chinese);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return pinyin.toString();
	}
	public static String toSpell(String str){
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		List<List<String>> spellList = new ArrayList<List<String>>();
		for(int i=0; i<str.length(); i++){
			char c = str.charAt(i);
			List<String> charSpell = new ArrayList<String>();
			if(StringUtil.isChinese(c)){
				try {
					String[] charSpells = PinyinHelper.toHanyuPinyinStringArray(c, format);
					if(charSpells != null){
						charSpell = Arrays.asList(charSpells);
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			}else{
				charSpell.add(Character.toString(c));
			}
			spellList.add(charSpell);
		}
		
		StringBuilder spell = new StringBuilder();
		
		for(int i=0; i<spellList.size(); i++){
			List<String> charSpellList = spellList.get(i);
			List<String> repeatSpellList = new ArrayList<String>();
			for(String charSpell : charSpellList){
				if(repeatSpellList.contains(charSpell)){
					continue;
				}
				repeatSpellList.add(charSpell);
				spell.append(charSpell).append(" ");
			}
			spell = StringUtil.compareAndDeleteLastChar(spell, ' ');
			spell.append(";");
		}
		
		spell = StringUtil.compareAndDeleteLastChar(spell, ';');
		return spell.toString();
	}

	/**
	 * 将汉字转换为全拼
	 * 
	 * @param src
	 * @return String
	 */
	public static String getPinYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		// System.out.println(t1.length);
		String[] t2 = new String[t1.length];
		// System.out.println(t2.length);
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符
				// System.out.println(t1[i]);
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return t4;
	}

	/**
	 * 提取每个汉字的首字母
	 * 
	 * @param str
	 * @return String
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	/**
	 * 将字符串转换成ASCII码
	 * 
	 * @param cnStr
	 * @return String
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		// 将字符串转换成字节序列
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// System.out.println(Integer.toHexString(bGBK[i] & 0xff));
			// 将每个字符转换成ASCII码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	  /**
	      * 汉字转换位汉语拼音首字母，英文字符不变
	      * @param chines 汉字
	      * @return 拼音
	      */  
	     public static String converterToFirstSpell(String chines){          
	          String pinyinName = "";   
	         char[] nameChar = chines.toCharArray();   
	          HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
	          defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
	          defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
	         for (int i = 0; i < nameChar.length; i++) {   
	             if (nameChar[i] > 128) {   
	                 try {   
	                      pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);   
	                  } catch (BadHanyuPinyinOutputFormatCombination e) {   
	                      e.printStackTrace();   
	                  }   
	              }else{   
	                  pinyinName += nameChar[i];   
	              }   
	          }   
	         return pinyinName;   
	      }   
	   
	     /**
	      * 汉字转换位汉语拼音，英文字符不变
	      * @param chines 汉字
	      * @return 拼音
	      */  
	     public static String converterToSpell(String chines){           
	          String pinyinName = "";   
	         char[] nameChar = chines.toCharArray();   
	          HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
	          defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
	          defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
	         for (int i = 0; i < nameChar.length; i++) {   
	             if (nameChar[i] > 128) {   
	                 try {   
	                      pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];   
	                  } catch (BadHanyuPinyinOutputFormatCombination e) {   
	                      e.printStackTrace();   
	                  }   
	              }else{   
	                  pinyinName += nameChar[i];   
	              }   
	          }   
	         return pinyinName;   
	      }   
}