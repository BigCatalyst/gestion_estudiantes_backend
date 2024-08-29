/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.serializadores;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import cu.edu.unah.demo.model.*;

public class NotesSerializer extends JsonSerializer<Notes> {
    @Override
    public void serialize(Notes notes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//        System.out.println("entro en el serializador");
//        System.out.println(jsonGenerator);
        jsonGenerator.writeStartObject();
//        System.out.println("va abuscar los campos");
        jsonGenerator.writeStringField("acs", notes.getAcs()+"");
//        System.out.println("paso campo 1");
        jsonGenerator.writeStringField("finalNote", notes.getFinalNote()+"");
//        System.out.println("paso campo 2");
        jsonGenerator.writeStringField("finalExam", notes.getFinalExam()+"");
        jsonGenerator.writeStringField("tcp1", notes.getTcp1()+"");
        jsonGenerator.writeStringField("tcp2", notes.getTcp2()+"");
        
        jsonGenerator.writeStringField("student", notes.getNotesPK().getStudentCi());
        jsonGenerator.writeStringField("subject", notes.getNotesPK().getSubjectId()+"");
//        System.out.println("paso por aqui");
        // Agrega aquí información adicional si lo deseas
        // Por ejemplo, información del estudiante o del sujeto, si lo deseas
//        jsonGenerator.writeStringField("student", notes.getStudents().getCi());
//        jsonGenerator.writeStringField("subject", notes.getSubjects().getId()+"");
        
        jsonGenerator.writeEndObject();
//        System.out.println("salio del serializador");
    }
}
