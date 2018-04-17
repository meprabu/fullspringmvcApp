package org.ms.DmhThymeLeaf.Dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.ms.DmhThymeLeaf.entity.BcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class BcUserDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	
	public List<BcUser> getAllUsers(){
		String sql = "select * from DMH.dbo.bc_user_table";
		return this.jdbcTemplate.query(sql, getRowMapper());
	}
	
	private RowMapper<BcUser> getRowMapper(){
		return (rs, rowNum) -> {
			BcUser bc = new BcUser();
			bc.setWho(rs.getString("who"));
			bc.setFirst_name(rs.getString("first_name"));
			bc.setLast_name(rs.getString("last_name"));
			bc.setLocation_code(rs.getString("location_code"));
			bc.setPermit(rs.getString("permit"));
			return bc;
		};
	}
}
