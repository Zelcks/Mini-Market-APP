package AppMinimarket;

// Carrito.java
import java.util.*;
import java.io.*;

public class Carrito {
    // Lista de productos en el carrito
    private List<Producto> productos;
    
    // Mapa que almacena la cantidad de cada producto en el carrito
    private Map<Producto, Integer> cantidades;

    // Constructor de la clase Carrito
    public Carrito() {
        productos = new ArrayList<>();
        cantidades = new HashMap<>();
    }

    // Método para agregar un producto al carrito
    public void agregarProducto(Producto producto, int cantidad) {
        productos.add(producto);
        cantidades.put(producto, cantidad);
        // Actualiza el stock del producto restando la cantidad agregada al carrito
        producto.setStock(producto.getStock() - cantidad);
    }

    // Método para mostrar el contenido del carrito
    public void mostrarCarrito() {
        double total = 0;
        System.out.println("\nCarrito de compras:");
        // Itera sobre los productos en el carrito y muestra su información
        for (Producto producto : productos) {
            int cantidad = cantidades.get(producto);
            // Aplica el descuento correspondiente a la categoría del producto
            double precioConDescuento = producto.getPrecio() * (1 - Oferta.obtenerDescuentoPorCategoria(producto.getCategoria()));
            double subtotal = precioConDescuento * cantidad;
            total += subtotal;
            System.out.printf("%s - Cantidad: %d - Subtotal: %.2f (Precio con descuento: %.2f)\n", producto.getNombre(), cantidad, subtotal, precioConDescuento);
        }
        System.out.printf("Total: %.2f\n", total);
    }

    // Método para realizar la compra y generar la boleta
    public void realizarCompra() throws IOException {
        double total = 0;
        System.out.println("\nBoleta de compra:");
        // Itera sobre los productos en el carrito y muestra su información en la boleta
        for (Producto producto : productos) {
            int cantidad = cantidades.get(producto);
            // Aplica el descuento correspondiente a la categoría del producto
            double precioConDescuento = producto.getPrecio() * (1 - Oferta.obtenerDescuentoPorCategoria(producto.getCategoria()));
            double subtotal = precioConDescuento * cantidad;
            total += subtotal;
            System.out.printf("%s - Cantidad: %d - Subtotal: %.2f (Precio con descuento: %.2f)\n", producto.getNombre(), cantidad, subtotal, precioConDescuento);
        }
        System.out.printf("Total: %.2f\n", total);
        // Actualiza el stock de los productos en el archivo de productos
        Producto.actualizarStock();
        // Limpia el carrito después de realizar la compra
        productos.clear();
        cantidades.clear();
    }
}
