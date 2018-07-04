package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session session = sessionFactory.getCurrentSession();
		
		//create a query AND sort by last name
		Query<Customer> query = session.createQuery("from Customer order by lastName", 
													 Customer.class);
		
		//get the list of customers from the query by executing it
		List<Customer> customers = query.getResultList();
		
		//return the retrieved list of customers
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		// get the current hibernate session
		Session session = sessionFactory.getCurrentSession();
		//save the customer to the database
		//session.save(customer);
		// since we want to use the same method to either update an existing customer OR save a new one... we can use method saveOrUpdate()
		// hibernate will determine if it's a new entry or an existing entry that should be updated...
		session.saveOrUpdate(customer);
		
	}

	@Override
	public Customer getCustomer(int id) {
		//get the current hibernate session
		Session session = sessionFactory.getCurrentSession();
		//retrieve/read from database reading the primary key
		Customer customer = session.get(Customer.class, id);
		
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		//get the current hibernate session
		Session session = sessionFactory.getCurrentSession();
		
		//delete the customer record from the database using the primary key
		
		//create a query which will pass in a parameter in a where clause
		Query query = session.createQuery("delete from Customer where id = :customerId");
		//set parameter to customer id
		query.setParameter("customerId", id);
		//execute
		query.executeUpdate();
		
		
	}

}
