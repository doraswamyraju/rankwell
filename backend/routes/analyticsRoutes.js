const express = require('express');
const router = express.Router();
const { logEvent, getBusinessMetrics } = require('../controllers/analyticsController');
const { protect } = require('../middleware/auth');

router.post('/log', logEvent);
router.get('/business/:businessId', protect, getBusinessMetrics);

module.exports = router;
