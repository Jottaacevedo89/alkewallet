package com.javiera.alke.service;

import com.javiera.alke.dto.ContactoDTO;
import com.javiera.alke.model.Contacto;
import com.javiera.alke.model.User;
import com.javiera.alke.repository.ContactoRepository;
import com.javiera.alke.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoServiceImpl implements ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Contacto> getContactosByUserId(String userId) {
        return contactoRepository.findByUserId(userId);
    }

    @Override
    public String createContacto(ContactoDTO contactoDTO) {
        // Verificar si el usuario actual existe
        Optional<User> currentUserOptional = userRepository.findById(contactoDTO.getUserId());
        if (!currentUserOptional.isPresent()) {
            return "El usuario no está registrado en el sistema.";
        }

        // Verificar si el usuario de contacto existe
        Optional<User> contactUserOptional = userRepository.findById(contactoDTO.getContactUserId());
        if (!contactUserOptional.isPresent()) {
            return "El contacto no está registrado en el sistema.";
        }

        User currentUser = currentUserOptional.get();
        User contactUser = contactUserOptional.get();

        // Crear nuevo contacto
        Contacto contacto = new Contacto();
        contacto.setUser(currentUser);
        contacto.setContactUser(contactUser);
        contacto.setNameContactUserId(contactoDTO.getNameContactUserId());
        contacto.setEmail(contactoDTO.getEmail());
        contactoRepository.save(contacto);

        return "success";
    }
}


