package org.ms.DmhThymeLeaf.Dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.sql.DataSource;

import org.ms.DmhThymeLeaf.entity.DmhUser;
import org.ms.DmhThymeLeaf.enums.DMHRoles;
import org.ms.DmhThymeLeaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceDao implements UserService {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public DmhUser getUserById(String email) {
		String sql = "select * from DMH.dbo.USER_TABLE where who =?";
		String getunit = "select * from bc_user_table where who = ?";
		@SuppressWarnings("unchecked")
		DmhUser user = (DmhUser) jdbcTemplate.queryForObject(sql, new Object[] { email },
				new BeanPropertyRowMapper(DmhUser.class));
		// user.setRole(DMHRoles.USER);
		
		if (null != user) {
			setuserRoles(user);
			updateUser(user, getunit);
		}

		return user;
	}

	@Override
	public void updateUser(DmhUser dmhuser) {
		String sql = "INSERT INTO DMH.dbo.USER_TABLE(who, pswd, permit, first_name, last_name) values(?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[] { dmhuser.getWho(), dmhuser.getPswd(), dmhuser.getFirst_name(),
				dmhuser.getLast_name(), dmhuser.getPermit() });
	}

	@Override
	public Optional<List<DmhUser>> getAllusers() {
		String sql = "SELECT * FROM DMH.dbo.USER_TABLE";
		List<DmhUser> customers = jdbcTemplate.query(sql, new BeanPropertyRowMapper(DmhUser.class));
		return Optional.ofNullable(customers);
	}
	
	private static void setuserRoles(DmhUser user){
		if(user.getPermit().equals("999")){
			user.setRole(DMHRoles.ADMIN);
		}else{
			user.setRole(DMHRoles.USER);
		}
	}

	// TODO complete this
	private void updateUser(DmhUser user, String sql) {
		List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql, new Object[] { user.getWho() });
		lists.stream().forEach(map -> {
			map.entrySet().stream().forEach(entry -> {
				checkUserAccess(entry, user);
			});
		});
	}

	private static void checkUserAccess(Entry<String, Object> entry, DmhUser user) {
		if (entry.getKey().equals("csu") && entry.getValue().toString().equals("Y")) {
			System.out.println(entry.getValue().toString());
			System.out.println(entry.getKey().toString());
			user.setCsu("Y");
		}
		if (entry.getKey().equals("cl") && entry.getValue().toString().equals("Y")) {
			System.out.println(entry.getValue().toString());
			System.out.println(entry.getKey().toString());
			user.setCl("Y");
		}
		/*if (entry.getKey().equals("IDD") && entry.getValue().toString().equals("Y")) {
			System.out.println(entry.getValue().toString());
			System.out.println(entry.getKey().toString());
			user.setIdd("Y");
		}*/
		if (entry.getKey().equals("IDD") && Optional.ofNullable(entry.getValue()).isPresent() && entry.getValue().equals("Y")) {
			System.out.println(entry.getValue().toString());
			System.out.println(entry.getKey().toString());
			user.setIdd("Y");
		}
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
