package uz.ali.controller;

import uz.ali.db.DatabaseUtil;
import uz.ali.dto.ContactDto;
import uz.ali.service.ContactService;

import java.util.Scanner;

// Controller classini asosiy vazifasi, user dan malumotlarni olib Service classiga bervorish.
public class ContactController {

    // Optimizatsiya qilingan variyamnti ham vaqtdan yutamiz ham hotiradan
    private ContactService contactService = new ContactService();
    private Scanner scannerNum = new Scanner(System.in);
    private Scanner scannerStr = new Scanner(System.in);

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Basic validation: Check if the phone number is not null and not empty
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        // Remove non-digit characters from the phone number
        String numericPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        String trimPhoneNumber = numericPhoneNumber.trim();

        // Check if the phone number starts with the country code 998 and has a length of 12
        return trimPhoneNumber.startsWith("998") && trimPhoneNumber.length() == 12;
    }

    public boolean checkIfNumber(String input) {
        try {
            // Attempt to parse the input as an integer
            Integer.parseInt(input);
            return true; // If successful, it's a number
        } catch (NumberFormatException e) {
            return false; // If an exception occurs, it's not a number
        }
    }

    public void start() {
        DatabaseUtil.createTable();

        boolean start = true;

        while (start) {
            showMenu();
            switch (getAction()) {
                case 1:
                    System.out.println("\n ************* Add a contact *************");
                    addContact();
                    break;
                case 2:
                    System.out.println("************* Contact list *************");
                    contactList();
                    break;
                case 3:
                    System.out.println("************* Delete Contact *************");
                    deleteContact();
                    break;
                case 4:
                    System.out.println("************* Search *************");
                    searchContact();
                    break;
                case 0:
                    System.out.println("Exit");
                    start = false;
                    break;
                default:
                    System.out.println("\n Please, choose one of the following menus below!");
            }
        }
    }

    public void showMenu() {
        System.out.println("\n\t****** Welcome to Contact Manager project **** \n \t\t\t ***** Menu ***** ");
        System.out.println("1. Add a contact.");
        System.out.println("2. See all contact list.");
        System.out.println("3. Delete a contact.");
        System.out.println("4. Search a contact.");
        System.out.println("0. Exit.");
    }

    public int getAction() {
        while (true) {
            System.out.print("Choose one of the actions above: ");
            String chosenMenu = scannerStr.next();
            if (checkIfNumber(chosenMenu)) {
                return Integer.parseInt(chosenMenu);
            } else {
                showMenu();
                System.out.println("\n Please, choose one of the following menus above!");
            }
        }
    }

    public void addContact() {
        String name = getNonEmptyInput("Enter contact name: ");
        String surname = getNonEmptyInput("Enter contact surname: ");

        String phone_number = getValidPhoneNumber();
        contactService.addContact(new ContactDto(name, surname, phone_number));
    }

    private String getValidPhoneNumber() {
        scannerStr = new Scanner(System.in);
        String phone_number;
        do {
            System.out.print("Enter phone number: ");
            phone_number = scannerStr.nextLine();
            if (!isValidPhoneNumber(phone_number)) {
                System.out.println("Please enter a valid phone number!");
            }
        } while (!isValidPhoneNumber(phone_number));
        return phone_number;
    }

    public void contactList() {
        contactService.getContactList();
    }


    public void deleteContact() {
        String validPhoneNumber = getValidPhoneNumber();
        contactService.deleteContact(validPhoneNumber);
    }


    public void searchContact() {
        String searchTerm = getNonEmptyInput("Enter search term (name, surname, or phone number): ");
        contactService.searchContact(searchTerm);
    }

    public String getNonEmptyInput(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        do {
            System.out.print(message);
            input = scannerStr.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\nInput cannot be empty!");
            }
        } while (input.isEmpty());
        return input;
    }


}
