package uz.ali;

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
                    System.out.println("Add contact");
                    break;
                case 2:
                    System.out.println("Contact List");
                    break;
                case 3:
                    System.out.println("Delete Contact");
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


}