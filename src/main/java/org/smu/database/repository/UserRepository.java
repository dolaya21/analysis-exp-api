package org.smu.database.repository;
import org.smu.database.entity.*;
import org.smu.database.key.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {}
