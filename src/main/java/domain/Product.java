/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.util.Objects;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;


/**
 *
 * @author gensa844
 */
public class Product {
//fields
        @NotNull(message = "ID must be provided.")
	@NotNegative(message = "ID must be zero or greater.")
        @Length(max=10, message = "ID can't be more than 10 digits")
	private BigDecimal productID;
        
	@NotNull(message = "Name must be provided.")
	@NotBlank(message = "Name must be provided.")
	@Length(min=2, message = "Name must contain at least two characters.")
	private String name;
        
	private String description;
        
        @NotNull(message = "Category must be provided.")
	@NotBlank(message = "Category must be provided.")
        @Length(min=2, message = "Category must contain at least two characters.")
	private String category;
        
	@NotNull(message = "Price must be provided.")
	@NotNegative(message = "Price must be zero or greater.")
	private BigDecimal listPrice;
        
        @NotNull(message = "Stock must be provided.")
	@NotNegative(message = "Stock must be zero or greater.")
	private BigDecimal quantityInStock;
	
	public Product(){}
	public Product(BigDecimal productID, String name, String description, String category, BigDecimal listPrice, BigDecimal quantityInStock){
		this.productID = productID;
		this.name = name;
		this.description = description;
		this.category = category;
		this.listPrice = listPrice;
		this.quantityInStock=quantityInStock;
	}	

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.productID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productID, other.productID)) {
            return false;
        }
        return true;
    }


	public BigDecimal getProductID() {
		return productID;
	}

	public void setProductID(BigDecimal productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getListPrice() {
		return listPrice;
	}

	public void setListPrice(BigDecimal listPrice) {
		this.listPrice = listPrice;
	}

	public BigDecimal getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(BigDecimal quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
	
		@Override
	public String toString() {
		return productID + ", " + name;
	}
}
