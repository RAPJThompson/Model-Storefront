package exception;

public class QuantityException extends Exception {

	private static final long serialVersionUID = 1L;

	public QuantityException() {
		super("The item does not have sufficient stock.");
	}

	public QuantityException(String e) {
		super(e);
	}

}