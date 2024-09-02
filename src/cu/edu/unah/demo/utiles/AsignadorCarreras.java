/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.utiles;

import java.util.*;
import java.util.stream.Collectors;
import cu.edu.unah.demo.exceptions.BadRequestException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.reportes.ReportesUtiles;
import cu.edu.unah.demo.repository.StudentCareerRepository;
import cu.edu.unah.demo.serializadores.*;
import cu.edu.unah.demo.services.*;
import java.util.HashMap;
import javax.persistence.EntityExistsException;
import org.hibernate.id.IdentifierGenerationException;

public class AsignadorCarreras {
//    @Autowired
//    private StudentCareerServices studentcareerservices;
//    
//    @Autowired
//    private StudentCareerRepository studentcareerrepository;
//
//    @Autowired
//    private SubjectsServices subjectsservices;
//
//    @Autowired
//    private CareersServices careersservices;
//
//    @Autowired
//    private StudentsServices studentsservices;
//    
//    private OtorgamientoService otorgamientoService;
//    
//    public void asignarCarreras(List<Careers> carrerasDisponibles, 
//                                                List<BoletaEstudiante> boletasEstudiantes, 
//                                                List<UbicacionEscalafonResponse> ubicacionEscalafon) {
//        Date date = new Date();
//        
//        // Mapa para llevar el control de las plazas disponibles por carrera
//        Map<String, Integer> plazasDisponibles = carrerasDisponibles.stream()
//            .collect(Collectors.toMap(Careers::getName, Careers::getAmount));
//
//        List<Otorgamiento> otorgamientos = new ArrayList<>();
//        
//        // Asignar carreras, considerando la priorización de escalafón y deseos
//        for (UbicacionEscalafonResponse ubicacion : ubicacionEscalafon) {
//            String ciEstudiante = ubicacion.getEstudiante().getCi();
//            BoletaEstudiante boleta = boletasEstudiantes.stream()
//                .filter(b -> b.getCi().equals(ciEstudiante))
//                .findFirst()
//                .orElse(null);
//            
//            if (boleta != null) {
//                // Buscar la carrera que puede ser asignada según los deseos y disponibilidad
//                for (String deseo : boleta.nombre_carreras) {
//                    Integer plazas = plazasDisponibles.get(deseo);
//                    if (plazas != null && plazas > 0) {
//                        Otorgamiento otorgamiento=otorgamientoService.findById(ciEstudiante);
//                        if(otorgamiento==null){
//                            otorgamiento = new Otorgamiento();
//                            otorgamiento.setCi(ciEstudiante);
//                            otorgamiento.setNoescalafon(ubicacion.getLugar());
//                            otorgamiento.setYeargraduacion(date.getYear() + 1900);
//                            System.out.println(otorgamiento.getYeargraduacion());
//                            otorgamiento.setNotaescalafon(ubicacion.getPromedio());
//                            otorgamiento.setCarrera(deseo);
//                            otorgamientoService.save(otorgamiento);
//                        }else {
//                            otorgamientoService.update(otorgamiento);
//                        }
//                        
////                        // Asignamos la carrera
////                        otorgamientos.add(new Otorgamiento(ciEstudiante, deseo));
//                        // Reducimos la cantidad de plazas disponibles
//                        plazasDisponibles.put(deseo, plazas - 1);
//                        break; // Salimos del bucle ya que el estudiante ha sido asignado
//                    }
//                }
//            }
//        }
//        
//        return otorgamientos;
//    }
}
