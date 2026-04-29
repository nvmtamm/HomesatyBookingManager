import { alerts, bookings, homestays, metrics, tours } from './data/mockData';

const navItems = ['Dashboard', 'Tours', 'Bookings', 'Homestays', 'Reports'];

function App() {
  const selectedTour = tours[0];
  const topBookers = [
    { name: 'Nguyen Minh Tam', count: 2 },
    { name: 'Tran Thi Mai', count: 2 },
    { name: 'Le Quoc Huy', count: 1 },
  ];

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div>
          <div className="brand">
            <div className="brand-mark">HB</div>
            <div>
              <h1>Homestay Booking</h1>
              <p>Admin console</p>
            </div>
          </div>

          <nav className="nav-list">
            {navItems.map((item, index) => (
              <button key={item} className={index === 0 ? 'nav-item active' : 'nav-item'}>
                {item}
              </button>
            ))}
          </nav>
        </div>

        <div className="sidebar-card">
          <span className="eyebrow">System status</span>
          <strong>All services online</strong>
          <p>Last sync: 2 minutes ago</p>
        </div>
      </aside>

      <main className="content">
        <section className="hero">
          <div>
            <span className="eyebrow">Booking operations</span>
            <h2>Manage tours, homestays, and bookings from one clean dashboard.</h2>
            <p>
              Designed for the backend project so you can demo the logic with a polished UI,
              fast filtering, and clear business rules.
            </p>
          </div>
          <div className="hero-actions">
            <button className="primary-btn">Add Booking</button>
            <button className="secondary-btn">Export Data</button>
          </div>
        </section>

        <section className="metrics-grid">
          {metrics.map((metric) => (
            <article key={metric.label} className={`metric-card tone-${metric.tone}`}>
              <span>{metric.label}</span>
              <strong>{metric.value}</strong>
              <p>{metric.delta}</p>
            </article>
          ))}
        </section>

        <section className="workspace-grid">
          <div className="panel large-panel">
            <div className="panel-header">
              <div>
                <span className="eyebrow">Tours table</span>
                <h3>Operational overview</h3>
              </div>
              <input className="search-input" type="search" placeholder="Search tours, bookings, homestays" />
            </div>

            <div className="table-wrap">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Homestay</th>
                    <th>Schedule</th>
                    <th>Capacity</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {tours.map((tour) => {
                    const fillRate = Math.round((tour.tourists / tour.capacity) * 100);
                    return (
                      <tr key={tour.id}>
                        <td>{tour.id}</td>
                        <td>
                          <strong>{tour.name}</strong>
                          <span>{tour.time} · {tour.price}</span>
                        </td>
                        <td>{tour.home}</td>
                        <td>
                          <strong>{tour.departure}</strong>
                          <span>to {tour.end}</span>
                        </td>
                        <td>
                          <div className="capacity-cell">
                            <span>{tour.tourists}/{tour.capacity}</span>
                            <div className="progress-bar">
                              <div style={{ width: `${fillRate}%` }} />
                            </div>
                          </div>
                        </td>
                        <td>
                          <span className={tour.status === 'Full' ? 'pill danger' : 'pill success'}>{tour.status}</span>
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
          </div>

          <div className="stack">
            <section className="panel detail-panel">
              <span className="eyebrow">Quick form</span>
              <h3>Create booking</h3>
              <div className="form-grid">
                <label>
                  Full name
                  <input type="text" placeholder="Customer name" />
                </label>
                <label>
                  Tour ID
                  <select defaultValue="">
                    <option value="" disabled>Select tour</option>
                    {tours.map((tour) => (
                      <option key={tour.id} value={tour.id}>{tour.id} - {tour.name}</option>
                    ))}
                  </select>
                </label>
                <label>
                  Booking date
                  <input type="date" defaultValue="2026-04-29" />
                </label>
                <label>
                  Phone
                  <input type="tel" placeholder="0900 000 000" />
                </label>
              </div>
              <button className="primary-btn full-width">Save booking</button>
            </section>

            <section className="panel detail-panel">
              <span className="eyebrow">Alerts</span>
              <h3>Business rules</h3>
              <ul className="alert-list">
                {alerts.map((alert) => (
                  <li key={alert}>{alert}</li>
                ))}
              </ul>
            </section>
          </div>
        </section>

        <section className="bottom-grid">
          <article className="panel">
            <div className="panel-header compact">
              <div>
                <span className="eyebrow">Bookings</span>
                <h3>Recent activity</h3>
              </div>
            </div>
            <div className="mini-list">
              {bookings.map((booking) => (
                <div key={booking.id} className="mini-row">
                  <div>
                    <strong>{booking.name}</strong>
                    <p>{booking.id} · {booking.tour} · {booking.date}</p>
                  </div>
                  <span className={booking.status === 'Confirmed' ? 'pill success' : 'pill warning'}>{booking.status}</span>
                </div>
              ))}
            </div>
          </article>

          <article className="panel">
            <div className="panel-header compact">
              <div>
                <span className="eyebrow">Homestays</span>
                <h3>Occupancy map</h3>
              </div>
            </div>
            <div className="occupancy-list">
              {homestays.map((home) => {
                const rate = Math.round((home.occupancy / home.capacity) * 100);
                return (
                  <div key={home.id} className="occupancy-item">
                    <div className="occupancy-top">
                      <div>
                        <strong>{home.name}</strong>
                        <p>{home.id} · {home.address}</p>
                      </div>
                      <span>{rate}%</span>
                    </div>
                    <div className="progress-bar small">
                      <div style={{ width: `${rate}%` }} />
                    </div>
                    <small>{home.occupancy}/{home.capacity} people</small>
                  </div>
                );
              })}
            </div>
          </article>

          <article className="panel">
            <div className="panel-header compact">
              <div>
                <span className="eyebrow">Customers</span>
                <h3>Frequent bookers</h3>
              </div>
            </div>
            <div className="mini-list">
              {topBookers.map((person) => (
                <div key={person.name} className="mini-row">
                  <div>
                    <strong>{person.name}</strong>
                    <p>{person.count} bookings</p>
                  </div>
                  <span className="pill neutral">Top</span>
                </div>
              ))}
            </div>
          </article>
        </section>

        <section className="callout">
          <div>
            <span className="eyebrow">Integration note</span>
            <h3>Next step: connect this UI to your Java backend API or local file service.</h3>
          </div>
          <p>
            Right now the UI uses mock data. Once your backend exposes endpoints, replace the
            static arrays in <code>src/data/mockData.js</code> with fetch calls.
          </p>
        </section>
      </main>
    </div>
  );
}

export default App;
