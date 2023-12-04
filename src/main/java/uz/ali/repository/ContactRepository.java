package uz.ali.repository;

import uz.ali.dto.ContactDto;
import uz.ali.db.DatabaseUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

// repository ni asosiy vazifasi database bilan ishlash
public class ContactRepository {

    public boolean saveContact(ContactDto contactDto) {
        String insertContactQuery = "insert into contact(name, surname, phone_number) values (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertContactQuery)) {
            preparedStatement.setString(1, contactDto.getName());
            preparedStatement.setString(2, contactDto.getSurname());
            preparedStatement.setString(3, contactDto.getPhoneNumber());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ContactDto getContactByPhoneNumber(String phoneNumber) {
        String selectQuery = "select * from contact where phone_number = ?";
        ContactDto contact = null;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                contact = new ContactDto(
                        resultSet.getInt("Id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("phone_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }


    public List<ContactDto> getAllContacts() {
        List<ContactDto> contactDtoList = new LinkedList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement()) {
            ContactDto contactDto = null;
            ResultSet resultSet = statement.executeQuery("select * from contact");
            while (resultSet.next()) {
                contactDto = new ContactDto(
                        resultSet.getInt("Id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("phone_number"));
                contactDtoList.add(contactDto);
            }
            return contactDtoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return contactDtoList;
        }
    }


    public int deleteContactFromDb(String phoneNumber) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from contact where phone_number = ?")) {
            preparedStatement.setString(1, phoneNumber);
            int isDeleted = preparedStatement.executeUpdate();
            return isDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
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


}
