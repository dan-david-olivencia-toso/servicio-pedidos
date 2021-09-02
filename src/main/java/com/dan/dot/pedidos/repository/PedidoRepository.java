package com.dan.dot.pedidos.repository;

import com.dan.dot.pedidos.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    Optional<Pedido> findPedidoById(Integer id);
    boolean existsById(Integer id);
    Pedido save(Pedido pedido);
}
