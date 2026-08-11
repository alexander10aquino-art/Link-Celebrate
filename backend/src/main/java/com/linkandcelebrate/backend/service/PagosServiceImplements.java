package com.linkandcelebrate.backend.service;

import com.linkandcelebrate.backend.model.Pagos;
import com.linkandcelebrate.backend.exception.ResourceNotFoundException;
import com.linkandcelebrate.backend.repository.PagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagosServiceImplements implements PagosService {

    @Autowired
    private PagosRepository pagosRepository;

    @Override
    public List<Pagos> getAllPagos() {
        return pagosRepository.findAll();
    }

    @Override
    public Pagos getPagoById(Integer id) {
        return pagosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagos", "idPago", id));
    }

    @Override
    public List<Pagos> getPagosByUsuarioId(Integer fkIdUsuario) {
        return pagosRepository.findByFkIdUsuario(fkIdUsuario);
    }

    @Override
    public Pagos savePago(Pagos pago) {
        return pagosRepository.save(pago);
    }

    @Override
    public Pagos updatePago(Integer id, Pagos pago) {
        Pagos existente = getPagoById(id);
        existente.setMonto(pago.getMonto());
        existente.setFechaPago(pago.getFechaPago());
        existente.setMetodoPago(pago.getMetodoPago());
        existente.setEstado(pago.getEstado());
        existente.setFkIdUsuario(pago.getFkIdUsuario());
        existente.setFkIdInvitacion(pago.getFkIdInvitacion());
        return pagosRepository.save(existente);
    }

    @Override
    public void deletePago(Integer id) {
        Pagos existente = getPagoById(id);
        pagosRepository.delete(existente);
    }
}