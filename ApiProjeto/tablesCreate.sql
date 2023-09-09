create table Rodadas(
	numeroRodadas int primary key not null,
	qtaInimigos int not null
);

create table Player(
	nome varchar(30) primary key not null,
	senha varchar(20) not null,
	apelido varchar(10) not null,
	maiorRodada int not null, 
	foreign key (maiorRodada) references android.Rodadas (numeroRodadas)
); 

select * from Rodadas
select * from Player

/*100 waves insert: */
declare @i
set @i = 1;
declare @value
set @value = 10

while @i <= 100
begin
	if @i % 2 != 0
	begin
		@value += 3;
		insert into Rodadas values (@i, @value);
	end;
	else
		begin
		@value += 2;
		insert into Rodadas values (@i, @value);
	end;

	@i += 1;
end;


