package tmp.j2d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 监听包装器
 * @author yuan<cihang.yuan@happyelements.com>
 *
 */
public class ListenWrapper implements ActionListener, DocumentListener {

	private Object listener;
	private ConcurrentMap<String, String> actionMappingMap = new ConcurrentHashMap<String, String>();
			
	public ListenWrapper(Object listener){
		this.listener = listener;
	}
	
	public void addActionMapping(String action, String methodName){
		actionMappingMap.put(action, methodName);
	}
	
	public static ListenWrapper getActionInstance(Object listener, String methodName){
		ListenWrapper listenWrapper = new ListenWrapper(listener);
		listenWrapper.addActionMapping("actionPerformed", methodName);
		return listenWrapper;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionName = "actionPerformed";
		if(actionMappingMap.containsKey(actionName)){
			try {
				Method method = listener.getClass().getMethod(actionMappingMap.get(actionName), ActionEvent.class);
				method.invoke(listener, e);
			} catch (NoSuchMethodException e1) {
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		execDocumentAction("insertUpdate", e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		execDocumentAction("removeUpdate", e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		execDocumentAction("changedUpdate", e);
	}
	
	private void execDocumentAction(String actionName, DocumentEvent e){
		if(actionMappingMap.containsKey(actionName)){
			try {
				Method method = listener.getClass().getMethod(actionMappingMap.get(actionName), DocumentEvent.class);
				method.invoke(listener, e);
			} catch (NoSuchMethodException e1) {
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
