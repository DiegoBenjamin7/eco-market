package com.padima.microservicioproveedores.service;

import com.padima.microservicioproveedores.model.proveedores;
import com.padima.microservicioproveedores.repository.ProveedoresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class proveedoresService {

    private final ProveedoresRepository proveedorRepository;

    public proveedores crearProveedor(proveedores proveedor) {
        if (proveedorRepository.existsByRutEmpresa(proveedor.getRutEmpresa())) {
            throw new IllegalStateException("Ya existe un proveedor con este RUT");
        }
        return proveedorRepository.save(proveedor);
    }

    public List<proveedores> obtenerTodosProveedores() {
        return proveedorRepository.findAll();
    }

    public proveedores buscarProveedorPorId(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
    }

    public proveedores actualizarProveedor(Long id, proveedores proveedorActualizado) {
        return proveedorRepository.findById(id)
                .map(proveedor -> {
                    proveedor.setNombre(proveedorActualizado.getNombre());
                    proveedor.setDireccion(proveedorActualizado.getDireccion());
                    proveedor.setEmail(proveedorActualizado.getEmail());
                    proveedor.setTelefono(proveedorActualizado.getTelefono());
                    proveedor.setTipo(proveedorActualizado.getTipo());
                    return proveedorRepository.save(proveedor);
                })
                .orElseGet(() -> {
                    proveedorActualizado.setId(id);
                    return proveedorRepository.save(proveedorActualizado);
                });
    }

    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    public List<proveedores> buscarProveedoresPorTipo(String tipo) {
        return proveedorRepository.findByTipo(tipo);
    }

    public List<proveedores> buscarPorTipoYNombre(String tipo, String nombre) {
        return proveedorRepository.findByTipoAndNombreContainingIgnoreCase(tipo, nombre);
    }

    public List<proveedores> buscarPorNombreContaining(String nombre) {
        return proveedorRepository.findByNombreContainingIgnoreCase(nombre);
    }

}