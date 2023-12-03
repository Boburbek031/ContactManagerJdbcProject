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




}
