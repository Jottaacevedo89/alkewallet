package com.javiera.alke.controller;

import com.javiera.alke.dto.ContactoDTO;
import com.javiera.alke.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddContactController {

    @Autowired
    private ContactoService contactoService;

    @GetMapping("/addContactForm")
    public String showAddContactForm(Model model) {
        model.addAttribute("contacto", new ContactoDTO());
        return "addContactForm";
    }

    @PostMapping("/addContact")
    public String addContact(@ModelAttribute("contacto") ContactoDTO contactoDTO, Model model) {
        // Obtener el usuario actual
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        contactoDTO.setUserId(currentUserId);

        // Crear contacto
        String result = contactoService.createContacto(contactoDTO);
        if (result.equals("success")) {
            model.addAttribute("successMessage", "Contacto añadido con éxito");
        } else {
            model.addAttribute("errorMessage", result);
        }
        return "addContactForm";
    }
}
