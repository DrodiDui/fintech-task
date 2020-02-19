package by.kapitonov.fintech.task.repository;

import by.kapitonov.fintech.task.model.UserAccount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long> {

    Optional<UserAccount> findByUsername(String username);
}
