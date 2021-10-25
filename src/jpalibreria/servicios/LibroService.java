/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpalibreria.servicios;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import jpalibreria.entidades.Autor;
import jpalibreria.entidades.Editorial;
import jpalibreria.entidades.Libro;
import jpalibreria.persistencia.AutorDAO;
import jpalibreria.persistencia.EditorialDAO;
import jpalibreria.persistencia.LibroDAO;

/**
 *
 * @author LENOVO
 */
public class LibroService {
    
    private final LibroDAO daoLibro;
    private final EditorialDAO daoEditorial;
    private final AutorDAO daoAutor;

    public LibroService() {
        this.daoLibro = new LibroDAO();
        this.daoEditorial = new EditorialDAO();
        this.daoAutor = new AutorDAO();
    }
    
    public void createLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Boolean alta, String nombreAutor, String nombreEditorial) throws Exception{
        try {
            if (isbn == null || isbn < 0) {
                throw new Exception("Isbn no valido.");
            }
            Libro libroValidacion = daoLibro.searchForIsbn(isbn);
            if (libroValidacion != null) {
                throw new Exception("No es posible ingresar el libro por ya hay uno con el mismo isbn.");
            }
            
            if (titulo == null || titulo.trim().isEmpty()) {
                throw new Exception("Debe indicar el titulo.");
            }
            if (anio < 0) {
                throw new Exception("Año invalido.");
            }
            if (ejemplares <= 0) {
                throw new Exception("Numero de ejemplares invalido.");
            }
            if (ejemplaresPrestados > ejemplares) {
                throw new Exception("La cantidad de ejemplares prestados no puede ser mayor a la cantidad total de ejemplares.");
            }
            
            //validacion de autor
            Autor autorValidacion = daoAutor.searchForName(nombreAutor);
            if(autorValidacion == null){
                throw new Exception("Debe crear el autor antes de ingresar su libro.");
            }
            
            //validacion de editorial
            Editorial editorialValidacion = daoEditorial.searchForName(nombreEditorial);
            if (editorialValidacion == null) {
                throw new Exception("Debe crear la editorial antes de ingresar el libro.");
            }
            
            
            Libro libro = new Libro();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
            libro.setAlta(true);
            libro.setAutor(autorValidacion);
            libro.setEditorial(editorialValidacion);
            
            daoLibro.saveLibro(libro);
            System.out.println("Libro guardado exitosamente!!");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void removeLibro(Long isbn) throws Exception {
        try {
            //Valido el isbn.
            if (isbn == null || isbn < 0) {
                throw new Exception("Id no valido.");
            }
            
            //Busco el libro y valido
            Libro libro = daoLibro.searchForIsbn(isbn);
            if (libro == null) {
                throw new Exception("No se ha eliminado el libro.");
            }

            //Elimino el libro
            daoLibro.removeLibro(libro);
            System.out.println("Libro eliminado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void changeEjemplares(Long isbn, Integer ejemplares) throws Exception{
        try {
            //validacion de existencia de libro
            Libro libroValidacion = daoLibro.searchForIsbn(isbn);
            if (libroValidacion == null) {
                throw new Exception("No se encontro ningun libro, por ende no se modificará nada.");
            }
            
            //validacion del numero de ejemplares a modificar
            if (ejemplares == null || ejemplares < 0) {
                throw new Exception("Numero de ejemplares invalido.");
            }
            
            libroValidacion.setEjemplares(ejemplares);
            libroValidacion.setEjemplaresRestantes(ejemplares - libroValidacion.getEjemplaresPrestados());
            daoLibro.changeLibro(libroValidacion);
            System.out.println("El numero de ejemplares fue modificado exitosamente!!");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void changeEjemplaresPrestados(Long isbn, Integer ejemplaresPrestados) throws Exception{
        try {
            //validacion de existencia del libro en base de datos
            Libro libroValidacion = daoLibro.searchForIsbn(isbn);
            if (libroValidacion == null) {
                throw new Exception("No se encontro ningun libro, por ende no se modificará nada.");
            }
            
            //validacion del numero de ejemplares a modificar
            if (ejemplaresPrestados == null || ejemplaresPrestados < 0) {
                throw new Exception("Numeor de ejemplares invalido.");
            }
            //valido para que la cantidad de ejemplares prestados sea igual o menor a la cantidad total de ejemplares
            if (ejemplaresPrestados > libroValidacion.getEjemplares()) {
                throw new Exception("La cantidad de ejemplares prestados no puede ser mayor a la cantidad total de ejemplares.");
            }
            
            libroValidacion.setEjemplaresPrestados(ejemplaresPrestados);
            libroValidacion.setEjemplaresRestantes(libroValidacion.getEjemplares() - ejemplaresPrestados);
            daoLibro.changeLibro(libroValidacion);
            System.out.println("El numero de ejemplares prestados fue modificado exitosamente!!");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public Libro searchLibroForIsbn(Long isbn) throws Exception{
        try {
            //validacion de isbn
            if (isbn == null || isbn < 0) {
                throw new Exception("Isbn no valido.");
            }
            
            Libro libro = daoLibro.searchForIsbn(isbn);
            if (libro == null) {
                throw new Exception("No se ha encontrado ningun libro.");
            }
            
            return libro;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public Libro searchLibroForTitle(String title) throws Exception{
        try {
            //validacion del titulo
            if (title == null || title.trim().isEmpty()) {
                throw new Exception("Titulo no valido.");
            }
            
            Libro libro = daoLibro.searchForTitle(title);
            if (libro == null) {
                throw new Exception("No se ha encontrado ningun libro.");
            }
            
            return libro;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<Libro> searchLibrosForAutor(String name) throws Exception{
        try {
            //validacion del nombre
            if (name == null || name.trim().isEmpty()) {
                throw new Exception("Nombre de autor no valido.");
            }
            
            List<Libro> libros = daoLibro.searchForAutor(name);
            if (libros == null) {
                throw new Exception("No se ha encontrado ningun libro.");
            }
            
            return libros;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<Libro> searchLibrosForEditorial(String name) throws Exception{
        try {
            //validacion del nombre
            if (name == null || name.trim().isEmpty()) {
                throw new Exception("Nombre de editorial no valido.");
            }
            
            List<Libro> libros = daoLibro.searchForEditorial(name);
            if (libros == null) {
                throw new Exception("No se ha encontrado ningun libro.");
            }
            
            return libros;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public void imprimirLibros(List<Libro> libros) throws Exception {
        try {

            //Imprimimos los usuarios - Solo algunos atributos....
            if (libros.isEmpty()) {
                throw new Exception("No existen libros para imprimir");
            } else {
                for (Libro l : libros) {
                    System.out.println("*****************************************");
                    System.out.println(" ISBN:" + l.getIsbn() +
                                   "\n Titulo:" + l.getTitulo() +
                                   "\n Año:" + l.getAnio() +
                                   "\n Ejemplares:" + l.getEjemplares() +
                                   "\n Ejemplares Prestados:" + l.getEjemplaresPrestados() +
                                   "\n Ejemplares Restantes:" + l.getEjemplaresRestantes() +
                                   "\n Alta:" + l.getAlta() +
                                   "\n Autor:" + l.getAutor().toString() +
                                   "\n Editorial:" + l.getEditorial().toString());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }

    public Collection<Libro> listaLibro() throws Exception {
        try {
            Collection<Libro> libros = daoLibro.listarLibros();
            return libros;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimirLibroPorIsbn(Long isbn) throws Exception {
        try {
            Libro l = daoLibro.searchForIsbn(isbn);
            System.out.println(" ISBN:" + l.getIsbn() +
                                   "\n Titulo:" + l.getTitulo() +
                                   "\n Año:" + l.getAnio() +
                                   "\n Ejemplares:" + l.getEjemplares() +
                                   "\n Ejemplares Prestados:" + l.getEjemplaresPrestados() +
                                   "\n Ejemplares Restantes:" + l.getEjemplaresRestantes() +
                                   "\n Alta:" + l.getAlta() +
                                   "\n Autor:" + l.getAutor().toString() +
                                   "\n Editorial:" + l.getEditorial().toString());
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimirLibroPorTitulo(String title) throws Exception {
        try {
            Libro l = daoLibro.searchForTitle(title);
            System.out.println(" ISBN:" + l.getIsbn() +
                                   "\n Titulo:" + l.getTitulo() +
                                   "\n Año:" + l.getAnio() +
                                   "\n Ejemplares:" + l.getEjemplares() +
                                   "\n Ejemplares Prestados:" + l.getEjemplaresPrestados() +
                                   "\n Ejemplares Restantes:" + l.getEjemplaresRestantes() +
                                   "\n Alta:" + l.getAlta() +
                                   "\n Autor:" + l.getAutor().toString() +
                                   "\n Editorial:" + l.getEditorial().toString());
        } catch (Exception e) {
            throw e;
        }
    }
}
