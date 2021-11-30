/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
"use strict";

class SaleItem {

    constructor(product, quantity) {
        // only set the fields if we have a valid product
        if (product) {
            this.product = product;
            this.quantityPurchased = quantity;
            this.salePrice = product.listPrice;
        }
    }

    getItemTotal() {
        return this.salePrice * this.quantityPurchased;
    }

}


class ShoppingCart {

    constructor() {
        this.items = new Array();
    }

    reconstruct(sessionData) {
        for (let item of sessionData.items) {
            this.addItem(Object.assign(new SaleItem(), item));
        }
    }

    getItems() {
        return this.items;
    }

    addItem(item) {
        this.items.push(item);
    }

    setCustomer(customer) {
        this.customer = customer;
    }

    getTotal() {
        let total = 0;
        for (let item of this.items) {
            total += item.getItemTotal();
        }
        return total;
    }

}

// create a new module, and load the other pluggable modules
var module = angular.module('ShoppingApp', ['ngResource', 'ngStorage']);

module.factory('productAPI', function ($resource) {
    return $resource('/api/products/:id');
});

module.factory('categoryAPI', function ($resource) {
    return $resource('/api/categories/:cat');
});

module.factory('salesAPI', function ($resource){
    return $resource('/api/sales');
});

module.factory('registerAPI', function ($resource) {
    return $resource('/api/register');
});

module.factory('signInAPI', function ($resource) {
   return $resource('/api/customers/:username');
});

module.factory('cart', function ($sessionStorage) {
    let cart = new ShoppingCart();

    // is the cart in the session storage?
    if ($sessionStorage.cart) {

        // reconstruct the cart from the session data
        cart.reconstruct($sessionStorage.cart);
    }

    return cart;
});

module.controller('ProductController', function (productAPI, categoryAPI) {
    
    this.products = productAPI.query();
    this.categories = categoryAPI.query();
    
    this.selectCategory = function (selectedCat) {
        this.products = categoryAPI.query({"cat": selectedCat});
    };
    this.viewAll = function(){
        this.products = productAPI.query();
    };
    
});

module.controller('CustomerController', function (registerAPI, $window, signInAPI, $sessionStorage) {
    this.signedIn=false;
    
    let ctrl = this;
    this.registerCustomer = function (customer) {
        
        registerAPI.save(null, customer,

        // success callback
            function () {
                $window.location = 'signin.html';
            },

            // error callback
            function (error) {
                console.log(error);
            }
        );
    };
    
    this.signInMessage = "Please sign in to continue.";
    
    
    

    this.signIn = function (username, password) {

        // get customer from web service
        signInAPI.get({'username': username},
            // success callback
            function (customer) {
                // also store the retrieved customer
                $sessionStorage.customer = customer;

                // redirect to home
                $window.location = 'index.html';
            },
            // fail callback
            function () {
                ctrl.signInMessage = 'Sign in failed. Please try again.';
            }
        );
    };
    
    this.checkSignIn = function () {
            if ($sessionStorage.customer) {
                ctrl.signedIn = true;
                ctrl.welcome = "Welcome " + $sessionStorage.customer.firstName + " " + $sessionStorage.customer.surname;
            }
        };
        
    this.signOut=function(){
        $sessionStorage.$reset();
        ctrl.signedIn = false;
        $window.location = 'index.html';
    };
});


module.controller('ShoppingCartController', function(cart, $sessionStorage, $window, salesAPI){
    this.items = cart.getItems();
    this.total = cart.getTotal();
    this.selectedProduct = $sessionStorage.selectedProduct;
    
    this.buyItem=function(product){
        $sessionStorage.selectedProduct = product;
        
        $window.location = 'quantityToPurchase.html';
        
    };
    
    this.addToCart=function(quantity){
        let saleItem = new SaleItem(this.selectedProduct, quantity);
        
        cart.addItem(saleItem);
        $sessionStorage.cart=cart;
        
        $window.location = 'viewProducts.html';
      
    };
    
    this.checkOutCart=function(){
        
        cart.setCustomer($sessionStorage.customer);
        salesAPI.save(null, cart,

        // success callback
            function () {
                delete $sessionStorage.cart;
                $window.location = 'thankYou.html';
            },

            // error callback
            function (error) {
                console.log(error);
            }
        );        
    };
    
    
    
});
