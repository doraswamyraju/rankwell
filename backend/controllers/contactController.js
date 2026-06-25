const Contact = require('../models/Contact');
const Business = require('../models/Business');

// @desc    Add a single contact
// @route   POST /api/contacts
// @access  Private
exports.createContact = async (req, res) => {
  const { name, phone, email, tags, businessId, preferredService, notes } = req.body;

  if (!name || !businessId) {
    return res.status(400).json({ message: 'Name and Business ID are required' });
  }

  try {
    const business = await Business.findOne({ _id: businessId, owner: req.user._id });
    if (!business) {
      return res.status(404).json({ message: 'Business not found or unauthorized' });
    }

    const contact = await Contact.create({
      name,
      phone: phone || '',
      email: email || '',
      tags: tags || [],
      business: businessId,
      preferredService: preferredService || '',
      notes: notes || ''
    });

    res.status(201).json(contact);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Batch import contacts (CSV parser output)
// @route   POST /api/contacts/import
// @access  Private
exports.importContacts = async (req, res) => {
  const { contacts, businessId } = req.body; // Array of contact objects

  if (!contacts || !Array.isArray(contacts) || !businessId) {
    return res.status(400).json({ message: 'Contacts list and Business ID are required' });
  }

  try {
    const business = await Business.findOne({ _id: businessId, owner: req.user._id });
    if (!business) {
      return res.status(404).json({ message: 'Business not found or unauthorized' });
    }

    // Map each contact to contain the business ID
    const formattedContacts = contacts.map(c => ({
      name: c.name,
      phone: c.phone || '',
      email: c.email || '',
      tags: c.tags || [],
      business: businessId,
      preferredService: c.preferredService || '',
      notes: c.notes || ''
    }));

    const imported = await Contact.insertMany(formattedContacts);
    res.json({ message: `Successfully imported ${imported.length} contacts`, count: imported.length });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Get all contacts for a business
// @route   GET /api/contacts/business/:businessId
// @access  Private
exports.getContactsByBusiness = async (req, res) => {
  const { businessId } = req.params;

  try {
    const business = await Business.findOne({ _id: businessId, owner: req.user._id });
    if (!business) {
      return res.status(404).json({ message: 'Business not found or unauthorized' });
    }

    const contacts = await Contact.find({ business: businessId });
    res.json(contacts);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};
