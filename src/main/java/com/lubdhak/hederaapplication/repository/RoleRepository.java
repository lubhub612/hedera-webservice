package com.lubdhak.hederaapplication.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lubdhak.hederaapplication.model.Role;
import com.lubdhak.hederaapplication.model.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	 Optional<Role> findByName(RoleName roleName);
}
