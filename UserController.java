package com.javiera.alke.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.javiera.alke.model.Contacto;
import com.javiera.alke.model.Transaction;
import com.javiera.alke.model.TransferForm;
import com.javiera.alke.model.User;
import com.javiera.alke.service.ContactoService;
import com.javiera.alke.service.UserService;
import com.javiera.alke.exception.InsufficientBalanceException;

import java.security.Principal;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        userService.save(user);
        model.addAttribute("successMessage", "Usuario registrado con éxito");
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "success", required = false) String success,
                                @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("errorMessage", "Usuario o contraseña incorrectos, por favor ingrese nuevamente");
        }
        if (success != null) {
            model.addAttribute("successMessage", "Registro exitoso, por favor inicie sesión");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Sesión cerrada con éxito");
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("userId") String userId, @RequestParam("password") String password, Model model) {
        User user = userService.findByUserId(userId);
        if (user != null) {
            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                return "redirect:/dashboard?success";
            } else {
                return "redirect:/login?error";
            }
        } else {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/balance")
    public String showBalance(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "balance";
    }

    @GetMapping("/deposit")
    public String showDepositForm(Model model) {
        return "deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam("amount") double amount, Model model) {
        userService.deposit(amount);
        return "redirect:/dashboard?success";
    }

    @GetMapping("/withdraw")
    public String showWithdrawForm(Model model) {
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("amount") double amount, Model model, RedirectAttributes redirectAttributes) {
        if (amount <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "El monto debe ser un número positivo");
            return "redirect:/withdraw";
        }
        try {
            userService.withdraw(amount);
            redirectAttributes.addFlashAttribute("successMessage", "Retiro realizado con éxito");
        } catch (InsufficientBalanceException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/withdraw";
    }

    @GetMapping("/transfer")
    public String showTransferForm(Model model, Principal principal) {
        String userId = principal.getName();
        List<Contacto> contactos = contactoService.getContactosByUserId(userId);
        model.addAttribute("contactos", contactos);
        model.addAttribute("transferForm", new TransferForm());
        return "transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@ModelAttribute TransferForm transferForm, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        String senderId = principal.getName();
        if (transferForm.getAmount() <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "El monto debe ser un número positivo");
            return "redirect:/transfer";
        }
        try {
            userService.transfer(senderId, transferForm.getContact(), transferForm.getAmount());
            redirectAttributes.addFlashAttribute("successMessage", "Transferencia realizada con éxito");
        } catch (InsufficientBalanceException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/transfer";
    }
    
    @GetMapping("/transactions")
    public String showTransactions(Model model) {
        User user = userService.getCurrentUser();
        List<Transaction> transactions = userService.getTransactions(user.getUserId());
        model.addAttribute("transactions", transactions);
        return "transactions";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}

