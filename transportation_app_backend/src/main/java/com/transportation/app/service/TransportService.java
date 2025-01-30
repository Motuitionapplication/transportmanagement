package com.transportation.app.service;

import java.util.List;
import com.transportation.app.binding.Transport;

public interface TransportService {
	
	public String upsert(Transport transport); //insert + update
	
	public Transport getById(Integer tid); // finding by id
	
	public List<Transport> getAllTransports();// all 
	
	public String deleteById(Integer tid);// id delete 
	
	
	
	
	

	
	
	

}
 