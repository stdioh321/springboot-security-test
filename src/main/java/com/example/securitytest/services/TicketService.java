package com.example.securitytest.services;

import com.example.securitytest.dtos.ticket.TicketCreateForm;
import com.example.securitytest.dtos.ticket.TicketDto;
import com.example.securitytest.dtos.ticket.TicketPatchForm;
import com.example.securitytest.exceptions.NotFoundException;
import com.example.securitytest.models.Ticket;
import com.example.securitytest.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;


    public Ticket getById(Long id) throws NotFoundException {
        Ticket ticket = this.ticketRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket entity with id: %d Not found".formatted(id)));
        return ticket;
    }

    public List<Ticket> getAll() {
        return this.ticketRepository.findAll();
    }


    @Transactional
    public Ticket post(TicketCreateForm ticketCreateForm) {
        return ticketRepository.save(ticketCreateForm.toEntity());
    }


    @Transactional
    public Ticket putById(Long id, TicketCreateForm ticketCreateForm) throws NotFoundException {
        var currTicket = this.getById(id);
        currTicket.setName(ticketCreateForm.getName());
        currTicket.setField01(ticketCreateForm.getField01());
        currTicket.setField02(ticketCreateForm.getField02());
        return ticketRepository.save(currTicket);
    }


    @Transactional
    public void deleteById(Long id) throws NotFoundException {
        this.getById(id);
        this.ticketRepository.deleteById(id);
    }


    @Transactional
    public Ticket patchById(Long id, TicketPatchForm ticketPatchForm) throws NotFoundException {
        Ticket ticketToUpdate = this.getById(id);
        if (ticketPatchForm.getName() != null) ticketToUpdate.setName(ticketPatchForm.getName());
        if (ticketPatchForm.getField01() != null) ticketToUpdate.setField01(ticketPatchForm.getField01());
        if (ticketPatchForm.getField02() != null) ticketToUpdate.setField02(ticketPatchForm.getField02());

        return this.ticketRepository.save(ticketToUpdate);
    }
}
