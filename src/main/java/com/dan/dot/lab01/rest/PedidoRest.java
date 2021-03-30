package com.dan.dot.lab01.rest;

import com.dan.dot.lab01.domain.DetallePedido;
import com.dan.dot.lab01.domain.Pedido;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/pedido")
@Api(value = "PedidoRest", description = "Permite gestionar los pedidos de la empresa")
public class PedidoRest {

    private static final List<Pedido> listaPedidos = new ArrayList<>();
    private static Integer ID_GEN_PEDIDO = 1;
    private static Integer ID_GEN_DETALLE = 1;

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un pedido por id")
    public ResponseEntity<Pedido> pedidoPorId(@PathVariable Integer id) {

        Optional<Pedido> c = listaPedidos
                .stream()
                .filter(unPed -> unPed.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(c);
    }

    @GetMapping(path = "/{idPedido}/detalle/{idDetalle}")
    @ApiOperation(value = "Busca un pedido por id")
    public ResponseEntity<DetallePedido> pedidoPorId(@PathVariable Integer idPedido,@PathVariable Integer idDetalle) {

        Optional<Pedido> p = listaPedidos
                .stream()
                .filter(unPed -> unPed.getId().equals(idPedido))
                .findFirst();

        Optional<DetallePedido> d = p.get().getDetalle()
                .stream()
                .filter(unDetPed -> unDetPed.getId().equals(idDetalle))
                .findFirst();

        return ResponseEntity.of(d);
    }


    @GetMapping(path = "/obra/{idObra}")
    @ApiOperation(value = "Busca pedidos por id de obra")
    public ResponseEntity<List<Pedido>> pedidoPorObra(@PathVariable Integer idObra){

        List<Pedido> listaO =  listaPedidos
                .stream()
                .filter(unPed -> unPed.getObra().getId().equals(idObra))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaO);
    }

    @GetMapping(path = "/cliente/{idCliente}")
    @ApiOperation(value = "Busca pedidos por id de cliente")
    public ResponseEntity<List<Pedido>> pedidoPorIdCliente(@PathVariable String idCliente){
        /*
        List<Pedido> listaP =  listaPedidos
                .stream()
                .filter(unPed -> unPed.getCliente().getId().equals(idCliente))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaP);*/
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/cliente/cuit/{cuit}")
    @ApiOperation(value = "Busca pedidos por cuit del cliente")
    public ResponseEntity<List<Pedido>> pedidoPorCuit(@PathVariable String cuit){
        /*
        List<Pedido> listaP =  listaPedidos
                .stream()
                .filter(unPed -> unPed.getCliente().getCuit().equals(cuit))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaP);*/
        return ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<Pedido>> todos(){
        return ResponseEntity.ok(listaPedidos);
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido nuevo){
    	System.out.println("Crear pedido: "+nuevo);
        nuevo.setId(ID_GEN_PEDIDO++);
        listaPedidos.add(nuevo);
        return ResponseEntity.ok(nuevo);
    }

    @PostMapping(path = "/{idPedido}/detalle")
    public ResponseEntity<Pedido> agregarDetalle(@RequestBody DetallePedido detalle, @PathVariable Integer idPedido){
        System.out.println("Agregar detalle: "+detalle);
        detalle.setId(ID_GEN_DETALLE++);

        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
                .filter(i -> listaPedidos.get(i).getId().equals(idPedido))
                .findFirst();

        if(indexOpt.isPresent()){
            Pedido pedConDetalle = listaPedidos.get(indexOpt.getAsInt());
            pedConDetalle.getDetalle().add(detalle);
            listaPedidos.set(indexOpt.getAsInt(), pedConDetalle);
            return ResponseEntity.ok(pedConDetalle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza un pedido")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Actualizado correctamente"),
        @ApiResponse(code = 401, message = "No autorizado"),
        @ApiResponse(code = 403, message = "Prohibido"),
        @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Pedido> actualizarPedido(@RequestBody Pedido nuevo,  @PathVariable Integer id){
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.set(indexOpt.getAsInt(), nuevo);
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{idPedido}")
    public ResponseEntity<Pedido> borrarPedido(@PathVariable Integer idPedido){
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(idPedido))
        .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{idPedido}/detalle/{idDetalle}")
    public ResponseEntity<Pedido> borrarDetalle(@PathVariable Integer idPedido,@PathVariable Integer idDetalle){
        OptionalInt indexOptPedido =   IntStream.range(0, listaPedidos.size())
                .filter(i -> listaPedidos.get(i).getId().equals(idPedido))
                .findFirst();

        if(indexOptPedido.isPresent()){
            Pedido pedSinDetalle = listaPedidos.get(indexOptPedido.getAsInt());

            OptionalInt indexOptDetalle =   IntStream.range(0, pedSinDetalle.getDetalle().size())
                    .filter(j -> pedSinDetalle.getDetalle().get(j).getId().equals(idDetalle))
                    .findFirst();

            if(indexOptDetalle.isPresent()){
                pedSinDetalle.getDetalle().remove(indexOptDetalle.getAsInt());
                listaPedidos.set(indexOptPedido.getAsInt(), pedSinDetalle);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

