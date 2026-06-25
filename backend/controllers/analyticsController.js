const Analytics = require('../models/Analytics');

// @desc    Log a new conversion/funnel activity event
// @route   POST /api/analytics/log
// @access  Public
exports.logEvent = async (req, res) => {
  const { businessId, campaignId, reviewModeSelected, copiedText, clickedGoogleLink, deviceType, browser, operatingSystem, location } = req.body;

  if (!businessId) {
    return res.status(400).json({ message: 'Business ID is required' });
  }

  try {
    const event = await Analytics.create({
      business: businessId,
      campaign: campaignId || null,
      reviewModeSelected: reviewModeSelected || 'None',
      copiedText: copiedText || false,
      clickedGoogleLink: clickedGoogleLink || false,
      deviceType: deviceType || 'Unknown',
      browser: browser || 'Unknown',
      operatingSystem: operatingSystem || 'Unknown',
      location: location || {}
    });

    res.status(201).json(event);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Get aggregated metrics for a business
// @route   GET /api/analytics/business/:businessId
// @access  Private
exports.getBusinessMetrics = async (req, res) => {
  const { businessId } = req.params;

  try {
    // 1. Calculate overall scans count
    const totalScans = await Analytics.countDocuments({ business: businessId });

    // 2. Aggregate counts by review mode selected
    const modeAggregation = await Analytics.aggregate([
      { $match: { business: new require('mongoose').Types.ObjectId(businessId) } },
      { $group: { _id: '$reviewModeSelected', count: { $sum: 1 } } }
    ]);

    // 3. Aggregate Google Link click conversions
    const conversionsCount = await Analytics.countDocuments({
      business: businessId,
      clickedGoogleLink: true
    });

    // Format mode metrics
    const reviewModes = { Custom: 0, Smart: 0, UltraSmart: 0, None: 0 };
    modeAggregation.forEach(item => {
      const modeKey = item._id === 'Ultra Smart' ? 'UltraSmart' : item._id;
      if (reviewModes.hasOwnProperty(modeKey)) {
        reviewModes[modeKey] = item.count;
      }
    });

    res.json({
      totalScans,
      conversionsCount,
      conversionRate: totalScans > 0 ? ((conversionsCount / totalScans) * 100).toFixed(1) : 0,
      reviewModes
    });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};
