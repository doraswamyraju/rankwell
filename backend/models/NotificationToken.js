const mongoose = require('mongoose');

const NotificationTokenSchema = new mongoose.Schema({
  user: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  deviceToken: {
    type: String,
    required: true,
    unique: true
  },
  deviceType: {
    type: String,
    enum: ['ios', 'android'],
    required: true
  },
  updatedAt: {
    type: Date,
    default: Date.now
  }
});

module.exports = mongoose.model('NotificationToken', NotificationTokenSchema);
