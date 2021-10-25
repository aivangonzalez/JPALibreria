/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpalibreria;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;
import jpalibreria.entidades.Libro;
import jpalibreria.servicios.AutorService;
import jpalibreria.servicios.EditorialService;
import jpalibreria.servicios.LibroService;

/**
 *
 * @author LENOVO
 */
public class Menu {

    private final Scanner sc;
    private final AutorService autorService;
    private final EditorialService editoService;
    private final LibroService libroService;

    public Menu() {
        this.sc = new Scanner(System.in).useDelimiter("\n");
        this.autorService = new AutorService();
        this.editoService = new EditorialService();
        this.libroService = new LibroService();
    }

    public void menuOpciones() throws Exception {
        Integer opcion;
        do {
            System.out.println("Seleccione la opción:");
            System.out.println("=====================================");

            System.out.println(" 1- Crear Autor");//
            System.out.println(" 2- Crear Editorial");//
            System.out.println(" 3- Crear Libro");//

            System.out.println(" 4- Buscar Autor por nombre");//
            System.out.println(" 5- Buscar libro por ISBN");//
            System.out.println(" 6- Buscar libro por titulo");//
            System.out.println(" 7- Buscar libro/s por nombre de autor");//
            System.out.println(" 8- Buscar libro/s por nombre de editorial");//

            System.out.println(" 9 - Modificar nombre de autor");
            System.out.println(" 10 - Modificar nombre de editorial");
            System.out.println(" 11 - Modificar ejemplares de un libro");
            System.out.println(" 12 - Modificar ejemplares prestados de un libro");
            
            System.out.println(" 13 - Eliminar un libro.");

            System.out.println(" 0- Salir");

            System.out.println("Ingrese la opcion que desea ejecutar.");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    autorService.createAutor(cargarNombreAutor(), Boolean.TRUE);
//                    usuarioService.crearUsuario(cargarCorreo(), cargarClave(), cargarDni(), cargarFecha(), "PAIS", "PROVINCIA");
                    break;
                case 2:
                    editoService.createEditorial(cargarNombreEditorial(), Boolean.TRUE);
                    break;
                case 3:
                    libroService.createLibro(cargarIsbn(), cargarTitulo(), cargarAnio(), cargarEjemplares(), cargarEjemplaresPrestados(), Boolean.TRUE, cargarNombreAutorParaLibro(), cargarNombreEditorialParaLibro());
//                    usuarioService.imprimirUsuarios();
                    break;
                case 4:
                    //reutilizo el metodo cargarNombreAutor() 
                    autorService.imprimirUnAutor(cargarNombreAutor());
                    break;
                case 5:
                    libroService.imprimirLibroPorIsbn(ingresarIsbn());
                    break;
                case 6:
                    //reutilizo el metodo cargarTitulo()
                    libroService.imprimirLibroPorTitulo(cargarTitulo());
                    break;
                case 7:
                    //reutilizo el metodo cargarNombreAutor()
                    libroService.imprimirLibros(libroService.searchLibrosForAutor(cargarNombreAutor()));
                    break;
                case 8:
                    //reutilizo el metodo cargarNombreEditorialParaLibro()
                    libroService.imprimirLibros(libroService.searchLibrosForEditorial(cargarNombreEditorialParaLibro()));
                    break;
                case 9:
                    //IngresarIdBuscar es reutilizado en el case 9 y case 10
                    autorService.changeNameOfAutor(ingresarIdBuscar(), cargarNombreAutor());
                    break;
                case 10:
                    //IngresarIdBuscar es reutilizado en el case 9 y case 10
                    editoService.changeNameOfEditorial(ingresarIdBuscar(), cargarNombreEditorial());
                    break;
                case 11:
                    //IngresarIdBuscar es reutilizado en el case 9 y case 10
                    libroService.changeEjemplares(ingresarIsbn(), ingresarModificacionEjemplares());
                    break;
                case 12:
                    //IngresarIdBuscar es reutilizado en el case 9 y case 10
                    libroService.changeEjemplaresPrestados(ingresarIsbn(), ingresarModificacionEjemplares());
                    break;
                case 13: 
                    libroService.removeLibro(ingresarIsbn());
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("La opcion elegida es invalida!");
                    break;
            }

//            limpiarPantalla();
        } while (opcion != 0);
    }

    public void limpiarPantalla() throws AWTException {
        //Dejo esre metodo para ir borrando la consola.. y que no sea un desorden.
        Robot pressbot = new Robot();
        pressbot.setAutoDelay(30); // Tiempo de espera antes de borrar
        pressbot.keyPress(17); // Orden para apretar CTRL key
        pressbot.keyPress(76);// Orden para apretar L key
        pressbot.keyRelease(17); // Orden para soltar CTRL key
        pressbot.keyRelease(76); // Orden para soltar L key

    }

    public String cargarNombreAutor() {
        System.out.println("Ingrese el nombre del autor.");
        String name = sc.next();
        return name;
    }

    public String cargarNombreEditorial() {
        System.out.println("Ingrese el nombre de la editorial.");
        String name = sc.next();
        return name;
    }

    public Long cargarIsbn() throws Exception {
        Long isbn;
        System.out.println("Ingrese el isbn:");
        isbn = sc.nextLong();
        try {
            if (validarIsbn(isbn)) {
                System.out.println("ISBN NO registrado con antelacion, continue con el registro de datos");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //Si se encuentra un isbn igual al que quiere ingresar el usuario, entonces se vuelve a 
            //llamar a si mismo hasta que ingrese un isbn valido
            cargarIsbn();
        }
        return isbn;
    }

    public boolean validarIsbn(Long isbn) throws Exception {
        boolean validar = false;
        try {
            Collection<Libro> libros = libroService.listaLibro();
            for (Libro l : libros) {
                if (Objects.equals(l.getIsbn(), isbn)) {
                    throw new Exception("ISBN ya registrado, ingrese un ISBN valido");
                }
            }
            validar = true;
        } catch (Exception e) {
            throw e;
        }
        return validar;
    }

    public String cargarTitulo() {
        System.out.println("Ingrese el titulo del libro.");
        String title = sc.next();
        return title;
    }

    public Integer cargarAnio() {
        System.out.println("Ingrese el año de publicacion del libro.");
        Integer year = sc.nextInt();
        return year;
    }

    public Integer cargarEjemplares() {
        System.out.println("Ingrese la cantidad de ejemplares del libro.");
        Integer ejemplares = sc.nextInt();
        return ejemplares;
    }

    public Integer cargarEjemplaresPrestados() {
        System.out.println("Ingrese la cantidad de ejemplares prestados del libro.");
        Integer ejemPrestados = sc.nextInt();
        return ejemPrestados;
    }

    //CASE 3
    //esta funcion pide un nombre de autor para despues buscar el objeto autor en la base de datos segun el 
    //nombre ingresado, si no hay un autor con ese nombre se llama al metodo para crearlo
    public String cargarNombreAutorParaLibro() {
        System.out.println("Ingrese el nombre del autor del libro.");
        String name = sc.next();
        return name;
    }

    //CASE 3
    //esta funcion pide un nombre de editorial para despues buscar el objeto editorial en la base de datos segun el 
    //nombre ingresado, si no hay una editorial con ese nombre se llama al metodo para crearla
    public String cargarNombreEditorialParaLibro() {
        System.out.println("Ingrese el nombre de la editorial del libro.");
        String name = sc.next();
        return name;
    }

    //CASE 5
    //metodo utilizado para buscar libro por isbn
    public Long ingresarIsbn() {
        System.out.println("Ingrese el isbn del libro");
        Long isbn = sc.nextLong();
        return isbn;
    }

    //CASE 9 y CASE 10
    public Integer ingresarIdBuscar() {
        System.out.println("Ingrese el id del objeto a modificar:");
        Integer id = sc.nextInt();
        return id;
    }

    //CASE 11 y CASE 12
    public Integer ingresarModificacionEjemplares() {
        System.out.println("Ingrese la nueva cantidad de ejemplares:");
        Integer ejemplares = sc.nextInt();
        return ejemplares;
    }
}
