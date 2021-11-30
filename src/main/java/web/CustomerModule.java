/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.CustomerDAO;
import domain.Customer;
import org.jooby.Jooby;
import org.jooby.Result;
import org.jooby.Status;

/**
 *
 * @author samgentry
 */
public class CustomerModule extends Jooby{
    public CustomerModule(CustomerDAO custDao){
        get("/api/customers/:username", (req) -> {
            String username = req.param("username").value();
            if(custDao.getCustomer(username)==null){
                return new Result().status(Status.NOT_FOUND);
            }else{
                return custDao.getCustomer(username);
            }
        });
        post("/api/register", (req, rsp) -> {
            Customer customer = req.body().to(Customer.class);
            custDao.saveCustomer(customer);
            rsp.status(Status.CREATED);
        });
    }
    
}
