package com.huseyn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void constructorWithId_setsAllFields() {
        Member member = new Member("M1", "John", "Doe", 25, "john@example.com");

        assertEquals("M1", member.getId());
        assertEquals("John", member.getFirstName());
        assertEquals("Doe", member.getLastName());
        assertEquals(25, member.getAge());
        assertEquals("john@example.com", member.getEmail());
    }

    @Test
    void constructorWithoutId_generatesSixDigitNumericId() {
        Member member = new Member("John", "Doe", 25, "john@example.com");

        assertNotNull(member.getId());
        assertTrue(member.getId().matches("\\d{6}"));
    }

    @Test
    void settersUpdateFields() {
        Member member = new Member("M1", "John", "Doe", 25, "john@example.com");

        member.setFirstName("Jane");
        member.setLastName("Smith");
        member.setAge(30);
        member.setEmail("jane@example.com");

        assertEquals("Jane", member.getFirstName());
        assertEquals("Smith", member.getLastName());
        assertEquals(30, member.getAge());
        assertEquals("jane@example.com", member.getEmail());
    }

    @Test
    void toString_containsAllFields() {
        Member member = new Member("M1", "John", "Doe", 25, "john@example.com");

        String result = member.toString();

        assertTrue(result.contains("M1"));
        assertTrue(result.contains("John"));
        assertTrue(result.contains("Doe"));
        assertTrue(result.contains("25"));
        assertTrue(result.contains("john@example.com"));
    }
}
