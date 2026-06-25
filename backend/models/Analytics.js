const mongoose = require('mongoose');

const AnalyticsSchema = new mongoose.Schema({
  business: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Business',
    required: true
  },
  campaign: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Campaign'
  },
  reviewModeSelected: {
    type: String,
    enum: ['Custom', 'Smart', 'Ultra Smart', 'None'],
    default: 'None'
  },
  copiedText: {
    type: Boolean,
    default: false
  },
  clickedGoogleLink: {
    type: Boolean,
    default: false
  },
  deviceType: String,
  browser: String,
  operatingSystem: String,
  location: {
    country: String,
    state: String,
    city: String
  },
  timestamp: {
    type: Date,
    default: Date.now
  }
});

module.exports = mongoose.model('Analytics', AnalyticsSchema);
