package uz.ali.repository;

import uz.ali.dto.ContactDto;
import uz.ali.db.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// repository ni asosiy vazifasi database bilan ishlash
public class ContactRepository {

    public boolean saveContact(ContactDto contact) {
        String insertContactQuery = "insert into contact(name, surname, phone_number) values (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertContactQuery)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getSurname());
            preparedStatement.setString(3, contact.getPhoneNumber().replaceAll("[^0-9]", ""));
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error while saving contact: " + e.getMessage());
            return false;
        }
    }

    public ContactDto getContactByPhoneNumber(String phoneNumber) {
        String selectQuery = "select * from contact where phone_number = ?";
        ContactDto contact = null;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null && resultSet.next()) {
                    contact = createContactFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }


    public List<ContactDto> getAllContacts() {
        List<ContactDto> contactList = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ContactDto contact = new ContactDto(
                        resultSet.getInt("Id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("phone_number"));
                contactList.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the error or handling it appropriately
        }
        return contactList;
    }


    public int deleteContactFromDb(String phoneNumber) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM contact WHERE phone_number = ?")) {
            preparedStatement.setString(1, phoneNumber);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the error or handling it appropriately
        }
        return 0;
    }


    public List<ContactDto> searchContacts(String searchTerm) {
        // ali ==> alish, alisher, alixon, alisattor ....
        // 066 ==> 0662414, 0661475 ....

        ContactDto contactDto = null;
        List<ContactDto> contactDtos = new LinkedList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Prepare the SQL query to search for contactDtos (case-insensitive)
            String query = "SELECT * FROM contact WHERE LOWER(name) LIKE LOWER(?) OR LOWER(surname) LIKE LOWER(?) OR phone_number LIKE ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchTerm + "%");
            preparedStatement.setString(2, "%" + searchTerm + "%");
            preparedStatement.setString(3, "%" + searchTerm + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Assuming ContactDto class with appropriate fields (name, surname, phone number)
                contactDto = new ContactDto();
                contactDto.setId(resultSet.getInt("id"));
                contactDto.setName(resultSet.getString("name"));
                contactDto.setSurname(resultSet.getString("surname"));
                contactDto.setPhoneNumber(resultSet.getString("phone_number"));
                contactDtos.add(contactDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactDtos;
    }

    private ContactDto createContactFromResultSet(ResultSet resultSet) {
        try {
            return new ContactDto(
                    resultSet.getInt("Id"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getString("phone_number"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
