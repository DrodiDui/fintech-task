package by.kapitonov.fintech.task.controller;

import by.kapitonov.fintech.task.dto.UserAccountDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AdminUserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountValidator userAccountValidator;

    @Autowired
    public AdminUserAccountController(UserAccountService userAccountService, UserAccountValidator userAccountValidator) {
        this.userAccountService = userAccountService;
        this.userAccountValidator = userAccountValidator;
    }

    @PostMapping("/user/{id}")
    public String changeStatus(@PathVariable(name = "id")Long id) throws UserAccountException {

        userAccountService.changeStatus(id);

        return "redirect:/user/" + id;
    }


    @GetMapping("/user/new")
    public String loadCreateUserPage(UserAccountDTO userAccountDTO) {
        return "createUser";
    }

    @PostMapping("/user/new")
    public String createUserAccount(@Valid@ModelAttribute(value = "userAccountDTO")UserAccountDTO userAccountDTO,
                                    BindingResult bindingResult) {
        userAccountValidator.validate(userAccountDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "createUser";
        }

        userAccountService.create(userAccountDTO);

        return "redirect:/user";
    }

    @GetMapping("/user/{id}/edit")
    public String loadEditUserPage(@PathVariable(name = "id")Long id, Model model) throws UserAccountException {

        Optional<UserAccount> currentUser = userAccountService.getByUsername(SecurityUtil.getCurrentUserUsername());
        model.addAttribute("currentUser", currentUser.get());

        return "editUser";
    }

    @PostMapping("/user/{id}/edit")
    public String updateUserAccount(@PathVariable(name = "id")Long id,
                                    UserAccountDTO userAccountDTO) {

        userAccountService.update(id, userAccountDTO);

        return "redirect:/user";
    }


}
