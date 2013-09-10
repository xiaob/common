package tmp.others;

import java.util.Date;

import tmp.others.JNATest.CLibrary.timeval;
import tmp.others.JNATest.CLibrary.timezone;
import tmp.others.JNATest.CLibrary.tm;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.ptr.LongByReference;

public class JNATest {
	
	public interface CLibrary extends Library {

        public static final CLibrary INSTANCE = (CLibrary)Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);
    
        public static class timeval extends Structure{
        	public static class ByReference extends timeval implements Structure.ByReference{}
			public static class ByValue extends timeval implements Structure.ByValue{}
			
        	public NativeLong tv_sec; /*秒*/
        	public NativeLong tv_usec; /*微秒*/
        }
        public static class timezone extends Structure{
        	public static class ByReference extends timezone implements Structure.ByReference{}
			public static class ByValue extends timezone implements Structure.ByValue{}
			
        	public int tz_minuteswest; /*和Greenwich 时间差了多少分钟*/
        	public int tz_dsttime; /*日光节约时间的状态*/
        }
        public static class time extends Structure{
        	public static class ByReference extends time implements Structure.ByReference{}
			public static class ByValue extends time implements Structure.ByValue{}
			
        	public byte ti_min; /*分钟*/
        	public byte ti_hour; /*小时*/
        	public byte ti_hund; 
        	public byte ti_sec; /*秒*/
        }
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
        
        void printf(String format, Object... args);
//        int gettimeofday (timeval.ByReference ti, timezone.ByReference tz);
        NativeLong time(LongByReference tloc); //注意返回的是秒而非毫秒
//        void gettime(time.ByReference timep);
        tm.ByReference gmtime(LongByReference time);
        tm.ByReference localtime(LongByReference time);
        void stime(LongByReference time); //设置时间
    }
	
	public static void time(){
		timeval.ByReference ti = new timeval.ByReference();
		timezone.ByReference tz = new timezone.ByReference();
		
//		CLibrary.INSTANCE.gettimeofday(ti, tz);
		System.out.println(ti.tv_sec.longValue());
//		System.out.println(ti.tv_usec.longValue());
//		System.out.println(tz.tz_minuteswest);
		System.out.println(tz.tz_dsttime);
	}
	
	public interface Kernel32 extends Library{
		
		public static final Kernel32 INSTANCE = (Kernel32)Native.loadLibrary((Platform.isWindows() ? "kernel32" : "c"), Kernel32.class);
		
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
		
		public void GetSystemTime(SYSTEMTIME st);
		public boolean SetSystemTime(SYSTEMTIME st);
		public int GetCurrentProcessId();
	}
	public interface Glibc extends Library{
		public static final Kernel32 INSTANCE = (Kernel32)Native.loadLibrary((Platform.isWindows() ? "kernel32" : "c"), Kernel32.class);
	}
	public interface TestDll extends Library{
		public static TestDll INSTANCE = (TestDll)Native.loadLibrary((Platform.isWindows() ? "libtestdll" : "c"), TestDll.class);
		
		public int say(int a, int b);
	}

	public static void main(String[] args) {
		CLibrary lib = CLibrary.INSTANCE;
		lib.printf("Hello, World\n你好");
		LongByReference tloc = new LongByReference();
		NativeLong rLong = lib.time(tloc);
		System.out.println("====" + tloc.getValue());
		System.out.println("==== " + new Date(rLong.longValue()*1000L));
		LongByReference time = new LongByReference();
		tm.ByReference tmByReference = lib.localtime(time);
		System.out.println(time.getValue());
		System.out.println(tmByReference.tm_year);
		System.out.println(tmByReference.tm_mon);
		System.out.println(tmByReference.tm_hour);
		System.out.println(tmByReference.tm_min);
		
		LongByReference timeByReference = new LongByReference();
		timeByReference.setValue(System.currentTimeMillis() + (5L*60*1000));
		lib.stime(timeByReference);
//		time.ByReference t1 = new time.ByReference();
//		lib.gettime(t1);
//		System.out.println(t1.ti_hour);
//		System.out.println(t1.ti_hund);
//		System.out.println(t1.ti_min);
//		System.out.println(t1.ti_sec);
		
//		time();
//		Kernel32 kernel32 = Kernel32.INSTANCE;
//		SYSTEMTIME st = new SYSTEMTIME();
//	    kernel32.GetSystemTime(st);
//	    System.out.printf("Year: %d%n", st.wYear);
//	    System.out.printf("Month: %d%n", st.wMonth);
//	    System.out.printf("Day: %d%n", st.wDay);
//	    System.out.printf("Hour: %d%n", st.wHour);
//	    System.out.printf("Minute: %d%n", st.wMinute);
//	    System.out.printf("Second: %d%n", st.wSecond);
//	    System.out.println(kernel32.GetCurrentProcessId());
		
//		TestDll testDll = TestDll.INSTANCE;
//		System.out.println(testDll.say(121, 232));
	}

}
