/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpalibreria.servicios;

import java.util.ArrayList;
import java.util.List;
import jpalibreria.entidades.Autor;
import jpalibreria.persistencia.AutorDAO;

/**
 *
 * @author LENOVO
 */
public class AutorService {

    private final AutorDAO daoAutor;

    public AutorService() {
        this.daoAutor = new AutorDAO();
    }

    public void createAutor(String nombre, Boolean alta) throws Exception {
        try {
            //Validaciones
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("Falto ingresar el nombre del autor.");
            }
            if (alta == null || alta == false) {
                throw new Exception("El atributo alta debe encontrarse como true.");
            }

            Autor autorValidacion = daoAutor.searchForName(nombre);
            if (autorValidacion != null) {
                System.out.println("Ya hay un autor con el nombre ingresado. No se permite crear dos autores con el mismo nombre.");
            } else {
                //Creacion del objeto y seteo de atributos
                Autor autor = new Autor();
                autor.setNombre(nombre);
                autor.setAlta(alta);

                daoAutor.saveAutor(autor);

                System.out.println("Autor creado exitosamente!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void removeAutor(Integer id) throws Exception {
        try {
            if (id == null || id < 0) {
                throw new Exception("Id no valido.");
            }

            Autor autor = daoAutor.searchForId(id);
            if (autor == null) {
                throw new Exception("No se ha eliminado nigun autor.");
            }

            daoAutor.removeAutor(autor);
            System.out.println("Autor eliminado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void changeNameOfAutor(Integer id, String name) throws Exception {
        try {
            if (id == null || id < 0) {
                throw new Exception("Id no valido.");
            }

            Autor autor = daoAutor.searchForId(id);
            if (autor == null) {
                throw new Exception("No se a modificado ningun autor");
            }

            autor.setNombre(name);
            daoAutor.changeAutor(autor);
            System.out.println("Autor modificado exitosamente!!");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Autor searchForId(Integer id) throws Exception {
        try {
            //Valido
            if (id == null || id < 0) {
                throw new Exception("Id no valido.");
            }

            //Busco y valido
            Autor autor = daoAutor.searchForId(id);
            if (autor == null) {
                throw new Exception("No se ha encontrado ningun autor con ese id.");
            }

            return autor;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Autor searchForName(String name) throws Exception {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new Exception("Nombre no valido.");
            }

            Autor autor = daoAutor.searchForName(name);
//            if (autor == null) {
//                throw new Exception("No se encontro ningun autor.");
//            }

            return autor;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void imprimirUnAutor(String name) throws Exception {
        try {
            Autor autor = daoAutor.searchForName(name);
            System.out.println("Id: " + autor.getId() + "\nNombre: " + autor.getNombre() + "\nAlta: " + autor.getAlta());
        } catch (Exception e) {
            throw e;
        }
    }
}
