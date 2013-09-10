package tmp.jmx;

public class ServerMonitor implements ServerMonitorMBean {

	private final ServerImpl target;
	private String name;
	
    public ServerMonitor(ServerImpl target){
        this.target = target;
    }

	@Override
	public long getUpTime() {
		return System.currentTimeMillis() - target.startTime;
	}

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void print() {
		System.out.println("print ... ... " + name);
		
	}

}
