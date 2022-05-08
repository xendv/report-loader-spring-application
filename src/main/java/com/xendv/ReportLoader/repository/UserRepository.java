package com.xendv.ReportLoader.repository;

import com.xendv.ReportLoader.model.SystemUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<SystemUser, Integer> {
    Optional<SystemUser> findByLogin(@NotNull String login);
}
