package com.javiera.alke.controller;

import com.javiera.alke.dto.ContactoDTO;
import com.javiera.alke.service.ContactoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AddContactControllerTest {

    @Mock
    private ContactoService contactoService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private AddContactController addContactController;

    @BeforeEach
    public void setup() {
        // Mockear el contexto de seguridad
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user1");
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testAddContactWithInvalidEmail() {
        // Configurar los parámetros de la solicitud
        ContactoDTO contactoDTO = new ContactoDTO();
        contactoDTO.setContactUserId("12345");
        contactoDTO.setNameContactUserId("John Doe");
        contactoDTO.setEmail("invalidemail.com"); // Sin el signo @

        // Configurar el servicio para que devuelva un mensaje de error
        when(contactoService.createContacto(contactoDTO)).thenReturn("El correo electrónico debe contener un '@'.");

        // Llamar al método addContact del controlador
        String view = addContactController.addContact(contactoDTO, model);

        // Verificar que la vista devuelta sea "addContactForm"
        assertEquals("addContactForm", view);

        // Verificar que se haya agregado el mensaje de error al modelo
        verify(model, times(1)).addAttribute("errorMessage", "El correo electrónico debe contener un '@'.");

        // Verificar que el método createContacto del servicio fue llamado
        verify(contactoService, times(1)).createContacto(contactoDTO);
    }
}
