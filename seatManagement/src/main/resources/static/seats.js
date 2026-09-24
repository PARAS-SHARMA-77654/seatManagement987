const API_BASE = (window.location.origin && window.location.origin !== 'null')
  ? window.location.origin
  : 'http://localhost:8080';

// Event lookup — matches the cards on events.html.
// Later this will come from GET /api/events/{id} instead of being hardcoded here.
const EVENTS = {
  "tech-conference": { name: "Tech Conference", meta: "Oct 14, 2026", totalSeats: 50, taken: 0 },
  "music-night":     { name: "Music Night",     meta: "Oct 21, 2026", totalSeats: 30, taken: 0 },
  "sports-meet":      { name: "Sports Meet",     meta: "Nov 2, 2026",  totalSeats: 60, taken: 0 }
};

const params = new URLSearchParams(window.location.search);
const eventId = params.get('event');
const event = EVENTS[eventId] || EVENTS["tech-conference"];

document.getElementById('welcome').textContent = sessionStorage.getItem('username')
  ? `Signed in as ${sessionStorage.getItem('username')}` : '';
document.getElementById('eventName').textContent = event.name;
document.getElementById('eventMeta').textContent = `${event.meta} · ${event.totalSeats - event.taken} of ${event.totalSeats} seats available`;

let selectedSeat = null;

function renderSeats() {
  const grid = document.getElementById('seatGrid');
  grid.innerHTML = '';

  for (let i = 1; i <= event.totalSeats; i++) {
    const seat = document.createElement('button');
    seat.type = 'button';
    seat.className = 'seat';
    seat.textContent = i;
    seat.dataset.seat = i;

    // First `taken` seats are pre-booked, for demo purposes.
    if (i <= event.taken) {
      seat.classList.add('taken');
      seat.disabled = true;
    } else {
      seat.addEventListener('click', () => selectSeat(i, seat));
    }

    grid.appendChild(seat);
  }
}

function selectSeat(number, el) {
  document.querySelectorAll('.seat.selected').forEach(s => s.classList.remove('selected'));
  el.classList.add('selected');
  selectedSeat = number;
  document.getElementById('selectedSeatLabel').textContent = `Seat ${number} selected`;
  document.getElementById('confirmBtn').disabled = false;
}

document.getElementById('bookingForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const msg = document.getElementById('msg');

  const payload = {
    eventId,
    seat: selectedSeat,
    name: document.getElementById('name').value.trim(),
    email: document.getElementById('email').value.trim(),
    registrationType: document.getElementById('regType').value
  };

  // Backend endpoint isn't wired up yet — this is where it plugs in next.
  try {
    const res = await fetch(`${API_BASE}/api/events/${eventId}/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (res.ok) {
      const data = await res.json();
      msg.style.color = 'var(--accent)';

      if (data.status === 'CONFIRMED') {
        msg.textContent = `Seat ${selectedSeat} confirmed!`;
        setTimeout(() => {
          window.location.href = 'events.html';
        }, 1200);
      } else {
        msg.textContent = 'Seats are full — you\'re on the waitlist.';
      }
    } else {
      msg.style.color = '#D96C6C';
      msg.textContent = 'Could not complete the reservation.';
    }
  } catch (err) {
    msg.style.color = '#D96C6C';
    msg.textContent = 'Backend not reachable yet — this button will work once the Spring Boot API is running.';
  }
});

renderSeats();