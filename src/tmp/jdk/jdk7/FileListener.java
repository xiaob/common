package tmp.jdk.jdk7;

import java.nio.file.Path;

public interface FileListener {

	public void fileCreated(Path path);
	
	public void fileDeleted(Path path);
	
	public void fileModified(Path path);
	
}
