package tmp.jdk.jdk8;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class TimeTest {

	public static void main(String[] args) {
//		test1();
//		test2();
		test3();
	}
	
	public static void test1(){
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("localDateTime :" + localDateTime);

		LocalDate localDate = LocalDate.now();
		System.out.println("localDate :" + localDate);

		LocalTime localtime = LocalTime.now();
		System.out.println("localtime :" + localtime);
		
	//  获取当前年份
		Year year = Year.now();
		System.out.println("year :" + year);
		//   从Year获取LocalDate
		LocalDate localDate1 = year.atDay(59);
		System.out.println("localDate1 :" + localDate1);
		//  把LocalTime关联到一个LocalDate得到一个LocalDateTime
		LocalDateTime localDateTime1 = localtime.atDate(localDate1);
		System.out.println("localDateTime1 :" + localDateTime1);

		//  用指定的年获取一个Year
		Year year1 = Year.of(2012);
		System.out.println("year1 :" + year1);

		//  从Year获取YearMonth
		YearMonth yearMonth = year1.atMonth(2);
		System.out.println("yearMonth :" + yearMonth);
		//  YearMonth指定日得到 LocalDate
		LocalDate localDate2 = yearMonth.atDay(29);
		System.out.println("localDate2 :" + localDate2);
		//  判断是否是闰年
		System.out.println("isLeapYear :" + localDate2.isLeapYear());

		//自动处理闰年的2月日期
		//创建一个 MonthDay
		MonthDay monthDay = MonthDay.of(2, 29);
		LocalDate leapYear = monthDay.atYear(2012);
		System.out.println("leapYear :" + leapYear);

		//同一个 MonthDay 关联到另一个年份上
		LocalDate nonLeapYear = monthDay.atYear(2011);
		System.out.println("nonLeapYear :" + nonLeapYear);
	}

	public static void test2(){
		DayOfWeek dayOfWeek = DayOfWeek.of(1);
		System.out.println("dayOfWeek :" + dayOfWeek);

		LocalDate localDate = LocalDate.now();
		LocalDate leapYear = LocalDate.of(2012, 2, 28);
		//计算两个日期之间的天数，还可以按其他时间单位计算两个时间点之间的间隔。
		long between = ChronoUnit.DAYS.between(localDate, leapYear);
		System.out.println("between :" + between);

		// 线程安全的格式化类，不用每次都new个SimpleDateFormat
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu MM dd");

		LocalDate nonLeapYear = LocalDate.of(2011, 2, 28);
		//  把日期时间转换为字符串标识
		System.out.println("date formatter :" + dateTimeFormatter.format(nonLeapYear));

		//  解析字符串形式的日期时间
		TemporalAccessor temporalAccessor = dateTimeFormatter.parse("2013 01 15");
		System.out.println("temporalAccessor :" + LocalDate.from(temporalAccessor));

		Instant instant = Instant.now(); //  时间戳
		System.out.println("instant :" + instant);

		//计算某月的第一天的日期
		LocalDate with = nonLeapYear.with(TemporalAdjusters.firstDayOfMonth());
		System.out.println("with :" + with);

		// 计算某月的第一个星期一的日期
		TemporalAdjuster temporalAdjuster = TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY);
		LocalDate with1 = localDate.with(temporalAdjuster);
		System.out.println("with1 :" + with1);

		// 计算localDate的下一个星期一的日期
		LocalDate with2 = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		System.out.println("with2 :" + with2);
	}
	
	public static void test3(){
		Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
		System.out.println(instant);
		System.out.println(instant.toEpochMilli());
		LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		System.out.println(localDate.isEqual(localDate));
		
		// 时间格式化
		System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now()));
		System.out.println(DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDateTime.now()));
		
		// 计算日期间隔
		System.out.println(ChronoUnit.DAYS.between(LocalDateTime.of(2014, 5, 23, 1, 0, 0), LocalDateTime.now()));
		System.out.println(ChronoUnit.DAYS.between(LocalDateTime.of(2014, 5, 23, 20, 0, 0), LocalDateTime.now()));
		System.out.println(ChronoUnit.HOURS.between(LocalDateTime.of(2014, 5, 23, 20, 0, 0), LocalDateTime.now()));
		
		// 时区
		System.out.println(ZoneOffset.MAX);
		System.out.println(ZoneOffset.MIN);
		System.out.println(ZoneOffset.UTC);
		System.out.println(ZonedDateTime.now().getOffset());
		System.out.println(ZonedDateTime.now().getZone());
		System.out.println(ZoneOffset.ofHours(8));
		System.out.println(ZoneOffset.of("+08:00"));
		System.out.println(ZoneId.systemDefault());
		System.out.println(ZonedDateTime.now().getOffset().getId());
		System.out.println(ZonedDateTime.now().getOffset().getTotalSeconds());
		System.out.println("======================");
		
		// 日期加减
		System.out.println(LocalDateTime.now().plusDays(1));
		System.out.println(LocalDateTime.now().minusHours(1));
		
		System.out.println(OffsetDateTime.now());
		System.out.println(LocalDateTime.now());
		System.out.println(ZonedDateTime.now());
	}
	
}
