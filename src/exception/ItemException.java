package exception;

public class ItemException extends Exception {

	private static final long serialVersionUID = 1L;

	public ItemException() {
		super("The item was not found in the database.");
	}

	public ItemException(String e) {
		super(e);
	}

}