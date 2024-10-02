package co.edu.uniquindio.notas.modelo;
import java.time.LocalDate;
import javafx.scene.image.Image;

public class Contacto {

    private String nombre;
    private String correo;
    private String numero;
    private LocalDate fechaNacimiento;
    private String urlFoto;
    private Image fotoPerfil;
    public Contacto( String nombre, String correo, String numero, LocalDate fechaNacimiento, String apellido, LocalDate fechaCreacion, String urlFoto) {

        this.nombre = nombre;
        this.correo = correo;
        this.numero = numero;
        this.fechaNacimiento = fechaNacimiento;
        this.apellido = apellido;
        this.fechaCreacion = fechaCreacion;
        this.urlFoto = urlFoto;
        if (urlFoto != null && !urlFoto.isEmpty()) {
            this.fotoPerfil = new Image(urlFoto);
        }
    }

    public static ContactoBuilder builder() {
        return new ContactoBuilder();
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public Image getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String urlFoto) {
        if (urlFoto != null && !urlFoto.isEmpty()) {
            this.fotoPerfil = new Image(urlFoto);
        } else {
            this.fotoPerfil = null;
        }
    }

    private String apellido;
    private LocalDate fechaCreacion;

    //public Nota(String titulo, String descripcion, String categoria) {

    //}



}
