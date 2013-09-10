package com.yuan.common.brpc.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote{

	public String hello(String messgae)throws RemoteException;
	
}
