package com.yuan.common.jnative;

import java.util.Calendar;
import java.util.Date;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;

public class WindowsImpl implements JNative{

	public static class SYSTEMTIME extends Structure {
	    public short wYear;
	    public short wMonth;
	    public short wDayOfWeek;
	    public short wDay;
	    public short wHour;
	    public short wMinute;
	    public short wSecond;
	    public short wMilliseconds;
	}
	
	public interface Kernel32 extends Library{
		
		public boolean SetLocalTime(SYSTEMTIME st); //带时区
		public int GetCurrentProcessId();
		
	}
	
	public static Kernel32 kernel32Instance = null;
	
	public WindowsImpl(){
		kernel32Instance = (Kernel32)Native.loadLibrary("kernel32", Kernel32.class);
	}
	
	@Override
	public void setLocalTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		SYSTEMTIME st = new SYSTEMTIME();
		st.wYear = (short)c.get(Calendar.YEAR);
		st.wMonth = (short)(c.get(Calendar.MONTH) + 1);
		st.wDay = (short)c.get(Calendar.DAY_OF_MONTH );
		st.wDayOfWeek = (short)c.get(Calendar.DAY_OF_WEEK);
		st.wHour = (short)c.get(Calendar.HOUR_OF_DAY);
		st.wMinute = (short)c.get(Calendar.MINUTE);
		st.wSecond = (short)c.get(Calendar.SECOND);
		st.wMilliseconds = (short)c.get(Calendar.MILLISECOND);
		
		kernel32Instance.SetLocalTime(st);
	}
	
	public long getPid(){
		return kernel32Instance.GetCurrentProcessId();
	}
	
	public static void main(String args[]){
		WindowsImpl windows = new WindowsImpl();
		System.out.println(windows.getPid());
	}

}
