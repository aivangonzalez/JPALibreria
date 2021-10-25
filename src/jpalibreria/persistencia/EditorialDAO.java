/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpalibreria.persistencia;

import javax.persistence.NoResultException;
import jpalibreria.entidades.Editorial;

/**
 *
 * @author LENOVO
 */
public class EditorialDAO extends DAO{
    
    public void saveEditorial(Editorial editorial) throws Exception{
        try {
           em.getTransaction().begin();
           em.persist(editorial);
           em.getTransaction().commit();
        } catch (Exception e) {
            try {
                em.getTransaction().rollback();
            } catch (Exception ex) {
                throw new Exception("Error haciendo un rollback");
            }
            
            throw new Exception("Error al persitir una editorial");
        }
    }
    
    
    public void changeEditorial (Editorial editorial) throws Exception{
        try {
            em.getTransaction().begin();
            em.merge(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error al modificar la editorial.");
        }
    }
    
    public void removeEditorial(Integer id) throws Exception{
        try {
            Editorial editorial = searchForId(id);
            if (editorial == null) {
                
            }
            em.getTransaction().begin();
            em.remove(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Error al eliminar una editorial.");
        }
    }
    
    public Editorial searchForId(Integer id) throws Exception{
        try {
            Editorial editorial = em.find(Editorial.class, id);
            return editorial;
        } catch (NoResultException ex) {
            System.out.println("No se ha encontrado la editorial.");
            return null;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    
    public Editorial searchForName(String nombre) throws Exception{
        try {
            Editorial editorial = (Editorial) em.createQuery("SELECT e FROM Editorial e WHERE e.nombre = :nombre").setParameter("nombre", nombre).getSingleResult();
            return editorial;
        } catch (NoResultException ex) {
            System.out.println("No se encontro ninguna editorial con ese nombre.");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
