package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.Pagos;
import java.util.List;

public interface PagosService {
    List<Pagos> getAllPagos();
    Pagos getPagoById(Integer id);
    List<Pagos> getPagosByUsuarioId(Integer fkIdUsuario);
    Pagos savePago(Pagos pago);
    Pagos updatePago(Integer id, Pagos pago);
    void deletePago(Integer id);
}