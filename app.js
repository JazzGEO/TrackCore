const API_URL = 'http://localhost:8080/api';

// Função para trocar de aba
function showTab(tabName) {
    document.querySelectorAll('.tab-content').forEach(content => {
        content.classList.remove('active');
    });
    document.querySelectorAll('.tab').forEach(tab => {
        tab.classList.remove('active');
    });
    
    document.getElementById(tabName).classList.add('active');
    event.target.classList.add('active');
    
    // Carregar dados da aba
    if (tabName === 'assets') loadAssets();
    if (tabName === 'users') loadUsers();
    if (tabName === 'locations') loadLocations();
    if (tabName === 'history') loadHistory();
}

// Carregar ativos
async function loadAssets() {
    try {
        const response = await fetch(`${API_URL}/assets`);
        const assets = await response.json();
        
        const tbody = document.querySelector('#assets-table tbody');
        tbody.innerHTML = '';
        
        // Atualizar estatísticas
        document.getElementById('total-assets').textContent = assets.length;
        document.getElementById('available-assets').textContent = 
            assets.filter(a => a.status === 'disponivel').length;
        document.getElementById('in-use-assets').textContent = 
            assets.filter(a => a.status === 'em_uso').length;
        
        assets.forEach(asset => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${asset.id}</td>
                <td>${asset.name}</td>
                <td>${asset.category}</td>
                <td>R$ ${parseFloat(asset.currentValue).toFixed(2)}</td>
                <td><span class="status ${asset.status}">${formatStatus(asset.status)}</span></td>
                <td>${asset.location ? asset.location.branch : 'N/A'}</td>
                <td>
                    ${asset.status === 'disponivel' ? 
                        `<button class="btn btn-primary" onclick="checkOut(${asset.id})">Check-out</button>` :
                        `<button class="btn btn-danger" onclick="checkIn(${asset.id})">Check-in</button>`
                    }
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Erro ao carregar ativos:', error);
        showError('assets-table', 'Erro ao carregar ativos');
    }
}

// Carregar usuários
async function loadUsers() {
    try {
        const response = await fetch(`${API_URL}/users`);
        const users = await response.json();
        
        const tbody = document.querySelector('#users-table tbody');
        tbody.innerHTML = '';
        
        users.forEach(user => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.department}</td>
                <td><span class="status ${user.active ? 'ativo' : 'inativo'}">${user.active ? 'Ativo' : 'Inativo'}</span></td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Erro ao carregar usuários:', error);
        showError('users-table', 'Erro ao carregar usuários');
    }
}

// Carregar localizações
async function loadLocations() {
    try {
        const response = await fetch(`${API_URL}/locations`);
        const locations = await response.json();
        
        const tbody = document.querySelector('#locations-table tbody');
        tbody.innerHTML = '';
        
        locations.forEach(location => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${location.id}</td>
                <td>${location.branch}</td>
                <td>${location.floor || 'N/A'}</td>
                <td>${location.room || 'N/A'}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Erro ao carregar localizações:', error);
        showError('locations-table', 'Erro ao carregar localizações');
    }
}

// Carregar histórico
async function loadHistory() {
    try {
        const response = await fetch(`${API_URL}/custody-history`);
        const history = await response.json();
        
        const tbody = document.querySelector('#history-table tbody');
        tbody.innerHTML = '';
        
        history.forEach(record => {
            const tr = document.createElement('tr');
            const isActive = !record.endDate;
            tr.innerHTML = `
                <td>${record.id}</td>
                <td>${record.asset.name}</td>
                <td>${record.user.name}</td>
                <td>${formatDate(record.startDate)}</td>
                <td>${record.endDate ? formatDate(record.endDate) : '-'}</td>
                <td><span class="status ${isActive ? 'ativa' : 'finalizada'}">${isActive ? 'Ativa' : 'Finalizada'}</span></td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Erro ao carregar histórico:', error);
        showError('history-table', 'Erro ao carregar histórico');
    }
}

// Check-out (atribuir ativo)
async function checkOut(assetId) {
    const userId = prompt('Digite o ID do usuário:');
    if (!userId) return;
    
    try {
        const response = await fetch(`${API_URL}/assets/${assetId}/checkout?userId=${userId}`, {
            method: 'POST'
        });
        
        if (response.ok) {
            alert('Check-out realizado com sucesso!');
            loadAssets();
        } else {
            alert('Erro ao realizar check-out');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao realizar check-out');
    }
}

// Check-in (devolver ativo)
async function checkIn(assetId) {
    try {
        const response = await fetch(`${API_URL}/assets/${assetId}/checkin`, {
            method: 'POST'
        });
        
        if (response.ok) {
            alert('Check-in realizado com sucesso!');
            loadAssets();
        } else {
            alert('Erro ao realizar check-in');
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro ao realizar check-in');
    }
}

// Funções auxiliares
function formatStatus(status) {
    const statusMap = {
        'disponivel': 'Disponível',
        'em_uso': 'Em Uso',
        'manutencao': 'Manutenção'
    };
    return statusMap[status] || status;
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-BR');
}

function showError(tableId, message) {
    const tbody = document.querySelector(`#${tableId} tbody`);
    tbody.innerHTML = `<tr><td colspan="7" class="error">${message}</td></tr>`;
}

// Carregar dados iniciais
document.addEventListener('DOMContentLoaded', () => {
    loadAssets();
});