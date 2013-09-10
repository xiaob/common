package com.yuan.common.jnative;

import java.util.Date;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

public class LinuxImpl implements JNative {

	public static class tm extends Structure{
		
		public static class ByReference extends tm implements Structure.ByReference{}
		public static class ByValue extends tm implements Structure.ByValue{}
		
		public int tm_sec;//seconds 0-61
		public int tm_min;//minutes 1-59
		public int tm_hour;//hours 0-23
		public int tm_mday;//day of the month 1-31
		public int tm_mon;//months since jan 0-11
		public int tm_year;//years from 1900
		public int tm_wday;//days since Sunday, 0-6
		public int tm_yday;//days since Jan 1, 0-365
		public int tm_isdst;//Daylight Saving time indicator
	}
	public static class timeval extends Structure{
		public static class ByReference extends timeval implements Structure.ByReference{}
		public static class ByValue extends timeval implements Structure.ByValue{}
		
		public NativeLong tv_sec; /* 秒数 */
		public NativeLong tv_usec; /* 微秒数 */
	}
	public static class timezone extends Structure{
		public static class ByReference extends timezone implements Structure.ByReference{}
		public static class ByValue extends timezone implements Structure.ByValue{}
		
		public int tz_minuteswest;
		public int tz_dsttime; 
	}
	
	public interface CLibrary extends Library{
		
		int gettimeofday(timeval.ByReference tv, timezone.ByReference tz);
		int settimeofday(timeval.ByReference tv, timezone.ByReference tz);
		
	}
	
	public static CLibrary cLibraryInstance = null;
	
	public LinuxImpl(){
		cLibraryInstance = (CLibrary)Native.loadLibrary("c", CLibrary.class);
	}
	
	@Override
	public void setLocalTime(Date date) {
		long ms = date.getTime();
		
		long s = ms / 1000; //秒
		long us = (ms % 1000) * 1000; //微秒
		
		timeval.ByReference tv = new timeval.ByReference();
		timezone.ByReference tz = new  timezone.ByReference();
		cLibraryInstance.gettimeofday(tv, tz);
		
		tv.tv_sec = new NativeLong(s);
		tv.tv_usec = new NativeLong(us);
		cLibraryInstance.settimeofday(tv, tz);
	}

}
