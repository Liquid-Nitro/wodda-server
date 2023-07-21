package client;

import java.io.Serializable;

public class Employee implements Serializable {
    private String staffId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String role;

   
    public Employee() {
    }



    public Employee(String staffId,String password,String firstName,String lastName,String email,String contactNumber, String role) {
        this.staffId=staffId;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.contactNumber=contactNumber;
        this.role=role;
    }


    public String getstaffId() {
        return this.staffId;
    }

    public void setstaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getRole() {
        return this.role;
    }



    public void setRole(String string) {
        this.role = string;
    }

    

   


}

