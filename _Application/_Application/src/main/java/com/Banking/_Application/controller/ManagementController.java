package com.Banking._Application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Banking._Application.exception.MyException;
import com.Banking._Application.helper.ResponseStructure;
import com.Banking._Application.model.BankAccount;
import com.Banking._Application.model.Management;
import com.Banking._Application.service.ManagementService;

@RestController
@RequestMapping("management")
public class ManagementController {
	
	@Autowired
	ManagementService service;
	
	@PostMapping("add")
	public ResponseStructure<Management> save(@RequestBody Management management){
		return service.save(management);
		}
	
	@PostMapping("login")
	public ResponseStructure<Management> login(@RequestBody Management management)throws MyException{
		return service.login(management);
		
	}
	@GetMapping("accounts")
	public ResponseStructure<List<BankAccount>> fetchAllAccount()throws MyException{
		return service.fetchAllAccount();
		
	}
	@PutMapping("accountchange/{acno}")
	public ResponseStructure<BankAccount> changeStatus(@PathVariable long acno){
		return service.changeStatus(acno);
		
	}
}