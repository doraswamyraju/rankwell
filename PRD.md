# Product Requirements Document (PRD)

# Project Name

**ReviewFlow AI**
*AI-Powered Google Review Growth Platform*

---

# Version

1.0

---

# Product Vision

ReviewFlow AI is a SaaS platform that helps local businesses increase Google reviews through QR campaigns, AI-assisted review writing, customer engagement, messaging campaigns, analytics, and agency management.

The platform does **not require Google Business Profile API access**. Instead, it relies on publicly available Google Maps business links supplied by the business owner.

The system is designed as a scalable multi-tenant SaaS supporting individuals, multi-location businesses, and agencies.

---

# Primary Objectives

* Increase review conversion rate
* Reduce review writing time
* Provide measurable analytics
* Allow businesses to manage campaigns
* Generate recurring subscription revenue
* Support agencies and white-label deployments

---

# Target Customers

* Restaurants
* Cafés
* Hotels
* Hospitals
* Clinics
* Dentists
* Chartered Accountants
* Lawyers
* Salons
* Gyms
* Retail Stores
* Automobile Dealers
* Educational Institutions
* Marketing Agencies

---

# User Roles

## Customer
Scans QR and leaves a review.

## Business Owner
Manages businesses, campaigns, contacts, analytics, subscriptions.

## Staff Member
Can create campaigns and send review requests. Cannot access billing.

## Agency Owner
Manages multiple client businesses.

## Super Admin
Complete platform control.

---

# Revenue Model

## Monthly Subscription
Starter / Growth / Business / Agency / Enterprise

## Annual Subscription
Discounted pricing.

## AI Credits
Charge based on AI review generation (e.g., 100, 500, 1000, or Unlimited).

## WhatsApp Credits
Recharge wallet (e.g., 100, 500, 1000, 5000 messages).

## SMS Credits
Recharge wallet.

## Email Credits
Optional.

## NFC Review Cards
PVC Cards, Metal Cards, Stands, Table Displays, Window Stickers.

## QR Print Templates
Premium downloadable templates.

## White Label
Monthly licensing.

## Enterprise Setup Fee
One-time onboarding fee.

---

# Dynamic Pricing Engine
All plans must be editable from Super Admin. Admin can create unlimited plans.
Every plan contains:
Plan Name, Monthly Price, Annual Price, Trial Days, Features Included, Maximum Businesses, Maximum Team Members, Maximum Contacts, Maximum Campaigns, Maximum QR Codes, Maximum AI Credits, Maximum SMS, Maximum WhatsApp, Maximum Emails, Storage Limit, Analytics Retention, Custom Branding, White Label, Priority Support, API Access, Feature Toggles, Visible/Hidden, Display Order, Recommended Badge, Popular Badge.

---

# Super Admin Pricing Module
Admin can Create, Edit, Duplicate, Archive, Delete, Reorder, Enable/Disable Plans, Create Coupons, Create Lifetime Plans, Flash Offers, Seasonal Discounts, Promo Codes, Referral Discounts, Free Trials, Upgrade Offers, Downgrade Rules, Proration Rules, Renewal Pricing, Regional Pricing, Currency Wise Pricing, GST Configuration, Tax Rules.

---

# Monetization Levels
- Level 1: Subscriptions
- Level 2: AI Credits
- Level 3: SMS
- Level 4: WhatsApp
- Level 5: Email Campaigns
- Level 6: Physical NFC Products
- Level 7: White Label
- Level 8: API Access
- Level 9: Enterprise Integrations
- Level 10: Marketplace (Future feature where agencies sell QR templates, landing pages, and AI prompt packs)

---

# Business Onboarding
Signup -> Create Account -> Add Business -> Paste Google Maps Link OR Share from Google Maps
↓
**Google Review URL Extraction Process**
* Business owner provides Google Maps link of their business listing.
* System parses the Maps URL to extract Place ID or business identifier.
* Platform automatically converts the Maps link into a direct Google Review URL.
* Validation ensures the link opens the correct business review page.
* System stores both original Maps URL and generated Review URL.
* Preview is shown to the user for confirmation.
* If invalid, user is prompted to re-submit correct Maps link.
↓
Business Created -> Generate QR

---

# Customer Review Flow
Customer scans QR -> Landing Page -> Choose Review Type (Custom Review, Smart Review, Ultra Smart Review) -> System redirects user to the generated Google Review URL -> Customer posts review voluntarily.

---

# Review Modes

## Custom
User writes manually. Estimated Time: 50–180 seconds.

## Smart
Customer answers questions -> AI drafts review -> Customer edits -> Copies -> Opens Google Review URL -> Pastes -> Posts.

## Ultra Smart
Customer selects Rating, Service, Tone -> AI instantly generates -> Copies -> Google Review URL opens -> Paste -> Submit.

---

# Contact Management
Import via CSV, Excel, Phone Contacts, Manual, Google Contacts (future).
Fields: Name, Phone, Email, Tags, Birthday, Last Visit, Preferred Service, Notes, Status.

---

# Campaigns
Campaign Types: QR, SMS, WhatsApp, Email, Invoice QR, Receipt QR, Table QR, Poster QR, Website Widget, NFC Card. Each campaign gets unique analytics.

---

# QR Generator
Shapes, Colors, Logo, Frame, CTA, Download (PNG, SVG, PDF, EPS), Print Quality.

---

# Landing Page Builder
Editable Logo, Banner, Theme, Colors, CTA, Questions, AI Tone, Background, Business Description, Social Links, Privacy Policy.

---

# Analytics

## Platform Analytics
Total Businesses, MRR, ARR, Subscriptions, Active Users, Churn, AI/SMS/WhatsApp Usage, Top Businesses/Agencies, Revenue, Growth Rate, Conversion.

## Business Analytics
QR Scans, Unique Visitors, Returning Visitors, Review Mode Selected, Copy Button Clicks, Google Button Clicks, Estimated Conversion, Campaign Performance, Device/Browser/OS, Location, Peak Hours, Average Time, Bounce Rate, AI/Template Usage, Review Length, Employee Comparison/Staff Leaderboard, Heatmaps, Traffic/Referral Source, UTM Tracking.

## Funnel Analytics
QR Viewed -> Landing Opened -> Review Type Selected -> AI Generated -> Copied -> Google Review URL Opened -> Estimated Review Completion. (Includes Count, Drop %, Conversion %).

---

# Notifications
Push, Email, SMS, WhatsApp. (Subscription Expiry, Campaign Success, Low Credits, Payment Success, Trial Ending, etc.).

---

# Payments
Support for Razorpay, Stripe, Cashfree, PayPal, Manual Payment, Bank Transfer, Invoice Generation (GST Invoice), Refunds, Wallet, Coupons, Gift Cards.

---

# Admin Modules
Dashboard, Users, Businesses, Campaigns, Plans, Pricing, Coupons, Orders, Invoices, Wallet, Credits (AI, SMS, WhatsApp, Email), Products, Support, Reports, Audit Logs, Settings, Feature Flags, Roles, Permissions, Themes, Templates, System Announcements, Maintenance Mode.

---

# Security
JWT, Role Permissions, Rate Limiting, Encryption, 2FA, Device Login, Session Logs, Audit Logs, Activity History, GDPR Ready.

---

# Future Roadmap
- **Version 2**: Review Monitoring, Review Response AI, Competitor Analytics, Review Sentiment, Negative Feedback Portal.
- **Version 3**: Voice Reviews, Video Testimonials, Website Widgets, CRM/POS Integrations, API Marketplace.

---

# Technology Stack
- **Frontend**: Flutter (Not used, transitioned to Native iOS Swift & Android Kotlin), React Admin Portal.
- **Backend**: Node.js, Express, MongoDB, Redis, BullMQ, Socket.IO.
- **Storage**: AWS S3, Cloudflare R2.
- **Authentication**: Firebase OTP, Google/Apple Login.
- **Payments**: Razorpay, Stripe.
- **Infrastructure**: Docker, Nginx, PM2, GitHub Actions.
- **Monitoring**: Grafana, Prometheus, Sentry, Cloudflare.
