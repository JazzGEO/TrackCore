INSERT INTO locations (branch, floor, room, created_at) VALUES
('Sede São Paulo', 'Térreo', 'Recepção', CURRENT_TIMESTAMP),
('Sede São Paulo', '1º Andar', 'TI', CURRENT_TIMESTAMP),
('Filial Rio de Janeiro', '2º Andar', 'Financeiro', CURRENT_TIMESTAMP);

INSERT INTO users (name, email, department, active, created_at) VALUES
('João Silva', 'joao.silva@trackcore.com', 'TI', true, CURRENT_TIMESTAMP),
('Maria Santos', 'maria.santos@trackcore.com', 'RH', true, CURRENT_TIMESTAMP),
('Pedro Costa', 'pedro.costa@trackcore.com', 'Financeiro', true, CURRENT_TIMESTAMP);

INSERT INTO assets (name, category, purchase_value, purchase_date, current_value, status, location_id, created_at, updated_at) VALUES
('Notebook Dell Inspiron 15', 'notebook', 3500.00, '2024-01-15', 3500.00, 'disponivel', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Monitor LG 27 polegadas', 'monitor', 1200.00, '2024-02-10', 1200.00, 'disponivel', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('iPhone 13 Pro', 'celular', 6500.00, '2023-11-20', 6500.00, 'disponivel', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Notebook MacBook Pro', 'notebook', 12000.00, '2024-03-05', 12000.00, 'disponivel', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);