const API_BASE = localStorage.getItem('bbmsApiBase') || 'http://localhost:8080/api';
const bankState = {
    items: [],
    editingId: null,
    filters: {
        search: '',
        assignment: 'all'
    }
};

const bankEls = {
    date: document.getElementById('bank-date'),
    alert: document.getElementById('bank-alert'),
    tableBody: document.getElementById('bank-table-body'),
    total: document.getElementById('bank-total'),
    avgVolume: document.getElementById('bank-avg-volume'),
    assigned: document.getElementById('bank-assigned'),
    form: document.getElementById('bank-form'),
    formHelper: document.getElementById('bank-form-helper'),
    submitLabel: document.getElementById('bank-submit-label'),
    resetBtn: document.getElementById('bank-reset-btn'),
    search: document.getElementById('bank-search'),
    filterAssignment: document.getElementById('bank-assignment-filter'),
    lastSync: document.getElementById('bank-last-sync')
};

const updateBankStats = () => {
    bankEls.total.textContent = bankState.items.length;
    const volumes = bankState.items.map(item => parseFloat(item.volume ?? 0)).filter(num => !isNaN(num));
    const avg = volumes.length ? (volumes.reduce((sum, num) => sum + num, 0) / volumes.length) : 0;
    bankEls.avgVolume.textContent = avg.toFixed(1);
    bankEls.assigned.textContent = bankState.items.filter(item => item.assigned_doctor).length;
};

const setBankAlert = (message, type = 'success') => {
    if (!message) {
        bankEls.alert.classList.add('hidden');
        return;
    }
    bankEls.alert.textContent = message;
    bankEls.alert.className = [
        'mb-6 rounded-xl border px-4 py-3 text-sm font-medium',
        type === 'success' ? 'border-emerald-200 bg-emerald-50 text-emerald-800' : 'border-red-200 bg-red-50 text-red-800'
    ].join(' ');
};

const setBankLoadingRow = (message = 'Loading...') => {
    bankEls.tableBody.innerHTML = `
        <tr>
            <td colspan="7" class="px-6 py-10 text-center text-gray-500 italic">${message}</td>
        </tr>
    `;
};

const resetBankForm = () => {
    bankState.editingId = null;
    bankEls.form.reset();
    bankEls.submitLabel.textContent = 'Register Blood Bank';
    bankEls.formHelper.textContent = 'Register a new blood bank.';
};

const applyBankFilters = (items) => items.filter(item => {
    const search = bankState.filters.search;
    const matchesSearch = search
        ? (
            item.bank_id?.toLowerCase().includes(search) ||
            item.bank_name?.toLowerCase().includes(search) ||
            item.location?.toLowerCase().includes(search)
        )
        : true;
    let matchesAssignment = true;
    if (bankState.filters.assignment === 'assigned') {
        matchesAssignment = Boolean(item.assigned_doctor);
    } else if (bankState.filters.assignment === 'unassigned') {
        matchesAssignment = !item.assigned_doctor;
    }
    return matchesSearch && matchesAssignment;
});

const renderBanks = () => {
    updateBankStats();
    const filtered = applyBankFilters(bankState.items);
    if (!filtered.length) {
        setBankLoadingRow('No blood banks found.');
        return;
    }
    bankEls.tableBody.innerHTML = filtered.map(bank => `
        <tr class="hover:bg-gray-50 transition-colors">
            <td class="px-6 py-4 text-sm font-semibold text-gray-800">${bank.bank_id}</td>
            <td class="px-6 py-4 text-sm text-gray-700">${bank.bank_name || '—'}</td>
            <td class="px-6 py-4 text-sm text-gray-600">${bank.location || '—'}</td>
            <td class="px-6 py-4 text-sm text-gray-600">
                <div>${bank.contact_phone || '—'}</div>
                <div class="text-gray-400 text-xs">${bank.contact_email || ''}</div>
            </td>
            <td class="px-6 py-4 text-sm text-gray-600">${bank.volume ?? '—'}</td>
            <td class="px-6 py-4 text-sm text-gray-600">${bank.assigned_doctor || '—'}</td>
            <td class="px-6 py-4 text-sm text-right space-x-2">
                <button data-action="edit" data-id="${bank.bank_id}" class="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg border border-gray-200 text-gray-600 hover:bg-gray-100 transition-colors">
                    <i class="fas fa-edit text-xs"></i> Edit
                </button>
                <button data-action="delete" data-id="${bank.bank_id}" class="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg border border-red-200 text-red-600 hover:bg-red-50 transition-colors">
                    <i class="fas fa-trash text-xs"></i> Delete
                </button>
            </td>
        </tr>
    `).join('');
};

const fetchBanks = async () => {
    // START MOCK DATA FOR OFFLINE USE
    setBankLoadingRow('Loading mock data...');
    await new Promise(resolve => setTimeout(resolve, 500)); // Simulate network latency

    bankState.items = [
        {
            bank_id: "BB-001",
            bank_name: "Central Blood Bank",
            location: "Ho Chi Minh City",
            contact_phone: "+84 901 123 456",
            contact_email: "hcm@bloodbank.org",
            volume: 125.5,
            assigned_doctor: "DOC-005",
            request_id: "REQ-001"
        },
        {
            bank_id: "BB-002",
            bank_name: "Hanoi Red Cross Center",
            location: "Hanoi",
            contact_phone: "+84 24 987 654",
            contact_email: "hn@bloodbank.org",
            volume: 80.0,
            assigned_doctor: null,
            request_id: ""
        },
        {
            bank_id: "BB-003",
            bank_name: "Da Nang Facility",
            location: "Da Nang",
            contact_phone: "+84 511 111 222",
            contact_email: "dn@bloodbank.org",
            volume: 55.2,
            assigned_doctor: "DOC-010",
            request_id: "REQ-002"
        }
    ];

    bankEls.lastSync.textContent = `Last sync: Loaded mock data`;
    renderBanks();
    setBankAlert('');
    // END MOCK DATA
    
    // (Bạn có thể giữ lại code fetch API gốc ở dưới nếu muốn)
};

const upsertBank = async (payload) => {
    const isEditing = Boolean(bankState.editingId);
    const url = isEditing ? `${API_BASE}/blood-banks/${bankState.editingId}` : `${API_BASE}/blood-banks`;
    const method = isEditing ? 'PUT' : 'POST';
    if (payload.volume) payload.volume = parseFloat(payload.volume);
    const response = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || 'Request failed.');
    }
};

const deleteBank = async (id) => {
    const response = await fetch(`${API_BASE}/blood-banks/${id}`, { method: 'DELETE' });
    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || 'Unable to delete blood bank.');
    }
};

bankEls.form.addEventListener('submit', async (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const payload = Object.fromEntries(formData.entries());
    try {
        await upsertBank(payload);
        setBankAlert(`Blood bank ${payload.bank_id} saved successfully.`);
        resetBankForm();
        await fetchBanks();
    } catch (error) {
        console.error(error);
        setBankAlert(error.message || 'Unable to save blood bank.', 'error');
    }
});

bankEls.tableBody.addEventListener('click', async (event) => {
    const button = event.target.closest('button[data-action]');
    if (!button) return;
    const id = button.dataset.id;
    const action = button.dataset.action;
    const bank = bankState.items.find(item => item.bank_id === id);

    if (action === 'edit' && bank) {
        bankState.editingId = id;
        bankEls.formHelper.textContent = `Editing bank ${id}. Save changes or reset to cancel.`;
        bankEls.submitLabel.textContent = 'Update Blood Bank';
        Object.entries(bank).forEach(([key, value]) => {
            const input = bankEls.form.elements.namedItem(key);
            if (!input || value === undefined || value === null) return;
            input.value = value;
        });
        bankEls.form.scrollIntoView({ behavior: 'smooth' });
    }

    if (action === 'delete') {
        const confirmDelete = confirm('Delete this blood bank?');
        if (!confirmDelete) return;
        try {
            await deleteBank(id);
            setBankAlert(`Blood bank ${id} deleted.`);
            await fetchBanks();
        } catch (error) {
            console.error(error);
            setBankAlert(error.message || 'Unable to delete blood bank.', 'error');
        }
    }
});

bankEls.search.addEventListener('input', (event) => {
    bankState.filters.search = event.target.value.toLowerCase();
    renderBanks();
});

bankEls.filterAssignment.addEventListener('change', (event) => {
    bankState.filters.assignment = event.target.value;
    renderBanks();
});

bankEls.resetBtn.addEventListener('click', () => {
    resetBankForm();
});

bankEls.date.textContent = new Date().toLocaleDateString(undefined, { weekday: 'long', month: 'long', day: 'numeric', year: 'numeric' });
fetchBanks();

