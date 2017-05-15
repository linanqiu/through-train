package engine;

public class UnorderedPair<T> {
	public T a;
	public T b;

	public UnorderedPair(T a, T b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public int hashCode() {
		return a.hashCode() + b.hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnorderedPair<T> other = (UnorderedPair<T>) obj;
		if (a == null || b == null || other.a == null || other.b == null) {
			return false;
		}
		if (other.a.equals(a) && other.b.equals(b)) {
			return true;
		}
		if (other.a.equals(b) && other.b.equals(a)) {
			return true;
		}
		return false;
	}
}
