/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.SaleDAO;
import domain.Sale;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author samgentry
 */
public class SaleModule extends Jooby{
    public SaleModule(SaleDAO saleDao){
        post("/api/sales", (req, rsp) -> {
            Sale sale = req.body().to(Sale.class);
            System.out.println(sale);
            saleDao.save(sale);
            rsp.status(Status.CREATED);
            
        });
    }
}
