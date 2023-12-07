package uz.ali.service;

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

    public void getContactList() {
        List<ContactDto> allContacts = contactRepository.getAllContacts();
        printContactList(allContacts);
    }


    public void deleteContact(String phoneNumber) {
        System.out.println(contactRepository.deleteContactFromDb(phoneNumber.replaceAll("[^0-9]", "")) == 1 ? "Contact is deleted "
                : "Contact is not deleted!");
    }


    public void searchContact(String searchTerm) {
        List<ContactDto> contactList = contactRepository.searchContacts(searchTerm);
        if (!contactList.isEmpty()) {
            printContactList(contactList);
        } else {
            System.out.println("No matching contacts found.");
        }
    }

    public void printContactList(List<ContactDto> contactList) {
        if (contactList.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("------------------------------------------------------");
            System.out.println("|     Name       |    Surname     |   Phone number      |");
            System.out.println("------------------------------------------------------");

            for (ContactDto contactDto : contactList) {
                String formattedContact = String.format("| %-15s| %-15s| %-20s|",
                        contactDto.getName(), contactDto.getSurname(), contactDto.getPhoneNumber());
                System.out.println(formattedContact);
            }
            System.out.println("------------------------------------------------------");
        }
    }

}
