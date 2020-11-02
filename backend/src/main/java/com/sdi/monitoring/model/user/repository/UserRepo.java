package com.sdi.monitoring.model.user.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sdi.monitoring.model.user.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, String> {
	public Optional<UserEntity> findUserByEmail(String email);

	@Query(value = "SELECT COUNT(*) FROM USER WHERE IS_ADMIN = TRUE", nativeQuery = true)
	public int hasAdminCheck();
	
	@Query(value = "SELECT IS_ADMIN FROM USER WHERE EMAIL = :email", nativeQuery = true)
	public boolean isAdminCheck(String email);

	@Transactional
	@Modifying
	@Query(value = "UPDATE USER SET IS_ADMIN = FALSE WHERE EMAIL = : email", nativeQuery = true)
	public int changeAuthorityToUser(String email);

	@Transactional
	@Modifying
	@Query(value = "UPDATE USER SET IS_ADMIN = TRUE WHERE EMAIL = : email", nativeQuery = true)
	public int changeAuthorityToAdmin(String email);

	@Query(value = "SELECT * FROM USER", nativeQuery = true)
	public List<UserEntity> getAllUserList();

}
