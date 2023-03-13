const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const cors = require('cors');
const routes = require('./routes/routes');
const port = process.env.APP_PORT ?? '9083';

const mongoString = process.env.DB  ?? "mongodb://127.0.0.1/rating";

mongoose.connect(mongoString);
const database = mongoose.connection;


database.on('error', (error) => {
    console.log(error)
})

database.once('connected', () => {
    console.log('okay...')
    console.log('Database Connected');
})


const app = express();
app.use(cors());
app.use(bodyParser.json());

app.use('/v1/rating', routes)

app.listen(port, () => console.log(`Listening on port ${port}!`));