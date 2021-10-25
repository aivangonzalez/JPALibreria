/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpalibreria.persistencia;

import java.util.Collection;
import java.util.List;
import javax.persistence.NoResultException;
import jpalibreria.entidades.Autor;

/**
 *
 * @author LENOVO
 */
public class AutorDAO extends DAO{
    
    public void saveAutor(Autor autor) throws Exception{
        try {
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            try {
                em.getTransaction().rollback();
            } catch (Exception ex) {
                throw new Exception("Error haciendo un rollback");
            }
            
            throw new Exception("Error al persitir un autor.");
        }
    }
    
    public void changeAutor(Autor autor) throws Exception{
        try {
            em.getTransaction().begin();
            em.merge(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error al modificar el autor.");
        }
    }
    
    public void removeAutor(Autor autor) throws Exception{
        try {
            em.getTransaction().begin();
            em.remove(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error al eliminar el autor.");
        }
    }
    
    public Autor searchForId(Integer id) throws Exception{
        try {
            Autor autor = em.find(Autor.class, id);
            return autor;
        } catch (NoResultException ex) {
            System.out.println("No se ha encontrado la editorial.");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public Autor searchForName(String nombre) throws Exception{
        try {
            Autor autor = (Autor) em.createQuery("SELECT a FROM Autor a WHERE a.nombre = :nombre ").setParameter("nombre", nombre).getSingleResult();
            return autor;
        } catch (NoResultException ex) {
            System.out.println("No se encontro ningun autor con ese nombre.");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
