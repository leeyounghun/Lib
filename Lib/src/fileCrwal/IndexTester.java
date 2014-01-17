package fileCrwal;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;


public class IndexTester {
	private static final BlockingQueue<File> fileQueue = new LinkedBlockingDeque<File>(100);
	private static final ConcurrentHashMap<String, String> indexCheck = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, String> index = new ConcurrentHashMap<>();
	private static final File root = new File("C:\\test");
	private static final int FILE_CRWAL_CNT = 3;
	private static final int FILE_INDEXER_CNT = 3;

	public static void main(String[] args){
		System.out.println("start main ");
		for (int i = 0; i < FILE_CRWAL_CNT; i++) {
			Thread thread = new Thread(new FileCrwal(fileQueue, root, indexCheck));
			thread.start();

		}

		for (int i = 0; i < FILE_INDEXER_CNT; i++) {
			new Thread(new FileIndexer(fileQueue, index)).start();
		}
	}
}
