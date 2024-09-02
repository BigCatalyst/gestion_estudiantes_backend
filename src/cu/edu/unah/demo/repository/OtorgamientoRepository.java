/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cu.edu.unah.demo.model.*;
@Repository
public interface OtorgamientoRepository extends JpaRepository<Otorgamiento,Integer>{
    
}
