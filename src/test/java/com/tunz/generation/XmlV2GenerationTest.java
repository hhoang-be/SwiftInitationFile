package com.tunz.generation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class XmlV2GenerationTest {

	@Test
	void main() {
		assertDoesNotThrow(() -> XmlV2Generation.main(new String[] {}));
	}
}