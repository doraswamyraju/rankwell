const express = require('express');
const router = express.Router();
const { generateSmartDraft, generateUltraDraft } = require('../controllers/aiController');

router.post('/smart-draft', generateSmartDraft);
router.post('/ultra-draft', generateUltraDraft);

module.exports = router;
