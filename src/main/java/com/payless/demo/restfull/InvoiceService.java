package com.payless.demo.restfull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payless.demo.model.Consumer;
import com.payless.demo.model.Invoice;
import com.payless.demo.model.InvoiceProduct;
import com.payless.demo.model.Product;
import com.payless.demo.model.Rating;
import com.payless.demo.model.Stock;
import com.payless.demo.model.StockProducts;
import com.payless.demo.model.Trader;
import com.payless.demo.repositories.ConsumerRepository;
import com.payless.demo.repositories.InvoiceRepository;
import com.payless.demo.repositories.ProductRepository;
import com.payless.demo.repositories.StockRepository;
import com.payless.demo.repositories.TraderRepository;
import com.payless.demo.services.InvoiceServiceImp;

/**
 * THIS SERVICE INVOICE FOR TRADER:
 * -ADD INVOICE
 * -REMOVE INVOICE IN A TRADER
 * -DELETE ALL INVOICE IN A TRADER
 * -ADD PRODUCTS IN A INVOICE OD A TRADER
 * -DELETE A PRODUCT AN INVOICE
 * -UPDATE A QUANTITY IN A INVOICEPRODUCT
 * */


@RestController
@RequestMapping(path="/services") 
public class InvoiceService {


	@Autowired
	private TraderRepository traderRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ConsumerRepository consumerRepository;
	
	@Autowired
	private  InvoiceServiceImp invoiceServiceImp;

	@Autowired
	private  StockRepository stockRepository;

	

	@GetMapping(path = "/invoice/commentproduct", produces={MediaType.APPLICATION_JSON_VALUE})
	public Collection<Rating> getCommenstInProduct(@RequestParam("idProduct") Long idProd, @RequestParam("idInvoice") Long idInvoice){
		Invoice invoice = invoiceServiceImp.findById(idInvoice).get();
		InvoiceProduct invP = invoice.getInvoiceProductWithProduct(idProd);
		return invP.getRatings();
	}
	
	
	@GetMapping(path="/consumer/buy", produces={MediaType.APPLICATION_JSON_VALUE})
	public Map<String,String> buyProduct(@RequestParam Long idTrader, @RequestParam String item_code, @RequestParam String item_values){
	   
		System.out.println(idTrader +" -- "+ item_code+"-- "+ item_values);
		Map<String,String> map = new HashMap<String,String>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Consumer consumer = consumerRepository.queryFindByUserName(auth.getName());
	
		Trader trader =   traderRepository.findById(idTrader).get();
	    Invoice invoice = new  Invoice(trader, consumer);
		   
	    String [] codes  = item_code.split(",");
	    String[] values = item_values.split(",");
	    Stock stockTrader = trader.getStock();    
	    
	    for(int i=0;  i < codes.length;  i++){
	    	StockProducts stockProduct =  stockTrader.findProductInOwnStock(codes[i]);
	    	invoice.addInvoiceProduct(stockProduct.getProduct()   , Integer.parseInt(values[i]), stockProduct.getSalesprice()  );
	   	}
	    /*save the invoice*/
	    invoiceServiceImp.save(invoice);
	    
	    /*update quantity in products*/
	    for(int i=0;  i < codes.length;  i++){
	    	StockProducts stockProduct =  stockTrader.findProductInOwnStock(codes[i]);
	    	int quantityUpdated = stockProduct.getQuantity() - Integer.parseInt(values[i]);
	    	stockProduct.setQuantity(quantityUpdated);
	    }  
	    stockRepository.save(stockTrader);
	    System.out.println("NUMINVOICE --> " + invoice.getNumInvoice()); 
	    map.put("idinvoice", String.valueOf(invoice.getNumInvoice()));
	    
	    
	    // ModelAndView modelAndView = new ModelAndView("redirect:/consumer/newinvoice?numInvoice="+invoice.getNumInvoice());
	    // ModelAndView modelAndView = new ModelAndView("redirect:/consumer");
	    
        return map;
	}
	
	
	
	
	@GetMapping(path = "/invoice/getinvoice",produces={MediaType.APPLICATION_JSON_VALUE})
	public Invoice myPurchases(@RequestParam("numInvoice") Long numInvoice){
		Optional<Invoice>  invoice = invoiceServiceImp.findInvoiceDetails(numInvoice);
		Invoice result=null;	
		if(invoice.isPresent()){
			result=invoice.get();
		}
		System.out.println(result);
		return result;
	}

	/*ojo esta es para traer los comentarios aun no eta implementada en c_mypurchase*/
	@GetMapping(path = "/invoice/getinvoicecomments",produces={MediaType.APPLICATION_JSON_VALUE})
	public List<InvoiceProduct> getInvoiceComments(@RequestParam("numInvoice") Long numInvoice){
		Optional<Invoice>  invoice = invoiceServiceImp.findInvoiceDetails(numInvoice);
		List<InvoiceProduct> invoiceProduct = (List<InvoiceProduct>) invoice.get().getProducts();
		return invoiceProduct;
	}

	
	
	
	
	
	@PostMapping(path="/invoice/addinvoice/trader/{idtrader}/consumer/{idconsumer}")
	public Invoice addInvoice(@PathVariable("idtrader") long idTrader , @PathVariable("idconsumer") long idConsumer ){
		
		Trader traderdb = traderRepository.findById(idTrader).get();
		Consumer consumerdb = consumerRepository.findById(idConsumer).get();
		Invoice invoice= new  Invoice(traderdb,consumerdb);
		return invoiceRepository.save(invoice);
	}

	@DeleteMapping(path="/invoice/deleteinvoice/invoice/{id}")
	public boolean deleteInvoice(@PathVariable("id") long id){
		invoiceRepository.deleteById(id);
		return true;
	}

	@DeleteMapping(path="/invoice/deleteinvoiceall/trader/{id}")
	public boolean deleteInvoiceAll(@PathVariable("id") long id){
		Trader traderdb = traderRepository.findById(id).get();
		traderdb.getInvoice().clear();
		traderRepository.save(traderdb);
		return true;
	}


	public boolean isIdInListInvoices(List<Invoice> list, long id){
		boolean flag=false;
		for (int i=0 ; i<list.size() ; i++ ){
			if(list.get(i).getId() == id){
				flag=true;
				break;
			}
		}	

		return flag;
	}



	@PostMapping(path="/invoice/addproduct/trader/{id}/invoice/{idinvoice}/prod/{prod}/cantidad/{canti}")
	public boolean addProductInvoice(@PathVariable("id") long id, @PathVariable("idinvoice") long idinvoice ,
			@PathVariable("prod") long idprod, @PathVariable("canti") int cantidad){

		boolean flag=false;

		if(cantidad!=0) {
			Trader traderdb = traderRepository.findById(id).get();
			List<Invoice> listInvoices = (List<Invoice>) traderdb.getInvoice();

			if(isIdInListInvoices(listInvoices, idinvoice) == true){
				/*Invoice dbinvoice = invoiceRepository.findById(idinvoice).get();
				dbinvoice.setTrader(traderdb);
				Product dbproduct = productRepository.findById(idprod).get();
				dbinvoice.addInvoiceProduct(dbproduct, cantidad, );				
				invoiceRepository.save(dbinvoice);
				flag=true;*/
			}
		}

		return flag;

	}

	@DeleteMapping(path="/invoice/deleteinvoice/{idinvoice}/product/{idprod}")
	public boolean removeProductInvoice(@PathVariable("id") long id, @PathVariable("idprod") long idprod){

		boolean flag=false;
		Collection<InvoiceProduct>  invoiceProducDB;

		if(invoiceRepository.existsById(id)){

			Product pr =  productRepository.findById(idprod).get();
			Invoice invoicedb = invoiceRepository.findById(id).get();
			invoiceProducDB = invoicedb.getProducts();
			invoiceProducDB.removeIf((InvoiceProduct ip) -> ip.getPoduct().equals(pr));
			invoiceRepository.save(invoicedb);
			flag=true;

		}


		return flag;

	}



	@PostMapping(path="/invoice/udproduct/trader/{id}/invoice/{idinvoice}/prod/{prod}/cantidad/{canti}")
	public boolean updateQuantityProduct(@PathVariable("id") long id, @PathVariable("idinvoice") long idinvoice ,
			@PathVariable("prod") long idprod, @PathVariable("canti") int cantidad){


		boolean flag= false;

		if(cantidad!=0) {

			Trader traderdb = traderRepository.findById(id).get();
			List<Invoice> listInvoices = (List<Invoice>) traderdb.getInvoice();

			if(isIdInListInvoices(listInvoices, idinvoice) == true){

					Invoice invoidedb  = invoiceRepository.findById(idinvoice).get();
					List<InvoiceProduct>  invoiceProducDB = (List<InvoiceProduct>) invoidedb.getProducts();
					for(int i=0; i< invoiceProducDB.size();i++){
						if(invoiceProducDB.get(i).getPoduct().getId() == idprod){
					
							invoiceProducDB.get(i).setQuantity((int)cantidad);
							invoiceRepository.save(invoidedb);
							flag=true;
							break;
						}	
					}
			
			}


		}

		return flag;
	}





}
