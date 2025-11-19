const API_BASE = localStorage.getItem('bbmsApiBase') || 'http://localhost:8080/api';
const accountState = {
    items: [],
    editingId: null,
    filters: {
        search: '',
        role: 'all',
        status: 'all'
    },
    loading: false
};

const els = {
    date: document.getElementById('page-date'),
    alert: document.getElementById('account-alert'),
    tableBody: document.getElementById('account-table-body'),
    total: document.getElementById('accounts-total'),
    active: document.getElementById('accounts-active'),
    form: document.getElementById('account-form'),
    formHelper: document.getElementById('account-form-helper'),
    submitLabel: document.getElementById('account-submit-label'),
    resetBtn: document.getElementById('account-reset-btn'),
    search: document.getElementById('account-search'),
    filterRole: document.getElementById('account-role-filter'),
    filterStatus: document.getElementById('account-status-filter'),
    lastSync: document.getElementById('account-last-sync')
};

const formatDate = (value) => {
    if (!value) return '—';
    const date = new Date(value);
    return isNaN(date) ? value : date.toLocaleString();
};

const updateDate = () => {
    const now = new Date();
    els.date.textContent = now.toLocaleDateString(undefined, { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' });
};

const setAlert = (message, type = 'success') => {
    if (!message) {
        els.alert.classList.add('hidden');
        return;
    }
    els.alert.textContent = message;
    els.alert.className = [
        'mb-6 rounded-xl border px-4 py-3 text-sm font-medium',
        type === 'success' ? 'border-emerald-200 bg-emerald-50 text-emerald-800' : 'border-red-200 bg-red-50 text-red-800'
    ].join(' ');
};

const setLoadingRow = (message = 'Loading...') => {
    els.tableBody.innerHTML = `
        <tr>
            <td colspan="6" class="px-6 py-10 text-center text-gray-500 italic">${message}</td>
        </tr>
    `;
};

const resetForm = () => {
    accountState.editingId = null;
    els.form.reset();
    els.submitLabel.textContent = 'Create Account';
    els.formHelper.textContent = 'Fill in the details to create a new account.';
};

const applyFilters = (items) => {
    return items.filter(item => {
        const matchesSearch = accountState.filters.search
            ? (item.id?.toLowerCase().includes(accountState.filters.search) ||
               item.email?.toLowerCase().includes(accountState.filters.search))
            : true;
        const matchesRole = accountState.filters.role === 'all'
            ? true
            : item.role?.toLowerCase() === accountState.filters.role.toLowerCase();
        const matchesStatus = accountState.filters.status === 'all'
            ? true
            : (accountState.filters.status === 'active' ? item.is_active : !item.is_active);
        return matchesSearch && matchesRole && matchesStatus;
    });
};

const renderAccounts = () => {
    const filtered = applyFilters(accountState.items);
    els.total.textContent = accountState.items.length;
    els.active.textContent = accountState.items.filter(acc => acc.is_active).length;

    if (!filtered.length) {
        setLoadingRow('No accounts found.');
        return;
    }

    els.tableBody.innerHTML = filtered.map(account => `
        <tr class="hover:bg-gray-50 transition-colors">
            <td class="px-6 py-4 text-sm font-semibold text-gray-800">${account.id}</td>
            <td class="px-6 py-4 text-sm text-gray-600">${account.email || '—'}</td>
            <td class="px-6 py-4">
                <span class="badge ${account.role === 'Doctor' ? 'badge-role-doctor' : 'badge-role'}">
                    <i class="fas fa-user-tag text-xs"></i> ${account.role || '—'}
                </span>
            </td>
            <td class="px-6 py-4">
                <span class="badge ${account.is_active ? 'badge-status-active' : 'badge-status-inactive'}">
                    <i class="fas ${account.is_active ? 'fa-check-circle' : 'fa-clock'} text-xs"></i>
                    ${account.is_active ? 'Active' : 'Inactive'}
                </span>
            </td>
            <td class="px-6 py-4 text-sm text-gray-600">${formatDate(account.time_created)}</td>
            <td class="px-6 py-4 text-sm text-right space-x-2">
                <button data-action="edit" data-id="${account.id}" class="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg border border-gray-200 text-gray-600 hover:bg-gray-100 transition-colors">
                    <i class="fas fa-edit text-xs"></i> Edit
                </button>
                <button data-action="delete" data-id="${account.id}" class="inline-flex items-center gap-1 px-3 py-1.5 rounded-lg border border-red-200 text-red-600 hover:bg-red-50 transition-colors">
                    <i class="fas fa-trash text-xs"></i> Delete
                </button>
            </td>
        </tr>
    `).join('');
};

const fetchAccounts = async () => {
    // MOCK DATA FOR OFFLINE USE
    setLoadingRow('Loading mock data...');
    await new Promise(resolve => setTimeout(resolve, 500)); // Simulate latency

    accountState.items = [
        {
            id: "ACCDOC001",
            email: "nguyenvtuan.doc1@example.com",
            password: "$2y$10$docpass1",
            role: "Doctor",
            is_active: true,
            time_created: "2025-11-15T09:00:00" 
        },
        {
            id: "ACCPAT002",
            email: "tranthibich.pat2@example.com",
            password: "$2y$10$examplehash2",
            role: "Patient",
            is_active: true,
            time_created: "2025-11-14T10:00:00"
        },
        {
            id: "ACCPAT004",
            email: "phamthidung.pat4@example.com",
            password: "$2y$10$examplehash4",
            role: "Patient",
            is_active: false, // Tài khoản Patient không hoạt động
            time_created: "2025-11-13T11:00:00"
        },
        {
            id: "ACCADM001",
            email: "admin@bloodbank.org",
            password: "$2y$10$adminpass",
            role: "Admin",
            is_active: true,
            time_created: "2025-11-10T12:00:00"
        },
        {
            id: "ACCSTA001",
            email: "staff@bloodbank.org",
            password: "$2y$10$staffpass",
            role: "Staff",
            is_active: true,
            time_created: "2025-11-10T12:30:00"
        },
    ];

    els.lastSync.textContent = `Last sync: Loaded mock data`;
    renderAccounts();
    setAlert('');
    // END MOCK DATA
};

const upsertAccount = async (payload) => {
    const isEditing = Boolean(accountState.editingId);
    const url = isEditing ? `${API_BASE}/accounts/${accountState.editingId}` : `${API_BASE}/accounts`;
    const method = isEditing ? 'PUT' : 'POST';
    const response = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });
    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Request failed.');
    }
};

const deleteAccount = async (id) => {
    const response = await fetch(`${API_BASE}/accounts/${id}`, { method: 'DELETE' });
    if (!response.ok) {
        const message = await response.text();
        throw new Error(message || 'Unable to delete account.');
    }
};

els.form.addEventListener('submit', async (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const payload = Object.fromEntries(formData.entries());
    payload.is_active = payload.is_active === 'true';

    try {
        await upsertAccount(payload);
        setAlert(`Account ${payload.id} saved successfully.`);
        resetForm();
        await fetchAccounts();
    } catch (error) {
        console.error(error);
        setAlert(error.message || 'Unable to save account.', 'error');
    }
});

els.tableBody.addEventListener('click', async (event) => {
    const button = event.target.closest('button[data-action]');
    if (!button) return;
    const id = button.dataset.id;
    const action = button.dataset.action;
    const account = accountState.items.find(item => item.id === id);

    if (action === 'edit' && account) {
        accountState.editingId = id;
        els.formHelper.textContent = `Editing account ${id}. Save changes or reset to cancel.`;
        els.submitLabel.textContent = 'Update Account';
        Object.entries(account).forEach(([key, value]) => {
            const input = els.form.elements.namedItem(key);
            if (!input || value === undefined || value === null) return;
            if (key === 'is_active') {
                input.value = value ? 'true' : 'false';
            } else if (typeof input.value !== 'undefined') {
                input.value = value;
            }
        });
        els.form.scrollIntoView({ behavior: 'smooth' });
    }

    if (action === 'delete') {
        const confirmDelete = confirm('Are you sure you want to delete this account?');
        if (!confirmDelete) return;
        try {
            await deleteAccount(id);
            setAlert(`Account ${id} deleted.`);
            await fetchAccounts();
        } catch (error) {
            console.error(error);
            setAlert(error.message || 'Unable to delete account.', 'error');
        }
    }
});

els.search.addEventListener('input', (event) => {
    accountState.filters.search = event.target.value.toLowerCase();
    renderAccounts();
});

els.filterRole.addEventListener('change', (event) => {
    accountState.filters.role = event.target.value;
    renderAccounts();
});

els.filterStatus.addEventListener('change', (event) => {
    accountState.filters.status = event.target.value;
    renderAccounts();
});

els.resetBtn.addEventListener('click', () => {
    resetForm();
});

updateDate();
fetchAccounts();

