# Implementation Plan - ReviewFlow AI Full Stack (Native Mobile + MERN Backend)

ReviewFlow AI is a growth platform designed to help local businesses increase Google reviews. 
This plan covers the full-stack architecture:
- **Backend (`backend/`)**: Node.js, Express, and MongoDB (aligned with a future MERN stack web portal), optimized for VPS hosting.
- **Push Notifications**: Integrated with Google Firestore and Firebase Cloud Messaging (FCM).
- **iOS Application (`ios/`)**: Native Swift and SwiftUI.
- **Android Application (`android/`)**: Native Kotlin and Jetpack Compose.

---

## User Review Required

> [!IMPORTANT]
> **Key Architecture Decisions**
> 1. **VPS Deployment Strategy**: The backend will be designed to run standalone on a VPS using PM2 and Nginx reverse proxy. We will structure the configuration using environment variables (`.env`) for MongoDB URIs, port configurations, and Firebase credentials.
> 2. **Firestore and Push Notifications**: The backend will use the Firebase Admin SDK to interact with Google Firestore for managing push notification tokens and triggering device notifications.

## Open Questions

> [!WARNING]
> Please clarify:
> - **Database Hosting**: Will you use a hosted MongoDB service (like MongoDB Atlas) or host MongoDB directly on the same VPS? (We will configure the backend to support a generic MongoDB URI string).

---

## Implementation Phases

To deliver the product efficiently, we propose structured phases matching the PRD:

### **Phase 1: Backend Infrastructure & Database Setup**
* Setup `backend/` skeleton with Express, Mongoose schemas (Users, Businesses, Campaigns, Contacts).
* Implement authentication endpoints (JWT Login/Signup/Roles) and base configurations for VPS hosting.
* Integrate Firebase Admin SDK and initialize Google Firestore connection for notification token storage.

### **Phase 2: Business Onboarding & Google Maps Link Parsing**
* **Mobile Clients**: Implement Auth views and the Business Onboarding flow.
* **Extraction Service**: Build URL resolution logic (resolving short Google Maps URLs to Place IDs and constructing the writereview link).
* **Verification**: Add manual review link testing interface in the apps.

### **Phase 3: QR Code Designer & Campaign Management**
* **Campaign Logic**: Backend endpoints to create, list, and archive campaigns.
* **Native QR Engine**: Implement custom canvas-based QR rendering in iOS (Swift) and Android (Kotlin) allowing color styling, frame selections, CTAs, and logo overlays.
* **Exports**: PDF/PNG/SVG native download and share sheets.

### **Phase 4: Customer Review Flow & AI Generation (Smart/Ultra Smart)**
* **Customer Landing UI**: High-fidelity native implementation of customer landing page UI on the mobile device (or web-link equivalent).
* **Smart Mode**: Multi-step Q&A form generating draft reviews.
* **Ultra Smart Mode**: Dynamic slider/button selection (Tone, Rating, Service) prompting AI API.
* **Copy/Redirect**: Automated clipboard copying and redirection to the generated Google Review link.

### **Phase 5: Contacts, Notifications & Payments Integration**
* **Contacts**: Native contact importer and CSV/Excel parsing tool.
* **Push Notifications**: Live testing of push notifications from Firestore to iOS (APNs) and Android (FCM).
* **Monetization**: Integrate Stripe/Razorpay SDKs on the mobile clients and process payments/webhooks on the backend.

### **Phase 6: Live Analytics Dashboard & Walkthrough**
* Implement analytics aggregation pipelines on the backend.
* Create high-fidelity visual graphs (scans, conversion funnel, staff leaderboards) in SwiftUI and Jetpack Compose.
* Perform system-wide end-to-end user journey tests.

---

## Proposed Changes

We will organize the repository under `d:/RankWell` with three main folders:
- `backend/`: Node.js/Express server code.
- `android/`: Android Studio Kotlin project.
- `ios/`: Xcode Swift project.

### 1. Backend Service (`backend/`)

#### [NEW] [package.json](file:///d:/RankWell/backend/package.json)
Configure dependencies for the Node.js/Express application:
- **Web Framework**: `express`, `cors`, `helmet`.
- **Database**: `mongoose` (for MongoDB object modeling).
- **Notifications**: `firebase-admin` (to interact with Firestore and send FCM notifications).
- **Security**: `jsonwebtoken` (JWT for API authentication), `bcryptjs`.
- **Process Manager**: Ready for `pm2` configuration.

#### [NEW] [server.js](file:///d:/RankWell/backend/server.js)
Entrypoint initializing Express, connecting to MongoDB, registering API routes, and initializing the Firebase Admin SDK.

#### [NEW] [Directory Structure](file:///d:/RankWell/backend)
- `config/`: MongoDB connection setup and Firebase credentials initialization.
- `models/`: Mongoose schemas (User, Business, Campaign, Contact, Analytics, NotificationToken).
- `controllers/`: Request handlers (auth, business onboarding, campaign management, Google Review URL resolution, push notifications sender).
- `routes/`: Express API endpoints (e.g., `/api/auth`, `/api/businesses`, `/api/campaigns`, `/api/notifications`).

---

### 2. Native Mobile Clients

Both native mobile clients will consume the Express API:

- **Authentication**: JWT-based login and signup.
- **Push Notifications**:
  - The mobile clients will register for push notifications via the Firebase SDK.
  - The client will upload their registration token to the `/api/notifications/register` endpoint, which the backend will save in Firestore or MongoDB.
  - The backend triggers notifications via the Firebase Admin SDK.

---

## Verification Plan

### Automated Tests
- **Backend**: Integration tests for API endpoints using `supertest` and `jest`.
- **Android/iOS**: Native unit tests for JSON parsing and API endpoint mapping.

### Manual Verification
- Deploying the backend to a local test environment and checking API connectivity from the iOS Simulator and Android Emulator.
- Simulating a mock push notification trigger from the backend to verify receipt on test devices.
