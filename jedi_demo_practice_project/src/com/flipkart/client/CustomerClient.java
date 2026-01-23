/**
 * 
 */
package com.flipkart.client;

import com.flipkart.business.CustomerImpl;
import com.flipkart.business.CustomerInterface;

/**
 * 
 */
public class CustomerClient {
	
	public static void main(String[] args)
	{
		
	
	//Create the instance of the class here
	
	CustomerInterface customer = new CustomerImpl();
	customer.createCustomer();
	customer.listCustomer();
	System.out.println("Update Customer--->" + customer.updateCustomer(101));
	System.out.println("Delete Customer--->" + customer.deleteCustomer(101));
	
	}
}
