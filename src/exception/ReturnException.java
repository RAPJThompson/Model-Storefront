package exception;

public class ReturnException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ReturnException() {
		super("The item was purchased too long ago to return.");
	}
	
}
