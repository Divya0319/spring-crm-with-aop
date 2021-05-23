package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.utils.SortingUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public List<Customer> getCustomers(int theSortField) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// determine the sort field
		String theFieldName = null;
		
		switch(theSortField) {
			case SortingUtils.FIRST_NAME:
				theFieldName = "firstName";
				break;
			
			case SortingUtils.LAST_NAME:
				theFieldName = "lastName";
				break;
				
			case SortingUtils.EMAIL:
				theFieldName = "email";
				break;	
		
			default:
				// if nothing matches the default to sort bu lastName
				theFieldName = "lastName";
		
		}
		
		// create a query  
		
		String queryString = "from Customer order by " + theFieldName;
		Query<Customer> theQuery =
				currentSession.createQuery(queryString, Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		// return the results
		return customers;
		
	}


	@Override
	public void saveCustomer(Customer theCustomer) {
		
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/ update the customer ... finally
		currentSession.saveOrUpdate(theCustomer);  // if primary key / id empty, INSERT new customer, or else UPDATE 
													// existing customer
		
		
	}


	@Override
	public Customer getCustomer(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/ read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
		
	}


	@Override
	public void deleteCustomer(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete the customer from database using the primary key
		Query theQuery = 
				currentSession.createQuery("delete from Customer where "
													+ "id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}


	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = null;
		
		//
		// only search by name if searchName is not empty
		//
		
		if(theSearchName != null && theSearchName.trim().length() > 0) {
			
			// search for firstname or lastname ..  case insensitive
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName "
					+ "or lower(lastName) like :theName", Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
			
		} else {
			// theSearchName is empty .. so just get all customers
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		
		// return the results
		return customers;
	}

	
}
