/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;

/**
 *
 * @author samgentry
 */
public class SaleItem {
//fields
    private Integer quantityPurchased;
    private BigDecimal salePrice;
//relationships
    private Product product;

    public BigDecimal getItemTotal() {
        return salePrice.multiply(new BigDecimal(quantityPurchased));
    }

    public Integer getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(Integer quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "SaleItem{" + "quantityPurchased=" + quantityPurchased + ", salePrice=" + salePrice + ", product=" + product +'}';
    }

    
}
