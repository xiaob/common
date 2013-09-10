package tmp.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class JmxTool {

	public void registMBean(String groupName, String id, Object mBean, MBeanServer mBeanServer) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException{
		ObjectName objectName = new ObjectName(groupName + ":id=" + id);
		mBeanServer.registerMBean(mBean, objectName);
	}
	public void registMBean(String groupName, String id, Object mBean) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException{
		registMBean(groupName, id, mBean, ManagementFactory.getPlatformMBeanServer());
	}
	
}
