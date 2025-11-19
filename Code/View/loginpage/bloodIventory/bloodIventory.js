const API_BASE = localStorage.getItem('bbmsApiBase') || 'http://localhost:8080/api';
const inventoryState = {
    items: [],
    editingId: null,
    filters: {
        search: '',
        bloodType: 'all',
        status: 'all'
    }
};

const inventoryEls = {
    date: document.getElementById('inventory-date'),
    alert: document.getElementById('inventory-alert'),
    tableBody: document.getElementById('inventory-table-body'),
    total: document.getElementById('inventory-total'),
    available: document.getElementById('inventory-available'),
    expiring: document.getElementById('inventory-expiring'),
    form: document.getElementById('inventory-form'),
    formHelper: document.getElementById('inventory-form-helper'),
    submitLabel: document.getElementById('inventory-submit-label'),
    resetBtn: document.getElementById('inventory-reset-btn'),
    search: document.getElementById('inventory-search'),
    filterType: document.getElementById('inventory-type-filter'),
    filterStatus: document.getElementById('inventory-status-filter'),
    lastSync: document.getElementById('inventory-last-sync')
};

const inventoryStatusClass = (status) => {
    switch ((status || '').toLowerCase()) {
        case 'used':
            return 'badge badge-status-used';
        case 'expired':
            return 'badge badge-status-expired';
        default:
            return 'badge badge-status-available';
    }
};

const formatDate = (value) => {
    if (!value) return '—';
    const date = new Date(value);
    if (isNaN(date)) return value;
    return date.toLocaleDateString();
};

const updateInventoryStats = () => {
    inventoryEls.total.textContent = inventoryState.items.length;
    inventoryEls.available.textContent = inventoryState.items.filter(item => item.status === 'available').length;
    const now = new Date();
    const expiringSoon = inventoryState.items.filter(item => {
        if (!item.expired_date) return false;
        const exp = new Date(item.expired_date);
        if (isNaN(exp)) return false;
        const diff = (exp - now) / (1000 * 60 * 60 * 24);
        return diff >= 0 && diff <= 7;
    }).length;
    inventoryEls.expiring.textContent = expiringSoon;
};

const setInventoryAlert = (message, type = 'success') => {
    if (!message) {
        inventoryEls.alert.classList.add('hidden');
        return;
    }
    inventoryEls.alert.textContent = message;
    inventoryEls.alert.className = [
        'mb-6 rounded-xl border px-4 py-3 text-sm font-medium',
        type === 'success' ? 'border-emerald-200 bg-emerald-50 text-emerald-800' : 'border-red-200 bg-red-50 text-red-800'
    ].join(' ');
};

const setInventoryLoadingRow = (message = 'Loading...') => {
    inventoryEls.tableBody.innerHTML = `
        <tr>
            <td colspan="8" class="px-6 py-10 text-center text-gray-500 italic">${message}</td>
        </tr>
    `;
};

const resetInventoryForm = () => {
    inventoryState.editingId = null;
    inventoryEls.form.reset();
    inventoryEls.submitLabel.textContent = 'Add Blood Unit';
    inventoryEls.formHelper.textContent = 'Register a new blood unit.';
};

const applyInventoryFilters = (items) => items.filter(item => {
    const matchesSearch = inventoryState.filters.search
        ? (item.unit_id?.toLowerCase().includes(inventoryState.filters.search) ||
           item.bank_id?.toLowerCase().includes(inventoryState.filters.search))
        : true;
    const matchesType = inventoryState.filters.bloodType === 'all'
        ? true
        : item.blood_type === inventoryState.filters.bloodType;
    const matchesStatus = inventoryState.filters.status === 'all'
        ? true
        : (item.status || '').toLowerCase() === inventoryState.filters.status;
    return matchesSearch && matchesType && matchesStatus;
});

const renderInventory = () => {
    updateInventoryStats();
    const filtered = applyInventoryFilters(inventoryState.items);
    if (!filtered.length) {
        setInventoryLoadingRow('No blood units found.');
        return;
    }

    inventoryEls.tableBody.innerHTML = filtered.map(item => `
        <tr class="hover:bg-gray-50 transition-colors">
            <td class="px-6 py-4 text-sm font-semibold text-gray-800">${item.unit_id}</td>
            <td class="px-6 py-4 text-sm text-gray-600">${item.blood_type || '—'} ${item.RH || ''}</td>
            <td class="px-6 py-4 text-sm text-gray-600">${item.volume_Litter ?? '—'}</td>
            <td class="px-6 py-4 text-sm text-gray-600">${formatDate(item.donated_date)}</td>
            <td class="px-6 py-4 text-sm text-gray-600">${formatDate(item.expired_date)}</td>
            <td class="px-6 py-4">
                <span class="${inventoryStatusClass(item.status)}">
                    <i class="fas ${item.status === 'used' ? 'fa-check' : item.status === 'expired' ? 'fa-exclamation-triangle' : 'fa-tint'} text-xs"></i>
                    ${item.status || '—'}
                </span>
            </td>
            <td class="px-6 py-4 text-sm text-gray-600">${item.bank_id || '—'}</td>
            <td class="px-6 py-4 text-sm text-right space-x-2">
                <button data-action="edit" data-id="${item.unit_id}" class="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg border border-gray-200 text-gray-600 hover:bg-gray-100 transition-colors">
                    <i class="fas fa-edit text-xs"></i> Edit
                </button>
                <button data-action="delete" data-id="${item.unit_id}" class="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg border border-red-200 text-red-600 hover:bg-red-50 transition-colors">
                    <i class="fas fa-trash text-xs"></i> Delete
                </button>
            </td>
        </tr>
    `).join('');
};

const fetchInventory = async () => {
    // START MOCK DATA FOR OFFLINE USE
    setInventoryLoadingRow('Loading mock data...');
    await new Promise(resolve => setTimeout(resolve, 500)); // Simulate network latency

    const now = new Date();
    const donated1 = new Date(now); donated1.setDate(now.getDate() - 5);
    const expired1 = new Date(now); expired1.setDate(now.getDate() + 15);
    const expired2 = new Date(now); expired2.setDate(now.getDate() - 2); // Expired

    inventoryState.items = [
        {
            unit_id: "UNIT-0001",
            blood_type: "A",
            RH: "+",
            volume_Litter: 0.5,
            donated_date: donated1.toISOString().split('T')[0], // YYYY-MM-DD format
            expired_date: expired1.toISOString().split('T')[0],
            status: "available",
            bank_id: "BB-001"
        },
        {
            unit_id: "UNIT-0002",
            blood_type: "O",
            RH: "-",
            volume_Litter: 1.0,
            donated_date: "2025-10-01",
            expired_date: "2025-11-20", // Expiring soon
            status: "available",
            bank_id: "BB-002"
        },
        {
            unit_id: "UNIT-0003",
            blood_type: "B",
            RH: "+",
            volume_Litter: 0.4,
            donated_date: "2025-08-01",
            expired_date: expired2.toISOString().split('T')[0],
            status: "expired",
            bank_id: "BB-001"
        },
        {
            unit_id: "UNIT-0004",
            blood_type: "AB",
            RH: "+",
            volume_Litter: 0.5,
            donated_date: "2025-10-10",
            expired_date: "2025-12-30",
            status: "used",
            bank_id: "BB-003"
        }
    ];

    inventoryEls.lastSync.textContent = `Last sync: Loaded mock data`;
    renderInventory();
    setInventoryAlert('');
    // END MOCK DATA

    // (Bạn có thể giữ lại code fetch API gốc ở dưới nếu muốn)
};

const upsertInventoryUnit = async (payload) => {
    const isEditing = Boolean(inventoryState.editingId);
    const url = isEditing ? `${API_BASE}/blood-inventory/${inventoryState.editingId}` : `${API_BASE}/blood-inventory`;
    const method = isEditing ? 'PUT' : 'POST';
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

const deleteInventoryUnit = async (id) => {
    const response = await fetch(`${API_BASE}/blood-inventory/${id}`, { method: 'DELETE' });
    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || 'Unable to delete unit.');
    }
};

inventoryEls.form.addEventListener('submit', async (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const payload = Object.fromEntries(formData.entries());
    payload.volume_Litter = payload.volume_Litter ? parseFloat(payload.volume_Litter) : null;

    try {
        await upsertInventoryUnit(payload);
        setInventoryAlert(`Blood unit ${payload.unit_id} saved successfully.`);
        resetInventoryForm();
        await fetchInventory();
    } catch (error) {
        console.error(error);
        setInventoryAlert(error.message || 'Unable to save unit.', 'error');
    }
});

inventoryEls.tableBody.addEventListener('click', async (event) => {
    const button = event.target.closest('button[data-action]');
    if (!button) return;
    const id = button.dataset.id;
    const action = button.dataset.action;
    const unit = inventoryState.items.find(item => item.unit_id === id);

    if (action === 'edit' && unit) {
        inventoryState.editingId = id;
        inventoryEls.formHelper.textContent = `Editing unit ${id}. Save changes or reset to cancel.`;
        inventoryEls.submitLabel.textContent = 'Update Blood Unit';
        Object.entries(unit).forEach(([key, value]) => {
            const input = inventoryEls.form.elements.namedItem(key);
            if (!input || value === undefined || value === null) return;
            input.value = value;
        });
        inventoryEls.form.scrollIntoView({ behavior: 'smooth' });
    }

    if (action === 'delete') {
        const confirmDelete = confirm('Delete this blood unit?');
        if (!confirmDelete) return;
        try {
            await deleteInventoryUnit(id);
            setInventoryAlert(`Unit ${id} deleted.`);
            await fetchInventory();
        } catch (error) {
            console.error(error);
            setInventoryAlert(error.message || 'Unable to delete unit.', 'error');
        }
    }
});

inventoryEls.search.addEventListener('input', (event) => {
    inventoryState.filters.search = event.target.value.toLowerCase();
    renderInventory();
});

inventoryEls.filterType.addEventListener('change', (event) => {
    inventoryState.filters.bloodType = event.target.value;
    renderInventory();
});

inventoryEls.filterStatus.addEventListener('change', (event) => {
    inventoryState.filters.status = event.target.value;
    renderInventory();
});

inventoryEls.resetBtn.addEventListener('click', () => {
    resetInventoryForm();
});

inventoryEls.date.textContent = new Date().toLocaleDateString(undefined, { weekday: 'long', month: 'long', day: 'numeric', year: 'numeric' });
fetchInventory();

