/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author gensa844
 */
public class ProductJdbcDAO implements ProductDAO {

	public String databaseURI = DbConnection.getDefaultConnectionUri();

	public ProductJdbcDAO() {

	}

	public ProductJdbcDAO(String databaseURI) {

	}

	@Override
	public Collection<Product> filterByCategory(String category) {
		String sql = "select * from PRODUCT where CATEGORY = ?";

		try (
				  // get a connection to the database
				  Connection dbCon = DbConnection.getConnection(databaseURI); // create the statement
				  PreparedStatement stmt = dbCon.prepareStatement(sql);) {
			stmt.setString(1, category);
			
			ResultSet rs = stmt.executeQuery();

			List<Product> plist = new ArrayList<>();

			// iterate through the query results
			while (rs.next()) {

				// get the data out of the query
				BigDecimal id = rs.getBigDecimal("PRODUCT_ID");
				String name = rs.getString("PRODUCT_NAME");
				String description = rs.getString("DESCRIPTION");
				String c = rs.getString("CATEGORY");
				BigDecimal listPrice = rs.getBigDecimal("PRICE");
				BigDecimal quantityInStock = rs.getBigDecimal("STOCK_QUANTITY");

				// use the data to create a product object
				Product p = new Product(id, name, description, c, listPrice, quantityInStock);

				// and put it in the collection
				plist.add(p);
			}

			return plist;
		} catch (SQLException ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	@Override
	public Collection<String> getCategories() {
		String sql = "select distinct CATEGORY from PRODUCT";

		try (
				  // get a connection to the database
				  Connection dbCon = DbConnection.getConnection(databaseURI);
				  // create the statement
				  PreparedStatement stmt = dbCon.prepareStatement(sql);) {
			// execute the query
			ResultSet rs = stmt.executeQuery();

			// Using a List to preserve the order in which the data was returned from the query.
			List<String> categories = new ArrayList<>();

			// iterate through the query results
			while (rs.next()) {

				// get the data out of the query
				String c = rs.getString("CATEGORY");

				// and put it in the collection
				categories.add(c);
			}
			return categories;

		} catch (SQLException ex) {
			//throw new RuntimeException(ex);
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	@Override
	public Collection<Product> getProducts() {
		String sql = "select * from PRODUCT order by PRODUCT_ID";

		try (
				  // get a connection to the database
				  Connection dbCon = DbConnection.getConnection(databaseURI); // create the statement
				  PreparedStatement stmt = dbCon.prepareStatement(sql);) {
			// execute the query
			ResultSet rs = stmt.executeQuery();

			// Using a List to preserve the order in which the data was returned from the query.
			List<Product> products = new ArrayList<>();

			// iterate through the query results
			while (rs.next()) {

				// get the data out of the query
				BigDecimal id = rs.getBigDecimal("PRODUCT_ID");
				String name = rs.getString("PRODUCT_NAME");
				String description = rs.getString("DESCRIPTION");
				String category = rs.getString("CATEGORY");
				BigDecimal listPrice = rs.getBigDecimal("PRICE");
				BigDecimal quantityInStock = rs.getBigDecimal("STOCK_QUANTITY");

				// use the data to create a student object
				Product p = new Product(id, name, description, category, listPrice, quantityInStock);

				// and put it in the collection
				products.add(p);
			}

			return products;

		} catch (SQLException ex) {  // we are forced to catch SQLException
			// don't let the SQLException leak from our DAO encapsulation
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	@Override
	public void removeProduct(Product productToRemove) {
		String sql = "delete from PRODUCT where PRODUCT_ID=?";
		try (
				  // get a connection to the database
				  Connection dbCon = DbConnection.getConnection(databaseURI); // create the statement
				  PreparedStatement stmt = dbCon.prepareStatement(sql);) {
			stmt.setBigDecimal(1, productToRemove.getProductID());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	@Override
	public void saveProduct(Product productToAdd) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		String sql = "insert into  PRODUCT(PRODUCT_ID, PRODUCT_NAME, DESCRIPTION, CATEGORY, PRICE, STOCK_QUANTITY) values (?, ? ,? ,? ,?, ?)";

		try (
				  Connection dbCon = DbConnection.getConnection(databaseURI); PreparedStatement stmt = dbCon.prepareStatement(sql);) {
			stmt.setBigDecimal(1, productToAdd.getProductID());
			stmt.setString(2, productToAdd.getName());
			stmt.setString(3, productToAdd.getDescription());
			stmt.setString(4, productToAdd.getCategory());
			stmt.setBigDecimal(5, productToAdd.getListPrice());
			stmt.setBigDecimal(6, productToAdd.getQuantityInStock());

			stmt.executeUpdate();
		} catch (SQLException ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	@Override
	public Product searchByID(BigDecimal productID) {
		String sql = "select * from PRODUCT where PRODUCT_ID = ?";

		try (
				  // get a connection to the database
				  Connection dbCon = DbConnection.getConnection(databaseURI); // create the statement
				  PreparedStatement stmt = dbCon.prepareStatement(sql);) {
			stmt.setBigDecimal(1, productID);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				BigDecimal id = rs.getBigDecimal("PRODUCT_ID");
				String name = rs.getString("PRODUCT_NAME");
				String description = rs.getString("DESCRIPTION");
				String category = rs.getString("CATEGORY");
				BigDecimal listPrice = rs.getBigDecimal("PRICE");
				BigDecimal quantityInStock = rs.getBigDecimal("STOCK_QUANTITY");

				return new Product(id, name, description, category, listPrice, quantityInStock);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}
}
