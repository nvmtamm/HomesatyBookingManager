export const metrics = [
  { label: 'Active Tours', value: '24', delta: '+12% this week', tone: 'teal' },
  { label: 'Bookings', value: '186', delta: '+34 new requests', tone: 'blue' },
  { label: 'Homestays', value: '18', delta: '3 nearly full', tone: 'amber' },
  { label: 'Revenue', value: '$48,920', delta: '+8.4% vs last month', tone: 'rose' },
];

export const tours = [
  {
    id: 'T001',
    name: 'Coastal Escape',
    home: 'HS003',
    time: '4D3N',
    price: '$420',
    departure: '2026-05-04',
    end: '2026-05-07',
    tourists: 18,
    capacity: 24,
    status: 'Open',
  },
  {
    id: 'T002',
    name: 'Mountain Retreat',
    home: 'HS008',
    time: '3D2N',
    price: '$280',
    departure: '2026-05-09',
    end: '2026-05-11',
    tourists: 22,
    capacity: 22,
    status: 'Full',
  },
  {
    id: 'T003',
    name: 'Old Town Weekend',
    home: 'HS002',
    time: '2D1N',
    price: '$145',
    departure: '2026-05-14',
    end: '2026-05-15',
    tourists: 9,
    capacity: 16,
    status: 'Open',
  },
  {
    id: 'T004',
    name: 'Island Discovery',
    home: 'HS011',
    time: '5D4N',
    price: '$680',
    departure: '2026-05-22',
    end: '2026-05-26',
    tourists: 14,
    capacity: 20,
    status: 'Open',
  },
];

export const bookings = [
  {
    id: 'B001',
    name: 'Nguyen Minh Tam',
    tour: 'T001',
    date: '2026-04-20',
    phone: '0901 234 567',
    status: 'Confirmed',
  },
  {
    id: 'B002',
    name: 'Tran Thi Mai',
    tour: 'T002',
    date: '2026-04-22',
    phone: '0912 345 678',
    status: 'Confirmed',
  },
  {
    id: 'B003',
    name: 'Le Quoc Huy',
    tour: 'T003',
    date: '2026-04-23',
    phone: '0988 111 222',
    status: 'Pending',
  },
  {
    id: 'B004',
    name: 'Pham Ngoc Anh',
    tour: 'T001',
    date: '2026-04-24',
    phone: '0977 333 444',
    status: 'Confirmed',
  },
];

export const homestays = [
  {
    id: 'HS001',
    name: 'Ocean Nest',
    room: '12',
    address: 'Da Nang, Vietnam',
    capacity: 28,
    occupancy: 24,
  },
  {
    id: 'HS002',
    name: 'Lantern House',
    room: '8',
    address: 'Hoi An, Vietnam',
    capacity: 16,
    occupancy: 11,
  },
  {
    id: 'HS003',
    name: 'Breeze Villa',
    room: '15',
    address: 'Quang Nam, Vietnam',
    capacity: 24,
    occupancy: 18,
  },
  {
    id: 'HS011',
    name: 'Sunrise Lodge',
    room: '10',
    address: 'Phu Quoc, Vietnam',
    capacity: 20,
    occupancy: 14,
  },
];

export const alerts = [
  'Tour T002 is fully booked and should be hidden from new booking actions.',
  '2 homestays are above 75% occupancy and need admin attention.',
  '3 bookings are waiting for confirmation within 24 hours.',
];
