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
	
	// Inject Customer Service
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		// Get Customers from DAO
		List<Customer> customers = customerService.getCustomers();
		
		// Add Customers to the Model
		theModel.addAttribute("customers", customers);
		
		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// Create Model Attribute to bind Form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		
		// Save the Customer using the service
		customerService.saveCustomer(theCustomer);
		
		
		return "redirect:/customer/list";
	}
	
	
	@GetMapping("/showFormForUpdate")
	public String ShowFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		
		// Get the Customer by Id from Service
		Customer customer = customerService.getCustomer(theId);
		
		// Set Customer as a model, to pre-populate the rendered from
		theModel.addAttribute("customer", customer);
		
		// Send over to the Form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int customerId) {
		
		customerService.deleteCustomer(customerId);	
		
		return "redirect:/customer/list";
	}
	
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel) {
		
		// search customers from the service
		List<Customer> foundCustomers = customerService.searchCustomers(theSearchName);
		
		// add the customers to the model
        theModel.addAttribute("customers", foundCustomers);
        return "list-customers";        
	}
	
	
	
	
}
