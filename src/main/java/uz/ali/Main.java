package uz.ali;

import uz.ali.Repo.ContactRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

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
                    System.out.println("Search");
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
        ContactRepository contactRepository = new ContactRepository();
        boolean savedContact = contactRepository.saveContact(new ContactDto(name, surname, phone_number));
        if (savedContact) {
            System.out.println("Contact added");
        } else
            System.out.println("Error!");
    }

    public static void contactList() {
        ContactRepository contactRepository = new ContactRepository();
        List<ContactDto> allContacts = contactRepository.getAllContacts();
        // contact bo'masa logika yoz.
        for (ContactDto allContact : allContacts) {
            System.out.println("Name: " + allContact.getName() + ", Surname: " + allContact.getSurname()
                    + ", Phone number: " + allContact.getPhoneNumber());
        }
    }


    public static void deleteContact() {
        System.out.print("Enter phone number:");
        String phoneNumber = scanner.next();
        ContactRepository contactRepository = new ContactRepository();
//        ContactDto byPhoneNumber = contactRepository.getByPhoneNumber(phoneNumber);
//        if (byPhoneNumber != null) {
        System.out.println(contactRepository.deleteContactFromDb(phoneNumber) == 1 ? "Contact is deleted "
                : "Contact is not deleted!");
    }
//        else {
//            System.out.println("There is no such Contact!");
//        }



}