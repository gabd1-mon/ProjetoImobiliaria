create table clientes (
                          id integer not null auto_increment,
                          nome_cliente varchar(255) not null,
                          cpf varchar(11) not null ,
                          telefone varchar(12) not null,
                          email varchar(100),
                          dt_nascimento date,
                          primary key (id)
);

create table imoveis(
                        id integer not null auto_increment,
                        tipo_imovel varchar(50),
                        endereco varchar(255),
                        cep varchar(10),
                        dormitorios integer,
                        banheiros integer,
                        suites integer,
                        metragem integer,
                        valor_aluguel_sug decimal,
                        obs text,
                        primary key(id)
);

create table locacao(
                        id integer not null auto_increment,
                        id_imovel integer not null,
                        id_inquilino integer not null,
                        ativo bit,
                        data_fim date,
                        data_inicio date,
                        dia_vencimento int,
                        perc_multa decimal,
                        valor_aluguel decimal,
                        obs text,
                        primary key (id),
                        constraint fk_locacao_cliente foreign key (id_inquilino) references clientes(id),
                        constraint fk_locacao_imoveis foreign key (id_imovel) references imoveis(id)
);

create table alugueis(
                         id integer not null auto_increment,
                         id_locacao integer,
                         dt_vencimento date,
                         valor_pago decimal,
                         obs text,
                         primary key (id),
                         constraint fk_aluguel_locacao foreign key (id_locacao) references locacao(id)
);