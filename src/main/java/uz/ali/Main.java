package uz.ali;

import uz.ali.entity.Contact;
import uz.ali.repository.ContactRepository;
import uz.ali.service.ContactService;

import java.util.List;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Single Responsibility Principle (SRP) in Java is one of the SOLID principles of object-oriented programming.
        // It emphasizes that a class should have only one reason to change, meaning it should have only one
        // responsibility or do one thing well.
        // Dexqonchasiga aytganda, bitta class o'ziga tegishli narsalarni olib javobgarlilarni olib
        // shu ishini chotki qilishi kerak.

        start();

    }


    public static void start() {
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


    public static void showMenu() {
        System.out.println("\t****** Welcome to Contact Manager project **** \n \t\t\t ***** Menu ***** ");
        System.out.println("1. Add contact");
        System.out.println("2. Contact list");
        System.out.println("3. Delete contact");
        System.out.println("4. Search contact");
        System.out.println("0. Exit");

    }


    public static int getAction() {
        System.out.print("Enter action: ");
        return scanner.nextInt();
    }

    public static void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter surname: ");
        String surname = scanner.next();
        System.out.print("Enter phone number: ");
        String phone_number = scanner.next();
        ContactService contactService = new ContactService();
        contactService.addContact(new Contact(name, surname, phone_number));
    }

    public static void contactList() {
        ContactService contactService = new ContactService();
        contactService.contactList();
    }


    public static void deleteContact() {
        System.out.print("Enter phone number:");
        String phoneNumber = scanner.next();
        ContactService contactService = new ContactService();
        contactService.deleteContact(phoneNumber);
    }


    public static void searchContact() {
        System.out.print("Enter query: ");
        String searchTerm = scanner.next();
        ContactService contactService = new ContactService();
        contactService.searchContact(searchTerm);

    }


}