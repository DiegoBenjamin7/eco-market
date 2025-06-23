package com.padima.microserviciousuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.padima.microserviciousuario.model.Usuario;
import com.padima.microserviciousuario.services.UsuarioService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/Usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioservice;

    @GetMapping
    public ResponseEntity<List<Usuario>> ListarUsuario(){
        List<Usuario> usuarios = usuarioservice.BuscarTodo();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(usuarios);
        }
    }

    @GetMapping("/{rut}")  //id identificador
    public ResponseEntity<?> BuscarUsuario(@PathVariable Long rut){
        try {
            Usuario usuario = usuarioservice.BuscarUsuario(rut);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra el usuario");
        }
    }
    
    @PostMapping
    public ResponseEntity<String> guardarUsuario(@RequestBody Usuario usuario){
        try {
            Usuario usuarioCrear = usuarioservice.BuscarUsuario(usuario.getRut());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El rut esta registrado");
        } catch (Exception e) {
            Usuario usuarioNuevo = usuarioservice.GuardarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado de manera exitosa");
        } 
    }
    




    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long rut){
        try {
            Usuario usuarioEliminar = usuarioservice.BuscarUsuario(rut);
            usuarioservice.EliminarUsuario(rut);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado de manera exitosa");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario que desea eliminar no existe");
        }
    }
    

    @PutMapping("/{rut}")
    public ResponseEntity<?> actualizar(@PathVariable Long rut, @RequestBody Usuario usuarioActualizar){
        try{
            Usuario BuscarUsuario = usuarioservice.BuscarUsuario(rut);
            BuscarUsuario.setRut(rut);
            BuscarUsuario.setNombre(usuarioActualizar.getNombre());
            BuscarUsuario.setMail(usuarioActualizar.getMail());
            BuscarUsuario.setTelefono(usuarioActualizar.getTelefono());
            BuscarUsuario.setDireccion(usuarioActualizar.getDireccion());

            usuarioservice.GuardarUsuario(BuscarUsuario);
            return ResponseEntity.ok(usuarioActualizar);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EL ID DEL USUARIO NO ESTÁ REGISTRADO EN LA BASE DE DATO PARA PODER SER ACTUALIZADO");
        }
    }

}
