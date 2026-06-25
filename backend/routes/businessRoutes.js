const express = require('express');
const router = express.Router();
const { createBusiness, previewReviewLink, getBusinesses } = require('../controllers/businessController');
const { protect } = require('../middleware/auth');

router.route('/')
  .post(protect, createBusiness)
  .get(protect, getBusinesses);

router.post('/preview-link', protect, previewReviewLink);

module.exports = router;
