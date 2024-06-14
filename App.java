package AppMinimarket;

// App.java
import java.io.IOException;
import java.util.*;

public class App {
    private static Usuario usuarioActual;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        // Carga las ofertas desde el archivo al inicio del programa
        Oferta.cargarOfertas();

        while (true) {
            // Muestra el menú de inicio y una oferta aleatoria
            System.out.println("Bienvenido al MiniMarket!");
            mostrarOfertasAleatorias();
            System.out.println("1. Ingresar");
            System.out.println("2. Crear nuevo usuario");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            // Llama a las funciones correspondientes según la opción seleccionada
            if (opcion == 1) {
                if (login()) {
                    mostrarMenuPrincipal();
                }
            } else if (opcion == 2) {
                crearNuevoUsuario();
            } else {
                System.out.println("Opción no válida");
            }
        }
    }

    // Función para manejar el proceso de login
    private static boolean login() throws IOException {
        System.out.print("Usuario: ");
        String username = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        usuarioActual = Usuario.autenticar(username, password);
        if (usuarioActual != null) {
            System.out.println("Login exitoso!");
            return true;
        } else {
            System.out.println("Usuario o contraseña incorrectos.");
            return false;
        }
    }

    // Función para crear un nuevo usuario
    private static void crearNuevoUsuario() throws IOException {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Nombre de Usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        Usuario nuevoUsuario = new Usuario(nombre, apellido, numero, direccion, nombreUsuario, password);
        nuevoUsuario.guardar();
        System.out.println("Usuario creado exitosamente!");
    }

    // Función para mostrar el menú principal después de iniciar sesión
    private static void mostrarMenuPrincipal() throws IOException {
        Carrito carrito = new Carrito();
        Producto.cargarProductos();

        while (true) {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Buscar producto");
            System.out.println("2. Productos por categoría");
            System.out.println("3. Editar información de usuario");
            System.out.println("4. Ver carrito");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            // Llama a las funciones correspondientes según la opción seleccionada
            if (opcion == 1) {
                buscarProducto(carrito);
            } else if (opcion == 2) {
                productosPorCategoria(carrito);
            } else if (opcion == 3) {
                editarInformacionUsuario();
            } else if (opcion == 4) {
                carrito.mostrarCarrito();
                System.out.println("1. Realizar compra");
                System.out.println("2. Volver");
                System.out.print("Seleccione una opción: ");
                int opcionCarrito = scanner.nextInt();
                scanner.nextLine();
                if (opcionCarrito == 1) {
                    carrito.realizarCompra();
                }
            } else if (opcion == 5) {
                System.out.println("Gracias por usar el MiniMarket!");
                break;
            } else {
                System.out.println("Opción no válida");
            }
        }
    }

    // Función para buscar un producto por su nombre y agregarlo al carrito
    private static void buscarProducto(Carrito carrito) throws IOException {
        while (true) {
            System.out.print("Ingrese el nombre del producto (o 'volver' para regresar): ");
            String nombreProducto = scanner.nextLine();
            if (nombreProducto.equalsIgnoreCase("volver")) {
                return;
            }
            Producto producto = Producto.buscarProductoPorNombre(nombreProducto);
            if (producto != null) {
                if (producto.getStock() <= 0) {
                    System.out.println("Producto agotado.");
                    continue;
                }
                System.out.println("Producto encontrado: " + producto);
                double descuento = Oferta.obtenerDescuentoPorCategoria(producto.getCategoria());
                if (descuento > 0) {
                    System.out.printf("¡Oferta! Descuento de %.2f%% en %s\n", descuento * 100, producto.getCategoria());
                }
                System.out.print("¿Desea agregar este producto al carrito? (si/no): ");
                String respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("si")) {
                    System.out.print("Ingrese la cantidad: ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine();
                    if (cantidad > producto.getStock()) {
                        System.out.println("Cantidad solicitada excede el stock disponible.");
                        continue;
                    }
                    carrito.agregarProducto(producto, cantidad);
                    System.out.println("Producto agregado al carrito.");
                }
            } else {
                System.out.println("Producto no encontrado.");
            }
        }
    }

    // Función para mostrar productos por categoría y agregar al carrito
    private static void productosPorCategoria(Carrito carrito) throws IOException {
        Map<String, List<Producto>> categorias = Producto.obtenerCategorias();
        while (true) {
            System.out.println("Categorías disponibles:");
            int index = 1;
            for (String categoria : categorias.keySet()) {
                System.out.println(index + ". " + categoria);
                index++;
            }
            System.out.print("Seleccione una categoría (o '0' para volver): ");
            int categoriaSeleccionada = scanner.nextInt();
            scanner.nextLine(); 
            if (categoriaSeleccionada == 0) {
                return;
            }
            String categoria = (String) categorias.keySet().toArray()[categoriaSeleccionada - 1];
            double descuento = Oferta.obtenerDescuentoPorCategoria(categoria);
            if (descuento > 0) {
                System.out.printf("¡Oferta! Descuento de %.2f%% en %s\n", descuento * 100, categoria);
            }
            List<Producto> productos = categorias.get(categoria);
            if (productos != null) {
                for (Producto producto : productos) {
                    System.out.println(producto.getNombre());
                }
                System.out.print("Ingrese el nombre del producto a agregar al carrito (o 'volver' para regresar): ");
                String nombreProducto = scanner.nextLine();
                if (nombreProducto.equalsIgnoreCase("volver")) {
                    continue;
                }
                Producto producto = Producto.buscarProductoPorNombre(nombreProducto);
                if (producto != null) {
                    if (producto.getStock() <= 0) {
                        System.out.println("Producto agotado.");
                        continue;
                    }
                    System.out.print("Ingrese la cantidad: ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine();
                    if (cantidad > producto.getStock()) {
                        System.out.println("Cantidad solicitada excede el stock disponible.");
                        continue;
                    }
                    carrito.agregarProducto(producto, cantidad);
                    System.out.println("Producto agregado al carrito.");
                } else {
                    System.out.println("Producto no encontrado.");
                }
            } else {
                System.out.println("Categoría no encontrada.");
            }
        }
    }

    // Función para editar la información del usuario actual
    private static void editarInformacionUsuario() throws IOException {
        while (true) {
            System.out.println("Editar información de usuario:");
            System.out.println("1. Contraseña");
            System.out.println("2. Nombre de usuario");
            System.out.println("3. Dirección");
            System.out.println("4. Número");
            System.out.println("5. Volver");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            if (opcion == 5) {
                return;
            }

            // Llama a la función editarInformacion de la clase Usuario para actualizar el campo correspondiente
            switch (opcion) {
                case 1:
                    System.out.print("Nueva contraseña: ");
                    String nuevaContrasena = scanner.nextLine();
                    usuarioActual.editarInformacion("contrasena", nuevaContrasena);
                    break;
                case 2:
                    System.out.print("Nuevo nombre de usuario: ");
                    String nuevoNombreUsuario = scanner.nextLine();
                    usuarioActual.editarInformacion("nombreUsuario", nuevoNombreUsuario);
                    break;
                case 3:
                    System.out.print("Nueva dirección: ");
                    String nuevaDireccion = scanner.nextLine();
                    usuarioActual.editarInformacion("direccion", nuevaDireccion);
                    break;
                case 4:
                    System.out.print("Nuevo número: ");
                    String nuevoNumero = scanner.nextLine();
                    usuarioActual.editarInformacion("numero", nuevoNumero);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
            System.out.println("Información actualizada.");
        }
    }

    // Función para mostrar ofertas aleatorias en la página de inicio
    private static void mostrarOfertasAleatorias() {
        List<Oferta> ofertas = Oferta.obtenerTodasLasOfertas();
        if (ofertas.isEmpty()) {
            return;
        }
        Random random = new Random();
        int index = random.nextInt(ofertas.size());
        Oferta oferta = ofertas.get(index);
        System.out.printf("¡Oferta aleatoria! Descuento de %.2f%% en %s\n", oferta.getDescuento() * 100, oferta.getCategoria());
    }
}
