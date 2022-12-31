package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustumorDAOImpl implements CustomerDAO {
	
	// Inject the Session Factory to Talk to DB
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		
		// Get the current Hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Create Query ... Sort by lastName
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", 
															  Customer.class);
		
		// Execute query and get Result list
		List<Customer> customers = theQuery.getResultList();
		
		// Return results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		
		// Get Current session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Save Customer to DB
		currentSession.saveOrUpdate(theCustomer);
		
	}

	@Override
	public Customer getCustomer(int theId) {
		
		// Get Current session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Read/retrieve from DB using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		
		// Get Current session
		Session currentSession = sessionFactory.getCurrentSession();
		Query<?> deleteQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		deleteQuery.setParameter("customerId", theId);
		
		// executeUpdate() For all CRUD Operations
		deleteQuery.executeUpdate();
		
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		// get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
		
        Query<Customer> theQuery = null;
        
        //
        // only search by name if theSearchName is not empty
        //
        if(theSearchName != null && theSearchName.trim().length() > 0) {
        	 // search for firstName or lastName ... case insensitive
            theQuery =currentSession.createQuery(
            				"from Customer where lower(firstName) like :theName or lower(lastName) like :theName",
            				Customer.class
            		   );
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
            
        }else {
        	// theSearchName is empty ... so just get all customers
            theQuery =currentSession.createQuery("from Customer", Customer.class); 
        }
        
        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();
        
        // return the results        
        return customers;
	}

}
