/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author samgentry
 */
public class ProductCollectionsDAO implements ProductDAO {
    
    
    private static Collection<Product> product = new HashSet<>();
    private static Collection<String> categories = new HashSet<>();
    private static Map<BigDecimal, Product> productIDs = new HashMap<>();
    
    
    
	@Override
    public void saveProduct(Product productToAdd){
        product.add(productToAdd);
        categories.add(productToAdd.getCategory());
        
        productIDs.put(productToAdd.getProductID(), productToAdd);
        
        //System.out.println(productToAdd);
        //System.out.println(productToAdd.getCategory());
        
    }
    
	@Override
    public Collection<Product> getProducts(){
        return product;
        
    }
    
	@Override
    public void removeProduct(Product productToRemove){
           product.remove(productToRemove);
           categories.remove(productToRemove.getCategory());
           productIDs.remove(productToRemove.getProductID());
           
    }
    
	@Override
    public Collection<String> getCategories(){
        return categories;
        
    }
    
	@Override
    public Product searchByID(BigDecimal productID){
        Product productSearched = productIDs.get(productID);
        boolean doesExist = productIDs.containsKey(productID);
        if (doesExist){
            return productSearched;
        }
        else{
            return null;
        }
    }
    
	@Override
    public Collection<Product> filterByCategory(String category){
        Multimap<String,Product> productsInCategories = ArrayListMultimap.create();
        for(Product p : product){
            productsInCategories.put(p.getCategory(), p);
        }
        Collection<Product> elements = productsInCategories.get(category);
        return elements;
        
    }
}


