package cache;

import annotation.ThreadSafe;

@ThreadSafe
public class ExampleFactorizer {

	/*대충 이런식으로 캐쉬사용
	 *
	 * implements Servlet
	 * private final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>() {
		public BigInteger[] compute(BigInteger arg) {
			return factor(arg);
		}
	};
	private final Computable<BigInteger, BigInteger[]> cache = new Memorizer<BigInteger, BigInteger[]>(c);

	public void service(ServletRequest req, ServletResponse resp) {
		try {
			BigInteger i = extractFromRequest(req);
			encodeIntoResponse(resp, cache.compute(i));
		} catch (InterruptedException e) {
			encodeError(resp, "factorization interrupted");
		}
	}*/
}