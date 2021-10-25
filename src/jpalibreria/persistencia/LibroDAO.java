/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpalibreria.persistencia;

import java.util.Collection;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import jpalibreria.entidades.Libro;

/**
 *
 * @author LENOVO
 */
public class LibroDAO extends DAO{
    
    public void saveLibro(Libro libro) throws Exception{
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            try {
                em.getTransaction().rollback();
            } catch (Exception ex) {
                throw new Exception("Error haciendo un rollback");
            }
            
            throw new Exception("Error al persitir una persona");
        }
    }
    
    public void changeLibro(Libro libro) throws Exception{
        try {
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al modificar el libro");
        }
    }
    
    public void removeLibro(Libro libro) throws Exception{
        try {
            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al eliminar un libro.");
        }
    }
    
    public Libro searchForIsbn(Long isbn) throws Exception{
        try {
            Libro libro = em.find(Libro.class, isbn);
            return libro;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public Libro searchForTitle(String titulo) throws Exception{
        try {
            Libro libro = (Libro) em.createQuery("SELECT l FROM Libro l WHERE l.titulo = :titulo ").setParameter("titulo", titulo).getSingleResult();
            return libro;
        
        } catch (NoResultException ex) {
            System.out.println("No se encontró ningun libro.");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<Libro> searchForAutor(String name) throws Exception{
        try {
            List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.autor.nombre = :name ").setParameter("name", name).getResultList();
            return libros;
        
        } catch (NoResultException ex) {
            System.out.println("No se encontro ningun libro.");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<Libro> searchForEditorial(String name) throws Exception{
        try {
            List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.editorial.nombre = :name ").setParameter("name", name).getResultList();
            return libros;
            
        } catch (NoResultException ex) {
            System.out.println("No se encontró ningun libro.");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<Libro> listarLibros() throws Exception {
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l").getResultList();
        return libros;
    }
}
