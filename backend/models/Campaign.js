const mongoose = require('mongoose');

const CampaignSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
    trim: true
  },
  type: {
    type: String,
    enum: ['QR', 'SMS', 'WhatsApp', 'Email', 'Invoice QR', 'Receipt QR', 'Table QR', 'Poster QR', 'Website Widget', 'NFC Card'],
    default: 'QR'
  },
  business: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Business',
    required: true
  },
  qrDesign: {
    foregroundColor: { type: String, default: '#000000' },
    backgroundColor: { type: String, default: '#FFFFFF' },
    logoUrl: { type: String },
    frameType: { type: String, default: 'none' },
    ctaText: { type: String }
  },
  createdAt: {
    type: Date,
    default: Date.now
  }
});

module.exports = mongoose.model('Campaign', CampaignSchema);
