const Business = require('../models/Business');
const { extractPlaceId, generateReviewUrl } = require('../utils/googleMapsParser');

// @desc    Onboard/create a new business and resolve Google Review link
// @route   POST /api/businesses
// @access  Private
exports.createBusiness = async (req, res) => {
  const { name, address, googleMapsUrl } = req.body;

  if (!name || !googleMapsUrl) {
    return res.status(400).json({ message: 'Name and Google Maps URL are required' });
  }

  try {
    const placeId = await extractPlaceId(googleMapsUrl);
    const googleReviewUrl = generateReviewUrl(placeId);

    const business = await Business.create({
      name,
      address: address || '',
      googleMapsUrl,
      googleReviewUrl,
      placeId: placeId || '',
      owner: req.user._id
    });

    res.status(201).json(business);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Preview Google Review URL from a Maps link (no save)
// @route   POST /api/businesses/preview-link
// @access  Private
exports.previewReviewLink = async (req, res) => {
  const { googleMapsUrl } = req.body;

  if (!googleMapsUrl) {
    return res.status(400).json({ message: 'Google Maps URL is required' });
  }

  try {
    const placeId = await extractPlaceId(googleMapsUrl);
    const googleReviewUrl = generateReviewUrl(placeId);

    res.json({
      googleMapsUrl,
      placeId: placeId || 'not found',
      googleReviewUrl: googleReviewUrl || 'could not generate'
    });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Get all businesses for the current user
// @route   GET /api/businesses
// @access  Private
exports.getBusinesses = async (req, res) => {
  try {
    const businesses = await Business.find({ owner: req.user._id });
    res.json(businesses);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};
