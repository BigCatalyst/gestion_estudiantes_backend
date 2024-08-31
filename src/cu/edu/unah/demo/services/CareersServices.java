package cu.edu.unah.demo.services;

import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CareersServices {

    @Autowired
    private CareersRepository careersrepository;

    public List<Careers> findAll() {
        return careersrepository.findAll();
    }
    
    public int getIdCorrespondiente(){
        List<Careers> entidades=findAll();
        if(entidades.isEmpty()){
            return 1;
        }
        entidades.sort((o1, o2) -> {
            return o1.getId().compareTo(o2.getId()); //To change body of generated lambdas, choose Tools | Templates.
        });
        return entidades.get(entidades.size()-1).getId()+1;
    }

    public Careers findById(Integer id) {
        if (!careersrepository.existsById(id)) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        return careersrepository.findById(id).get();
    }

    public Careers save(Careers careers) {
        if (careers.getId() != null && careersrepository.existsById(careers.getId())) {
            throw new EntityExistsException("There is already existing entity with such ID in the database.");
        }
        careers.setId(getIdCorrespondiente());
        return careersrepository.save(careers);
    }

    public Careers update(Careers careers) {
        if (careers.getId() != null && !careersrepository.existsById(careers.getId())) {
            throw new EntityNotFoundException("There is no entity with such ID in the database.");
        }
        return careersrepository.save(careers);
    }

    public void delete(Integer id) {
        careersrepository.deleteById(id);
    }

    public String[][] getDatosCarreras() {
        return getDatosCarreras(findAll());
    }

    private String[][] getDatosCarreras(List<Careers> carreras) {
        String[] titulos = new String[]{"Nombre", "Cantidad"};
        String[][] datos = new String[carreras.size() + 2][titulos.length];
        datos[0] = titulos;
        datos[1] = new String[]{"3", "1"};
        int row = 2;
        for (Careers careers : carreras) {

            datos[row][0] = careers.getName();
            datos[row][1] = careers.getAmount() + "";

            row++;
        }
        return datos;
    }
}
