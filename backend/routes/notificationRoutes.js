const express = require('express');
const router = express.Router();
const { registerToken, sendTestNotification } = require('../controllers/notificationController');
const { protect } = require('../middleware/auth');

router.post('/register', protect, registerToken);
router.post('/test', protect, sendTestNotification);

module.exports = router;
