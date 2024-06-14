package AppMinimarket;

// Usuario.java
import java.io.*;
import java.util.*;

public class Usuario {
    private String nombre;
    private String apellido;
    private String numero;
    private String direccion;
    private String nombreUsuario;
    private String contrasena;

    // Ruta del archivo de usuarios
    private static final String RUTA_USUARIOS = "C:\\Users\\Lucho\\Documents\\mavenproject2\\src\\Archivos de texto\\usuarios.txt";

    // Constructor de la clase Usuario
    public Usuario(String nombre, String apellido, String numero, String direccion, String nombreUsuario, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.direccion = direccion;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    // Método para autenticar un usuario basado en nombre de usuario y contraseña
    public static Usuario autenticar(String username, String password) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(RUTA_USUARIOS));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                // Divide cada línea en partes separadas por comas
                String[] parts = line.split(",");
                // Verifica si el nombre de usuario y la contraseña coinciden
                if (parts.length >= 6 && parts[4].equals(username) && parts[5].equals(password)) {
                    return new Usuario(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                }
            }
        } finally {
            reader.close();
        }
        return null;
    }

    // Método para guardar un nuevo usuario en el archivo
    public void guardar() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_USUARIOS, true));
        writer.write(nombre + "," + apellido + "," + numero + "," + direccion + "," + nombreUsuario + "," + contrasena);
        writer.newLine();
        writer.close();
    }

    // Método para editar la información de un usuario
    public void editarInformacion(String campo, String nuevoValor) throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(RUTA_USUARIOS));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                // Divide cada línea en partes separadas por comas
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[4].equals(this.nombreUsuario)) {
                    // Actualiza el campo correspondiente con el nuevo valor
                    switch (campo) {
                        case "contrasena":
                            parts[5] = nuevoValor;
                            break;
                        case "nombreUsuario":
                            parts[4] = nuevoValor;
                            break;
                        case "direccion":
                            parts[3] = nuevoValor;
                            break;
                        case "numero":
                            parts[2] = nuevoValor;
                            break;
                    }
                }
                // Añade el usuario a la lista si tiene la longitud correcta
                if (parts.length >= 6) {
                    usuarios.add(new Usuario(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        } finally {
            reader.close();
        }

        // Escribe todos los usuarios actualizados de nuevo en el archivo
        BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_USUARIOS));
        try {
            for (Usuario usuario : usuarios) {
                writer.write(usuario.nombre + "," + usuario.apellido + "," + usuario.numero + "," + usuario.direccion + "," + usuario.nombreUsuario + "," + usuario.contrasena);
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }
}
