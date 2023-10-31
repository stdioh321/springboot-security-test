package com.example.securitytest.dtos.ticket;


import com.example.securitytest.dtos.ICommonDto;
import com.example.securitytest.models.Ticket;
import com.example.securitytest.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketCreateForm implements ICommonDto<Ticket>  {
    @NotNull
    @Size(min = 2)
    private String name;

    @NotNull
    @Size(min = 2)
    private String field01;

    @NotNull
    @Size(min = 2)
    private String field02;

    public TicketCreateForm(Ticket ticket) {
        this.setName(ticket.getName());
        this.setField01(ticket.getField01());
        this.setField02(ticket.getField02());
    }

    @Override
    public Ticket toEntity() {
        var ticket = new Ticket();
        ticket.setName(this.getName());
        ticket.setField01(this.getField01());
        ticket.setField02(this.getField02());

        return ticket;
    }
}
