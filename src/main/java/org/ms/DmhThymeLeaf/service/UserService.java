package org.ms.DmhThymeLeaf.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.ms.DmhThymeLeaf.entity.DmhUser;

public interface UserService {

	DmhUser getUserById(String email);

	void updateUser(DmhUser dmhuser) throws SQLException;

	Optional<List<DmhUser>> getAllusers();

}
