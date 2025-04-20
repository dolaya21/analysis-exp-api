package org.smu.database.repository;
import org.smu.database.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Integer> {}
