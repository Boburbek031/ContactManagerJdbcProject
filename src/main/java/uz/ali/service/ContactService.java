package uz.ali.service;

import uz.ali.entity.Contact;
import uz.ali.repository.ContactRepository;

import java.util.List;

// Service class da biznes logika yoziladi, asosiy kod va mohiyat shu classda yoziladi.
// Asosan Service classi Repository bilan aloqa qilib ishledi
public class ContactService {

    private ContactRepository contactRepository = new ContactRepository();

    public void addContact(Contact contact) {
        Contact contactExists = contactRepository.getByPhoneNumber(contact.getPhoneNumber());
        if (contactExists != null) {
            System.out.println("Bu telefon raqamlik Contact bor.");
        } else {
            boolean savedContact = contactRepository.saveContact(contact);
            if (savedContact) {
                System.out.println("Contact added");
            } else
                System.out.println("Error!");
        }
    }

    public void contactList() {
        List<Contact> allContacts = contactRepository.getAllContacts();
        // contact bo'masa logika yoz.
        // search methodida shuni yana ishlatyapman shunga boshqa bitta method qilib
        // ikkita joyda ishlatadigan qilib optimizatsiya qilib qo'y
        for (Contact allContact : allContacts) {
            System.out.println("Name: " + allContact.getName() + ", Surname: " + allContact.getSurname()
                    + ", Phone number: " + allContact.getPhoneNumber());
        }
    }


    public void deleteContact(String phoneNumber) {
//        Contact byPhoneNumber = contactRepository.getByPhoneNumber(phoneNumber);
//        if (byPhoneNumber != null) {
        System.out.println(contactRepository.deleteContactFromDb(phoneNumber) == 1 ? "Contact is deleted "
                : "Contact is not deleted!");
        //        else {
//            System.out.println("There is no such Contact!");
//        }
    }


    public void searchContact(String searchTerm) {
        List<Contact> contactList = contactRepository.searchContacts(searchTerm);
        for (Contact allContact : contactList) {
            System.out.println("Name: " + allContact.getName() + ", Surname: " + allContact.getSurname()
                    + ", Phone number: " + allContact.getPhoneNumber());
        }

    }


}
