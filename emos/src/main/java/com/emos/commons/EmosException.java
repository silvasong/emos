package com.emos.commons;

public class EmosException extends RuntimeException {
	private static final long serialVersionUID = 2741768848973601742L;
	
	protected String errID;

	protected String msg = "";

	protected String str = "";

	public EmosException() {
		super();
	}
	
	public EmosException(Throwable ex) {
		super(ex);
		this.msg = ex.getMessage();
		this.str = "com.EmosException.EmosException:"
				+ "\n    nested exception:" + ex.toString();
	}

	public EmosException(String ID, Throwable ex) {
		super(ex);
		this.errID = ID;
		this.msg = ex.getMessage();
		this.str = "com.EmosException.EmosException:" + ex.getMessage()
				+ "\n    nested exception:" + ex.toString();
	}

	public EmosException(String ID, String message, Throwable ex) {
		super(ex);
		this.errID = ID;
		this.msg = message + " nested exception:" + ex.getMessage();
		this.str = "com.EmosException.EmosException:" + message
				+ "\n    nested exception:" + ex.toString();
	}

	public EmosException(int ID, String message, Throwable ex) {
		super(ex);
		this.errID = String.valueOf(ID);
		this.msg = message + " nested exception:" + ex.getMessage();
		this.str = "com.EmosException.EmosException:" + message
				+ "\n    nested exception:" + ex.toString();
	}

	public EmosException(String ID, String message) {
		this.errID = ID;
		this.msg = message;
		this.str = "com.EmosException.EmosException:" + message;
	}

	public EmosException(int ID, String message) {
		this.errID = String.valueOf(ID);
		this.msg = message;
		this.str = "com.EmosException.EmosException:" + message;
	}

	public EmosException(String ID) {
		this.errID = ID;
	}

	public EmosException(int ID) {
		this.errID = String.valueOf(ID);
	}

	public String getErrorID() {
		return this.errID;
	}

	@Override
	public String getMessage() {
		return this.msg;
	}
	
	@Override
	public String toString() {
		return this.str;
	}

}
