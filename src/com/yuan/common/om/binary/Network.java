package com.yuan.common.om.binary;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class Network {
	static public final int port = 1000;

	// These IDs are used to register objects in ObjectSpaces.
	static public final short PLAYER = 1;
	static public final short CHAT_FRAME = 2;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		// This must be called in order to use ObjectSpaces.
		ObjectSpace.registerClasses(kryo);
		// The interfaces that will be used as remote objects must be registered.
//		kryo.register(Student.class);
//		kryo.register(BufferedImage.class);
//		kryo.register(DirectColorModel.class);
//		kryo.register(ICC_ColorSpace.class);
//		kryo.register(ICC_ProfileRGB.class);
//		kryo.register(IntegerInterleavedRaster.class);
//		kryo.register(float[].class);
//		kryo.register(short[].class);
//		kryo.register(byte[].class);
//		kryo.register(int[].class);
//		// The classes of all method parameters and return values
//		// for remote objects must also be registered.
//		kryo.register(String[].class);
	}
}
