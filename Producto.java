package AppMinimarket;

// Producto.java
import java.io.*;
import java.util.*;

public class Producto {
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;

    // Ruta del archivo de productos
    private static final String RUTA_PRODUCTOS = "C:\\Users\\Lucho\\Documents\\mavenproject2\\src\\Archivos de texto\\productos.txt";
    
    // Listado de todos los productos
    private static List<Producto> productos = new ArrayList<>();
    
    // Mapa que agrupa los productos por categoría
    private static Map<String, List<Producto>> productosPorCategoria = new HashMap<>();

    // Constructor de la clase Producto
    public Producto(String nombre, String categoria, double precio, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters para los atributos de la clase Producto
    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    // Setter para el atributo stock
    public void setStock(int stock) {
        this.stock = stock;
    }

    // Cargar productos desde el archivo de texto
    public static void cargarProductos() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(RUTA_PRODUCTOS));
        String line;
        while ((line = reader.readLine()) != null) {
            // Divide cada línea en partes separadas por comas
            String[] parts = line.split(",");
            String nombre = parts[0];
            String categoria = parts[1];
            double precio = Double.parseDouble(parts[2]);
            int stock = Integer.parseInt(parts[3]);
            // Crea un nuevo producto y lo agrega a la lista de productos
            Producto producto = new Producto(nombre, categoria, precio, stock);
            productos.add(producto);
            // Agrega el producto al mapa de productos por categoría
            productosPorCategoria.computeIfAbsent(categoria, k -> new ArrayList<>()).add(producto);
        }
        reader.close();
    }

    // Buscar un producto por su nombre
    public static Producto buscarProductoPorNombre(String nombre) {
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return producto;
            }
        }
        return null;
    }

    // Obtener un mapa de productos por categoría
    public static Map<String, List<Producto>> obtenerCategorias() {
        return productosPorCategoria;
    }

    // Actualizar el stock de los productos en el archivo de texto
    public static void actualizarStock() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_PRODUCTOS));
        for (Producto producto : productos) {
            writer.write(producto.getNombre() + "," + producto.categoria + "," + producto.getPrecio() + "," + producto.getStock());
            writer.newLine();
        }
        writer.close();
    }

    // Sobrescribir el método toString para mostrar información del producto
    @Override
    public String toString() {
        return String.format("Producto: %s, Categoría: %s, Precio: %.2f, Stock: %d", nombre, categoria, precio, stock);
    }
}
