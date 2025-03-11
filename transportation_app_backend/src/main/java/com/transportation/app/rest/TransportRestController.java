package com.transportation.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.transportation.app.binding.Transport;
import com.transportation.app.service.TransportService;

@RestController
public class TransportRestController {
	@Autowired
	private TransportService transportService;
	

	
	@PostMapping("/Creat_transport")
     public ResponseEntity<String> creatTransport(@RequestBody Transport transport){
		 
		String status = transportService.upsert(transport);
		return new ResponseEntity<>(status, HttpStatus.CREATED);
		  
	  }
	
	
	@GetMapping("/Get_transport/{Tid}")
	 public ResponseEntity<Transport> getTransport(@PathVariable Integer tid) {
		Transport transport = transportService.getById(tid);
		return new ResponseEntity<> (transport, HttpStatus.OK);
	}
	 
	@GetMapping("/All_transports/")
	public ResponseEntity<List<Transport>> getAllTransports(){
		List<Transport> allTransports = transportService.getAllTransports();
		return new ResponseEntity<>(allTransports, HttpStatus.OK);
	}
	
	@PutMapping("/Update_transport")
    public ResponseEntity<String> updateTransport(@RequestBody Transport transport){
		 
		String status = transportService.upsert(transport);
		return new ResponseEntity<>(status, HttpStatus.OK);
		  
	  }
	@DeleteMapping("/Delete_transport/{Tid}")
	 public ResponseEntity<String> deleteTransport(@PathVariable Integer Tid) {
	    String status = transportService.deleteById(Tid);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

}
 