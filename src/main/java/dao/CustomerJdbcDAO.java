/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author samgentry
 */
public class CustomerJdbcDAO implements CustomerDAO {

    public String databaseURI = DbConnection.getDefaultConnectionUri();

    public CustomerJdbcDAO() {

    }

    public CustomerJdbcDAO(String databaseURI) {
        //this.databaseURI = newDatabaseURI;
    }

    @Override
    public void saveCustomer(Customer customer){
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	String sql = "insert into Customer(Customer_Username, Password, First_Name, Last_Name, Address, Email) values (?, ?, ?, ?, ?, ?)";

	try (
                Connection dbCon = DbConnection.getConnection(databaseURI); PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {

                stmt.setString(1, customer.getUsername());
                stmt.setString(2, customer.getPassword());
                stmt.setString(3, customer.getFirstName());
                stmt.setString(4, customer.getSurname());
                stmt.setString(5, customer.getShippingAddress());
                stmt.setString(6, customer.getEmailAddress());

                stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	@Override
    public Customer getCustomer(String username) {

        String sql = "select * from Customer where Customer_Username = ?";

        try (
                Connection dbCon = DbConnection.getConnection(databaseURI); PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {
                stmt.setString(1, username);

                ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Integer id = rs.getInt("Customer_ID");
                String uname = rs.getString("Customer_Username");
                String password = null;
                String fname = rs.getString("First_Name");
                String lname = rs.getString("Last_Name");
                String address = rs.getString("Address");
                String email = rs.getString("Email");

                Customer c = new Customer();

                c.setCustomerID(id);
                c.setUsername(uname);
                c.setPassword(password);
                c.setFirstName(fname);
                c.setSurname(lname);
                c.setShippingAddress(address);
                c.setEmailAddress(email);

                return c;

            } else {
                return null;
            }

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean validateCredentials(String username, String password) {
        String sql = "select * from Customer where Customer_Username = ?, password = ?";

        try (
                Connection dbCon = DbConnection.getConnection(databaseURI); PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {
                
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            
            if (rs != null) {
                return true;
            } else {
                return null;
            }
            
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    } 
}


