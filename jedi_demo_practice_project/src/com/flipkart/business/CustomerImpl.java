package com.flipkart.business;

public class CustomerImpl implements CustomerInterface
{

	@Override
	public void createCustomer() {
		// TODO Auto-generated method stub
		System.out.println("The Create Customer Method");
		
	}

	@Override
	public boolean deleteCustomer(int id) {
		// TODO Auto-generated method stub
		System.out.println("The Delete by Id Methods--->");
		return false;
	}

	@Override
	public boolean updateCustomer(int id) {
		// TODO Auto-generated method stub
		System.out.println("Update by Ids--->");
		return false;
	}

	@Override
	public void listCustomer() {
		// TODO Auto-generated method stub
		System.out.println("List of Customers");
		
	}
	
}