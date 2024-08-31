package cu.edu.unah.demo.services;

import cu.edu.unah.demo.exceptions.BadRequestException;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.reportes.ReportesUtiles;
import cu.edu.unah.demo.repository.*;
import java.util.HashMap;
import org.hibernate.id.IdentifierGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class NotagraduadoServices {

    @Autowired
    private GraduadoServices graduadoservices;
    @Autowired
    private NotagraduadoRepository notagraduadorepository;

    public List<Notagraduado> findAll() {
        return notagraduadorepository.findAll();
    }

    public Notagraduado findById(Integer id) {
        return notagraduadorepository.findById(id).get();
    }

    public Notagraduado save(Notagraduado notagraduado) {
        if (notagraduado.getId() != null && notagraduadorepository.existsById(notagraduado.getId())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        return notagraduadorepository.save(notagraduado);
    }

    public Notagraduado update(Notagraduado notagraduado) {
        if (notagraduado.getId() != null && !notagraduadorepository.existsById(notagraduado.getId())) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        return notagraduadorepository.save(notagraduado);
    }

    public void delete(Integer id) {
        notagraduadorepository.deleteById(id);
    }

    public String[][] getDatosReporteNotasGraduado() {
        return getDatosReporteNotasGraduado(findAll());
    }

    private String[][] getDatosReporteNotasGraduado(List<Notagraduado> notas) {
        String[] titulos = new String[]{"Ci", "Asignatura", "AS", "TCP1", "TCP2", "EF", "NF"};
        String[][] datos = new String[notas.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"2", "3", "1", "1", "1", "1", "1"};
        int row = 2;
        for (Notagraduado nota : notas) {
            if(nota.getIdgraduado()!=null){
                Graduado graduado = graduadoservices.findById(nota.getIdgraduado());
                datos[row][0] = graduado.getCi();
            }else{
                datos[row][0] = "-";
            }
            
            datos[row][1] = nota.getNombreasignatura();
            datos[row][2] = nota.getAsnota() != null ? nota.getAsnota() + "" : "";
            datos[row][3] = nota.getTcp1() != null ? nota.getTcp1() + "" : "";
            datos[row][4] = nota.getTcp2() != null ? nota.getTcp2() + "" : "";
            datos[row][5] = nota.getPruebafinal() != null ? nota.getPruebafinal() + "" : "";
            datos[row][6] = nota.getNotafinal() != null ? nota.getNotafinal() + "" : "";
            row++;
        }
        return datos;
    }
}
