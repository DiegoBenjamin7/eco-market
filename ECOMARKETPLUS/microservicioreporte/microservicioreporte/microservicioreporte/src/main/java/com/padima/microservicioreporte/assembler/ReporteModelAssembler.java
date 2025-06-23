package com.padima.microservicioreporte.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.padima.microservicioreporte.controller.ReporteController;
import com.padima.microservicioreporte.model.Reporte;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>> {

    @Override
    public EntityModel<Reporte> toModel(Reporte reporte) {
        return EntityModel.of(
            reporte,
            linkTo(methodOn(ReporteController.class).getReporteById(reporte.getId())).withSelfRel(),
            linkTo(methodOn(ReporteController.class).generarReporte(reporte.getTipoReporte(), reporte.getFormato())).withRel("generar"),
            linkTo(methodOn(ReporteController.class).descargarReporte(reporte.getId())).withRel("descargar"),
            linkTo(methodOn(ReporteController.class).updateReporte(reporte.getId(), reporte)).withRel("actualizar"),
            linkTo(methodOn(ReporteController.class).deleteReporte(reporte.getId())).withRel("eliminar"),
            linkTo(methodOn(ReporteController.class).getAllReportes()).withRel("reportes")
        );
    }
}
