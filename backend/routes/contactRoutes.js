const express = require('express');
const router = express.Router();
const { createContact, importContacts, getContactsByBusiness } = require('../controllers/contactController');
const { protect } = require('../middleware/auth');

router.post('/', protect, createContact);
router.post('/import', protect, importContacts);
router.get('/business/:businessId', protect, getContactsByBusiness);

module.exports = router;
