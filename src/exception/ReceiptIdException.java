package exception;

public class ReceiptIdException extends Exception {

	private static final long serialVersionUID = 1L;

	public ReceiptIdException() {
		super("The receipt ID does not exist in the database.");
	}
	
	public ReceiptIdException(String e) {
		super(e);
	}

}
