package tmp.net.mina;

import org.apache.mina.core.buffer.IoBuffer;

public class Test {

	public static void main(String[] args) {
		IoBuffer buf = IoBuffer.allocate(4);
		buf.put((byte)3);
		buf.put((byte)'a');
		buf.put((byte)'a');
		buf.put((byte)'a');
		
		buf.position(1);
		IoBuffer buf2 = buf.slice();
		System.out.println(new String(getRemaining(buf2)));
		System.out.println(new String(getRemaining(buf2)));
		while(buf2.hasRemaining()){
			System.out.print(buf2.get() + ", ");
		}
		
	}
	private static byte[]getRemaining(IoBuffer buf){
		byte[] data = new byte[buf.remaining()];
		for(int i=0; buf.hasRemaining(); i++){
			data[i] = buf.get();
		}
		
		buf.rewind();
		return data;
	}

}
