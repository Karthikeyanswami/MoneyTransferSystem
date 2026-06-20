package com.fidelity.mts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.mts.dto.TransferRequest;
import com.fidelity.mts.services.TransferService;

@RestController
@RequestMapping("/api/v1/transfers")
@CrossOrigin(origins="http://localhost:4200")
public class TransferController {
	@Autowired TransferService service;
	
	
	@PostMapping
	public ResponseEntity<?> transfer(@RequestBody TransferRequest tr) {
		System.out.println(tr);
		return service.transfer(tr);
	}
	
}
