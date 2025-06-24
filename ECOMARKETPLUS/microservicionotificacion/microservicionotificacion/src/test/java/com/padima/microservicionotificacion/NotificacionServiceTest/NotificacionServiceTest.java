package com.padima.microservicionotificacion.NotificacionServiceTest;



import com.padima.microservicionotificacion.dto.UsuarioDto;

import com.padima.microservicionotificacion.model.Notificacion;

import com.padima.microservicionotificacion.repository.NotificacionRepository;

import com.padima.microservicionotificacion.service.NotificacionService;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.time.LocalDate;

import java.util.Arrays;

import java.util.List;

import java.util.Optional;



import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class NotificacionServiceTest {



  @Mock

  private NotificacionRepository notificacionRepository;



  @Mock

  private WebClient webClient;



  @Mock

  private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;



  @Mock

  private WebClient.RequestHeadersSpec requestHeadersSpec;



  @Mock

  private WebClient.ResponseSpec responseSpec;



  @InjectMocks

  private NotificacionService notificacionService;



  private Notificacion notificacion;

  private UsuarioDto usuarioDto;



  @BeforeEach

  void setUp() {

    notificacion = new Notificacion();

    notificacion.setId(1L);

    notificacion.setRun("27020812-4");

    notificacion.setMensaje("Tu pedido ha sido Entregado con exito");

    notificacion.setFechaEnvio(LocalDate.now());

    notificacion.setEstado("Enviado");



    usuarioDto = new UsuarioDto();

    usuarioDto.setRun(25820817L);

    usuarioDto.setNombre("Juan");

    usuarioDto.setApellido("Pérez");

    usuarioDto.setDireccion("Av. Principal 123");

    usuarioDto.setContacto(987654321L);

    usuarioDto.setEmail("diego@duocuc.cl");

  }



  @Test

  void testBuscarTodo() {

    when(notificacionRepository.findAll()).thenReturn(Arrays.asList(notificacion));

    List<Notificacion> result = notificacionService.buscarTodo();

    assertEquals(1, result.size());

    assertEquals(notificacion, result.get(0));

    verify(notificacionRepository, times(1)).findAll();

  }



  @Test

  void testBuscar() {

    when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));

    Notificacion result = notificacionService.buscar(1L);

    assertNotNull(result);

    assertEquals(notificacion, result);

    verify(notificacionRepository, times(1)).findById(1L);

  }



  @Test

  void testGuardar() {

    when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacion);

    Notificacion result = notificacionService.guardar(notificacion);

    assertNotNull(result);

    assertEquals(notificacion, result);

    verify(notificacionRepository, times(1)).save(notificacion);

  }



  @Test

  void testEliminar() {

    doNothing().when(notificacionRepository).deleteById(1L);

    notificacionService.eliminar(1L);

    verify(notificacionRepository, times(1)).deleteById(1L);

  }



  @Test

  void testBuscarUsuario() {

    String run = "27020812-4";

    when(webClient.get()).thenReturn(requestHeadersUriSpec);

    when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);

    when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

    when(responseSpec.bodyToMono(UsuarioDto.class)).thenReturn(Mono.just(usuarioDto));



    UsuarioDto result = notificacionService.BuscarUsuario(run);

    assertNotNull(result);

    assertEquals(usuarioDto, result);

    verify(webClient, times(1)).get();

  }



  @Test

  void testBuscarUsuario_NotFound() {

    String run = "27020812-4";

    when(webClient.get()).thenReturn(requestHeadersUriSpec);

    when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);

    when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

    when(responseSpec.bodyToMono(UsuarioDto.class)).thenReturn(Mono.empty());



    UsuarioDto result = notificacionService.BuscarUsuario(run);

    assertNull(result);

    verify(webClient, times(1)).get();

  }

}