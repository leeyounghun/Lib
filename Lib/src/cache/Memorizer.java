package cache;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import annotation.ThreadSafe;


import Util.ThrowUtil;

@ThreadSafe
public class Memorizer<A, V> implements Computable<A, V> {
	private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> c;

	public Memorizer(Computable c) {
		this.c = c;
	}

	@Override
	public V compute(final A key) throws InterruptedException {
		while (true) { //cahce pollution 이 일어 날수 있음
			Future<V> f = cache.get(key);
			if (f == null) {
				Callable<V> eval = new Callable<V>() {
					@Override
					public V call() throws Exception {
						return c.compute(key);
					}
				};

				FutureTask<V> ft = new FutureTask<>(eval);
				f = cache.putIfAbsent(key, ft); // atomic
				if (f == null) {
					f = ft;
					ft.run();
				};
			}

			try {
				return f.get();	// while 문 아웃
			} catch (CancellationException e) {
				cache.remove(key, f); // 삭제하고 while 문으로
			} catch (ExecutionException e) {
				throw ThrowUtil.launderThrowable(e.getCause());
			}
		}

	}
}
