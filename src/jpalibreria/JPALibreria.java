/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpalibreria;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author LENOVO
 */
public class JPALibreria {

    /**
     * Cosas por mejorar: 
     *  En caso numero 3, estaria interesante que cuando se quiera crear un 
     * sin  antes haber ingresado el autor o la editorial, en vez de que me tire la exception
     * me lleve hacia el metodo para crear el autor o la editorial o el autor y la editorial.
     * 
     *  En el caso 5 y 6, creo dos metodos en LibroService que me imprimen los objetos de forma individual
     * , (lo que cambia son los parametros pasados) y en esos mismo metodo se llama a los metodos de 
     * buscar por isbn o buscar por titulo. En los casos 7 y 8, utilizo otro forma de hacer esto en donde 
     * se esta escribiendo mas codigo en los case pero me estoy ahorrando el crear dos metodos por separado
     * para imprima las listas de libros. Estaria interesante encontrar la forma mas eficiente de hacerlo y
     * aplicarla en los 4 casos.
     * 
     * No estoy seguro de la cantidad de metodos creados en la clase Menu(), ya que me reduce el switch
     * del menu pero me agrega muchos metodos por separado, igualmente creo que la forma en que lo hice 
     * es la mas correcta y mas ordenada.
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            Menu menu1 = new Menu();
            menu1.menuOpciones();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
