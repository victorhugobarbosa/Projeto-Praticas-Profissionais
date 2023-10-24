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
const rodadas = collection(db, 'Rodadas');

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
    const rodadaSnapshot = await getDocs(query(collection(db, 'Rodadas'), where('id', '==', id)));
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
    console.log('ligou...');
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
const addPlayer = async (nickname, email, senha) => {
    await setDoc(doc(usersCol, nickname), {
        email: email,
        maiorRodada: 0,
        senha: senha
    });
}

app.post('/players', async (req, res) => {
    const nickname = req.body.nickname;
    const email = req.body.email;
    const senha = req.body.senha;

    if (nickname && email && senha) {
        existsEmail(email).then(exists => {
            if (exists) {
                res.status(409).send('[409 Conflict]:Player already exists');
            } else {
                addUser(nickname, email, senha).then(() => {
                    res.send({response: '201 Created'});
                });
            }
        });
    } else {
        res.status(400).send('400 Bad Request');
    }
});

app.post('/rodadas', async (req, res) => {
    const newRodada = req.body;

    if (!validateRodada(newRodada)) {
        handleResponse(res, 400, 'Invalid rodada data');
        return;
    }

    if (await existisRodada(newRodada.id)) {
        handleResponse(res, 400, 'Rodada with the same ID already exists');
        return;
    }

    try {
        const rodadaRef = doc(rodadas, newRodada.id);
        await setDoc(rodadaRef, newRodada);
        handleResponse(res, 201, 'Rodada created successfully');
    } catch (error) {
        console.error('Error creating rodada: ', error);
        handleResponse(res, 500, 'Internal Server Error');
    }
});

/*---------------- PUT ------------------*/
app.put('/players/:id', async (req, res) => {
    const id = req.params.id;
    const updatedPlayer = req.body;

    if (!validatePlayer(updatedPlayer)) {
        handleResponse(res, 400, 'Invalid player data');
        return;
    }

    if (!(await existsPlayer(id))) {
        handleResponse(res, 404, 'Player not found');
        return;
    }

    try {
        const playerRef = doc(players, id);
        await setDoc(playerRef, updatedPlayer, { merge: true });
        handleResponse(res, 200, 'Player updated successfully');
    } catch (error) {
        console.error('Error updating player: ', error);
        handleResponse(res, 500, 'Internal Server Error');
    }
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
app.delete('/players/:id', async (req, res) => {
    const id = req.params.id;

    if (!(await existsPlayer(id))) {
        handleResponse(res, 404, 'Player not found');
        return;
    }

    try {
        const playerRef = doc(players, id);
        await deleteDoc(playerRef);
        handleResponse(res, 200, 'Player deleted successfully');
    } catch (error) {
        console.error('Error deleting player: ', error);
        handleResponse(res, 500, 'Internal Server Error');
    }
});
