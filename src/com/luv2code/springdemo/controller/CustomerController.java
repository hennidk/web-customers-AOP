package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	//need to inject the Customer Service into this controller - use @Autowired to tell Container to instantiate automatically
	@Autowired
	private CustomerService customerService;
	
	/*
	 * A note on different mapping requests... @RequestMapping can be used, with a specifier attribute to limit requests to POST or GET, etc...
	 * alternatively, the new annotations @GetMapping and @PostMapping may be used instead - these simply provide a shorthand to specify which type of
	 * requests should be handled by the specific method.
	 * The @RequestMapping will handle ALL HTTP requests, unless a specific type of request is specified in the attributes.
	 */
	
	@GetMapping("/list")
	public String listCustomers(Model model) {
		
		//get customers from the DAO
		List<Customer> customers = customerService.getCustomers();
		
		//add the customers to the model
		model.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd (Model model) {
		
		// create model attribute to bind form data
		Customer customer = new Customer();
		
		model.addAttribute("customer", customer);
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
		
		// save the customer using the service layer
		customerService.saveCustomer(customer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model model) {
		
		//get the customer from the service - who will get it from DAO - who will get it from the DB
		Customer customer = customerService.getCustomer(id);
		//set customer as a model attribute to pre-populate the form
		model.addAttribute("customer", customer);
		
		//send over to our form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {
		
		// delete the customer
		customerService.deleteCustomer(id);
		
		return "redirect:/customer/list";
	}
}
