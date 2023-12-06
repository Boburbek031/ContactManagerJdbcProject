package uz.ali.service;

import uz.ali.controller.ContactController;
import uz.ali.dto.ContactDto;
import uz.ali.repository.ContactRepository;

import java.util.List;

// Service class da biznes logika yoziladi, asosiy kod va mohiyat shu classda yoziladi.
// Asosan Service classi Repository bilan aloqa qilib ishledi
public class ContactService {

    private final ContactRepository contactRepository = new ContactRepository();

    public void addContact(ContactDto contact) {
        ContactDto contactExists = contactRepository.getContactByPhoneNumber(contact.getPhoneNumber().replaceAll("[^0-9]", ""));
        if (contactExists != null) {
            System.out.println("There is a contact with such phone number " + contactExists.getPhoneNumber());
        } else {
            boolean savedContact = contactRepository.saveContact(contact);
            if (savedContact) {
                System.out.println("Contact saved.");
            } else {
                System.out.println("Error in saving contact!");
            }
        }
    }

    public void contactList() {
        List<ContactDto> allContacts = contactRepository.getAllContacts();

        // search methodida shuni yana ishlatyapman shunga boshqa bitta method qilib
        // ikkita joyda ishlatadigan qilib optimizatsiya qilib qo'y

        if (allContacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("------------------------------------------------------");
            System.out.println("|     Name       |    Surname     |   Phone number      |");
            System.out.println("------------------------------------------------------");

            for (ContactDto contactDto : allContacts) {
                String formattedContact = String.format("| %-15s| %-15s| %-20s|",
                        contactDto.getName(), contactDto.getSurname(), contactDto.getPhoneNumber());
                System.out.println(formattedContact);
            }
            System.out.println("------------------------------------------------------");
        }
    }


    public void deleteContact(String phoneNumber) {
        System.out.println(contactRepository.deleteContactFromDb(phoneNumber.replaceAll("[^0-9]", "")) == 1 ? "Contact is deleted "
                : "Contact is not deleted!");
    }


    public void searchContact(String searchTerm) {
        List<ContactDto> contactDtoList = contactRepository.searchContacts(searchTerm);
        for (ContactDto allContactDto : contactDtoList) {
            System.out.println("Name: " + allContactDto.getName() + ", Surname: " + allContactDto.getSurname()
                    + ", Phone number: " + allContactDto.getPhoneNumber());
        }

    }


}
