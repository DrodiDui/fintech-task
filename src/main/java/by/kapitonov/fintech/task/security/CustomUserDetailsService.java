package by.kapitonov.fintech.task.security;

import by.kapitonov.fintech.task.exception.CustomValidationException;
import by.kapitonov.fintech.task.model.UserAccount;
import by.kapitonov.fintech.task.repository.UserAccountRepository;
import by.kapitonov.fintech.task.util.UserAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public CustomUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!UserAccountValidator.isValidUsername(username)) {
            throw new CustomValidationException("Invalid username");
        }

        UserAccount userAccount = userAccountRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("UserAccount hasn't been found")
        );

        return new UserPrincipal(userAccount);
    }
}
