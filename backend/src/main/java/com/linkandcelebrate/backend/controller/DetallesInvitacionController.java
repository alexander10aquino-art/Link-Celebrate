package com.linkandcelebrate.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DetallesInvitacionController {

    @Autowired
    private PlantillasCatalogoController plantillasCatalogoController;
    @Autowired
    private UsuariosController usuariosController;
}
