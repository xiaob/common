package tmp.net.mina;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TailedProtocolDecoder extends CumulativeProtocolDecoder {

	private static final Logger log = LoggerFactory.getLogger(TailedProtocolDecoder.class);
	
	protected abstract ByteBuffer getDelimiter();
	
	protected abstract Object doDecode(IoSession session, IoBuffer in);
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		ByteBuffer delimiter  = getDelimiter();
		delimiter.rewind();
		if(in.limit() < delimiter.limit()){
			return false;
		}
		
		LinkedList<Byte> list = new LinkedList<Byte>(Collections.nCopies(delimiter.limit(), (byte)0));
		
		int start = in.position();
		while(in.hasRemaining()){
			list.poll();
			list.offer(in.get());
			
			if(isTailed(delimiter,list )){
				int position = in.position();
                int limit = in.limit(); 
                
                try{
	                in.position(start);
	                in.limit(position - list.size());
	                out.write(doDecode(session, in.slice()));
                }finally{
                	in.limit(limit);
                	in.position(position);
                }
                
                return true;
			}
		}
		
		in.position(start);
		return false;
	}
	
	private boolean isTailed(ByteBuffer delimiter, LinkedList<Byte> list){
		for(int i=0; i<list.size(); i++){
			if(list.get(i) != delimiter.get(i)){
				return false;
			}
		}
		return true;
	}

}
