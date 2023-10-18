const player_database = {};
const playerRef = firebase.database().ref('/Players');

function exists_player(nickname) {
    let player_ref = firebase.database().ref('/Player/' + nickname);

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

async function getTimeStamp(nickname){
    if (nickname === "") {
        return { success: false, message: 'Invalid player nickname' };
    }

    try {
        const playerRef = firebase.database().ref('/Player/' + nickname);
        const snapshot = await playerRef.once('value');
        
        if (snapshot.exists()) {
            const playerData = snapshot.val();
            if (playerData && playerData.createdat) {
                console.log(playerData.createdat);
                return { success: true, timestamp: playerData.createdat };
            } else {
                return { success: false, message: 'Timestamp not found' };
            }
        } else {
            return { success: false, message: 'Player not found' };
        }
    } catch (error) {
        return { success: false, message: `Error getting timestamp: ${error.message}` };
    }
}

(function(){
    async function new_player(nickname, senha, email) {
        if (email.trim() == "" /*|| !email.include('@') || !email.include('.')*/) return { success: false, message: 'Invalid params' };
    
        const playerExists = await exists_player(nickname);
        if (playerExists) {
            return { success: false, message: 'Player already exists' };
        }
    
        const player_data = {
            email: email,
            senha: senha,
            nickname: nickname,
            maiorRodada: 0,
            createdat: firebase.database.ServerValue.TIMESTAMP,
        };
    
        let updates = {};
        updates['/Player/' + nickname] = player_data;
    
        let player_ref = firebase.database().ref();
    
        try {
            await player_ref.update(updates);
            return { success: true, message: 'Player created' };
        } catch (error) {
            return { success: false, message: `Creation failed: ${error.message}` };
        }
    }

    async function remove_player(nickname) {
        const playerExists = await exists_player(nickname);
        if(nickname.trim() == "" || !playerExists) return {success: false, message: `Remove failed: ${error.message}`};

        let player_ref = firebase.database().ref('/Player/' + nickname);

        player_ref.remove()
        .then(function(){
            return {success: true, message: 'Player removed'};
        })
        .catch(function(){
            return {success: false, message: `Remove failed: ${error.message}`};
        });
    }

    async function update_player(nickname, senha, email, maiorRodada) {
        const playerExists = await exists_player(nickname);
        if(nickname.trim() == "" || !playerExists) return {success: false, message: 'Invalid player'};

        let playerCreatedat = getTimeStamp(nickname);
        const player_data = {
            email: email,
            senha: senha,
            nickname: nickname,
            maiorRodada: maiorRodada,
            createdat: (await playerCreatedat).timestamp,
        };

        let updates = {};
        updates['/Player/' + nickname] = player_data;

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
        let playerRef = firebase.database().ref('/Player');
    
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

    async function getPlayer(nickname){
        if (nickname === "") {
        return Promise.resolve({ success: false, message: 'Invalid player nickname' });
    }

    let playerRef = firebase.database().ref('/Player/' + nickname);

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
    player_database.getTimeStamp = getTimeStamp;
})()