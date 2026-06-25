// @desc    Draft a smart review based on customer answers
// @route   POST /api/ai/smart-draft
// @access  Public
exports.generateSmartDraft = async (req, res) => {
  const { answers } = req.body; // Array of { question: string, answer: string }

  if (!answers || !Array.isArray(answers) || answers.length === 0) {
    return res.status(400).json({ message: 'Answers are required to draft a review' });
  }

  try {
    // Generate draft combining answers with premium readability flow
    const cleanAnswers = answers.map(a => a.answer.trim()).filter(Boolean);
    
    let draft = "";
    if (cleanAnswers.length > 0) {
      draft = `I recently visited and had a wonderful experience! ${cleanAnswers.join(' ')} The staff were extremely helpful, and the service was prompt. I will definitely be returning and highly recommend them to others!`;
    } else {
      draft = "Excellent experience! The service was prompt, professional, and met all our expectations. Highly recommended!";
    }

    res.json({ draft });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

// @desc    Draft an ultra-smart review based on rating, service, and tone
// @route   POST /api/ai/ultra-draft
// @access  Public
exports.generateUltraDraft = async (req, res) => {
  const { rating, service, tone } = req.body; // e.g., rating: 5, service: 'Coffee', tone: 'Friendly'

  if (!rating || !service) {
    return res.status(400).json({ message: 'Rating and Service are required' });
  }

  try {
    const selectedTone = tone || 'Friendly';
    let draft = "";

    // Generate response matching requested tone templates
    if (selectedTone.toLowerCase() === 'friendly') {
      draft = `Absolutely loved the ${service} here! The staff were so warm and welcoming. Easily a solid ${rating}-star experience. Can't wait to visit again!`;
    } else if (selectedTone.toLowerCase() === 'professional') {
      draft = `The ${service} provided was of exceptional quality. Highly professional staff and efficient service delivery. Rated ${rating}/5 for outstanding standards.`;
    } else { // Detailed
      draft = `I'm leaving a ${rating}-star review because of the outstanding ${service}. From start to finish, the attention to detail was impressive. Highly recommended for anyone looking for top-notch quality!`;
    }

    res.json({ draft });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};
