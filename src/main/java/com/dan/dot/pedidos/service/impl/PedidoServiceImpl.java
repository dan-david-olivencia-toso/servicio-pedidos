package com.dan.dot.pedidos.service.impl;

import com.dan.dot.pedidos.domain.DetallePedido;
import com.dan.dot.pedidos.domain.EstadoPedido;
import com.dan.dot.pedidos.domain.Pedido;
import com.dan.dot.pedidos.repository.PedidoRepository;
import com.dan.dot.pedidos.service.PedidoService;
import com.dan.dot.pedidos.service.RiesgoCrediticioService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired //Servicio del que depende
    private final RiesgoCrediticioService riesgoService;

    @Autowired //Configuration necesaria para guardar en memoria
    PedidoRepository pedidoRepository;

    public PedidoServiceImpl(RiesgoCrediticioService riesgoService) {
        this.riesgoService = riesgoService;
    }

    @Override
    public Pedido guardarPedido(Pedido p) throws ProblemaCrediticioException {

        //a- Si tiene stock sus productos

        //b- No genera saldo al deudor
        //Si genera:
        //1. saldo deudor < descubierto
        //2. que la situacion crediticia en BCRA sea bajo riesgo

        //a y b => ACEPTADO
        //b => PENDIENTE
        //Sino EXCEPCION

        EstadoPedido ep = new EstadoPedido();
        if (!generaSaldoDeudor(p)||riesgoService.reporteBCRAPositivo(p.getObra().getId())) { //Sin riesgo crediticio o saldo deudor alto
            if(validarStockProductos(p.getDetalle())){ //Stock disponible de los proudctors del pedido
                ep.setEstado("ACEPTADO");
            }else{
                ep.setEstado("PENDIENTE");
            }
            p.setEstado(ep);
        } else {
            throw new ProblemaCrediticioException("Pedido rechazado: saldo deudor o riesgo crediticio alto");
        }

        pedidoRepository.save(p);

        return p;
    }

    @Override
    public Pedido actualizarPedido(Integer id, Pedido p) throws ProblemaCrediticioException, RecursoNoEncontradoException {

        if(pedidoRepository.existsById(id)){
            p.setId(id);
        }else{
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID:", id);
        }

        pedidoRepository.save(p);

        return p;
    }

    @Override
    public DetallePedido guardarDetallePedido(Integer idPedido, DetallePedido d) throws RecursoNoEncontradoException {

        Pedido p;
        if(pedidoRepository.existsById(idPedido)){
            p = pedidoRepository.findById(idPedido).get();
            p.getDetalle().add(d);
        }else{
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID:", idPedido);
        }

        pedidoRepository.save(p);

        return d;
    }

    @Override
    public List<Pedido> listarPedidos() {
        return Lists.newArrayList(pedidoRepository.findAll().iterator());
    }

    @Override
    public Optional<Pedido> buscarPedidoPorId(Integer id) throws RecursoNoEncontradoException {
        if (!pedidoRepository.existsById(id))
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID:", id);

        return this.pedidoRepository.findById(id);
    }

    @Override
    public Optional<DetallePedido> buscarDetallePedidoPorIdEnPedido(Integer idPedido, Integer idDetalle) throws RecursoNoEncontradoException {
        Pedido p;
        Optional<DetallePedido> dp;
        if(pedidoRepository.existsById(idPedido)){
            p = pedidoRepository.findById(idPedido).get();

            dp = p.getDetalle()
                    .stream()
                    .filter(unDetPed -> unDetPed.getId().equals(idDetalle))
                    .findFirst();
            if(!dp.isPresent()){
                throw new RecursoNoEncontradoException("Detalle Pedido no encontrado con ID:", idPedido);
            }
        } else{
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID:", idPedido);
        }

        return dp;
    }

    @Override
    public List<Pedido> buscarPedidosPorIdObra(Integer idObra) {
        return this.pedidoRepository.findPedidosByObraId(idObra);
    }

//    @Override
//    public List<Pedido> buscarPedidosPorIdCliente(String idCliente) {
//        /*
//        List<Pedido> lp = (List<Pedido>) listarPedidos()
//                .stream()
//                .filter(unPed -> unPed.getObra().getIdCliente().equals(idCliente));
//        */
//        return new ArrayList<Pedido>();
//    }

    @Override
    public void borrarPedido(Integer id) throws RecursoNoEncontradoException {
        if (!pedidoRepository.existsById(id))
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID:", id);

        pedidoRepository.delete(pedidoRepository.findById(id).get());
    }

    @Override
    public void borrarDetallePedidoDePedido(Integer idPedido, Integer idDetalle) throws RecursoNoEncontradoException {
        Pedido p;
        Optional<DetallePedido> dp;
        if(pedidoRepository.existsById(idPedido)){
            p = pedidoRepository.findById(idPedido).get();

            dp = p.getDetalle()
                    .stream()
                    .filter(unDetPed -> unDetPed.getId().equals(idDetalle))
                    .findFirst();
            if(dp.isPresent()){
                List<DetallePedido> ldp = p.getDetalle()
                        .stream()
                        .filter(unDetPed -> unDetPed.getId()!=idDetalle)
                        .collect(Collectors.toList());

                p.setDetalle(ldp);
            } else{
                throw new RecursoNoEncontradoException("Detalle Pedido no encontrado con ID:", idPedido);
            }
        }else{
            throw new RecursoNoEncontradoException("Cliente no encontrado con ID:", idPedido);
        }

        pedidoRepository.save(p);
    }

    //TODO: Implementar este método
    private boolean generaSaldoDeudor(Pedido p) { //NECESITO CLIENTE O CUENTA CORRIENTE SERVICE
        return false; //Hardcodeado para que no genere un saldo deudor
    }

    //TODO: Implementar este método
    private boolean validarStockProductos(List<DetallePedido> detalle) {
        return true; //Hardcodeado para que siempre haya stock de los productos
    }
}
