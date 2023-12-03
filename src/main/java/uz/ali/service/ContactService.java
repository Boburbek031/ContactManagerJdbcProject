package uz.ali.service;

import uz.ali.dto.ContactDto;
import uz.ali.repository.ContactRepository;

import java.util.List;

// Service class da biznes logika yoziladi, asosiy kod va mohiyat shu classda yoziladi.
// Asosan Service classi Repository bilan aloqa qilib ishledi
public class ContactService {

    private ContactRepository contactRepository = new ContactRepository();

    public void addContact(ContactDto contactDto) {
        ContactDto contactDtoExists = contactRepository.getByPhoneNumber(contactDto.getPhoneNumber());
        if (contactDtoExists != null) {
            System.out.println("Bu telefon raqamlik ContactDto bor.");
        } else {
            boolean savedContact = contactRepository.saveContact(contactDto);
            if (savedContact) {
                System.out.println("ContactDto added");
            } else
                System.out.println("Error!");
        }
    }

    public void contactList() {
        List<ContactDto> allContactDtos = contactRepository.getAllContacts();
        // contact bo'masa logika yoz.
        // search methodida shuni yana ishlatyapman shunga boshqa bitta method qilib
        // ikkita joyda ishlatadigan qilib optimizatsiya qilib qo'y
        for (ContactDto allContactDto : allContactDtos) {
            System.out.println("Name: " + allContactDto.getName() + ", Surname: " + allContactDto.getSurname()
                    + ", Phone number: " + allContactDto.getPhoneNumber());
        }
    }


    public void deleteContact(String phoneNumber) {
//        ContactDto byPhoneNumber = contactRepository.getByPhoneNumber(phoneNumber);
//        if (byPhoneNumber != null) {
        System.out.println(contactRepository.deleteContactFromDb(phoneNumber) == 1 ? "ContactDto is deleted "
                : "ContactDto is not deleted!");
        //        else {
//            System.out.println("There is no such ContactDto!");
//        }
    }


    public void searchContact(String searchTerm) {
        List<ContactDto> contactDtoList = contactRepository.searchContacts(searchTerm);
        for (ContactDto allContactDto : contactDtoList) {
            System.out.println("Name: " + allContactDto.getName() + ", Surname: " + allContactDto.getSurname()
                    + ", Phone number: " + allContactDto.getPhoneNumber());
        }

    }


}
