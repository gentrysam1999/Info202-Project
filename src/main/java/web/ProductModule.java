/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.ProductDAO;
import java.math.BigDecimal;
import org.jooby.Jooby;

/**
 *
 * @author samgentry
 */
public class ProductModule extends Jooby{
    public ProductModule(ProductDAO productDao){
        get("/api/products", () -> productDao.getProducts());
        get("/api/products/:id", (req) -> {
                String id = req.param("id").value();
                BigDecimal idBD= new BigDecimal(id);
                return productDao.searchByID(idBD);
            });
        get("/api/categories", () -> productDao.getCategories());
        get("/api/categories/:category", (req) -> {
                String cat = req.param("category").value();
                return productDao.filterByCategory(cat);
            });
    }
    
}
