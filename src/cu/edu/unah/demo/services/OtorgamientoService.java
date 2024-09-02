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
}
