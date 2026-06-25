// @desc    Create Stripe Checkout Session
// @route   POST /api/payments/create-checkout
// @access  Private
exports.createCheckoutSession = async (req, res) => {
  const { planId, successUrl, cancelUrl } = req.body;

  if (!planId) {
    return res.status(400).json({ message: 'Plan ID is required' });
  }

  try {
    // Prototyping checkout redirect response
    const mockSessionId = `cs_test_${Math.random().toString(36).substring(2, 15)}`;
    const mockCheckoutUrl = `https://checkout.stripe.com/c/pay/${mockSessionId}`;

    res.json({
      sessionId: mockSessionId,
      url: mockCheckoutUrl,
      message: 'Checkout session created successfully (Mock Mode)'
    });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Stripe Webhook handler
// @route   POST /api/payments/webhook
// @access  Public
exports.handleStripeWebhook = async (req, res) => {
  const event = req.body; // In production, verify event signature using stripe sdk

  try {
    // Process payment success webhook triggers
    if (event.type === 'checkout.session.completed') {
      const session = event.data.object;
      console.log(`Payment success received for checkout: ${session.id}`);
      
      // Update business owner subscriptions status here
    }

    res.json({ received: true });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Validate Promo Coupon Code
// @route   POST /api/payments/validate-coupon
// @access  Private
exports.validateCoupon = async (req, res) => {
  const { code } = req.body;

  if (!code) {
    return res.status(400).json({ message: 'Coupon code is required' });
  }

  try {
    // Basic mock coupon matching
    if (code.toUpperCase() === 'WELCOME50') {
      return res.json({
        valid: true,
        discountPercentage: 50,
        message: '50% discount applied successfully!'
      });
    }

    res.status(400).json({ valid: false, message: 'Invalid or expired coupon code' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};
