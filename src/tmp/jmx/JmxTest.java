package tmp.jmx;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class JmxTest {

	public static void main(String[] args) {
		OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
		System.out.println(os.getName());
		System.out.println(os.getVersion());
		System.out.println(os.getAvailableProcessors());
	}

}
