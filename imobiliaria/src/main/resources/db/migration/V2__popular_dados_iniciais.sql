-- Inserção de Clientes
INSERT INTO clientes (nome_cliente, cpf, telefone, email, dt_nascimento) VALUES
('Gabriel Davi', '12345678901', '98981234567', 'gabriel@email.com', '1998-05-15'),
('Maria Clara Souza', '23456789012', '98982345678', 'maria.clara@email.com', '1995-10-20'),
('Carlos Eduardo Lima', '34567890123', '98983456789', 'carlos.lima@email.com', '1988-03-12'),
('Ana Paula Ferreira', '45678901234', '98984567890', 'ana.ferreira@email.com', '2001-08-25'),
('Roberto Santos', '56789012345', '98985678901', 'roberto.santos@email.com', '1982-12-05');

-- Inserção de Imóveis (variados em tipo, endereço/cidade e dormitórios para paginação e filtros)
INSERT INTO imoveis (tipo_imovel, endereco, cep, dormitorios, banheiros, suites, metragem, valor_aluguel_sug, obs) VALUES
('Apartamento', 'Av. Litorânea, 1500, Calhau, São Luís - MA', '65076170', 3, 2, 1, 95, 3500.00, 'Vista para o mar'),
('Apartamento', 'Rua das Dálias, 45, Renascença, São Luís - MA', '65075630', 2, 2, 1, 70, 2200.00, 'Próximo ao shopping'),
('Casa', 'Rua dos Curiós, 12, Cohafuma, São Luís - MA', '65070000', 4, 3, 2, 180, 4000.00, 'Casa ampla com quintal e garagem'),
('Casa', 'Av. Babaçulândia, 800, Juçara, Imperatriz - MA', '65900000', 3, 2, 1, 120, 1800.00, 'Excelente localização comercial/residencial'),
('Kitnet', 'Rua do Sol, 320, Centro, São Luís - MA', '65020590', 1, 1, 0, 35, 800.00, 'Ideal para estudantes'),
('Apartamento', 'Av. Principal, 100, Araçagi, São José de Ribamar - MA', '65110000', 2, 1, 0, 55, 1200.00, 'Condomínio fechado com piscina'),
('Casa', 'Rua 15 de Novembro, 250, Três Poderes, Imperatriz - MA', '65903000', 3, 2, 1, 150, 2500.00, 'Perto de escolas e farmácias'),
('Sobrado', 'Rua das Palmeiras, 88, Olho d Água, São Luís - MA', '65065000', 4, 4, 3, 220, 5500.00, 'Piscina e área gourmet'),
('Sala Comercial', 'Av. Colares Moreira, 500, Jardim Renascença, São Luís - MA', '65075440', 1, 1, 0, 40, 1600.00, 'Sala para consultório ou escritório');

-- Inserção de uma Locação de exemplo (para testar validação de períodos e exclusão)
-- Imóvel 1 alugado para Gabriel Davi (Cliente 1) de 2026-11-01 a 2026-11-30
INSERT INTO locacao (id_imovel, id_inquilino, ativo, data_inicio, data_fim, dia_vencimento, perc_multa, valor_aluguel, obs) VALUES
(1, 1, 1, '2026-11-01', '2026-11-30', 10, 2.00, 3500.00, 'Locação de temporada confirmada');
