package com.dan.dot.lab01.repository;

import com.dan.dot.lab01.domain.Pedido;
import com.dan.dot.lab01.service.PedidoService;
import frsf.isi.dan.InMemoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PedidoRepository extends InMemoryRepository<Pedido> {

    @Override
    public Integer getId(Pedido entity) {
        return entity.getId();
    }

    @Override
    public void setId(Pedido entity, Integer id) {
        entity.setId(id);
    }
}
