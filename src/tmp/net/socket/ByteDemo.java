package tmp.net.socket;

public class ByteDemo {

	public static void main(String[] args) {
		print(toUnsignedShort(256));

	}
	
	public static int setBit(int value, int index){
		return value | (1 << index);
	}
//	public static int clearBit(int value, int index){
//		return value & (1 << index);
//	}
	public static int getBit(int value, int index){
		return value & (1 << index);
	}
	
	public static byte[] toUnsignedShort(int i){
		byte[] data = new byte[2];
		data[0] = (byte)((i & 0x0000ff00) >> Byte.SIZE);
		data[1] = (byte)(i & 0x000000ff);
		return data;
	}
	public static void print(byte[] data){
		for(byte d : data){
			System.out.print(d + ", ");
		}
	}

}
