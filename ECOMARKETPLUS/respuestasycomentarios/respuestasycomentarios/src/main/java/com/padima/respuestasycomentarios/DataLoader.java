package com.padima.respuestasycomentarios;

import com.padima.respuestasycomentarios.model.Comentario;
import com.padima.respuestasycomentarios.service.ComentarioService;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {
    private final ComentarioService comentarioService;
    private final Faker faker = new Faker(new Locale("es"));

    public DataLoader(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @Override
    public void run(String... args) {
        for (int i = 0; i < 10; i++) {
            Comentario comentario = new Comentario();
            comentario.setUsuarioId(faker.number().randomNumber());
            comentario.setContenido(faker.lorem().sentence());
            comentario.setPublicacionId(faker.number().randomNumber());
            comentarioService.crearComentario(comentario);
        }
    }
}
