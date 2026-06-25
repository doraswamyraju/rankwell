const { registerDeviceToken, sendPushNotification } = require('../utils/notifications');

// @desc    Register device token for push notifications
// @route   POST /api/notifications/register
// @access  Private
exports.registerToken = async (req, res) => {
  const { deviceToken, deviceType } = req.body;

  if (!deviceToken || !deviceType) {
    return res.status(400).json({ message: 'Device token and device type are required' });
  }

  try {
    await registerDeviceToken(req.user._id, deviceToken, deviceType);
    res.json({ message: 'Device token registered successfully' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Send a test push notification to the logged-in user
// @route   POST /api/notifications/test
// @access  Private
exports.sendTestNotification = async (req, res) => {
  const { title, body } = req.body;

  try {
    const result = await sendPushNotification(
      req.user._id,
      title || 'RankWell Test',
      body || 'This is a test notification from your server!',
      { type: 'test' }
    );
    res.json({ message: 'Notification dispatch completed', result });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};
