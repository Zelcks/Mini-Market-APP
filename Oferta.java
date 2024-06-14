package AppMinimarket;

// Oferta.java
import java.io.*;
import java.util.*;

public class Oferta {
    private String categoria;
    private double descuento;

    // Ruta del archivo de ofertas
    private static final String RUTA_OFERTAS = "C:\\Users\\Lucho\\Documents\\mavenproject2\\src\\Archivos de texto\\ofertas.txt";
    
    // Lista de todas las ofertas
    private static List<Oferta> ofertas = new ArrayList<>();
    
    // Mapa que almacena el descuento por categoría
    private static Map<String, Double> ofertasPorCategoria = new HashMap<>();

    // Constructor de la clase Oferta
    public Oferta(String categoria, double descuento) {
        this.categoria = categoria;
        this.descuento = descuento;
    }

    // Getters para los atributos de la clase Oferta
    public String getCategoria() {
        return categoria;
    }

    public double getDescuento() {
        return descuento;
    }

    // Método para cargar las ofertas desde el archivo de texto
    public static void cargarOfertas() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(RUTA_OFERTAS));
        String line;
        while ((line = reader.readLine()) != null) {
            // Divide cada línea en partes separadas por comas
            String[] parts = line.split(",");
            String categoria = parts[0];
            double descuento = Double.parseDouble(parts[1]);
            // Crea una nueva oferta y la agrega a la lista de ofertas
            Oferta oferta = new Oferta(categoria, descuento);
            ofertas.add(oferta);
            // Agrega la oferta al mapa de ofertas por categoría
            ofertasPorCategoria.put(categoria, descuento);
        }
        reader.close();
    }

    // Método para obtener el descuento correspondiente a una categoría
    public static double obtenerDescuentoPorCategoria(String categoria) {
        return ofertasPorCategoria.getOrDefault(categoria, 0.0);
    }

    // Método para obtener todas las ofertas
    public static List<Oferta> obtenerTodasLasOfertas() {
        return ofertas;
    }
}
