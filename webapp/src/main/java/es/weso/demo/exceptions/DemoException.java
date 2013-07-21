package es.weso.demo.exceptions;

public class DemoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7551856720818179553L;
	
	public DemoException(String msg) {
		super(msg);
	}
	
	public DemoException(String msg, Exception e) {
		super(msg, e);
	}

	

}
