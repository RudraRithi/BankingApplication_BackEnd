package com.Banking._Application.service;

import java.sql.Struct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.Banking._Application.exception.MyException;
import com.Banking._Application.helper.MailVerification;
import com.Banking._Application.helper.ResponseStructure;
import com.Banking._Application.model.BankAccount;
import com.Banking._Application.model.BankTransaction;
import com.Banking._Application.model.Customer;
import com.Banking._Application.model.Login;
import com.Banking._Application.repository.BankRepository;
import com.Banking._Application.repository.CustomerRepository;

import lombok.extern.java.Log;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	BankRepository bankRepository;
	
	@Autowired
	MailVerification mailVerification;
	
	@Autowired
	BankAccount account;
	
	@Autowired
	BankTransaction bankTransaction;
	
	
	public ResponseStructure<Customer>save (Customer customer) throws MyException{
		ResponseStructure< Customer> structure= new ResponseStructure();
		int age= Period.between(customer.getDob().toLocalDate(), LocalDate.now()).getYears();
		customer.setAge(age);
		if(age<18) {
			throw new MyException("You should be 18+ to create Account");
		}else {
			Random random= new Random();
			int otp= random.nextInt(100000, 999999);
			customer.setOtp(otp);
			structure.setMessage("Verification Mail Sent");
			structure.setCode(HttpStatus.PROCESSING.value());
			structure.setData(customerRepository.save(customer));
			
		}
		
		return structure;
	
	}
	 public ResponseStructure<Customer> verify (int custid, int otp) throws MyException{
		 ResponseStructure<Customer> structure = new  ResponseStructure<Customer>();
		 Optional<Customer> optional = customerRepository.findById(custid);
		 if(optional.isEmpty()) {
			 throw new MyException("Check ID And Try Again");
		 }else {
			 Customer customer= optional.get();
			 if(customer.getOtp()==otp) {
				 structure.setCode(HttpStatus.CREATED.value());
				 structure.setMessage("Account Created Successfully");
				 customer.setStatus(true);
				 structure.setData(customerRepository.save(customer));
			 }else {
				 throw new MyException("otp MissMatched");
			 }
		 }
		 
		return structure;
		 
	 }
	 public ResponseStructure<Customer> login(Login login) throws MyException{
		 ResponseStructure<Customer > structure= new ResponseStructure<Customer>();
		 
		 Optional<Customer> optional= customerRepository.findById(login.getId());
		 if(optional.isEmpty()) {
			 throw new MyException("Invalid Customer ID");
			 
		 }else {
			 Customer customer= optional.get();
			 if(customer.getPassword().equals(login.getPassword())){
				 if(customer.isStatus()){
					structure.setCode(HttpStatus.ACCEPTED.value());
					structure.setMessage("login Success");
					structure.setData(customer);
				 }else {
					 throw new MyException("verifiy Email first");
				 }
			 }else {
				 throw new MyException("invalid Password");
			 }
			}
		 return structure;
		 
	 }
	 
	 public ResponseStructure<Customer> createAccount(int cust_id, String type) throws MyException{
		 
		 ResponseStructure<Customer> structure = new ResponseStructure<Customer>();
		 
		 Optional<Customer> optional = customerRepository.findById(cust_id);
		 if(optional.isEmpty()) {
			 throw new MyException("Invalid Customer Id");
		 }else {
			 Customer customer= optional.get();
			 List<BankAccount> list= customer.getAccount();
			 
			 boolean flag= true;
			 for(BankAccount account:list) {
				 if(account.getType().equals(type)) {
					 flag= false;
					 break;
				 }
			 }
			 
			 if(!flag) {
				 throw new MyException(type +" Account Already Exist");
			 }else {
				 account.setType(type);
				 if(type.equals("savings")) {
					 account.setBanklimit(50000);
				 }else {
					 account.setBanklimit(250000);
				 }
				 list.add(account);
				 customer.setAccount(list);
				 
			 }
			 structure.setCode(HttpStatus.ACCEPTED.value());
			 structure.setMessage("Account created wait for management Approval");
			 structure.setData(customerRepository.save(customer));
		 }
		return structure;
		 
	 }
	 
	 
		 public ResponseStructure<List<BankAccount>> fetchAllTrue(int custid) throws MyException{
			
			 ResponseStructure<List<BankAccount>> structure= new ResponseStructure<List<BankAccount>>();
			 
			 Optional<Customer> optional= customerRepository.findById(custid);
			 Customer customer= optional.get();
			 
			 List<BankAccount> list = customer.getAccount();
			 
			 List<BankAccount> res= new ArrayList<BankAccount>();
			 
			 for(BankAccount account:list) {
				 if(account.isStatus()) {
					 res.add(account);
				 }
			 }
			 
			 if(res.isEmpty()) {
				 throw new MyException("No Active Account Found");
			 }else {
				 structure.setCode(HttpStatus.FOUND.value());
				 structure.setMessage("Account Found");
				 structure.setData(res);
			 }
			 
			 
			 return structure;
			 
		 }
		 
		 public  ResponseStructure<Double> checkBlance(long accno){
			 ResponseStructure<Double> responseStructure= new ResponseStructure<Double>();
			 
			 Optional<BankAccount> optional= bankRepository.findById(accno);
			 BankAccount account= optional.get();
			 
			 responseStructure.setCode(HttpStatus.FOUND.value());
			 responseStructure.setMessage("Data Found");
			 responseStructure.setData(account.getAmount());
			return responseStructure;
			 
		 }
		 
		 public ResponseStructure<BankAccount> deposit(long accno, double amount){
			
			 ResponseStructure<BankAccount> structure = new  ResponseStructure<BankAccount>();
			 BankAccount account=bankRepository.findById(accno).get();
			 account.setAmount(account.getAmount()+amount);
			 
			 bankTransaction.setDateTime(LocalDateTime.now());
			 bankTransaction.setDeposit(amount);
			 bankTransaction.setBalance(account.getAmount());
			 
			 
			 List<BankTransaction>bankTransaction= account.getBankTransactions();
			 bankTransaction.addAll(bankTransaction);
			 
			 account.setBankTransactions(bankTransaction);
			 
			 structure.setCode(HttpStatus.ACCEPTED.value());
			 structure.setMessage("Ammount Added Successfully");
			 structure.setData(bankRepository.save(account));
			 
			 return structure;
			 
		 }
		 
		 public ResponseStructure<BankAccount> withdraw(long acno, double amount) throws MyException{
			ResponseStructure<BankAccount> structure= new ResponseStructure<BankAccount>();
			
			BankAccount account= bankRepository.findById(acno).get();
			
			if(amount >account.getBanklimit()) {
				throw new MyException("out of Limit");
			}else {
				if(amount>account.getAmount()) {
					throw new MyException("Insufficent Fund");
					
				}else {
					account.setAmount(account.getAmount()-amount);
					
					bankTransaction.setDateTime(LocalDateTime.now());
					bankTransaction.setDeposit(0);
					bankTransaction.setWithdraw(amount);
					bankTransaction.setBalance(account.getAmount());
					
					List<BankTransaction> bankTransactions= account.getBankTransactions();
					bankTransactions.add(bankTransaction);
					
					account.setBankTransactions(bankTransactions);
					
					structure.setCode(HttpStatus.ACCEPTED.value());
					structure.setMessage("Ammount Withdrawn Successfully");
					structure.setData(bankRepository.save(account));
					
				}
			}
			 return structure;
			 
		 }
		 
		 public ResponseStructure<List<BankTransaction>> viewtransaction(long acno) throws MyException{
			
			 ResponseStructure<List<BankTransaction>> structure = new ResponseStructure<List<BankTransaction>>();
			 
			 BankAccount account= bankRepository.findById(acno).get();
			 List<BankTransaction> list= account.getBankTransactions();
			 if(list.isEmpty()) {
				 throw new MyException("No Transaction");
				 
			 }else {
				 structure.setCode(HttpStatus.FOUND.value());
				 structure.setMessage("data found");
				 structure.setData(list);
			 }
			 
			 return structure;
			 
		 }
		 
}
		 
		 

	
	

