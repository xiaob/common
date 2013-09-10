package tmp.jmx;

public interface ServerMonitorMBean {
	
	public long getUpTime();
	
	public String getName();
	public void setName(String name);
	
	public void print();
	
}
