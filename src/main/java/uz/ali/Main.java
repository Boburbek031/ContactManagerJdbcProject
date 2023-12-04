package uz.ali;

import uz.ali.controller.ContactController;

// Main classini asosiy vazifasi Project ni start qilib berish, bu yerda patchi code yozilmaydi.
public class Main {
    public static void main(String[] args) {

      /*   Single Responsibility Principle (SRP) in Java is one of the SOLID principles of object-oriented programming.
         It emphasizes that a class should have only one reason to change, meaning it should have only one
         responsibility or do one thing well.
         Dexqonchasiga aytganda, bitta class o'ziga tegishli narsalarni olib javobgarlilarni olib
         shu ishini chotki qilishi kerak.
         package larni nomi kichkina xarflar bilan yozilishi kerak.*/

        ContactController controller = new ContactController();
        controller.start();

    }
}