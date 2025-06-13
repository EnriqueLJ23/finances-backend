package com.jasso.finance.finance.dao;

import com.jasso.finance.finance.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(String name);
}
