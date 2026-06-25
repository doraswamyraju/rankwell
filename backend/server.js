require('dotenv').config();
const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const connectDB = require('./config/db');
const { initializeFirebase } = require('./config/firebase');

const app = express();

// Middlewares
app.use(helmet());
app.use(cors());
app.use(express.json());

// Database and Firebase Initialization
connectDB().then(() => {
  seedAdmin();
});
initializeFirebase();

async function seedAdmin() {
  try {
    const User = require('./models/User');
    const bcrypt = require('bcryptjs');
    
    // 1. Seed Super Admin
    const adminExists = await User.findOne({ email: 'doraswamyraju.ca@gmail.com' });
    if (!adminExists) {
      const salt = await bcrypt.genSalt(10);
      const hashedPassword = await bcrypt.hash('BOHPM6139n@', salt);
      await User.create({
        name: 'Super Admin',
        email: 'doraswamyraju.ca@gmail.com',
        password: hashedPassword,
        role: 'admin'
      });
      console.log('Super Admin user seeded successfully.');
    }

    // 2. Seed Business Owner (Rajugari Ventures)
    const ownerExists = await User.findOne({ email: 'rajugariventures@gmail.com' });
    if (!ownerExists) {
      const salt = await bcrypt.genSalt(10);
      const hashedPassword = await bcrypt.hash('BOHPM6139n@', salt);
      await User.create({
        name: 'Rajugari Ventures',
        email: 'rajugariventures@gmail.com',
        password: hashedPassword,
        role: 'owner'
      });
      console.log('Business Owner user seeded successfully.');
    }
  } catch (error) {
    console.error('Failed to seed users:', error.message);
  }
}

// Routes
const authRoutes = require('./routes/authRoutes');
const businessRoutes = require('./routes/businessRoutes');
const campaignRoutes = require('./routes/campaignRoutes');
const aiRoutes = require('./routes/aiRoutes');
const contactRoutes = require('./routes/contactRoutes');
const notificationRoutes = require('./routes/notificationRoutes');
const paymentRoutes = require('./routes/paymentRoutes');
const analyticsRoutes = require('./routes/analyticsRoutes');
app.use('/api/auth', authRoutes);
app.use('/api/businesses', businessRoutes);
app.use('/api/campaigns', campaignRoutes);
app.use('/api/ai', aiRoutes);
app.use('/api/contacts', contactRoutes);
app.use('/api/notifications', notificationRoutes);
app.use('/api/payments', paymentRoutes);
app.use('/api/analytics', analyticsRoutes);

// Base Health check route
app.get('/api/health', (req, res) => {
  res.json({
    status: 'healthy',
    timestamp: new Date(),
    service: 'RankWell Backend (a product of RGV)'
  });
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
