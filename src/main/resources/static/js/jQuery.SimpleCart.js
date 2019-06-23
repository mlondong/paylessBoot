/*
 * jQuery Simple Shopping Cart v0.1
 * Basis shopping cart using javascript/Jquery.
 *
 * Authour : Sirisha
 */


/* '(function(){})();' this function is used, to make all variables of the plugin Private */

(function ($, window, document, undefined) {

    /* Default Options */
    var defaults = {
        cart: [],
        addtoCartClass: '.sc-add-to-cart',
        sC_Ssend: '.sc-send-form',
        cartProductListClass: '.cart-products-list',
        totalCartCountClass: '.total-cart-count',
        totalCartCostClass: '.total-cart-cost',
        showcartID : '#show-cart',
        itemCountClass : '.item-count'
    };

    function Item(name, price, count, code) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.code=code;
    }
    
    
    /*Constructor function*/
    function simpleCart(domEle, options) {

        /* Merge user settings with default, recursively */
        this.options = $.extend(true, {}, defaults, options);
        //Cart array
        this.cart = [];
        //Dom Element
        this.cart_ele = $(domEle);
        //Initial init function
        this.init();
    }


    /*plugin functions */
    $.extend(simpleCart.prototype, {
        init: function () {
            this._setupCart();
            this._setEvents();
            this._loadCart();
            this._updateCartDetails();
        },
        
        
        /*panel inicial*/
        _setupCart: function () {
            //this.cart_ele.addClass("cart-grid panel panel-defaults");
            this.cart_ele.append("<div class='panel-heading cart-heading'> ITEMS SELECTED </div>" )
            this.cart_ele.append("<div class='panel-body cart-body'>" +
            					 "<div class='cart-products-list' id='show-cart'>" +
            					 "<!-- Dynamic Code from Script comes here--></div></div>")
            this.cart_ele.append("<div class='cart-summary-container'>\n\
                                  <div class='cart-offer'></div>\n\
                                        <div class='cart-total-amount'>\n\
                                            <div>Total</div>\n\
                                            <div class='spacer'></div>\n\
                                            <div><i class='fa fa-dollar total-cart-cost'>0</i></div>\n\
                                            </div>\n\
                                            <div class='cart-checkout'>\n\
                                            \n\
                                                <button id='submitbuy' type='submit' class='btn btn-primary sc-send-form'>Buy</button>\n\
                                            \n\
                                        </div>\n\
                                 </div>");
        },
        
        _addProductstoCart: function () {
        
        },
        
        _updateCartDetails: function () {
            var mi = this;
            $(this.options.cartProductListClass).html(mi._displayCart());
            $(this.options.totalCartCountClass).html("Your Cart " + mi._totalCartCount() + " items");
            $(this.options.totalCartCostClass).html(mi._totalCartCost());
        },
        
        _setCartbuttons: function () {

        },
        
        /*eventos en botones*/
        _setEvents: function () {
            var mi = this;
            $(this.options.addtoCartClass).on("click", function (e) {
                e.preventDefault();
                
                var code= $(this).attr("data-code");
                var name = $(this).attr("data-name");
                var cost = Number($(this).attr("data-price"));
                var quantity = Number($(this).attr("data-quantity"));
                
                
                
                mi._addItemToCart(name, cost, 1, code, quantity);
                mi._updateCartDetails();
            });

            
            
            /*send values to server*/
            $(this.options.sC_Ssend).on("click", function (e) {
            	var item_code = [];
            	var item_name = [];
                var item_values = [];
            	var item_price = [];
            	var idTrader= $("#label-trader").attr("data-trader");
            	
            	
            	$("#quantityContainer > input").each(function(){
            		item_code.push($(this).attr("data-code"));
            		item_name.push($(this).attr("data-name"));
            		item_values.push($(this).val());
            		item_price.push(Number($(this).attr("data-price")));
            	});
           
            	
            	if(item_code.length>0){
		            	/*send via ajax*/
		            	$.ajax({
		            			url: "/services/consumer/buy",
		            			method:"GET",
		            			dataType: "json",
		            	        data: { 
		            					"idTrader":idTrader,
		            					item_code: decodeURIComponent(item_code),
		            					item_values: decodeURIComponent(item_values),
		            				  },
		            				  success: function(data) {
				            				console.log(data);
				            				 window.location= '/consumer/newinvoice?numInvoice='+data.idinvoice;
				            		  },
		            				  error: function(request, status, errorThrown) {
				            				console.log(request);
				            				console.log(status);
				            				console.log(errorThrown);
				            			}
		            				  
		            	});
		            	
            	}else{
            			alert("Yo need to choose an product to buy!.");
            		}	
		            	
            	
            	
            	
            });  
            
            
            $(this.options.showcartID).on("change", this.options.itemCountClass, function (e) {
                var ci = this;
                e.preventDefault();
		        var count = $(this).val();
		        var name = $(this).attr("data-name");
		        var cost = Number($(this).attr("data-price"));
		        		        
		        mi._removeItemfromCart(name, cost, count);
		        mi._updateCartDetails();
		    });

        },
        
        
        /* Helper Functions */
        _addItemToCart: function (name, price, count , code, quantity) {
           var flag=true;
        	
        	for (var i in this.cart) {
            	if (quantity != this.cart[i].count) {
            		if (this.cart[i].name === name) {
            			this.cart[i].code = code;
            			this.cart[i].count++;
            			this.cart[i].price = price * this.cart[i].count;
            			this._saveCart();
            			return;
            		}
            	}else{
            			flag=false;
            			alert("Imposible to add.. more elemets of " + name);
            	     }	
            }
          
        	if(flag==true){
        		var item = new Item(name, price, count, code);
        		this.cart.push(item);
        		this._saveCart();
        	}	
        
        },
        
        
        _removeItemfromCart: function (name, price, count) {
            for (var i in this.cart) {
                if (this.cart[i].name === name) {
                    var singleItemCost = Number(price / this.cart[i].count);
                    this.cart[i].count = count;
                    this.cart[i].price = singleItemCost * count;
                    if (count == 0) {
                        this.cart.splice(i, 1);
                    }
                    break;
                }
            }
            this._saveCart();
        },
        
        _clearCart: function () {
            this.cart = [];
            this._saveCart();
        },
        
        _totalCartCount: function () {
            return this.cart.length;
        },
        
        
        /*display cart */
        _displayCart: function () {
            var cartArray = this._listCart();
            
            console.log(cartArray);
            
            var output = "";
            if (cartArray.length <= 0) {
         
            }
            for (var i in cartArray) {
                output += "<div class='cart-each-product'>\n\
                		   <div class='name'>" + cartArray[i].code +' - '+cartArray[i].name + "</div>\n\
                		      <div id='quantityContainer' class='quantityContainer'>\n\
                            	<input type='number' class='quantity form-control item-count'  " +
                            			" data-code='" + cartArray[i].code+ "' " +
                            			" data-name='" + cartArray[i].name + "' " +
                            		    " data-price='" + cartArray[i].price + "' " +
                            		    " min='0' " +
                            		    " value=" + cartArray[i].count + " " +
                            		    " name='number'>\n\
                              </div>\n\
                              <div class='quantity-am'>" +
                           		"<i class='fa fa-dollar'>" + cartArray[i].price + "</i>" +
                              "</div>\n\
                           </div>";
            }
            return output;
        },
        _totalCartCost: function () {
            var totalCost = 0;
            for (var i in this.cart) {
                totalCost += this.cart[i].price;
            }
            return totalCost;
        },
        _listCart: function () {
            var cartCopy = [];
            for (var i in this.cart) {
                var item = this.cart[i];
                var itemCopy = {};
                for (var p in item) {
                    itemCopy[p] = item[p];
                }
                cartCopy.push(itemCopy);
            }
            return cartCopy;
        },
        _calGST: function () {
            var GSTPercent = 18;
            var totalcost = this.totalCartCost();
            var calGST = Number((totalcost * GSTPercent) / 100);
            return calGST;
        },
        _saveCart: function () {
           // localStorage.setItem("shoppingCart", JSON.stringify(this.cart));
        },
        _loadCart: function () {
            /*this.cart = JSON.parse(localStorage.getItem("shoppingCart"));
            if (this.cart === null) {
                this.cart = [];
            }*/
        }
    });
    
    
    
    /* Defining the Structure of the plugin 'simpleCart'*/
    $.fn.simpleCart = function (options) {
        return this.each(function () {
            $.data(this, "simpleCart", new simpleCart(this));
            console.log($(this, "simpleCart"));
        });
    }
    ;
})(jQuery, window, document);



