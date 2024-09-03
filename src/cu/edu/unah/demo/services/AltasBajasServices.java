package cu.edu.unah.demo.services;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import java.util.ArrayList;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AltasBajasServices {

    @Autowired
    private AltasBajasRepository altasbajasrepository;
    @Autowired
    private StudentsRepository studentsRepository;

    public List<AltasBajas> findAll() {
        return altasbajasrepository.findAll();
    }

    public List<AltasBajas> findAll(int year, int mount) {
        List<AltasBajas> lista = new ArrayList<>();
        for (AltasBajas altasBajas : altasbajasrepository.findAll()) {
            if (altasBajas.getDate().getMonth() == mount && altasBajas.getDate().getYear() == year) {
                lista.add(altasBajas);
            }
        }
        return lista;
    }
    
    public AltasBajas findByIdIfExist(String id) {
        if(altasbajasrepository.existsById(id)){
            return altasbajasrepository.findById(id).get();
        }
        return null;
    }
    public AltasBajas findById(String id) {
        return altasbajasrepository.findById(id).get();
    }

    public AltasBajas save(AltasBajas altasbajas) {
        if (altasbajas.getCi() != null && altasbajasrepository.existsById(altasbajas.getCi())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
//                System.out.println("paso");
//                altasbajas.setStudents(studentsRepository.findById(altasbajas.getCi()).get());
        return altasbajasrepository.save(altasbajas);
    }

    public AltasBajas update(AltasBajas altasbajas) {
        if (altasbajas.getCi() != null && !altasbajasrepository.existsById(altasbajas.getCi())) {
            System.out.println("no encuentra el ci");
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        return altasbajasrepository.save(altasbajas);
    }

    public void delete(String id) {
        if (!altasbajasrepository.existsById(id)) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        altasbajasrepository.deleteById(id);
    }

    public String[][] getDatosAltasBajas() {
        return getDatosAltasBajas(findAll());
    }

    public String[][] getDatosAltasBajas(int year, int mount) {
        return getDatosAltasBajas(findAll(year, mount));
    }

    private String[][] getDatosAltasBajas(List<AltasBajas> altasbajas) {
        altasbajas.sort(new Comparator<AltasBajas>() {
            @Override
            public int compare(AltasBajas o1, AltasBajas o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        String[] titulos = new String[]{"Ci", "Baja", "Fecha", "Municipio", "Provincia", "Escuela"};
        String[][] datos = new String[altasbajas.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"2", "1", "2", "2", "2", "2"};
        int row = 2;
        for (AltasBajas altabaja : altasbajas) {
            datos[row][0] = altabaja.getCi();
            datos[row][1] = altabaja.getBaja() ? "Baja" : "Alta";
            datos[row][2] = altabaja.getDate().getYear() + "-" + (altabaja.getDate().getMonth() + 1) + "-" + altabaja.getDate().getDate();
            datos[row][3] = altabaja.getMunicipality();
            datos[row][4] = altabaja.getProvince();
            datos[row][5] = altabaja.getSchool();
            row++;
        }
        return datos;
    }
}
