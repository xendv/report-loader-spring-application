package com.xendv.ReportLoader.repository;

import com.xendv.ReportLoader.model.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SystemUser, String> {
    Optional<SystemUser> findByLogin(@NotNull String login);
}
