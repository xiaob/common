package tmp.avro;

import java.util.List;

import org.apache.avro.AvroRemoteException;

import tmp.avro.stub.Message;
import tmp.avro.stub.SimpleService;

public class ServiceImpl implements SimpleService {

	@Override
	public int publish(CharSequence context, List<Message> messages)throws AvroRemoteException {
		System.out.println("======= " + context);
		return 0;
	}

}
