/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.services;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import cu.edu.unah.demo.exceptions.*;
import cu.edu.unah.demo.serializadores.UbicacionEscalafonResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtorgamientoService {

    @Autowired
    OtorgamientoRepository otorgamientoRepository;

    public List<Otorgamiento> findAll() {
        return otorgamientoRepository.findAll();
    }

    public Otorgamiento findById(Integer id) {
        return otorgamientoRepository.findById(id).get();
    }
    
    public Otorgamiento findById(String ci) {
        for (Otorgamiento otorgamiento : findAll()) {
            if(otorgamiento.getCi().equals(ci)){
                return otorgamiento;
            }
        }
        return null;
    }

    public Otorgamiento save(Otorgamiento otorgamiento) {
        if (otorgamiento.getId() != null && otorgamientoRepository.existsById(otorgamiento.getId())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        return otorgamientoRepository.save(otorgamiento);
    }

    public Otorgamiento update(Otorgamiento otorgamiento) {
        if (otorgamiento.getId() != null && !otorgamientoRepository.existsById(otorgamiento.getId())) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        return otorgamientoRepository.save(otorgamiento);
    }

    public void delete(Integer id) {
        otorgamientoRepository.deleteById(id);
    }
    public void deleteAll() {
        for (Otorgamiento otorgamiento : findAll()) {
            delete(otorgamiento.getId());
        }
    }
    public String[][] getDatosReporteOtorgamiento() {
        return getDatosReporteOtorgamiento(findAll());
    }
    private String[][] getDatosReporteOtorgamiento(List<Otorgamiento> otorgamientos) {
        String[] titulos = new String[]{"Ci", "Año", "Carrera", "Nota", "Escalafon"};
        String[][] datos = new String[otorgamientos.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"2", "1", "3", "1", "1"};
        int row = 2;
        for (Otorgamiento otorgamiento : otorgamientos) {
            
            datos[row][0] = otorgamiento.getCi();
            datos[row][1] = otorgamiento.getYeargraduacion()+"";
            datos[row][2] = otorgamiento.getCarrera();
            datos[row][3] = otorgamiento.getNotaescalafon()+"";
            datos[row][4] = otorgamiento.getNoescalafon()+"";
            row++;
        }
        return datos;
    }
}

