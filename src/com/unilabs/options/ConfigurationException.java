package com.unilabs.options;

import java.io.IOException;

public class ConfigurationException extends IOException {

	private static final long serialVersionUID = -7356486576002596577L;

	public ConfigurationException() {
		super("Votre installation est corrompue! Veuillez réinstaller.");
	}
}
