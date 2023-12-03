package uz.ali.entity;

public class Contact {

    private Integer id;
    private String name;
    private String surname;
    private String phoneNumber;


    public Contact() {
    }

    public Contact(Integer id, String name, String surname, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public Contact(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
