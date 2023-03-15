const express = require('express');
const router = express.Router();



router.get('/error/random', async (req, res) => {
    let random = getRandomInt(10);
    if(random <= 8){
        res.status(401).json({ message: "dummy error" })
    }else{
        res.json({
            "message" : "Hello there!!"
        })
    }
});


function getRandomInt(max) {
  return Math.floor(Math.random() * max);
}

module.exports = router;