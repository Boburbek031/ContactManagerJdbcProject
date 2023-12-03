package uz.ali.repository;

import uz.ali.entity.Contact;
import uz.ali.db.DatabaseUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

// repository ni asosiy vazifasi database bilan ishlash
public class ContactRepository {

    public boolean saveContact(Contact contact) {
        String insertContactQuery = "insert into contact(name, surname, phone_number) values (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertContactQuery)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getSurname());
            preparedStatement.setString(3, contact.getPhoneNumber());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Contact getByPhoneNumber(String phoneNumber) {
        String selectQuery = "select * from contact where phone_number = ?";
        Contact contact = null;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                contact = new Contact(
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


    public List<Contact> getAllContacts() {
        List<Contact> contactList = new LinkedList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement()) {
            Contact contact = null;
            ResultSet resultSet = statement.executeQuery("select * from contact");
            while (resultSet.next()) {
                contact = new Contact(
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


    public List<Contact> searchContacts(String searchTerm) {
        // ali ==> alish, alisher, alixon, alisattor ....
        // 066 ==> 0662414, 0661475 ....

        Contact contact = null;
        List<Contact> contacts = new LinkedList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            // Prepare the SQL query to search for contacts (case-insensitive)
            String query = "SELECT * FROM contact WHERE LOWER(name) LIKE LOWER(?) OR LOWER(surname) LIKE LOWER(?) OR phone_number LIKE ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchTerm + "%");
            preparedStatement.setString(2, "%" + searchTerm + "%");
            preparedStatement.setString(3, "%" + searchTerm + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Assuming Contact class with appropriate fields (name, surname, phone number)
                contact = new Contact();
                contact.setId(resultSet.getInt("id"));
                contact.setName(resultSet.getString("name"));
                contact.setSurname(resultSet.getString("surname"));
                contact.setPhoneNumber(resultSet.getString("phone_number"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }


}
