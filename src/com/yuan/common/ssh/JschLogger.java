/**
 * 
 */
package com.yuan.common.ssh;

import com.jcraft.jsch.Logger;

/**
 * @version 1.0 2014年7月2日
 * @since 1.6
 */
public class JschLogger implements Logger {

	@Override
	public boolean isEnabled(int level) {
		return true;
	}

	@Override
	public void log(int level, String message) {
		System.out.println(String.format("[JSCH --> %s]", message));
	}

}
