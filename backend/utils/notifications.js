const { getFirestore, admin } = require('../config/firebase');
const NotificationToken = require('../models/NotificationToken');

/**
 * Register a device token for push notifications.
 * Attempts to save to Firestore first, fallback to MongoDB.
 */
const registerDeviceToken = async (userId, deviceToken, deviceType) => {
  try {
    // 1. Try Firestore
    const firestore = getFirestore();
    if (firestore) {
      await firestore.collection('notification_tokens').doc(deviceToken).set({
        userId: userId.toString(),
        deviceType,
        updatedAt: admin.firestore.FieldValue.serverTimestamp()
      });
    }
  } catch (error) {
    console.error('Failed to save notification token to Firestore:', error.message);
  }

  // 2. Always persist in MongoDB as a local fallback/replica
  return await NotificationToken.findOneAndUpdate(
    { deviceToken },
    { user: userId, deviceType, updatedAt: Date.now() },
    { upsert: true, new: true }
  );
};

/**
 * Send push notification to a user's registered devices.
 */
const sendPushNotification = async (userId, title, body, data = {}) => {
  const tokens = [];

  // 1. Try querying from Firestore
  try {
    const firestore = getFirestore();
    if (firestore) {
      const snapshot = await firestore.collection('notification_tokens')
        .where('userId', '==', userId.toString())
        .get();
      snapshot.forEach(doc => {
        tokens.push(doc.id);
      });
    }
  } catch (error) {
    console.error('Failed to read notification tokens from Firestore:', error.message);
  }

  // 2. Fallback to MongoDB if no tokens found in Firestore (or if Firestore failed)
  if (tokens.length === 0) {
    const mongoTokens = await NotificationToken.find({ user: userId });
    mongoTokens.forEach(t => tokens.push(t.deviceToken));
  }

  if (tokens.length === 0) {
    console.log(`No registered notification tokens for user: ${userId}`);
    return { success: false, reason: 'No tokens found' };
  }

  // 3. Dispatch notifications via Firebase Admin SDK
  try {
    const message = {
      notification: { title, body },
      data: { ...data, click_action: 'FLUTTER_NOTIFICATION_CLICK' },
      tokens
    };

    const response = await admin.messaging().sendEachForMulticast(message);
    console.log(`Successfully sent ${response.successCount} push notifications. Failed: ${response.failureCount}`);
    return { success: true, response };
  } catch (error) {
    console.error('Error sending FCM push notifications:', error.message);
    return { success: false, error: error.message };
  }
};

module.exports = {
  registerDeviceToken,
  sendPushNotification
};
