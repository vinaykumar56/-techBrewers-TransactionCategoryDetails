package com.ibm.transactioncategory.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.ibm.transactioncategory.api.model.CustomerCategoryDetails;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/transationcategory")
public class RestController {

	@Autowired
	private Database db;
	
	@RequestMapping(method=RequestMethod.POST, path="/add",consumes = "application/json")
	public @ResponseBody String addCustomerTransactionCategory(@RequestBody List<CustomerCategoryDetails> tds) {
		System.out.println("aggregate category method..... start");
		Response r= null;
		System.out.println("transaction Details="+tds);
		if(tds!=null) {
			for(CustomerCategoryDetails td: tds) {
			System.out.println(td.toString());
			r = db.post(td);
			}
		}
		
		System.out.println("aggregate category method..... END");
		return r.getId();
	}

	@RequestMapping(method=RequestMethod.GET, path="/get")
	public ResponseEntity<List<CustomerCategoryDetails>> getCustomerTransactionCategory(@RequestParam(required=false) Integer customerId) {

		System.out.println("aggregate category method get transactions..... start");
		List<CustomerCategoryDetails> allDocs = null;
		
			try {
				if(customerId==null) {
				allDocs = db.getAllDocsRequestBuilder().includeDocs(true)
						.build()
						.getResponse()
						.getDocsAs(CustomerCategoryDetails.class);
				}
				else {
////					db.createIndex("querybycustomerIdView","designdoc","json", new IndexField()[] {new IndexField("",)});
//					allDocs = db.find(TransactionDetails.class, customerId);
//					db.
					
					System.out.println("------------------In else --------");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		System.out.println("aggregate category method get transctions..... END"+allDocs);
		return new ResponseEntity<>(allDocs, HttpStatus.OK);
	}


	@RequestMapping(method=RequestMethod.PUT, path="/update",consumes = "application/json")
	public @ResponseBody void updateCustomerTransactionCategory(@RequestBody List<CustomerCategoryDetails> ccdList) {
		System.out.println("update category details method..... start");
		Response r = null;
		System.out.println("category Details=" + ccdList);
		if (ccdList != null) {
			for (CustomerCategoryDetails ccd : ccdList) {
				System.out.println(ccd.toString());
				r = db.update(ccd);
			}
		}
	}
}
