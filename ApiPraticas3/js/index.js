import express from 'express';
import { initializeApp } from 'firebase/app';
import { collection, doc, getDoc, getDocs, getFirestore, setDoc, deleteDoc, query, where } from 'firebase/firestore/lite';

const porta = 3000;
const app = express();

const firebaseConfig = {
    apiKey: "AIzaSyDOD29wdZR_sizdl61KASPgHckL9vDpOkA",
    authDomain: "api-praticas3.firebaseapp.com",
    projectId: "api-praticas3",
    storageBucket: "api-praticas3.appspot.com",
    messagingSenderId: "20720358920",
    appId: "1:20720358920:web:18deaa42698ee5982f63bd",
    measurementId: "G-6LLERQTR8X"
}; 

const firebase = initializeApp(firebaseConfig);
const db = getFirestore(firebase);

const players = collection(db, 'Player');
const rodadas = collection(db, 'Rodada');

const validatePlayer = (player) => {
    try {
        if (!player.id || !player.email || !player.senha) {
            return false;
        }
        return player.id.length >= 4 && player.id.length <= 15 &&
            player.email.length >= 5 && player.email.length <= 30 && player.email.includes('@') && player.email.includes('.') &&
            player.senha.length >= 8 && player.senha.length <= 30;
    } catch (error) {
        console.error('Error validating player: ', error);
        return false;
    }
};
const validateRodada = (rodada) => {
    try {
        if (!rodada.descanso || !rodada.descricao || !rodada.nome || !rodada.quant || !rodada.treino || !rodada.id) {
            return false;
        }
        return rodada.id >= 0 &&
            rodada.enimes >= 13 && rodada.descricao.length <= 300;
    } catch (error) {
        console.error('Error validating rodada: ', error);
        return false;
    }
};

const existsPlayer = async (id) => {
    try {
        const data = await getDocs(players);
        return data.docs.some((doc) => doc.data().id === id);
    } catch (error) {
        console.error('Error checking if player exists: ', error);
        return false;
    }
};
const existsEmail = async (email) => {
    try {
        const data = await getDocs(players);
        return data.docs.some((doc) => doc.data().email === email);
    } catch (error) {
        console.error('Error checking if player email exists: ', error);
        return false;
    }
};
const existisRodada = async (id) => {
    const rodadaSnapshot = await getDocs(query(collection(db, 'Rodada'), where('id', '==', id)));
    return !rodadaSnapshot.empty;
};

const handleResponse = (res, status, message) => {
    res.status(status).send(message);
};

app.use(express.json());

app.get("/", (req, res) =>{
    res.send("[API]: On")
})

app.listen(porta, () => {
    console.log('Ligou na porta '+porta);
})

/*---------------- GET ------------------*/
app.get('/players/', async (req, res) => {
    try {
        const playersSnapshot = await getDocs(players);
        const playersList = [];

        playersSnapshot.forEach((playerDoc) => {
            playersList.push(playerDoc.data());
        });

        res.status(200).send(playersList);
    } catch (error) {
        console.error('Error retrieving players: ', error);
        handleResponse(res, 500, 'Internal Server Error');
    }
});

app.get('/players/:id', async (req, res) => {
    const id = req.params.id;
    try {
        const playerRef = doc(players, id); // Get the specific player document by ID
        const playerDoc = await getDoc(playerRef);

        if (playerDoc.exists()) {
            res.status(200).send(playerDoc.data());
        } else {
            handleResponse(res, 404, 'Player does not exist');
        }
    } catch (error) {
        console.error('Error retrieving player: ', error);
        handleResponse(res, 500, 'Internal Server Error');
    }
});

app.get('/rodadas/', async (req, res) => {
    try {
        const rodadasSnapshot = await getDocs(rodadas);
        const rodadasList = [];

        rodadasSnapshot.forEach((rodadaDoc) => {
            rodadasList.push(rodadaDoc.data());
        });

        res.status(200).send(rodadasList);
    } catch (error) {
        console.error('Error retrieving players: ', error);
        handleResponse(res, 500, 'Internal Server Error');
    }
});

app.get('/rodadas/:id', async (req, res) => {
    const id = req.params.id;
    try {
        const rodadasRef = doc(rodadas, id); // Get the specific player document by ID
        const rodadasDoc = await getDoc(rodadasRef);

        if (rodadasDoc.exists()) {
            res.status(200).send(rodadasDoc.data());
        } else {
            handleResponse(res, 404, 'Rodada does not exist');
        }
    } catch (error) {
        console.error('Error retrieving rodada: ', error);
        handleResponse(res, 500, 'Internal Server Error');
    }
});

/*---------------- POST ------------------*/
const addPlayer = async (nickname, email, senha, maiorRodada) => {
    await setDoc(doc(players, nickname), {
        email: email,
        maiorRodada: maiorRodada,
        nickname: nickname,
        senha: senha
    });
}
const addRodada = async (id, enemies) => {
    await setDoc(doc(rodadas, id), {
        enemies: enemies,
        id: id
    });
}

app.post('/players', async (req, res) => {
    const email = req.body.email;
    const nickname = req.body.nickname;
    const senha = req.body.senha;

    if (email && nickname && senha) {
        existsEmail(email).then(exists => {
            if (exists) {
                res.status(409).send('409 Conflict');
            } else {
                addPlayer(nickname, email, senha, 1).then(() => {
                    res.send({response: '201 Created'});
                });
            }
        });
    } else {
        res.status(400).send('400 Bad Request');
    }
});

app.post('/rodadas', async (req, res) => {
    const id = req.body.id;
    const enemies = req.body.enemies;

    console.log(req.body);

    if (id && enemies) {
        existisRodada(id).then(exists => {
            if (exists) {
                res.status(409).send('409 Conflict');
            } else {
                addRodada(id, enemies).then(() => {
                    res.send({response: '201 Created'});
                });
            }
        });
    } else {
        res.status(400).send('400 Bad Request');
    }
});

/*---------------- PUT ------------------*/
const getPlayers = async () => {
    const data = await getDocs(players);
    const playersArray = [];
    data.docs.forEach(doc => {
        playersArray.push(doc.data());
    });
    return playersArray;
}

app.put('/players/:nickname', async (req, res) => {
    const email = req.body.email;
    const nickname = req.body.nickname;
    const senha = req.body.senha;
    const maiorRodada = req.body.maiorRodada;

    getPlayers().then(players => {
        const player = players.find(player => player.email === email);
        if (player) {
            if (email && nickname && senha) {
                existsEmail(email).then(exists => {
                    if (exists) {
                        addPlayer(nickname, email, senha, maiorRodada).then(() => {
                            res.send({response: '200 OK'});
                        });
                    } else {
                        res.status(404).send('404 Not Found');
                    }
                });
            } else {
                res.status(400).send('400 Bad Request');
            }
        } else {
            res.status(404).send('404 Not Found');
        }
    });
});

app.put('/rodadas/:id', async (req, res) => {
    const id = req.params.id;
    const updatedRodada = req.body;

    if (!validateRodada(updatedRodada)) {
        handleResponse(res, 400, 'Invalid rodada data');
        return;
    }

    if (!(await existisRodada(id))) {
        handleResponse(res, 404, 'Rodada not found');
        return;
    }

    try {
        const rodadaRef = doc(rodadas, id);
        await setDoc(rodadaRef, updatedRodada, { merge: true });
        handleResponse(res, 200, 'Rodada updated successfully');
    } catch (error) {
        console.error('Error updating rodada: ', error);
        handleResponse(res, 500, 'Internal Server Error');
    }
});

/*---------------- DELETE ------------------*/
app.delete('/players/:nickname', async (req, res) => {
    const nickname = req.params.nickname;

    if (!(await existsPlayer(nickname))) {
        handleResponse(res, 404, 'Player not found');
        return;
    }

    try {
        const playerRef = doc(players, nickname);
        await deleteDoc(playerRef);
        handleResponse(res, 200, 'Player deleted successfully');
    } catch (error) {
        console.error('Error deleting player: ', error);
        handleResponse(res, 500, 'Internal Server Error');
    }
});
