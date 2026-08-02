package com.huseyn;

import java.util.Scanner;

public class Validator {
    private static Scanner scanner;

    public Validator() {
        scanner = new Scanner(System.in);
    }

    public int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public int readInt(String message, int min, int max) {
        while (true) {
            int value = readInt(message);

            if (value >= min && value <= max) {
                return value;
            }

            System.out.printf("Please enter a value between %d and %d.%n", min, max);
        }
    }

    public double readDouble(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public double readDouble(String message, double min, double max) {
        while (true) {
            double value = readDouble(message);

            if (value >= min && value <= max) {
                return value;
            }

            System.out.printf("Please enter a value between %.2f and %.2f.%n", min, max);
        }
    }

    public String readString(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    public String readBookId(String message) {
        while (true) {
            String id = readString(message);

            if (id.matches("^B\\d{4}$")) {
                return id;
            }

            System.out.println("Book ID must be in the format B0001.");
        }
    }

    public String readMemberId(String message) {
        while (true) {
            String id = readString(message);

            if (id.matches("^M\\d{4}$")) {
                return id;
            }

            System.out.println("Member ID must be in the format M0001.");
        }
    }

    public String readEmail(String message) {
        while (true) {
            String email = readString(message);

            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                return email;
            }

            System.out.println("Please enter a valid email address.");
        }
    }

    public String readName(String message) {
        while (true) {
            String name = readString(message);

            if (name.matches("^[A-Za-z ]{2,50}$")) {
                return name;
            }

            System.out.println("Name must contain only letters and spaces.");
        }
    }

    public void close() {
        scanner.close();
    }
}