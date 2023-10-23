const express = require('express')
const porta = 3000;
const app = express();

app.get("/", (req, res) =>{
    res.send("ta funfando")
})

app.listen(porta, () => {
    console.log('abriu');
})

app.get("/test", (req, res) =>{
    res.send({mlk: 'jota', idade:14});
})