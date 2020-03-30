package com.tunz.generation;

import java.nio.file.Path;
import java.nio.file.Paths;

public class XmlV2Detail {

	private String payloadPath;
	private String initialisationPath;

	XmlV2Detail(String filePath) {
		this.payloadPath = filePath;
		this.initialisationPath = filePath.replace(".xml", ".xmlv2");
	}

	public Path getPayloadPath() {
		return Paths.get(payloadPath);
	}

	public Path getInitialisationPath() {
		return Paths.get(initialisationPath);
	}
}
