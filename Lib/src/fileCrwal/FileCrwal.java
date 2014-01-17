package fileCrwal;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;


public class FileCrwal implements Runnable{
	private final BlockingQueue<File> fileQueue;
	private final ConcurrentHashMap<String, String> index;
	private final File root;

	public FileCrwal(BlockingQueue<File> q, File r, ConcurrentHashMap<String, String>  index){
		this.fileQueue = q;
		this.index = index;
		this.root = r;
	}

	@Override
	public void run() {
		try {
			crwal(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void crwal(File root) throws InterruptedException {
		File[] entries = root.listFiles();
		for (File entry : entries) {
			if(entry.isDirectory()){
				crwal(entry);
			}
			else if(!alreadyIndexed(entry)){
				/*while(!fileQueue.offer(entry)){
					Thread.sleep(10);
					System.out.println("fileCrwal sleep : " + this.hashCode() + " : i am sleep");
				}*/
			}
		}
	}

	private boolean alreadyIndexed(File entry) {
		boolean result = index.putIfAbsent(entry.getAbsolutePath(), "") != null;
		System.out.println(":" +result+ ": " + this.hashCode() +  "   "  + entry.getAbsolutePath() + "  ");
		return result;
	}
}
