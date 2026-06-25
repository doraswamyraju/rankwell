const express = require('express');
const router = express.Router();
const { createCheckoutSession, handleStripeWebhook, validateCoupon } = require('../controllers/paymentController');
const { protect } = require('../middleware/auth');

router.post('/create-checkout', protect, createCheckoutSession);
router.post('/webhook', express.raw({ type: 'application/json' }), handleStripeWebhook);
router.post('/validate-coupon', protect, validateCoupon);

module.exports = router;
