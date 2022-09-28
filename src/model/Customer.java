package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private final String emailRegex = "^(.+)@(.+).(.+)$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email) {
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("The Email does not match the format 'name@domain.com'");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "First Name: " + firstName + "; Last Name: " + lastName + "; Email: " + email;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer custom = (Customer) obj;
        return custom.email.equals(email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
