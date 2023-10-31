package com.example.securitytest.controllers.api;

import com.example.securitytest.dtos.ticket.TicketCreateForm;
import com.example.securitytest.dtos.ticket.TicketDto;
import com.example.securitytest.dtos.ticket.TicketPatchForm;
import com.example.securitytest.exceptions.NotFoundException;
import com.example.securitytest.models.Ticket;
import com.example.securitytest.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<?> get() {
        var result = this.ticketService.getAll().stream().map(it -> new TicketDto(it));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> putById(@PathVariable("id") Long id) {
        try {
            var result = this.ticketService.getById(id);
            return ResponseEntity.ok(new TicketDto(result));
        } catch (NotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody TicketCreateForm ticketCreateForm) {
        return ResponseEntity.ok(new TicketDto(this.ticketService.post(ticketCreateForm)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putById(@PathVariable("id") Long id, @Valid @RequestBody TicketCreateForm ticketCreateForm) {
        try {
            var result = this.ticketService.putById(id, ticketCreateForm);
            return ResponseEntity.ok(new TicketDto(result));
        } catch (NotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchById(@PathVariable("id") Long id, @Valid @RequestBody TicketPatchForm ticketPatchForm) throws NotFoundException {
        Ticket result = this.ticketService.patchById(id, ticketPatchForm);
        return ResponseEntity.ok(new TicketDto(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        try {
            this.ticketService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
