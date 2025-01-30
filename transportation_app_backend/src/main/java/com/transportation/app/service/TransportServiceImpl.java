package com.transportation.app.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.transportation.app.binding.Transport;
import com.transportation.app.repo.TransportRepository;


@Service
public class TransportServiceImpl implements TransportService {

	@Autowired
	private TransportRepository transportRepo;
	
	
	@Override
	public String upsert(Transport transport) {
		transportRepo.save(transport);
		return "Sucess";
	}

	@Override
	public Transport getById(Integer tid) {
	Optional<Transport> findById = transportRepo.findById(tid);
		if(findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public List<Transport> getAllTransports() {
		return transportRepo.findAll();
		 
	}

	@Override
	public String deleteById(Integer tid) {
		if(transportRepo.existsById(tid)) {
			transportRepo.deleteById(tid);
			return " Delete success ";
		} else {
			return " No record Found ";
		}
		
	}

	
	
}
