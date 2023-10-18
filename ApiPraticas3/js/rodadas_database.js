const rodadas_database = {};

function exists_rodadas(number) {
    if(number <= 0) return false;

    const rodadas_ref = firebase.database().ref('/Rodadas/' + number);

    return rodadas_ref.once('value')
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
    async function new_rodada(number, qtaInimigos) {
        if (qtaInimigos <= 0) return { success: false, message: 'Invalid number of enimies' };
    
        const rodadaExists = await exists_rodadas(number);
        if (rodadaExists) {
            return { success: false, message: 'Rodada already exists' };
        }
    
        const rodada_data = {
            rodada: number,
            qtaInimigos: qtaInimigos,
        };
    
        let updates = {};
        updates['/Rodadas/' + number] = rodada_data;
    
        let rodada_ref = firebase.database().ref();
    
        try {
            await rodada_ref.update(updates);
            return { success: true, message: 'Rodada created' };
        } catch (error) {
            return { success: false, message: `Creation failed: ${error.message}` };
        }
    }

    async function update_rodada(number, qtaInimigos) {
        const rodadaExists = await exists_rodadas(number);
        if(qtaInimigos <= 0 || !rodadaExists) return {success: false, message: 'Invalid rodada'};

        const rodada_data = {
            rodada: number,
            qtaInimigos: qtaInimigos,
        };
    
        let updates = {};
        updates['/Rodadas/' + number] = rodada_data;
    
        let rodada_ref = firebase.database().ref();
    
        try {
            await rodada_ref.update(updates);
            return { success: true, message: 'Rodada created' };
        } catch (error) {
            return { success: false, message: `Creation failed: ${error.message}` };
        }
    }

    async function getAll_rodadas(){
        const rodadaRef = firebase.database().ref('/Rodadas');
    
        return rodadaRef.once('value')
            .then(function(snapshot) {
                const rodadaData = snapshot.val();
                console.log(rodadaData);
                return true;
            })
            .catch(function(error) {
                console.log(`Error getting rodadas: ${error.message}`);
                return false;
            });
    }

    async function getRodada(number){
        if (number < 1) {
        return Promise.resolve({ success: false, message: 'Invalid rodada number'});
    }

    const rodadaRef = firebase.database().ref('/Rodadas/' + number);

    return rodadaRef.once('value')
        .then(function(snapshot) {
            if (snapshot.exists()) {
                const rodadasData = snapshot.val();
                console.log(rodadasData);
                return {success:true, data: rodadasData};
            } else {
                console.log(`Error getting rodada: ${error.message}`);
                return false;
            }
        })
        .catch(function(error) {
            return { success: false, message: `Error getting rodada: ${error.message}` };
        });
    }

    rodadas_database.new = new_rodada;
    rodadas_database.update = update_rodada;
    rodadas_database.getAll = getAll_rodadas;
    rodadas_database.getRodada = getRodada; 
})()