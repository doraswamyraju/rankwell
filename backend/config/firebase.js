const admin = require('firebase-admin');
const fs = require('fs');
const path = require('path');

let firebaseApp = null;
let db = null;

const initializeFirebase = () => {
  const credentialsPath = process.env.FIREBASE_CREDENTIALS_PATH || './config/firebase-service-account.json';
  const resolvedPath = path.resolve(credentialsPath);

  if (fs.existsSync(resolvedPath)) {
    try {
      const serviceAccount = require(resolvedPath);
      firebaseApp = admin.initializeApp({
        credential: admin.credential.cert(serviceAccount)
      });
      db = admin.firestore();
      console.log('Firebase Admin SDK initialized successfully.');
    } catch (error) {
      console.error('Failed to initialize Firebase Admin SDK:', error.message);
    }
  } else {
    console.warn(`Firebase credentials not found at ${resolvedPath}. Push notifications and Firestore messaging features will run in mock mode.`);
  }
};

module.exports = {
  initializeFirebase,
  getFirebaseApp: () => firebaseApp,
  getFirestore: () => db,
  admin
};
