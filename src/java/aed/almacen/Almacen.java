package aed.almacen;

import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.indexedlist.ArrayIndexedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Implementa la logica del almacen.
 */
public class Almacen implements ClienteAPI, AlmacenAPI, ProductorAPI {

  // Compras (sin ningun orden especial)
  private ArrayIndexedList<Compra> compras;
  // Productos ordenados ascendamente usando el productoId de un Product.
  private ArrayIndexedList<Producto> productos;

  // No es necesario cambiar el constructor
  /**
   * Crea un almacen.
   */
  public Almacen() {
    this.compras = new ArrayIndexedList<>();
    this.productos = new ArrayIndexedList<>();
  }

  /**
   * Devuelve un producto que esta en el almacén (o null si no esta).
   *
   * @param productoId
   */
  @Override
  public Producto getProducto(String productoId) {
    for (int i = 0; i < productos.size(); i++) {
      if (Objects.equals(productos.get(i).getProductoId(), productoId))
        return productos.get(i);
    }
    return null;
  }

  /**
   * Devuelve una compra (o null si no existe).
   *
   * @param compraId
   */
  @Override
  public Compra getCompra(Integer compraId) {
    for (int i = 0; i < compras.size(); i++) {
      if (Objects.equals(compras.get(i).getCompraId(), compraId))
        return compras.get(i);
    }
    return null;
  }

  /**
   * Todos los productos conocidos, ordenados por productoId en orden ascendente.
   * Notad que la lista devuelta tiene que ser nueva, es decir, no se puede
   * devolver la lista que contiene el atributo productos dentro la clase Almacen.
   */
  @Override
  public IndexedList<Producto> getProductos() {
    List<Producto> listaProductos = new ArrayList<>();
    productos.forEach(listaProductos::add);
    listaProductos.sort(Producto::compareTo);

    IndexedList<Producto> result = new ArrayIndexedList<>();
    for (int i = 0; i < listaProductos.size(); i++) {
      result.add(i, listaProductos.get(i));
    }

      return result;
  }

  /**
   * Devuelve todas las compras (sin ningun orden en especial).
   *    Notad que la lista devuelta tiene que ser nueva, es decir, no se puede
   * devolver la lista que contiene el atributo compras dentro la clase Almacen.
   */
  @Override
  public IndexedList<Compra> getCompras() {
    IndexedList<Compra> nuevaLista = new ArrayIndexedList<Compra>();
    for (int i = 0; i < compras.size(); i++) {
      nuevaLista.add(i, compras.get(i));
    }
    return nuevaLista;
  }

  /**
   * Devuelve las compras de un cliente (sin ningun orden en especial).
   *
   * @param clienteId
   */
  @Override
  public IndexedList<Compra> comprasCliente(String clienteId) {
    IndexedList<Compra> nuevaLista = new ArrayIndexedList<Compra>();
    int aux = 0;
    for (int i = 0; i < compras.size(); i++) {
      if (Objects.equals(compras.get(i).getClienteId(), clienteId)) {
        nuevaLista.add(aux, compras.get(i));
        aux++;
      }
    }
    return nuevaLista;
  }

  /**
   * Devuelve las compras de un producto (sin ningun orden en especial).
   *
   * @param productoId
   */
  @Override
  public IndexedList<Compra> comprasProducto(String productoId) {
    IndexedList<Compra> nuevaLista = new ArrayIndexedList<Compra>();
    int aux = 0;
    for (int i = 0; i < compras.size(); i++) {
      if (Objects.equals(compras.get(i).getProductoId(), productoId)) {
        nuevaLista.add(aux, compras.get(i));
        aux++;
      }
    }
    return nuevaLista;
  }

  /**
          * Un cliente identificado por clienteId realiza una compra
   * de cantidad productos identificados por el productoId, si hay suficientes
   * articulos disponibles.
   * Devuelve la compraId de la compra (o null si no hay suficientes articulos disponibles).
   * Debe cambiar (reducir) el numero de productos disponibles.
   *
   * @param clienteId
   * @param productoId
   * @param cantidad
   */
  @Override
  public Integer pedir(String clienteId, String productoId, int cantidad) { //raul
    Producto productoAux = getProducto(productoId);
    if (productoAux != null) {
      if (productoAux.getCantidadDisponible() >= cantidad) { //if hay suficientes
        productos.get(productos.indexOf(productoAux)).setCantidadDisponible(productoAux.getCantidadDisponible() - cantidad);
        Compra nuevoCliente = new Compra(clienteId, productoId, cantidad);
        compras.add(compras.size(),nuevoCliente);
        return nuevoCliente.getCompraId();
      } else { //if no hay suficientes
        return null;
      }
    }
    else {
      return null;
    }
  }

  /**
   * Llega al almacen una cantidad de un producto (identificado por productoId)
   * desde un productor.
   * El metodo debe aumentar la disponibilidad de los productos en el almacen.
   *
   * @param productoId
   * @param cantidad
   */
  @Override
  public void reabastecerProducto(String productoId, int cantidad) { //raul
    Producto productoAux = getProducto(productoId);
    if (productoAux != null) { //esta en el almacen
      productos.get(productos.indexOf(productoAux))
              .setCantidadDisponible(productoAux.getCantidadDisponible() + cantidad);
    } else { //no está en el almacen
      productos.add(productos.size(), new Producto(productoId, cantidad));
    }
  }


  // Implementa los métodos necesarios aqui ...
}
