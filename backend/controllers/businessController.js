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

// @desc    Get public profile of a business (no auth)
// @route   GET /api/businesses/:id/public
// @access  Public
exports.getBusinessPublic = async (req, res) => {
  try {
    const business = await Business.findById(req.params.id);
    if (!business) {
      return res.status(404).json({ message: 'Business not found' });
    }
    // Return only name, address, googleReviewUrl, and placeId
    res.json({
      name: business.name,
      address: business.address || '',
      googleReviewUrl: business.googleReviewUrl,
      placeId: business.placeId || ''
    });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Search Google Places for business profiles
// @route   GET /api/businesses/search-google
// @access  Private
exports.searchGooglePlaces = async (req, res) => {
  const { query } = req.query;

  if (!query) {
    return res.status(400).json({ message: 'Query is required' });
  }

  const apiKey = process.env.GOOGLE_MAPS_API_KEY;

  // Development Fallback: If API key is not present or is placeholder, return mock results
  if (!apiKey || apiKey === 'YOUR_GOOGLE_MAPS_API_KEY' || apiKey.includes('key')) {
    console.log('Using mock Google Places results (No GOOGLE_MAPS_API_KEY set)');
    const mockResults = [
      {
        name: `${query.charAt(0).toUpperCase() + query.slice(1)} Cafe`,
        formattedAddress: '123 Market St, San Francisco, CA 94103',
        placeId: 'ChIJyTMQ5lhGkFQR8h5s5wZyC-c', // Real place ID for Space Needle
        googleMapsUrl: 'https://maps.google.com/?cid=12345',
        googleReviewUrl: 'https://search.google.com/local/writereview?placeid=ChIJyTMQ5lhGkFQR8h5s5wZyC-c'
      },
      {
        name: `The Local ${query.charAt(0).toUpperCase() + query.slice(1)} Lounge`,
        formattedAddress: '456 Mission St, San Francisco, CA 94105',
        placeId: 'ChIJu46S-uB-hYGRwT3QyP6Wj2Y',
        googleMapsUrl: 'https://maps.google.com/?cid=67890',
        googleReviewUrl: 'https://search.google.com/local/writereview?placeid=ChIJu46S-uB-hYGRwT3QyP6Wj2Y'
      }
    ];
    return res.json(mockResults);
  }

  try {
    const url = `https://maps.googleapis.com/maps/api/place/textsearch/json?query=${encodeURIComponent(query)}&type=establishment&key=${apiKey}`;
    const response = await fetch(url);
    const data = await response.json();

    if (data.status !== 'OK' && data.status !== 'ZERO_RESULTS') {
      return res.status(500).json({ message: `Google API Error: ${data.status} - ${data.error_message || ''}` });
    }

    const results = (data.results || []).map(place => ({
      name: place.name,
      formattedAddress: place.formatted_address,
      placeId: place.place_id,
      googleMapsUrl: `https://www.google.com/maps/place/?q=place_id:${place.place_id}`,
      googleReviewUrl: `https://search.google.com/local/writereview?placeid=${place.place_id}`
    }));

    res.json(results);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};
