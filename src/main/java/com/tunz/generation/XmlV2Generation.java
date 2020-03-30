package com.tunz.generation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class XmlV2Generation {

	private static final Logger LOGGER = LogManager.getLogger();
	private static final int PREFIX_LENGTH = 31;
	private static final byte NULL_BYTE = 0x0;
	private static final byte ZERO_PADDING = 0x30;
	private static final byte RECORD_SEPARATOR = 0x1E;


	public static void main(String[] args) throws IllegalArgumentException, IOException {
		String payloadPath = "target/classes/initiation_file.xml";
		ArgParser argParser = new ArgParser(args);
		if (!argParser.contains("-f")) {
			LOGGER.info("No -f switch found. Using default configuration finder.");
		}
		else {
			payloadPath = argParser.get("-f");
		}

		XmlV2Detail xmlV2Detail = new XmlV2Detail(payloadPath);
		new XmlV2Generation().generateInitiationFile(xmlV2Detail);
	}

	void generateInitiationFile(XmlV2Detail xmlv2Detail) throws IOException {
		byte[] fileBytes = Files.readAllBytes(xmlv2Detail.getPayloadPath());
		ByteBuffer bytePrefix = initiationPrefix(fileBytes);
		ByteBuffer fileByteGenerated = initiationBytes(fileBytes, bytePrefix);
		Files.write(xmlv2Detail.getInitialisationPath(), fileByteGenerated.array());
	}

	private ByteBuffer initiationPrefix(byte[] fileBytes) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(PREFIX_LENGTH);
		byteBuffer.put(RECORD_SEPARATOR);
		String lengthStr = String.valueOf(fileBytes.length);

		if (lengthStr.length() > 6) {
			throw new IOException("Payload file too big");
		}

		// 6 byte payload length in bytes
		// Zero padding
		for (int i = lengthStr.length(); i < 6; i++) {
			byteBuffer.put(ZERO_PADDING);
		}

		// Real length
		byte[] lengthBytes = lengthStr.getBytes(StandardCharsets.US_ASCII);
		for (byte b : lengthBytes) {
			byteBuffer.put(b);
		}

		// 24 bytes signature - NULL if not needed
		for (int i = 0; i < 24; i++) {
			byteBuffer.put(NULL_BYTE);
		}
		return byteBuffer;
	}

	private ByteBuffer initiationBytes(byte[] fileBytes, ByteBuffer bytePrefix) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(fileBytes.length + PREFIX_LENGTH);
		byteBuffer.put(bytePrefix.array()).put(fileBytes);
		return byteBuffer;
	}
}
