const express = require('express');
const router = express.Router();
const { createBusiness, previewReviewLink, getBusinesses, getBusinessPublic, searchGooglePlaces } = require('../controllers/businessController');
const { protect } = require('../middleware/auth');

router.route('/')
  .post(protect, createBusiness)
  .get(protect, getBusinesses);

router.get('/search-google', protect, searchGooglePlaces);
router.get('/:id/public', getBusinessPublic);

router.post('/preview-link', protect, previewReviewLink);

module.exports = router;
