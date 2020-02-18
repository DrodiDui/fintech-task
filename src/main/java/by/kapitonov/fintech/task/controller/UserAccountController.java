package by.kapitonov.fintech.task.controller;

import by.kapitonov.fintech.task.exception.UserAccountException;
import by.kapitonov.fintech.task.model.UserAccount;
import by.kapitonov.fintech.task.security.SecurityUtil;
import by.kapitonov.fintech.task.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class UserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/")
    public String getMainPage() {
        return "redirect:/user";
    }

    @GetMapping("/user")
    public String getAllUserAccounts(@PageableDefault Pageable pageable, Model model) throws UserAccountException {

        Optional<UserAccount> currentUser = userAccountService.getByUsername(SecurityUtil.getCurrentUserUsername());
        model.addAttribute("currentUser", currentUser.get());

        Page<UserAccount> page = userAccountService.getAllUserAccounts(pageable);
        model.addAttribute("page", page);

        return "users";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable(name = "id")Long id, Model model) throws UserAccountException {

        Optional<UserAccount> currentUser = userAccountService.getByUsername(SecurityUtil.getCurrentUserUsername());
        model.addAttribute("currentUser", currentUser.get());

        Optional<UserAccount> userAccount = userAccountService.getById(id);
        model.addAttribute("userAccount", userAccount.get());

        return "/user";
    }

}
