/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.serializadores;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import cu.edu.unah.demo.model.*;
import cu.edu.unah.demo.services.GraduadoServices;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Rene
 */
public class NotasGraduadoSerializer extends JsonSerializer<Notagraduado> {

    @Autowired
    private GraduadoServices graduadoservices;

    @Override
    public void serialize(Notagraduado nota, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("asnota", nota.getAsnota() == null ? "" : nota.getAsnota() + "");

        jsonGenerator.writeStringField("notafinal", nota.getNotafinal() == null ? "" : nota.getNotafinal() + "");

        jsonGenerator.writeStringField("pruebafinal", nota.getPruebafinal() == null ? "" : nota.getPruebafinal() + "");
        jsonGenerator.writeStringField("tcp1", nota.getTcp1() == null ? "" : nota.getTcp1() + "");
        jsonGenerator.writeStringField("tcp2", nota.getTcp2() == null ? "" : nota.getTcp2() + "");
        if (nota.getIdgraduado() == null) {
            jsonGenerator.writeStringField("idgraduado", "");
            jsonGenerator.writeStringField("ci", "");
            jsonGenerator.writeStringField("nombre", "");
            jsonGenerator.writeStringField("apellidos", "");
        } else {
            Graduado graduado = graduadoservices.findById(nota.getIdgraduado());
            jsonGenerator.writeStringField("idgraduado", nota.getIdgraduado() + "");
            jsonGenerator.writeStringField("ci", graduado.getCi());
            jsonGenerator.writeStringField("nombre", graduado.getNombre());
            jsonGenerator.writeStringField("apellidos", graduado.getApellidos());

        }

        jsonGenerator.writeStringField("nombreasignatura", nota.getNombreasignatura());
//       

        jsonGenerator.writeEndObject();
//        System.out.println("salio del serializador");
    }
}
