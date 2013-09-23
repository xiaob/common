package tmp.net.socket.udp;

public enum ServerType {

	AGENT(1),
	WORLD(2),
	GAME(3),
	DBS(4),
	BATTLE(5)
	;
	private int value;

	private ServerType(int value) {
		this.value = value;
	}
	
	public static ServerType fromValue(int v){
		for(ServerType type : ServerType.values()){
			if(type.getValue() == v){
				return type;
			}
		}
		throw new IllegalArgumentException(String.valueOf(v));
	}

	public int getValue() {
		return value;
	}
	
}
