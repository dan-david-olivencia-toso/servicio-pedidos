package com.dan.dot.pedidos.service;

import com.dan.dot.pedidos.domain.DetallePedido;
import com.dan.dot.pedidos.domain.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {

    Pedido guardarPedido(Pedido c) throws ProblemaCrediticioException;
    DetallePedido guardarDetallePedido(Integer idPedido, DetallePedido detalle) throws RecursoNoEncontradoException;
    Pedido actualizarPedido(Integer id, Pedido nuevo) throws ProblemaCrediticioException, RecursoNoEncontradoException;

    List<Pedido> listarPedidos();
    Optional<Pedido> buscarPedidoPorId(Integer id) throws RecursoNoEncontradoException;
    Optional<DetallePedido> buscarDetallePedidoPorIdEnPedido(Integer idPedido,Integer idDetalle) throws RecursoNoEncontradoException;
    List<Pedido> buscarPedidosPorIdObra(Integer idObra);
    List<Pedido> buscarPedidosPorIdCliente(String idCliente);
    List<Pedido> buscarPedidosPorCuit(String cuit);
    void borrarPedido(Integer id) throws RecursoNoEncontradoException;
    void borrarDetallePedidoDePedido(Integer idPedido, Integer idDetalle) throws RecursoNoEncontradoException;

    class ProblemaCrediticioException extends Exception {
        public ProblemaCrediticioException(String errorMessage) {
            super(errorMessage);
        }
    }

    class RecursoNoEncontradoException extends Exception {
        public RecursoNoEncontradoException(String errorMessage, Integer id) {
            super(errorMessage+id);
        }
    }

}
