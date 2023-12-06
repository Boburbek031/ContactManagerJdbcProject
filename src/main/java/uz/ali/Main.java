package uz.ali;

import uz.ali.controller.ContactController;

// Main classini asosiy vazifasi Project ni start qilib berish, bu yerda patchi code yozilmaydi.
public class Main {
    public static void main(String[] args) {

        ContactController controller = new ContactController();
        controller.start();

    }
}