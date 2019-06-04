package org.ms.DmhThymeLeaf.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.ms.DmhThymeLeaf.entity.CSULicensed;
import org.ms.DmhThymeLeaf.entity.CurrentUser;
import org.ms.DmhThymeLeaf.service.CsuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CsuDao implements CsuService{
	
	 
	private static final Logger logger = LoggerFactory.getLogger(CsuDao.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	@Override
	public void insertCsuLicenseBeds(List<CSULicensed> licBeds) {
		CurrentUser user = (CurrentUser)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp sqlToday = new java.sql.Timestamp(today.getTime());
		String insertSql  =  "insert into bc_license Values (?,?,?,?,?,?,?,?,?,?,?)";
		licBeds.stream().map(licBed -> {
			int i = jdbcTemplate.update(insertSql, licBed.getOrgCode(), licBed.getBedtype(), licBed.getLicBedMale(), licBed.getLicBedFemale(),
					licBed.getActBedMale(), licBed.getActBedFemale(), user.getUsername(), sqlToday, licBed.getLocationCode(),0,0);
			return i;
		}).forEach(i -> logger.debug(String.format("The value of the current insert is %d", i)));
	}
	

	/*@Override
	public Map<String, String> loadCsuAdmin() {
		String selectQuery  =  "select distinct ORG_CODE, PROVIDER_NAME from DMH.dbo.bc_providers where ORG_CODE in (select Provider_number from DMH.dbo.bc_locations where Location_code not like 'CL%' and Location_code not like 'ID%')";
		return this.jdbcTemplate.query(selectQuery,  new ResultSetExtractor<Map>());
	}

	@SuppressWarnings("unchecked")
	private RowMapper<Map<String, String>> getRowMapper(){
		Map providerMap = new HashMap<String, String>();
		return (rs, rowNum) -> {
			providerMap.put(rs.getString("ORG_CODE"), rs.getString("PROVIDER_NAME"));
			return providerMap;
		};
	}*/
	
	public List<String> getCSUProviders(){
		
		List<String> distinctProviders = new ArrayList<String>();
		String selectQuery  =  "select distinct ORG_CODE, PROVIDER_NAME from DMH.dbo.bc_providers where ORG_CODE in (select Provider_number from DMH.dbo.bc_locations where Location_code not like 'CL%' and Location_code not like 'ID%')";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery);
			rows.stream().forEach((row) -> {
				distinctProviders.add(row.get("org_code") + " - " + row.get("PROVIDER_NAME"));
			});
		return distinctProviders;
	}
	
	
	public List<CSULicensed> getCSULocations(String locId){
		System.out.println("----------------------------");
		logger.debug("In GetCsu Locations Dao");
		CurrentUser user = (CurrentUser)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> distinctProviders = new ArrayList<String>();
		String selectQuery  =  "select  distinct P.org_code, P.PROVIDER_NAME, L.LOCATION_CODE, L.LOCATION_NAME, L.BED_TYPE, D.bed_type_desc from DMH.dbo.bc_providers P "
				+ "inner join DMH.dbo.bc_locations L on P.org_code = L.PROVIDER_NUMBER inner join DMH.dbo.bc_bed_type D on L.BED_TYPE = D.bed_type where P.ORG_CODE = ? and L.LOCATION_CODE not like 'CL%' and Location_code not like 'ID%'";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery, new Object[] { locId });
			rows.stream().forEach((row) -> {
				distinctProviders.add(row.get("org_code") + " - "   + row.get("location_code") + " - " + row.get("bed_type") +  " - " + row.get("PROVIDER_NAME") + " - "   + row.get("LOCATION_NAME") + " - " + row.get("bed_type_desc"));
			});
		logger.debug("Exit GetCsu Locations Dao");	
		return getLicenseBedRecords(distinctProviders, user.getOrg());
	}
	
	public List<CSULicensed> getCSUAllLocations(){
		System.out.println("----------------------------");
		List<String> distinctProviders = new ArrayList<String>();
		String permit = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
		String selectQuery  =  "select  distinct P.org_code, P.PROVIDER_NAME, L.LOCATION_CODE, L.LOCATION_NAME, L.BED_TYPE, D.bed_type_desc from DMH.dbo.bc_providers P "
				+ " inner join DMH.dbo.bc_locations L  on  P.org_code = L.PROVIDER_NUMBER inner join DMH.dbo.bc_bed_type D on L.BED_TYPE = D.bed_type and L.LOCATION_CODE not like 'CL%' order by P.org_code";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery);
		rows.stream().forEach((row) -> {
			distinctProviders.add(row.get("org_code")  + " - "   + row.get("location_code") + " - " + row.get("bed_type") +  " - " +  row.get("PROVIDER_NAME") + " - "   + row.get("LOCATION_NAME")  + " - " + row.get("bed_type_desc"));
		});
		//distinctProviders.add(rs.getString("org_code") + " - "   + rs.getString("location_code") + " - " + rs.getString("bed_type") +  " - " + rs.getString("PROVIDER_NAME") + " - "   + rs.getString("LOCATION_NAME") + " - " + rs.getString("bed_type_desc"));
		return getLicenseBedRecords(distinctProviders, permit);
	}
	
	
	private List<CSULicensed> getLicenseBedRecords(
			List<String> distinctProviders, String orgCode) {
		List<CSULicensed> licenseBeds =  new ArrayList<CSULicensed>();
		
		for(String provider : distinctProviders){
			if("995".equals(orgCode.trim())){
					String[] providers =   provider.split("-");
					String selectQuery = "SELECT * FROM DMH.dbo.bc_license WHERE modify_date= (SELECT MAX(modify_date) FROM DMH.dbo.bc_license where org_code = ? and location_code = ? and bed_type = ?) and org_code = ? and location_code = ? and bed_type = ?";
					licenseBeds  = (List<CSULicensed>) jdbcTemplate.query(selectQuery, new Object[] { providers[0].trim(), providers[1].trim(), providers[2].trim(),providers[0].trim(), providers[1].trim(), providers[2].trim() }, 
							new CountryWithStateExtractor(providers));
			}else if("999".equals(orgCode.trim())){
				String[] providers =   provider.split("-");
				CSULicensed bcLic = new CSULicensed();
				bcLic.setOrgCode(providers[0].trim());
				bcLic.setLocationCode(providers[1].trim());
				bcLic.setBedtype( providers[2].trim());
				bcLic.setLicBedMale("");
				bcLic.setLicBedFeMale("");
				bcLic.setLicBedCoed( "");
				bcLic.setActBedFemale("");
				bcLic.setActBedMale("");
				bcLic.setActBedCoed("");
				bcLic.setOrgName(providers[3]);
				bcLic.setLocationName(providers[4]);
				bcLic.setBedTypeDesc(providers[5]);
				licenseBeds.add(bcLic);
			}
		}
		return licenseBeds;
	}
	
	
	
	
	
	class CountryWithStateExtractor implements ResultSetExtractor<List<CSULicensed>> {
		String[] providers = null;
		List<CSULicensed> licenseBeds =  new ArrayList<CSULicensed>();
		CountryWithStateExtractor(String[] providers){
			this.providers =  providers;
		}
	    public List<CSULicensed> extractData(ResultSet rs) throws SQLException {
		while(rs.next()) {
			CSULicensed bcLic = new CSULicensed();
			bcLic.setOrgCode(rs.getString("org_code"));
			bcLic.setLocationCode(rs.getString("location_code"));
			bcLic.setBedtype( rs.getString("bed_type"));
			bcLic.setLicBedMale(rs.getString("lic_bed_male"));
			bcLic.setLicBedFeMale(rs.getString("lic_bed_female"));
			bcLic.setLicBedCoed( rs.getString("lic_bed_coed"));
			bcLic.setActBedFemale(rs.getString("act_bed_female"));
			bcLic.setActBedMale(rs.getString("act_bed_male"));
			bcLic.setActBedCoed(rs.getString("act_bed_coed"));
			bcLic.setOrgName(providers[3]);
			bcLic.setLocationName(providers[4]);
			bcLic.setBedTypeDesc(providers[5]);
			licenseBeds.add(bcLic);
		}
		return licenseBeds;
	  }
	}






}
