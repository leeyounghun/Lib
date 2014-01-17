import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class FileIndexer implements Runnable {
	private final BlockingQueue<File> fileQueue;
	private final ConcurrentHashMap<String, String> index;
	private final static int MAX_SLEEP_CNT = 10;
	private int sleepCnt = 0;
	public FileIndexer(BlockingQueue<File> filQueue, ConcurrentHashMap<String, String> index) {
		this.fileQueue = filQueue;
		this.index = index;
	}

	@Override
	public void run() {
		try {
			while (sleepCnt < MAX_SLEEP_CNT) {
				File file = fileQueue.poll();
				if (file == null) {
					System.out.println("indexer : " + this.hashCode() + " i am sleep");
					Thread.sleep(10);
					sleepCnt++;
				} else {
					indexing(file);
				}
			}
		} catch (Exception e) {
			e.fillInStackTrace();
		}
	}

	private void indexing(File file) {
		index.putIfAbsent(file.getAbsolutePath(), file.getAbsolutePath());
	}

}
