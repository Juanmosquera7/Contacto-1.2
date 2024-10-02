package co.edu.uniquindio.notas.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
public class ContactoPrincipal {

    private ArrayList<Contacto> contactos;
    private Random random;


    public ContactoPrincipal(){
        this.contactos = new ArrayList<>();
        this.random = new Random();
    }
    public void agregarContacto(String nombre, String apellido, String correo, String numero, LocalDate fechaNacimiento, LocalDate fechaCreacion, String urlFoto) {
        // Generar un ID aleatorio


        // Crear un nuevo contacto utilizando el patrón builder
        Contacto nuevoContacto = Contacto.builder()

                .nombre(nombre)
                .apellido(apellido)
                .correo(correo)
                .numero(numero)
                .fechaNacimiento(fechaNacimiento)
                .fechaCreacion(fechaCreacion)
                .urlFoto(urlFoto)
                .build();

        // Agregar el nuevo contacto a la lista
        contactos.add(nuevoContacto);
    }

    // Método para eliminar contacto por número
    public void eliminarContacto(String numero) throws Exception {
        int posContacto = obtenerContactoPorNumero(numero);

        if (posContacto == -1) {
            throw new Exception("No existe el contacto con el número proporcionado.");
        }

        contactos.remove(contactos.get(posContacto));
    }

    // Método para actualizar contacto por número
    public void actualizarContacto(String numero, String nombre, String apellido, String correo, LocalDate fechaNacimiento, String urlFoto) throws Exception {
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || fechaNacimiento == null) {
            throw new Exception("Todos los campos son obligatorios.");
        }

        int posContacto = obtenerContactoPorNumero(numero);

        if (posContacto == -1) {
            throw new Exception("No existe el contacto con el número proporcionado.");
        }

        // Actualizamos los valores del contacto existente
        Contacto contactoActualizado = contactos.get(posContacto);
        contactoActualizado.setNombre(nombre);
        contactoActualizado.setApellido(apellido);
        contactoActualizado.setCorreo(correo);
        contactoActualizado.setFechaNacimiento(fechaNacimiento);
        contactoActualizado.setUrlFoto(urlFoto);

        // Actualiza el contacto en la lista
        contactos.set(posContacto, contactoActualizado);
    }



    private int obtenerContactoPorNumero(String numero) {
        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getNumero().equals(numero)) {
                return i;
            }
        }
        return -1;  // Retorna -1 si no se encuentra el contacto
    }



    public ArrayList<Contacto> listarNotas(){
        return this.contactos;
    }
    public boolean existeContacto(String numero) {
        for (Contacto contacto : contactos) {
            if (contacto.getNumero().equals(numero)) {
                return true; // El contacto existe
            }
        }
        return false; // El contacto no existe
    }
    public Contacto obtenerContacto(String numero) {
        for (Contacto contacto : contactos) {
            if (contacto.getNumero().equals(numero)) {
                return contacto; // Retorna el contacto encontrado
            }
        }
        return null; // Retorna null si no se encuentra
    }
    public boolean existeContactoPorNumero(String numero) {
        for (Contacto contacto : contactos) {
            if (contacto.getNumero().equals(numero)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeContactoPorCorreo(String correo) {
        for (Contacto contacto : contactos) {
            if (contacto.getCorreo().equals(correo)) {
                return true;
            }
        }
        return false;
    }




}
