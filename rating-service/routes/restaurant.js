const express = require('express');
const Model = require('../models/model');
const router = express.Router();



router.get('/restaurant/:restaurantId', async (req, res) => {

	try {
        const data = await Model.find({restaurantId : req.params.restaurantId});
        if(data.length > 0){
        	res.json(data[0])
    	} else {
    		res.status(500).json({ message: "No rating found" })
    	}
    }
    catch (error) {
        res.status(500).json({ message: error.message })
    }
 /*
  const restaurantId = req.params.restaurantId;
  console.log(`get the call for restaurantId : ${restaurantId}`);
  
  res.send({"rating" : 4});
  */
});



router.post('/restaurant/', async (req, res) => {

	const data = new Model({
	    restaurantId: req.body.restaurantId,
	    rating: req.body.rating
	})

	try {
        const dataToSave = await data.save();
        res.status(200).json(dataToSave)
    }
    catch (error) {
        res.status(400).json({ message: error.message })
    }
  
});



module.exports = router;