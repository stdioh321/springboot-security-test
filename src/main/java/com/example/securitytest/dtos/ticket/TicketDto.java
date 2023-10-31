package com.example.securitytest.dtos.ticket;


import com.example.securitytest.dtos.ICommonDto;
import com.example.securitytest.models.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto implements ICommonDto<Ticket> {
    private Long id;
    private String name;
    private String field01;
    private String field02;

    public TicketDto(Ticket ticket) {
        this.setId(ticket.getId());
        this.setName(ticket.getName());
        this.setField01(ticket.getField01());
        this.setField02(ticket.getField02());
    }

    @Override
    public Ticket toEntity() {
        var ticket = new Ticket();
        ticket.setId(this.getId());
        ticket.setName(this.getName());
        ticket.setField01(this.getField01());
        ticket.setField02(this.getField02());

        return ticket;
    }
}
