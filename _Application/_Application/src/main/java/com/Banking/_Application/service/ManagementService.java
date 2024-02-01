package com.Banking._Application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.Banking._Application.exception.MyException;
import com.Banking._Application.helper.ResponseStructure;
import com.Banking._Application.model.BankAccount;
import com.Banking._Application.model.Management;
import com.Banking._Application.repository.BankRepository;
import com.Banking._Application.repository.ManagementRepository;

@Service
public class ManagementService {
	
	@Autowired
	ManagementRepository repository;
	
	
	@Autowired
	BankRepository repository2;
	
	public ResponseStructure<Management> save(Management management){
		
		ResponseStructure<Management> structure = new ResponseStructure<>();
		structure.setCode(HttpStatus.CREATED.value());
		structure.setMessage("Data Added Successfully");
		structure.setData(repository.save(management));
		return structure;
		
	}
	
	public ResponseStructure<Management>  login(Management management)throws MyException{
		ResponseStructure<Management> structure= new ResponseStructure<Management>();
		Management management1= repository.findByEmail(management.getEmail());
		
		if(management1==null) {
			throw new MyException("Invalid Managemene email");
			
		}else {
			if(management1.getPassword().equals(management.getPassword())) {
				structure.setCode(HttpStatus.ACCEPTED.value());
				structure.setMessage("login Success");
				structure.setData(management1);
			}else {
				throw new MyException("Invalid Password");
			}
		}
		return structure;
		
	}
	
	public ResponseStructure<List<BankAccount>> fetchAllAccount() throws MyException{
		
		ResponseStructure<List<BankAccount>>  structure= new ResponseStructure<List<BankAccount>>();
		
		List<BankAccount> list = repository2.findAll();
		if(list.isEmpty()) {
			throw new MyException("No Accounts Present");
			
		}else {
			structure.setCode(HttpStatus.FOUND.value());
			structure.setMessage("Data Found");
			structure.setData(list);
		}
		
		return structure;
		
		
	}
	
	public ResponseStructure<BankAccount> changeStatus(long acno){
		
		ResponseStructure<BankAccount>structure = new ResponseStructure<BankAccount>();
		
		Optional<BankAccount> optional= repository2.findById(acno);
		BankAccount account= optional.get();
		if(account.isStatus()) {
			account.setStatus(false);
		}else {
			account.setStatus(true);
		}
		
		structure.setCode(HttpStatus.OK.value());
		structure.setMessage("Changed Status Successfully");
		structure.setData(account);
		
		return structure;
		
	}
}
