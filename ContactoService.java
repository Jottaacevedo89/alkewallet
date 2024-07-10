package com.javiera.alke.service;

import com.javiera.alke.dto.ContactoDTO;
import com.javiera.alke.model.Contacto;

import java.util.List;

public interface ContactoService {
    List<Contacto> getContactosByUserId(String userId);
    String createContacto(ContactoDTO contactoDTO);
}
