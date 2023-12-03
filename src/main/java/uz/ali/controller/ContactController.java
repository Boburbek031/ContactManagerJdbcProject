package uz.ali.controller;

import uz.ali.db.DatabaseUtil;
import uz.ali.entity.Contact;
import uz.ali.service.ContactService;

import java.util.Scanner;

// Controller classini asosiy vazifasi, user dan malumotlarni olib Service classiga bervorish.
public class ContactController {
    // Optimizatsiya qilingan variyamnti ham vaqtdan yutamiz ham hotiradan
    private ContactService contactService = new ContactService();
    private Scanner scannerNum = new Scanner(System.in);
    private Scanner scannerStr = new Scanner(System.in);

    public void start() {
        DatabaseUtil.createTable();

        boolean b = true;

        while (b) {
            showMenu();
            int action = getAction();

            switch (action) {
                case 1:
//                    System.out.println("Add contact");
                    addContact();
                    break;
                case 2:
//                    System.out.println("Contact List");
                    contactList();
                    break;
                case 3:
//                    System.out.println("Delete Contact");
                    deleteContact();
                    break;
                case 4:
//                    System.out.println("Search");
                    searchContact();
                    break;
                case 0:
                    System.out.println("Exit");
                    b = false;
                    break;
                default:
                    b = false;

            }
        }

    }


    public void showMenu() {
        System.out.println("\t****** Welcome to Contact Manager project **** \n \t\t\t ***** Menu ***** ");
        System.out.println("1. Add contact");
        System.out.println("2. Contact list");
        System.out.println("3. Delete contact");
        System.out.println("4. Search contact");
        System.out.println("0. Exit");

    }


    public int getAction() {
        System.out.print("Enter action: ");
        return scannerNum.nextInt();
    }

    public void addContact() {
        System.out.print("Enter name: ");
        String name = scannerStr.next();
        System.out.print("Enter surname: ");
        String surname = scannerStr.next();
        System.out.print("Enter phone number: ");
        String phone_number = scannerStr.next();
        contactService.addContact(new Contact(name, surname, phone_number));
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
