create schema android

create table android.Rodadas(
	numeroRodadas int primary key not null,
	qtaInimigos int not null
);

select * from android.Rodadas

create table android.Player(
	nome varchar(30) primary key not null,
	senha varchar(20) not null,
	apelido varchar(10) not null,
	maiorRodada int not null, 
	foreign key (maiorRodada) references android.Rodadas (numeroRodadas)
); 

select * from android.Player
select * from android.Rodadas