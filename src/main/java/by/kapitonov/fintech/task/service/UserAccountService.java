package by.kapitonov.fintech.task.service;

import by.kapitonov.fintech.task.dto.UserAccountDTO;
import by.kapitonov.fintech.task.exception.UserAccountException;
import by.kapitonov.fintech.task.model.Role;
import by.kapitonov.fintech.task.model.Status;
import by.kapitonov.fintech.task.model.UserAccount;
import by.kapitonov.fintech.task.repository.UserAccountRepository;
import by.kapitonov.fintech.task.util.UserAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<UserAccount> getAllUserAccounts(Pageable pageable) throws UserAccountException {

        Page<UserAccount> page = userAccountRepository.findAll(pageable);

        if (page.getContent() == null) {
            throw new UserAccountException("UserAccounts haven't been found");
        }

        return page;
    }

    public Optional<UserAccount> getById(Long id) throws UserAccountException {

        UserAccount result = userAccountRepository.findById(id).orElseThrow(
                () -> new UserAccountException("User hasn't been found")
        );

        return Optional.of(result);
    }

    public Optional<UserAccount> getByUsername(String username) throws UserAccountException {

        UserAccount result = userAccountRepository.findByUsername(username).orElseThrow(
                () -> new UserAccountException("User hasn't been found")
        );

        return Optional.of(result);

    }

    public UserAccount create(UserAccountDTO userAccount) {

        String hashPassword = passwordEncoder.encode(userAccount.getPassword());

        UserAccount newUserAccount = UserAccount.builder()
                .username(userAccount.getUsername())
                .password(hashPassword)
                .firstName(userAccount.getFirstName())
                .lastName(userAccount.getLastName())
                .role(Role.USER)
                .status(Status.INACTIVE)
                .createdAt(Instant.now())
                .build();

        return userAccountRepository.save(newUserAccount);
    }

    public void update(Long id, UserAccountDTO userAccountDTO) {

         Optional<UserAccount> userAccount = userAccountRepository.findById(id);

         if (!userAccountDTO.getUsername().equals("") &&
                 UserAccountValidator.isValidUsername(userAccountDTO.getUsername())) {
             userAccount.get().setUsername(userAccountDTO.getUsername());
         }

         if (!userAccountDTO.getPassword().equals("") &&
                 UserAccountValidator.isValidPassword(userAccountDTO.getPassword())) {
             String hashPassword = passwordEncoder.encode(userAccountDTO.getPassword());
             userAccount.get().setPassword(hashPassword);
         }

         if (!userAccountDTO.getFirstName().equals("")) {
             userAccount.get().setFirstName(userAccountDTO.getFirstName());
         }

         if (!userAccountDTO.getLastName().equals("")) {
             userAccount.get().setLastName(userAccountDTO.getLastName());
         }

         userAccountRepository.save(userAccount.get());
    }

    public void changeStatus(Long id) throws UserAccountException {
        UserAccount userAccount = userAccountRepository.findById(id).orElseThrow(
                () -> new UserAccountException("User hasn't been found")
        );

        if (userAccount.getStatus().equals(Status.ACTIVE)) {
            userAccount.setStatus(Status.INACTIVE);
        } else {
            userAccount.setStatus(Status.ACTIVE);
        }

        userAccountRepository.save(userAccount);
    }

}
