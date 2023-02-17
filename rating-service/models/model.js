const mongoose = require('mongoose');

const ratingSchema = new mongoose.Schema({
    restaurantId: {
        required: true,
        type: String
    },
    rating: {
        required: true,
        type: Number
    }
})

module.exports = mongoose.model('Rating', ratingSchema)