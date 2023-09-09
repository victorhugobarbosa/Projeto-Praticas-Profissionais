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
declare @i integer;
set @i = 1;
declare @value integer;
set @value = 10;

while @i <= 100
begin
	if @i % 2 != 0
	begin
		set @value += 3;
		insert into praticas3.Rodadas values (@i, @value);
	end;
	else
		begin
		set @value += 2;
		insert into praticas3.Rodadas values (@i, @value);
	end;

	set @i += 1;
end;


