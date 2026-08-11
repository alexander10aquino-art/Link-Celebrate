package com.linkandcelebrate.backend.validator;

import com.linkandcelebrate.backend.model.Usuarios;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UsuariosValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Usuarios.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Usuarios usuario = (Usuarios) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required", "El nombre de usuario es obligatorio");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "El correo electrónico es obligatorio");
    }
}