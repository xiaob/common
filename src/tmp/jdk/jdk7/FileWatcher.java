package tmp.jdk.jdk7;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileWatcher {

	private static final Logger LOG = LoggerFactory.getLogger(FileWatcher.class);
	
	private WatchService watcher;   
	private FileListener fileListener;
	private AtomicBoolean running = new AtomicBoolean(true);
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
    
    public FileWatcher()throws IOException{     
        watcher = FileSystems.getDefault().newWatchService();     
    }
    
	public void regist(FileListener fileListener, Path... paths) throws IOException {
		this.fileListener = fileListener;
		if(paths != null){
			for(Path path : paths){
				path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
						StandardWatchEventKinds.ENTRY_MODIFY);
			}
		}
	}
         
    private void handleEvents() throws InterruptedException{ 
        while(running.get()){     
            WatchKey key = watcher.take();     
            for(WatchEvent<?> event : key.pollEvents()){     
                WatchEvent.Kind<?> kind = event.kind();     
                     
                if(kind == StandardWatchEventKinds.OVERFLOW){//事件可能lost or discarded     
                    continue;     
                }     
                
                if(fileListener == null){
                	continue;
                }
                
                Path path = (Path)event.context(); 
                
                try {
					if(kind == StandardWatchEventKinds.ENTRY_CREATE){
						fileListener.fileCreated(path);
					}else if(kind == StandardWatchEventKinds.ENTRY_DELETE){
						fileListener.fileDeleted(path);
					}else if(kind == StandardWatchEventKinds.ENTRY_MODIFY){
						fileListener.fileModified(path);
					}
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
            }     
            if(!key.reset()){     
                break;     
            }     
        }     
    }     
    
    public void start(){
    	executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					handleEvents();
				} catch (Exception e) {
					LOG.warn(e.getMessage(), e);
				}
			}
		});
    }
	
	public void shutdown(){
		running.set(false);
		executorService.shutdown();
		if(watcher != null){
			try {
				watcher.close();
			} catch (IOException e) {
				LOG.warn(e.getMessage(), e);
			}
		}
	}
	
	public static void main(String args[]) throws IOException{     
        FileWatcher fileWatcher = new FileWatcher();
        fileWatcher.regist(new FileListener() {
			@Override
			public void fileModified(Path path) {
				System.out.println("fileModified:" + path);
			}
			@Override
			public void fileDeleted(Path path) {
				System.out.println("fileDeleted:" + path);
			}
			@Override
			public void fileCreated(Path path) {
				System.out.println("fileCreated:" + path);
			}
		}, Paths.get("d:/tmp"), Paths.get("d:/download"));
        
        fileWatcher.start();
    }

    
}
