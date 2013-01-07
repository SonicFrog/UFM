package com.unilabs.io;

import java.io.IOException;

public class CorruptedFileException extends IOException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 8017065060163292506L;

	public CorruptedFileException() {
		super();
	}
	
	public CorruptedFileException(String reason) {
		super(reason);
	}
}
