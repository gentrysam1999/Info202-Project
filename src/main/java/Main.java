
import dao.ProductJdbcDAO;
import gui.MainMenu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gensa844
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
                ProductJdbcDAO productDao = new ProductJdbcDAO();
		MainMenu frame = new MainMenu(productDao);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}
