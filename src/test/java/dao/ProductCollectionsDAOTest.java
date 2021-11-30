/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author samgentry
 */
public class ProductCollectionsDAOTest {
    //private ProductCollectionsDAO dao;
    private ProductDAO dao;
    private Product prod1;
    private Product prod2;
    private Product prod3;
    
    
    @BeforeEach
    public void setUp() {
        //this.prod = new Product("AA1111", "name1", "desc1", "cat1",
          //      new BigDecimal("11.00"), new BigDecimal("22"));
        //dao=new ProductCollectionsDAO();
        dao = new ProductJdbcDAO("jdbc:h2:mem:tests;INIT=runscript from 'src/main/java/dao/schema.sql'");
        prod1=new Product();
        prod2=new Product();
        prod3=new Product();
       
        
        prod1.setProductID(new BigDecimal("1111"));
        prod1.setName("name1");
        prod1.setDescription("desc1");
        prod1.setCategory("cat1");
        prod1.setListPrice(new BigDecimal("11.00"));
        prod1.setQuantityInStock(new BigDecimal("22"));
        
        prod2.setProductID(new BigDecimal("2222"));
        prod2.setName("name2");
        prod2.setDescription("desc2");
        prod2.setCategory("cat2");
        prod2.setListPrice(new BigDecimal("22.00"));
        prod2.setQuantityInStock(new BigDecimal("44"));
        
        prod3.setProductID(new BigDecimal("3333"));
        prod3.setName("name3");
        prod3.setDescription("desc3");
        prod3.setCategory("cat3");
        prod3.setListPrice(new BigDecimal("33.00"));
        prod3.setQuantityInStock(new BigDecimal("88"));
        
        dao.saveProduct(prod1);
        dao.saveProduct(prod2);
        
        
        
        
        
        
    }
    
    @AfterEach
    public void tearDown() {
        dao.removeProduct(prod1);
        dao.removeProduct(prod2);
        dao.removeProduct(prod3);
    }

    @Test
    public void testSaveProduct() {
        //can comment out the save product to check if the test fails
        dao.saveProduct(prod3);
        assertThat(dao.getProducts(), hasItem(prod3));
        assertThat(dao.getProducts(), hasSize(3));
        
    }

    @Test
    public void testGetProducts() {
        Collection<Product> products = dao.getProducts();
        //can switch the comments between the empty array and the full array to check if the tests work
        //Collection<Product> products = new ArrayList<>();
        assertThat(products, hasItem(prod1));
        assertThat(products, hasItem(prod2));
        assertThat(products, hasSize(2));
        for (Product p : products) {
            if (p.equals(prod1)) {
            assertEquals(prod1.getProductID(), p.getProductID());
            assertEquals(prod1.getName(), p.getName());
            assertEquals(prod1.getDescription(), p.getDescription());
            assertEquals(prod1.getCategory(), p.getCategory());
            assertEquals(prod1.getListPrice(), p.getListPrice());
            assertEquals(prod1.getQuantityInStock(), p.getQuantityInStock());
            }
        
        }
    }
        
        @Test
    public void testGetCategories(){
        Collection<String> allCategories = dao.getCategories();
        
        String cat1=prod1.getCategory();
        String cat2=prod2.getCategory();
        //System.out.println(allCategories);
        assertThat(allCategories, hasItem(cat1));
        assertThat(allCategories, hasItem(cat2));
        assertThat(allCategories, hasSize(2));
        
    }
    
    
    
    @Test
    public void testRemoveProduct() {
        //can comment out the remove product to check that the tests still fail
        dao.removeProduct(prod2);
        assertThat(dao.getProducts(), not(hasItem(prod2)));
        assertThat(dao.getProducts(), hasItem(prod1));
        assertThat(dao.getProducts(), hasSize(1));
    }
	 
	 @Test
    public void testSearchByID() {
		 BigDecimal id1 = prod1.getProductID();
		 BigDecimal id2 = prod2.getProductID();
		 BigDecimal idWrong = new BigDecimal("69420");
		 
		 assertThat(dao.searchByID(id1), is(prod1));
		 assertThat(dao.searchByID(id2), is(prod2));
		 assertThat(dao.searchByID(id1), not(is(prod2)));
		 assertThat(dao.searchByID(id1), not(is(nullValue())));
		 assertThat(dao.searchByID(idWrong), is(nullValue()));
		 
	 }
    
	 @Test
	 public void testFilterByCategory() {
		 String cat1=prod1.getCategory();
       String cat2=prod2.getCategory();
		 
		 assertThat(dao.filterByCategory(cat1), hasItem(prod1));
		 assertThat(dao.filterByCategory(cat2), hasItem(prod2));
		 assertThat(dao.filterByCategory(cat2),not (hasItem(prod1)));
		 assertThat(dao.filterByCategory(cat1),not (hasItem(prod2)));
		 
		 //can uncomment to check that the test fails when it's supposed to
		 //assertThat(dao.filterByCategory(cat2), hasItem(prod1));
		 
	 }
    
}
