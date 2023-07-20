package server;

import java.io.Serializable;

public class Customer implements Serializable {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String password;
    private String nature;
	private String complaint;
    


    public Customer() {
    }



    
    public Customer(String customerId,String pass, String firstName, String lastName, String email, String contactNumber) {
        this.customerId = customerId;
        this.password = pass;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.nature = "";
        this.complaint = "";

    }


    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getNature() {
        return this.nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getComplaint() {
        return this.complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }


}
