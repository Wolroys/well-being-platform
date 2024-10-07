// Fetch all events from the server and display them on the calendar
fetch('http://localhost:8080/event')
    .then(response => response.json())
    .then(events => {
        // Display the events on the calendar
        const calendar = document.getElementById('calendar');
        events.forEach(event => {
            const cell = document.createElement('div');
            cell.classList.add('calendar-cell');
            cell.dataset.date = event.start;
            const eventItem = document.createElement('div');
            eventItem.classList.add('event-item');
            eventItem.dataset.id = event.id;
            eventItem.textContent = event.title;
            cell.appendChild(eventItem);
            calendar.appendChild(cell);
        });
    });

// Add event listeners to the calendar cells to open a modal window for creating a new event
document.getElementById('calendar').addEventListener('click', event => {
    if (event.target.classList.contains('calendar-cell')) {
        // Open the modal window for creating a new event
        document.getElementById('createEventModal').style.display = 'block';
        // Set the default start and end dates for the new event
        const start = new Date(event.target.dataset.date);
        const end = new Date(start.getTime() + 3600000);
        document.getElementById('start').value = start.toISOString().slice(0, 16);
        document.getElementById('end').value = end.toISOString().slice(0, 16);
    }
});

// Add event listeners to the event items to open a modal window for viewing and editing the event details
document.getElementById('calendar').addEventListener('click', event => {
    if (event.target.classList.contains('event-item')) {
        const eventId = event.target.dataset.id;
        // Fetch the event details from the server
        fetch(`http://localhost:8080/event/${eventId}`)
            .then(response => response.json())
            .then(event => {
                // Display the event details in the modal window
                document.getElementById('title').value = event.title;
                document.getElementById('start').value = event.start;
                document.getElementById('end').value = event.end;
                // Open the modal window for viewing and editing the event details
                document.getElementById('eventModal').style.display = 'block';
            });
    }
});

// Add event listener to the create event form to create a new event
document.getElementById('createEventForm').addEventListener('submit', event => {
    event.preventDefault();
    // Get the form data
    const title = document.getElementById('title').value;
    const start = document.getElementById('start').value;
    const end = document.getElementById('end').value;
    // Create a new event object
    const newEvent = {title, start, end};
    // Send a POST request to the server to create the new event
    fetch('http://localhost:8080/event/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newEvent)
    })
        .then(response => response.json())
        .then(event => {
            // Close the modal window
            document.getElementById('createEventModal').style.display = 'none';
            // Add the new event to the calendar
            const cell = document.querySelector(`.calendar-cell[data-date="${event.start}"]`);
            const eventItem = document.createElement('div');
            eventItem.classList.add('event-item');
            eventItem.dataset.id = event.id;
            eventItem.textContent = event.title;
            cell.appendChild(eventItem);
        });
});

// Add event listener to the edit event form to update the event
document.getElementById('editEventForm').addEventListener('submit', event => {
    event.preventDefault();
    // Get the form data
    const title = document.getElementById('title').value;
    const start = document.getElementById('start').value;
    const end = document.getElementById('end').value;
    // Get the event id from the modal window
    const eventId = document.getElementById('eventModal').dataset.id;
    // Create an updated event object
    const updatedEvent = {id: eventId, title, start, end};
    // Send a PUT request to the server to update the event
    fetch(`http://localhost:8080/event/${eventId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedEvent)
    })
        .then(response => response.json())
        .then(event => {
            // Close the modal window
            document.getElementById('eventModal').style.display = 'none';
            // Update the event on the calendar
            const eventItem = document.querySelector(`.event-item[data-id="${event.id}"]`);
            eventItem.textContent = event.title;
            const cell = eventItem.parentNode;
            cell.dataset.date = event.start;
        });
});
