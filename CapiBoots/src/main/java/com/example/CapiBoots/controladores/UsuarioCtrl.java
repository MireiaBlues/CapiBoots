package com.example.CapiBoots.controladores;

import com.example.CapiBoots.dto.UsuarioDto;
import com.example.CapiBoots.modelos.Contenidos;
import com.example.CapiBoots.modelos.Usuario;
import com.example.CapiBoots.servicios.AccesosSrvcImpls;
import com.example.CapiBoots.servicios.UsuarioSrvcImpls;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioCtrl {

    @Autowired
    private UsuarioSrvcImpls usuSrvc;

    @Autowired
    private AccesosSrvcImpls accessSrvc;

    @GetMapping("/usuario-id")
    public String UsuPorId(@PathVariable Long id, Model modelo){
        modelo.addAttribute("idusu", usuSrvc.buscaId(id));
        return "usuario";   //Buscar en búsqueda con el filtro de "Usuarios"
    }

    //Desplegable Perfil


    @GetMapping("/ajustes")
    public String Ajustes(Principal principal, Model modelo) {
        modelo.addAttribute("titulo", "Ajustes");
        String usuID = principal.getName();
        Usuario user =  usuSrvc.buscaPorNombre(usuID);
        modelo.addAttribute("usuario",user);
        modelo.addAttribute("userID", usuID);
        return "/administrarUsuario/ajustes";
    }


    //Listas de Usuarios
    @GetMapping("/lista-usuarios")
    public String listaUsus(Model modelo){
        modelo.addAttribute("listausuarios", usuSrvc.listaUsus());
        return "/listas/lista-usus";
    }
    @GetMapping("/buscarusus")
    public String buscarUsus(@PathVariable String keyword , Model modelo){
        List<Usuario> buscausu = usuSrvc.buscaUsus(keyword);
        modelo.addAttribute("buscausuarios", buscausu);
        return "/listas/lista-usus";
    }



    //Crear, Guardar, Borrar y Editar

    @GetMapping("/usuario/nuevo-usuario")
    public String nuevo(Model modelo){
        modelo.addAttribute("usuario", new Usuario());
        return "/forms/nuevo-usuario";
    }

    @PostMapping("/usuario/guardar")
    public String guardar(Usuario usu){
        usuSrvc.guardarUs(usu);
        return "redirect:/lista-usuarios";
    }

//    @PostMapping("/usuario/guardar")
//    public String guardar(@Valid @ModelAttribute("usuario") UsuarioDto userDto, BindingResult result,
//                               Model model){
//        //Busca si existe un usuario con el mismo nombre que hemos introducido
//        Usuario existingUser = usuSrvc.buscaPorCorreo(userDto.getCorreo());
//
//        //Si existe un usuario con el mismo nombre, salta un aviso
//        if(existingUser != null && existingUser.getCorreo() != null && !existingUser.getCorreo().isEmpty()){
//            result.rejectValue("correo", null,
//                    "Ya existe un usuario con ese email");
//        }
//
//        //Si hay algún error, recargamos la página de registro y declaramos otro userDto
//        if(result.hasErrors()){
//            model.addAttribute("usuario", userDto);
//            return "/forms/nuevo-usuario";
//        }
//
//        //Guardamos el usuario si se han cumplido las condiciones
//        usuSrvc.guardar(userDto);
//        return "redirect:/lista-usuarios";
//    }

    @PostMapping("/ajustes/guardar")
    public String guardarAjustes(Usuario usu){
        usuSrvc.guardarUs(usu);
        return "redirect:/ajustes";
    }

    @GetMapping("/usuario/borrar/{id}")
    public String borrar(@PathVariable Long id){
        usuSrvc.borrar(id);
        return "redirect:/lista-usuarios";
    }

    @GetMapping("/usuario/editar/{id}")
    public String editar(@PathVariable Long id, Model modelo){
        Optional<Usuario> usuOpt = usuSrvc.buscaId(id);
        if(usuOpt.isPresent()){
            modelo.addAttribute("usuario", usuOpt);
        }
        else{
            // Si no existe, redirigir a una página de error o mostrar un mensaje de error
            return "error";
        }
        return "/forms/nuevo-usuario";
    }

    @GetMapping("/ajustes/editar/{id}")
    public String editarAjustes(@PathVariable Long id, Model modelo){
        Optional<Usuario> usuOpt = usuSrvc.buscaId(id);
        if(usuOpt.isPresent()){
            modelo.addAttribute("usuario", usuOpt.get());
        }
        else{
            // Si no existe, redirigir a una página de error o mostrar un mensaje de error
            return "error";
        }
        return "/forms/editar-usuario-ajustes";
    }



    //Validación cuando se ha hecho login
    @GetMapping("/")
    String index(Principal principal) {
        return principal != null ? "home" : "inicio";
    }
}
