package com.Banking._Application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking._Application.exception.MyException;
import com.Banking._Application.helper.ResponseStructure;
import com.Banking._Application.model.BankAccount;
import com.Banking._Application.model.BankTransaction;
import com.Banking._Application.model.Customer;
import com.Banking._Application.model.Login;
import com.Banking._Application.repository.CustomerRepository;
import com.Banking._Application.service.CustomerService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	CustomerService service;
	
	
	@PostMapping("customer")
	public ResponseStructure<Customer> save(@RequestBody Customer customer) throws MyException{
		return service.save(customer);
		
	}
	
	@PutMapping("otp/{custid}/{otp}")
	public ResponseStructure<Customer> otpVerify(@RequestBody int custid, @PathVariable int otp) throws MyException{
	
		
		return service.verify(custid, otp);
	}
	
	@PostMapping("login")
	public ResponseStructure<Customer> login(@RequestBody Login login) throws MyException{
		return service.login(login);
	}

	@PostMapping("account/{cust_id}/{type}")
	public ResponseStructure<Customer>  createAccount(@PathVariable int cust_id, @PathVariable String type) throws MyException{
		return service.createAccount(cust_id, type);
		
	}
	
	@GetMapping("accounts/{custid}")
	public ResponseStructure<List<BankAccount>> fetchAllTrur(@PathVariable int custid)throws MyException{
		return service.fetchAllTrue(custid);
		
	}
	
	@GetMapping("accounts/check/{acno}")
	public ResponseStructure<Double> checkblance(@PathVariable long acno){
		return service.checkBlance(acno);
		
	}
	
	@PutMapping("account/deposit/{acno}/{amount}")
	public ResponseStructure<BankAccount> deposite(@PathVariable long acno, @PathVariable double amount){
		return service.deposit(acno, amount);
		
	}
	@PutMapping("account/withdraw/{acno}/{amount}")
	public ResponseStructure<BankAccount> withdraw(@PathVariable long acno, @PathVariable double amount)throws MyException{
		return service.withdraw(acno, amount);
		
	}

	@getMapping("account/viewtransaction/{acno}"})
	public ResponseStructure<List<BankTransaction>> viewtransaction(@PathVariable long acno) throws MyException{
		return service.viewtransaction(acno);
		
	}
	
	
}
