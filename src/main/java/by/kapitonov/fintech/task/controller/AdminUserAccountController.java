package by.kapitonov.fintech.task.controller;

import by.kapitonov.fintech.task.dto.UserAccountDTO;
import by.kapitonov.fintech.task.exception.CustomValidationException;
import by.kapitonov.fintech.task.exception.UserAccountException;
import by.kapitonov.fintech.task.model.Role;
import by.kapitonov.fintech.task.model.Status;
import by.kapitonov.fintech.task.model.UserAccount;
import by.kapitonov.fintech.task.security.SecurityUtil;
import by.kapitonov.fintech.task.service.UserAccountService;
import by.kapitonov.fintech.task.util.UserAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminUserAccountController {

    private final UserAccountService userAccountService;

    @Autowired
    public AdminUserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/user/{id}")
    public String changeStatus(@PathVariable(name = "id")Long id) throws UserAccountException {

        userAccountService.changeStatus(id);

        return "redirect:/user/" + id;
    }

    @GetMapping("/user/new")
    public String loadCreatUserPage(Model model) throws UserAccountException {
        Optional<UserAccount> currentUser = userAccountService.getByUsername(SecurityUtil.getCurrentUserUsername());
        model.addAttribute("currentUser", currentUser.get());
        model.addAttribute("roles", Role.values());
        model.addAttribute("statuses", Status.values());
        return "createUser";
    }

    @PostMapping("/user/new")
    public String createNewUser(@Valid UserAccountDTO userAccountDTO) {

        if (!UserAccountValidator.isValidUsername(userAccountDTO.getUsername()) ||
                !UserAccountValidator.isValidPassword(userAccountDTO.getPassword())
        ) {
            throw new CustomValidationException("Invalid username or password");
        }

        userAccountService.create(userAccountDTO);

        return "redirect:/user";
    }

    @GetMapping("/user/{id}/edit")
    public String loadEditUserPage(@PathVariable(name = "id")Long id, Model model) throws UserAccountException {

        Optional<UserAccount> currentUser = userAccountService.getByUsername(SecurityUtil.getCurrentUserUsername());
        model.addAttribute("currentUser", currentUser.get());
        model.addAttribute("id", id);

        return "editUser";
    }

    @PostMapping("/user/{id}/edit")
    public String updateUserAccount(@PathVariable(name = "id")Long id, UserAccountDTO userAccountDTO) {

        userAccountService.update(id, userAccountDTO);

        return "redirect:/user";
    }


}
