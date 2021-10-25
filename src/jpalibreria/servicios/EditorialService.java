/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpalibreria.servicios;

import jpalibreria.entidades.Editorial;
import jpalibreria.persistencia.EditorialDAO;

/**
 *
 * @author LENOVO
 */
public class EditorialService {

    private final EditorialDAO daoEditorial;

    public EditorialService() {
        this.daoEditorial = new EditorialDAO();
    }

    public void createEditorial(String nombre, Boolean alta) throws Exception {
        try {
            //validaciones
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new Exception("Falta ingresar el nombre de la editorial.");
            }
            if (alta == null || alta == false) {
                throw new Exception("El atributo alta tiene que encontrarse como true.");
            }

            Editorial editoValidacion = daoEditorial.searchForName(nombre);
            if (editoValidacion != null) {
                System.out.println("Ya hay una editorial con el nombre ingresado. No se permite crear dos editoriales con el mismo nombre.");
            } else {
                //creacion del objeto y llamado al dao
                Editorial editorial = new Editorial();

                editorial.setNombre(nombre);
                editorial.setAlta(alta);

                daoEditorial.saveEditorial(editorial);
                System.out.println("Editorial creada exitosamente!!");
            }
        } catch (Exception e) {
            System.out.println("No se creo la editorial." + e.getMessage());
        }
    }

    public void removeEditorial(Integer id) throws Exception {
        try {
            //Valido el id.
            if (id == null || id < 0) {
                throw new Exception("Id no valido.");
            }

            //Busco la editorial y valido
            Editorial editorial = daoEditorial.searchForId(id);
            if (editorial == null) {
                throw new Exception("No se ha eliminado la editorial.");
            }

            //Elimino la editorial
            daoEditorial.removeEditorial(id);
            System.out.println("Editorial eliminada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Solo se puede cambiar el atributo nombre de editorial, el id no es correcto cambiarlo.
    public void changeNameOfEditorial(Integer id, String name) throws Exception {
        try {
            //Valido el id
            if (id == null || id < 0) {
                throw new Exception("Id no valido.");
            }

            //Busco la editorial y valido 
            Editorial editorial = daoEditorial.searchForId(id);
            if (editorial == null) {
                throw new Exception("No se ha modificado la editorial.");
            }

            //seteo el nombre y modifico la editorial
            editorial.setNombre(name);
            daoEditorial.changeEditorial(editorial);
            System.out.println("Editorial modificada exitosamente!!");
        } catch (Exception e) {
            throw e;
        }
    }

    public Editorial searchEditorial(Integer id) throws Exception {
        try {
            //Valido el id
            if (id == null || id < 0) {
                throw new Exception("Id no valido.");
            }

            //Busco la editorial y valido 
            Editorial editorial = daoEditorial.searchForId(id);
            if (editorial == null) {
                throw new Exception("No se mostrará ninguna editorial.");
            }

            return editorial;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
