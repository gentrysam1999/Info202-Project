/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ProductDAO;
import domain.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author samgentry
 */
public class ProductEditorDialogTest {
    
	private ProductDAO dao;
	private DialogFixture fixture;
	private Robot robot;

	@BeforeEach
	public void setUp() {
		robot = BasicRobot.robotWithNewAwtHierarchy();

		// Slow down the robot a little bit - default is 30 (milliseconds).
		// Do NOT make it less than 10 or you will have thread-race problems.
		robot.settings().delayBetweenEvents(75);

		// add some majors for testing with
		Collection<String> categories = new ArrayList<>();
		categories.add("category 1");
		categories.add("category 2");

		// create a mock for the DAO
		dao = mock(ProductDAO.class);

		// stub the getMajors method to return the test majors
		when(dao.getCategories()).thenReturn(categories);
	}

	@AfterEach
	public void tearDown() {
		// clean up fixture so that it is ready for the next test
		fixture.cleanUp();
	}
    
    @Test
    public void testSave() {
		// create the dialog passing in the mocked DAO
		ProductEditor dialog = new ProductEditor(null, true, dao);

		// use AssertJ to control the dialog
		fixture = new DialogFixture(robot, dialog);

		// show the dialog on the screen, and ensure it is visible
		fixture.show().requireVisible();

		// click the dialog to ensure the robot is focused in the correct window
		// (can get confused by multi-monitor and virtual desktop setups)
		fixture.click();		
		
		// enter some details into the UI components
		fixture.textBox("txtId").enterText("1234");
		fixture.textBox("txtName").enterText("Item 1");
                fixture.textBox("txtDescription").enterText("I am item number 1");
		fixture.comboBox("comboBoxCategory").selectItem("category 1");
                fixture.textBox("txtPrice").enterText("123");
                fixture.textBox("txtStock").enterText("123");

		// click the save button
		fixture.button("saveButton").click();

		// create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
		ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

		// verify that the DAO.save method was called, and capture the passed student
		verify(dao).saveProduct(argument.capture());

		// retrieve the passed student from the captor
		Product savedProduct = argument.getValue();

		// test that the student's details were properly saved
		assertThat("Ensure the ID was saved", savedProduct.getProductID(), is(new BigDecimal(1234)));
		assertThat("Ensure the name was saved", savedProduct.getName(), is("Item 1"));
                assertThat("Ensure the description was saved", savedProduct.getDescription(), is("I am item number 1"));
		assertThat("Ensure the category was saved", savedProduct.getCategory(), is("category 1"));
                assertThat("Ensure the price was saved", savedProduct.getListPrice(), is(new BigDecimal(123)));
                assertThat("Ensure the stock was saved", savedProduct.getQuantityInStock(), is(new BigDecimal(123)));
	}

}
