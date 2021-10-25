/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpalibreria.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author LENOVO
 */
public class DAO {
    
    EntityManager em = Persistence.createEntityManagerFactory("JPALibreriaPU").createEntityManager();
    
}
