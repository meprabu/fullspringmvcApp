package org.ms.DmhThymeLeaf.service;

import java.util.List;
import java.util.Map;

import org.ms.DmhThymeLeaf.entity.CSULicensed;
import org.springframework.stereotype.Service;

@Service
public interface CsuService {
	
	
	//public Map<String, String> loadCsuAdmin();
	
	public List<String> getCSUProviders();
	public List<CSULicensed> getCSUAllLocations();
	public List<CSULicensed> getCSULocations(String locId);
	public void insertCsuLicenseBeds(List<CSULicensed> licBeds);

}
