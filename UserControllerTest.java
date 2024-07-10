package com.javiera.alke.controller;

import com.javiera.alke.service.UserService;
import com.javiera.alke.model.TransferForm;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService; // Mock del servicio de usuario

    @Mock
    private Principal principal; // Mock del objeto Principal para obtener el nombre del usuario autenticado

    @Mock
    private Model model; // Mock del objeto Model para agregar atributos al modelo

    @Mock
    private RedirectAttributes redirectAttributes; // Mock del objeto RedirectAttributes para manejar redirecciones y mensajes flash

    @InjectMocks
    private UserController userController; // Inyecta los mocks en una instancia del controlador de usuario

    @Test
    public void testTransferWithNegativeAmount() {
        // Crea un formulario de transferencia con un monto negativo
        TransferForm transferForm = new TransferForm();
        transferForm.setAmount(-100);

        // Configura el mock para que principal devuelva el nombre de usuario "user1"
        when(principal.getName()).thenReturn("user1");

        // Llama al método transfer del controlador
        String view = userController.transfer(transferForm, principal, model, redirectAttributes);

        // Verifica que la vista devuelta sea "redirect:/transfer"
        assertEquals("redirect:/transfer", view);

        // Verifica que se haya agregado el mensaje de error a RedirectAttributes
        verify(redirectAttributes, times(1)).addFlashAttribute("errorMessage", "El monto debe ser un número positivo");

        // Verifica que el método transfer del servicio de usuario nunca se haya llamado
        verify(userService, never()).transfer(anyString(), anyString(), anyDouble());
    }
}
