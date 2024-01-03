create schema if not exists desafio_sicredi;


create table if not exists desafio_sicredi.associado (
    id SERIAL primary key,
    cpf VARCHAR(11) not null unique,
    nome VARCHAR(200) not null,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

create table if not exists desafio_sicredi.pauta (
    id SERIAL primary key,
    titulo VARCHAR(100) not null,
    descricao VARCHAR(255) not null,
    resultado VARCHAR(3),
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

create table if not exists desafio_sicredi.sessao (
    id SERIAL primary key,
    id_pauta INTEGER not null,
    duracao INTEGER not null,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint fk_pauta foreign key(id_pauta) references desafio_sicredi.pauta(id)
);

create table if not exists desafio_sicredi.votos (
    id SERIAL primary key,
    id_sessao INTEGER not null,
    id_associado INTEGER not null,
    voto BOOLEAN NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint fk_sessao foreign key(id_sessao) references desafio_sicredi.sessao(id),
	constraint fk_associado foreign key(id_associado) references desafio_sicredi.associado(id)
);
