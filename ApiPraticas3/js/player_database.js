const player_database = {};

function exists_player(nome) {
    const player_ref = firebase.database().ref('/Player/' + nome);

    return player_ref.once('value')
        .then(function(snapshot) {
            if (snapshot.exists()) {
                return true;
            } else {

                return false;
            }
        })
        .catch(function(error) {
            return { success: false, message: `Check failed: ${error.message}` };
        });
}

(function(){
    async function new_player(nome, senha, apelido) {
        if (nome.trim() == "" || apelido.trim() == "") return { success: false, message: 'Invalid params' };
    
        const playerExists = await exists_player(nome);
        if (playerExists) {
            return { success: false, message: 'Player already exists' };
        }
    
        const player_data = {
            nome: nome,
            senha: senha,
            apelido: apelido,
            maiorRodada: 0,
            createdat: firebase.database.ServerValue.TIMESTAMP,
        };
    
        let updates = {};
        updates['/Player/' + nome] = player_data;
    
        let player_ref = firebase.database().ref();
    
        try {
            await player_ref.update(updates);
            return { success: true, message: 'Player created' };
        } catch (error) {
            return { success: false, message: `Creation failed: ${error.message}` };
        }
    }

    async function remove_player(nome) {
        const playerExists = await exists_player(nome);
        if(nome.trim() == "" || !playerExists) return {success: false, message: 'Invalid player'};

        let player_ref = firebase.database().ref('/Player/' + nome);

        player_ref.remove()
        .then(function(){
            return {success: true, message: 'Player removed'};
        })
        .catch(function(){
            return {success: false, message: `Remove failed: ${error.message}`};
        });
    }

    async function update_player(nome, senha, apelido, maiorRodada) {
        const playerExists = await exists_player(nome);
        if(nome.trim() == "" || !playerExists) return {success: false, message: 'Invalid player'};

        const player_data = {
            nome: nome,
            senha: senha,
            apelido: apelido,
            maiorRodada: maiorRodada,
            createdat: firebase.database.ServerValue.TIMESTAMP,
        };

        let updates = {};
        updates['/Player/' + nome] = player_data;

        let player_ref = firebase.database().ref();

        player_ref.update(updates)
        .then(function(){
            return {success: true, message: 'Player updated'};
        })
        .catch(function(){
            return {success: false, message: `Update failed: ${error.message}`};
        });
    }

    async function getAll_players(){
        const playerRef = firebase.database().ref('/Player');
    
        return playerRef.once('value')
            .then(function(snapshot) {
                const playersData = snapshot.val();
                console.log(playersData);
                return {success:true, data: playersData};
            })
            .catch(function(error) {
                console.log(`Error getting players: ${error.message}`);
                return false;
            });
    }

    async function getPlayer(nome){
        if (nome === "") {
        return Promise.resolve({ success: false, message: 'Invalid player name' });
    }

    const playerRef = firebase.database().ref('/Player/' + nome);

    return playerRef.once('value')
        .then(function(snapshot) {
            if (snapshot.exists()) {
                const playersData = snapshot.val();
                console.log(playersData);
                return {success:true, data: playersData};
            } else {
                console.log(`Error getting players: ${error.message}`);
                return false;
            }
        })
        .catch(function(error) {
            return { success: false, message: `Error getting player: ${error.message}` };
        });
    }

    player_database.new = new_player;
    player_database.remove = remove_player;
    player_database.update = update_player;
    player_database.getAll = getAll_players;
    player_database.getPlayer = getPlayer; 
})()