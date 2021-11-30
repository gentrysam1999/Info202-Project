/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author samgentry
 */
package dao;
 
import domain.Customer;
import java.util.HashMap;
import java.util.Map;
 
public final class CustomerCollectionsDAO implements CustomerDAO {
 
	private static final Map<String, Customer> customers = new HashMap<>();
 
	public CustomerCollectionsDAO() {
		// some dummy data for testing
		Customer boris = new Customer();
                boris.setCustomerID(1);
		boris.setUsername("boris");
		boris.setFirstName("Boris");
		boris.setSurname("McNorris");
		boris.setPassword("guest");
		boris.setShippingAddress("123 Some Street,\nNorth East Valley,\nDunedin");
		boris.setEmailAddress("boris@example.net");
 
		Customer doris = new Customer();
                doris.setCustomerID(2);
		doris.setUsername("doris");
		doris.setFirstName("Doris");
		doris.setSurname("Dolores");
		doris.setPassword("guest");
		doris.setShippingAddress("321 Anywere Ave,\nSt Clair,\nDunedin");
		doris.setEmailAddress("doris@example.net");
 
		saveCustomer(boris);
		saveCustomer(doris);
	}
 
	@Override
	public void saveCustomer(Customer customer) {
		System.out.println("Saving customer: " + customer);
		customers.put(customer.getUsername(), customer);
	}
 
	@Override
	public Customer getCustomer(String username) {
		return customers.get(username);
	}
 
	@Override
	public Boolean validateCredentials(String username, String password) {
		if (customers.containsKey(username)) {
			return customers.get(username).getPassword().equals(password);
		} else {
			return false;
		}
	}
 
}
