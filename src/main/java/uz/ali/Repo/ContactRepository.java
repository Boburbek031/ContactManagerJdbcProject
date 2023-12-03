package uz.ali.Repo;

import uz.ali.ContactDto;
import uz.ali.DatabaseUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

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

    public ContactDto getByPhoneNumber(String phoneNumber) {
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
        List<ContactDto> contactList = new LinkedList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement()) {
            ContactDto contact = null;
            ResultSet resultSet = statement.executeQuery("select * from contact");
            while (resultSet.next()) {
                contact = new ContactDto(
                        resultSet.getInt("Id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("phone_number"));
                contactList.add(contact);
            }
            return contactList;
        } catch (SQLException e) {
            e.printStackTrace();
            return contactList;
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


}
