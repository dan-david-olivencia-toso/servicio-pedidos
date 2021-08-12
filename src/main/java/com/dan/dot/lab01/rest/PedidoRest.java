package com.dan.dot.lab01.rest;

import com.dan.dot.lab01.domain.DetallePedido;
import com.dan.dot.lab01.domain.Pedido;
import com.dan.dot.lab01.service.PedidoService;
import com.dan.dot.lab01.service.rabbitmq.RabbitConfig;
import com.dan.dot.lab01.service.rabbitmq.RabbitProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedido")
@Api(value = "PedidoRest", description = "Permite gestionar los pedidos de la empresa")
public class PedidoRest {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private RabbitProducer rabbitProducer;

    private static final Logger logger = LoggerFactory.getLogger(PedidoRest.class);

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un pedido por id")
    public ResponseEntity<?> pedidoPorId(@PathVariable Integer id) {
        Optional<Pedido> c = null;
        try {
            c = this.pedidoService.buscarPedidoPorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.of(c);
    }

    @GetMapping(path = "/{idPedido}/detalle/{idDetalle}")
    @ApiOperation(value = "Busca un detalle pedido por id en un pedido")
    public ResponseEntity<?> detallePedidoPorIdEnPedido(@PathVariable Integer idPedido,@PathVariable Integer idDetalle) {

        Optional<DetallePedido> dp = null;
        try {
            dp = this.pedidoService.buscarDetallePedidoPorIdEnPedido(idPedido,idDetalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.of(dp);
    }

    @GetMapping(path = "/obra/{idObra}")
    @ApiOperation(value = "Busca pedidos por id de obra")
    public ResponseEntity<?> pedidoPorObra(@PathVariable Integer idObra){

        List<Pedido> lp = null;
        try {
            lp = this.pedidoService.buscarPedidosPorIdObra(idObra);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok(lp);
    }

    @GetMapping(path = "/cliente/{idCliente}")
    @ApiOperation(value = "Busca pedidos por id de cliente")
    public ResponseEntity<?> pedidoPorIdCliente(@PathVariable String idCliente){

        List<Pedido> lp = null;
        try {
            lp = this.pedidoService.buscarPedidosPorIdCliente(idCliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok(lp);
    }

    @GetMapping(path = "/cliente/cuit/{cuit}")
    @ApiOperation(value = "Busca pedidos por cuit del cliente")
    public ResponseEntity<?> pedidoPorCuit(@PathVariable String cuit){
        List<Pedido> lp = null;
        try {
            lp = this.pedidoService.buscarPedidosPorCuit(cuit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok(lp);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> todos(){
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody Pedido pedido) throws Exception {
        Pedido p = null;
        try {
            p = this.pedidoService.guardarPedido(pedido);
        } catch (Exception e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getMessage());
        }
        rabbitProducer.produce(p.getDetalle());
        return ResponseEntity.ok(p);
    }

    @PostMapping(path = "/{idPedido}/detalle")
    public ResponseEntity<?> agregarDetalle(@RequestBody DetallePedido detalle, @PathVariable Integer idPedido){

        DetallePedido dp = null;
        try {
            dp = this.pedidoService.guardarDetallePedido(idPedido, detalle);
        } catch (Exception e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getMessage());
        }
        return ResponseEntity.ok(dp);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza un pedido")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Actualizado correctamente"),
        @ApiResponse(code = 401, message = "No autorizado"),
        @ApiResponse(code = 403, message = "Prohibido"),
        @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<?> actualizarPedido(@RequestBody Pedido nuevo, @PathVariable Integer id){
        Pedido p = null;
        try {
            p = this.pedidoService.actualizarPedido(id, nuevo);
        } catch (Exception e1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e1.getMessage());
        }
        return ResponseEntity.ok(p);
    }

    @DeleteMapping(path = "/{idPedido}")
    public ResponseEntity<?> borrarPedido(@PathVariable Integer idPedido){
        try {
            pedidoService.borrarPedido(idPedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{idPedido}/detalle/{idDetalle}")
    public ResponseEntity<?> borrarDetalle(@PathVariable Integer idPedido,@PathVariable Integer idDetalle){
        try {
            pedidoService.borrarDetallePedidoDePedido(idPedido,idDetalle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}

