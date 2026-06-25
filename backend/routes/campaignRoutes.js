const express = require('express');
const router = express.Router();
const { createCampaign, getCampaignsByBusiness, deleteCampaign } = require('../controllers/campaignController');
const { protect } = require('../middleware/auth');

router.post('/', protect, createCampaign);
router.get('/business/:businessId', protect, getCampaignsByBusiness);
router.delete('/:id', protect, deleteCampaign);

module.exports = router;
