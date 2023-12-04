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
                    System.out.println(" ************* Add a contact *************");
                    addContact();
                    break;
                case 2:
//                    System.out.println("ContactDto List");
                    contactList();
                    break;
                case 3:
//                    System.out.println("Delete ContactDto");
                    deleteContact();
                    break;
                case 4:
//                    System.out.println("Search");
                    searchContact();
                    break;
                case 0:
                    System.out.println("Exit");
                    start = false;
                    break;
                default:
                    System.out.println("\n Please, choose one of the following menus below!\n");
            }
        }
    }

    public void showMenu() {
        System.out.println("\t****** Welcome to Contact Manager project **** \n \t\t\t ***** Menu ***** ");
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
                System.out.println("\n Please, choose one of the following menus above!\n");
            }
        }
    }

    public void addContact() {
        System.out.print("Enter name: ");
        String name = scannerStr.next();
        System.out.print("Enter surname: ");
        String surname = scannerStr.next();
        System.out.print("Enter phone number: ");
        String phone_number = scannerStr.next();
        contactService.addContact(new ContactDto(name, surname, phone_number));
    }

    public void contactList() {
        contactService.contactList();
    }


    public void deleteContact() {
        System.out.print("Enter phone number:");
        String phoneNumber = scannerStr.next();
        contactService.deleteContact(phoneNumber);
    }


    public void searchContact() {
        System.out.print("Enter query: ");
        String searchTerm = scannerStr.next();
        contactService.searchContact(searchTerm);
    }


}
