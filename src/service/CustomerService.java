package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    private final Map<String, Customer> listCustomer = new HashMap<>();
    private static CustomerService  single_instance;

    public static CustomerService getInstance() {
        if (single_instance == null) {
            single_instance = new CustomerService();
        }
        return single_instance;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        listCustomer.put(email.toLowerCase(), new Customer(firstName.toLowerCase(), lastName.toLowerCase(), email.toLowerCase()));
    }

    public Customer getCustomer(String customerEmail) {
        return listCustomer.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return listCustomer.values();
    }
}
