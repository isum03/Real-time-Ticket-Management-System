import React from 'react';

const TicketDisplay = ({ totalTickets }) => (
  <div className='panel totalTicket'>
    <h2>Tickets</h2>
    <div className='ticketCount'>
      {totalTickets}
    </div>
  </div>
);

export default TicketDisplay;
