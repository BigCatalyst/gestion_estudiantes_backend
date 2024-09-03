/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.edu.unah.demo.utiles;

/**
 *
 * @author Rene
 */
public abstract class Utiles {
    public static String getDoubleStr(String numero_str){
        if(numero_str==null){
            return null;
        }
        try{
            double numero=Double.parseDouble(numero_str);
            return String.format("%.2f", numero);
        }catch(Exception ex){
            return null;
        }
    }
    
    public static String getIntegerStr(String numero_str,int cantidad_caracteres){
        if(numero_str==null){
            return null;
        }
        try{
            double numero=Integer.parseInt(numero_str);
            return ajustarString(numero+"", cantidad_caracteres);
        }catch(Exception ex){
            return null;
        }
    }
    
    public static String ajustarString(String entrada,int cantidad_caracteres){
        if(entrada==null){
            return "";
        }
        if(entrada.length()>=cantidad_caracteres){
            return entrada.substring(0, cantidad_caracteres);
        }
        return entrada;
    }
    
}
