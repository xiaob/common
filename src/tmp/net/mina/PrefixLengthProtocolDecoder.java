package tmp.net.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PrefixLengthProtocolDecoder extends CumulativeProtocolDecoder {

	private static final Logger log = LoggerFactory.getLogger(PrefixLengthProtocolDecoder.class);
			
	protected abstract int getPrefixLength();
	protected abstract int getMinLength();
	
	protected abstract Object doDecode(IoSession session, IoBuffer in);
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		if(in.prefixedDataAvailable(getPrefixLength())){
			if(in.remaining() < getMinLength()){
				return false;
			}
			
			int dataLength = getDataLength(in, getPrefixLength());
			int oldLimit = in.limit();
			int start = in.position();
			try{
				in.limit(start + getPrefixLength() + dataLength);
				in.position(start + getPrefixLength());
				out.write(doDecode(session, in.slice()));
			}finally{
				in.limit(oldLimit);
				in.position(start + getPrefixLength() + dataLength);
			}
			
			return true;
		}
		
		return false;
	}
	
	private int getDataLength(IoBuffer buf, int prefixLength){
		int dataLength = 0;
		
		switch (prefixLength){
		case 1 :
			dataLength = buf.getUnsigned(buf.position());
			break;
		case 2 :
			dataLength = buf.getUnsignedShort(buf.position());
			break;
		case 4 :
			dataLength = buf.getInt(buf.position());
			break;
		default :
			throw new IllegalArgumentException("prefixLength: " + prefixLength);
		}
		return dataLength;
	}

}
