package org.ms.DmhThymeLeaf.Dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.ms.DmhThymeLeaf.entity.BcUser;
import org.ms.DmhThymeLeaf.entity.DmhUser;
import org.ms.DmhThymeLeaf.enums.DMHRoles;
import org.ms.DmhThymeLeaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceDao implements UserService{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public DmhUser getUserById(String email) {
		String sql = "select * from DMH.dbo.USER_TABLE where who =?";
		@SuppressWarnings("unchecked")
		DmhUser user = (DmhUser) jdbcTemplate.queryForObject(sql, new Object[] { email }, new BeanPropertyRowMapper(DmhUser.class));
		user.setRole(DMHRoles.USER);
		return user;
	}

	@Override
	public void updateUser(DmhUser dmhuser) {
		String sql = "INSERT INTO DMH.dbo.USER_TABLE(who, pswd, permit, first_name, last_name) values(?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[]{dmhuser.getWho(), dmhuser.getPswd(), dmhuser.getFirst_name(),dmhuser.getLast_name(), dmhuser.getPermit()});
	}
	
	
	
/*	@Override
	public void updateUser(DmhUser dmhuser) {
		String sql = "INSERT INTO DMH.dbo.USER_TABLE(who, pswd, permit, first_name, last_name) values(?,?,?,?,?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			 public PreparedStatement createPreparedStatement(Connection con)
	                    throws SQLException {
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, dmhuser.getWho());
				stmt.setString(2, dmhuser.getPswd());
				stmt.setString(3, dmhuser.getFirst_name());
				stmt.setString(4, dmhuser.getLast_name());
				stmt.setString(5, dmhuser.getPermit());
				return stmt;
			}
		});
	}*/
	
	

	@Override
	public Optional<List<DmhUser>> getAllusers() {
		String sql = "SELECT * FROM DMH.dbo.USER_TABLE";
		List<DmhUser> customers  = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper(DmhUser.class));
		return Optional.ofNullable(customers);
	}
	
	
/*class UserRowMapper implements RowMapper<DmhUser>{
		
		public DmhUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			DmhUser bc = new DmhUser();
			bc.setWho(rs.getString("who"));
			bc.setFirst_name(rs.getString("first_name"));
			bc.setLast_name(rs.getString("last_name"));
			bc.setLocation_code(rs.getString("location_code"));
			bc.setPermit(rs.getString("permit"));
			return bc;
		}
		
	}*/
	
	
	

}
