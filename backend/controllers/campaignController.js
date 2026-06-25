const Campaign = require('../models/Campaign');
const Business = require('../models/Business');

// @desc    Create a new campaign
// @route   POST /api/campaigns
// @access  Private
exports.createCampaign = async (req, res) => {
  const { name, type, businessId, qrDesign } = req.body;

  if (!name || !businessId) {
    return res.status(400).json({ message: 'Name and Business ID are required' });
  }

  try {
    // Check if the business exists and is owned by the current user
    const business = await Business.findOne({ _id: businessId, owner: req.user._id });
    if (!business) {
      return res.status(404).json({ message: 'Business not found or unauthorized' });
    }

    const campaign = await Campaign.create({
      name,
      type: type || 'QR',
      business: businessId,
      qrDesign: qrDesign || {}
    });

    res.status(201).json(campaign);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Get all campaigns for a specific business
// @route   GET /api/campaigns/business/:businessId
// @access  Private
exports.getCampaignsByBusiness = async (req, res) => {
  const { businessId } = req.params;

  try {
    const business = await Business.findOne({ _id: businessId, owner: req.user._id });
    if (!business) {
      return res.status(404).json({ message: 'Business not found or unauthorized' });
    }

    const campaigns = await Campaign.find({ business: businessId });
    res.json(campaigns);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Delete/Archive a campaign
// @route   DELETE /api/campaigns/:id
// @access  Private
exports.deleteCampaign = async (req, res) => {
  const { id } = req.params;

  try {
    const campaign = await Campaign.findById(id).populate('business');
    if (!campaign) {
      return res.status(404).json({ message: 'Campaign not found' });
    }

    // Verify ownership of the associated business
    if (campaign.business.owner.toString() !== req.user._id.toString()) {
      return res.status(403).json({ message: 'Unauthorized' });
    }

    await Campaign.findByIdAndDelete(id);
    res.json({ message: 'Campaign removed successfully' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};
