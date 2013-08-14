

public class SkipNode<E> {
	private SkipNode<E> next;
	private SkipNode<E> under;
	private E value;
	private int level;
	
	public SkipNode(SkipNode<E> next, SkipNode<E> under, E value,int level) {
		super();
		this.next = next;
		this.under = under;
		this.value = value;
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public SkipNode<E> getNext() {
		return next;
	}

	public void setNext(SkipNode<E> next) {
		this.next = next;
	}

	public SkipNode<E> getUnder() {
		return under;
	}

	public SkipNode<E> setUnder(SkipNode<E> under) {
		this.under = under;
		return under;
	}

	public E getValue() {
		return value;
	}

	public void setValue(E value) {
		this.value = value;
	}	
}
