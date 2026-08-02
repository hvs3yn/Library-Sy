package com.huseyn;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @AfterEach
    void restoreSystemStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private Validator validatorWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        return new Validator();
    }

    @Test
    void readInt_returnsParsedValue() {
        Validator validator = validatorWithInput("42\n");

        assertEquals(42, validator.readInt("prompt"));
    }

    @Test
    void readInt_reprompts_onInvalidInput() {
        Validator validator = validatorWithInput("abc\n7\n");

        assertEquals(7, validator.readInt("prompt"));
    }

    @Test
    void readInt_withRange_reprompts_outOfRange() {
        Validator validator = validatorWithInput("100\n5\n");

        assertEquals(5, validator.readInt("prompt", 1, 10));
    }

    @Test
    void readDouble_returnsParsedValue() {
        Validator validator = validatorWithInput("3.5\n");

        assertEquals(3.5, validator.readDouble("prompt"));
    }

    @Test
    void readDouble_withRange_reprompts_outOfRange() {
        Validator validator = validatorWithInput("99.9\n2.5\n");

        assertEquals(2.5, validator.readDouble("prompt", 0, 10));
    }

    @Test
    void readString_reprompts_onEmptyInput() {
        Validator validator = validatorWithInput("\nhello\n");

        assertEquals("hello", validator.readString("prompt"));
    }

    @Test
    void readBookId_reprompts_onInvalidFormat() {
        Validator validator = validatorWithInput("bad-id\nB0001\n");

        assertEquals("B0001", validator.readBookId("prompt"));
    }

    @Test
    void readMemberId_reprompts_onInvalidFormat() {
        Validator validator = validatorWithInput("bad-id\nM0001\n");

        assertEquals("M0001", validator.readMemberId("prompt"));
    }

    @Test
    void readEmail_reprompts_onInvalidFormat() {
        Validator validator = validatorWithInput("not-an-email\ntest@example.com\n");

        assertEquals("test@example.com", validator.readEmail("prompt"));
    }

    @Test
    void readName_reprompts_onInvalidCharacters() {
        Validator validator = validatorWithInput("John3\nJohn Doe\n");

        assertEquals("John Doe", validator.readName("prompt"));
    }
}
