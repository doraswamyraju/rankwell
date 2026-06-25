import SwiftUI

struct CustomerReviewView: View {
    @State private var selectedMode = "Smart" // Custom, Smart, Ultra Smart
    
    // Smart Mode Q&A
    @State private var serviceRating = 5
    @State private var favoriteThing = ""
    @State private var feedbackText = ""
    
    // Ultra Smart Mode Configuration
    @State private var serviceCategory = "Coffee"
    @State private var reviewTone = "Friendly" // Friendly, Professional, Detailed
    
    @State private var aiDraft = ""
    @State private var isGenerating = false
    @State private var hasCopied = false
    
    let googleReviewUrl = "https://search.google.com/local/writereview?placeid=ChIJu46S-uB-hYGRwT3QyP6Wj2Y"
    
    var body: some View {
        ZStack {
            LinearGradient(gradient: Gradient(colors: [Color.black, Color(red: 0.1, green: 0.1, blue: 0.25)]), startPoint: .top, endPoint: .bottom)
                .ignoresSafeArea()
            
            ScrollView {
                VStack(spacing: 24) {
                    // Business Brand Header
                    VStack(spacing: 8) {
                        Text("Share Your Feedback")
                            .font(.title2)
                            .fontWeight(.bold)
                            .foregroundColor(.white)
                        
                        Text("Blue Bottle Coffee")
                            .font(.largeTitle)
                            .fontWeight(.black)
                            .foregroundColor(.orange)
                    }
                    .padding(.top, 30)
                    
                    // Selector modes
                    Picker("Review Mode", selection: $selectedMode) {
                        Text("Smart Review").tag("Smart")
                        Text("Ultra Smart").tag("Ultra Smart")
                        Text("Custom").tag("Custom")
                    }
                    .pickerStyle(SegmentedPickerStyle())
                    .padding(.horizontal)
                    .background(Color.white.opacity(0.1))
                    .cornerRadius(8)
                    
                    // Mode UI Forms
                    if selectedMode == "Smart" {
                        smartModeForm
                    } else if selectedMode == "Ultra Smart" {
                        ultraSmartModeForm
                    } else {
                        customModeForm
                    }
                    
                    // Live AI Generated Result Draft
                    if !aiDraft.isEmpty {
                        aiDraftResultCard
                    }
                    
                    Spacer()
                }
                .padding()
            }
        }
    }
    
    // Smart Mode Layout
    var smartModeForm: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("TELL US MORE")
                .font(.caption2)
                .fontWeight(.bold)
                .foregroundColor(.orange)
            
            VStack(alignment: .leading, spacing: 8) {
                Text("What did you order or enjoy most?")
                    .foregroundColor(.white)
                    .font(.footnote)
                TextField("e.g. Latte, Almond Croissant", text: $favoriteThing)
                    .padding()
                    .background(Color.white.opacity(0.05))
                    .cornerRadius(8)
                    .foregroundColor(.white)
            }
            
            VStack(alignment: .leading, spacing: 8) {
                Text("Any quick comments about our service?")
                    .foregroundColor(.white)
                    .font(.footnote)
                TextField("e.g. Staff was super fast and friendly", text: $feedbackText)
                    .padding()
                    .background(Color.white.opacity(0.05))
                    .cornerRadius(8)
                    .foregroundColor(.white)
            }
            
            Button(action: generateSmartReview) {
                HStack {
                    if isGenerating {
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: .white))
                    } else {
                        Text("Generate AI Review")
                            .fontWeight(.bold)
                    }
                }
                .frame(maxWidth: .infinity)
                .padding()
                .background(Color.orange)
                .foregroundColor(.white)
                .cornerRadius(12)
            }
            .disabled(isGenerating)
        }
        .padding()
        .background(Color.white.opacity(0.03))
        .cornerRadius(16)
    }
    
    // Ultra Smart Mode Layout
    var ultraSmartModeForm: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("QUICK SELECTIONS")
                .font(.caption2)
                .fontWeight(.bold)
                .foregroundColor(.orange)
            
            // Star selection
            HStack {
                Text("Rating:")
                    .foregroundColor(.white)
                Spacer()
                ForEach(1...5, id: \.self) { num in
                    Image(systemName: num <= serviceRating ? "star.fill" : "star")
                        .foregroundColor(.yellow)
                        .onTapGesture {
                            serviceRating = num
                        }
                }
            }
            .padding()
            .background(Color.white.opacity(0.05))
            .cornerRadius(8)
            
            VStack(alignment: .leading, spacing: 8) {
                Text("Service / Product Type:")
                    .foregroundColor(.white)
                    .font(.footnote)
                TextField("e.g. Latte & Espresso", text: $serviceCategory)
                    .padding()
                    .background(Color.white.opacity(0.05))
                    .cornerRadius(8)
                    .foregroundColor(.white)
            }
            
            VStack(alignment: .leading, spacing: 8) {
                Text("Select Tone:")
                    .foregroundColor(.white)
                    .font(.footnote)
                
                Picker("Tone", selection: $reviewTone) {
                    Text("Friendly").tag("Friendly")
                    Text("Professional").tag("Professional")
                    Text("Detailed").tag("Detailed")
                }
                .pickerStyle(SegmentedPickerStyle())
            }
            
            Button(action: generateUltraReview) {
                HStack {
                    if isGenerating {
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: .white))
                    } else {
                        Text("Generate AI Review")
                            .fontWeight(.bold)
                    }
                }
                .frame(maxWidth: .infinity)
                .padding()
                .background(Color.orange)
                .foregroundColor(.white)
                .cornerRadius(12)
            }
            .disabled(isGenerating)
        }
        .padding()
        .background(Color.white.opacity(0.03))
        .cornerRadius(16)
    }
    
    // Custom Mode Layout
    var customModeForm: some View {
        VStack(spacing: 16) {
            Text("Write your review manually below and tap redirect to paste it on Google.")
                .font(.footnote)
                .foregroundColor(.gray)
                .multilineTextAlignment(.center)
            
            Button(action: redirectToGoogle) {
                Text("Open Google Reviews Directly")
                    .fontWeight(.bold)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.orange)
                    .foregroundColor(.white)
                    .cornerRadius(12)
            }
        }
        .padding()
        .background(Color.white.opacity(0.03))
        .cornerRadius(16)
    }
    
    // AI Output Card
    var aiDraftResultCard: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Your Draft Review:")
                .font(.subheadline)
                .fontWeight(.bold)
                .foregroundColor(.white)
            
            Text(aiDraft)
                .font(.body)
                .foregroundColor(.white.opacity(0.9))
                .padding()
                .background(Color.white.opacity(0.05))
                .cornerRadius(8)
            
            HStack(spacing: 12) {
                Button(action: copyToClipboard) {
                    Label(hasCopied ? "Copied!" : "Copy Review", systemImage: "doc.on.doc.fill")
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(hasCopied ? Color.green : Color.white.opacity(0.1))
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
                
                Button(action: redirectToGoogle) {
                    Text("Open Google")
                        .fontWeight(.bold)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(LinearGradient(colors: [.orange, .pink], startPoint: .leading, endPoint: .trailing))
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
            }
        }
        .padding()
        .background(Color.orange.opacity(0.08))
        .cornerRadius(12)
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(Color.orange.opacity(0.2), lineWidth: 1)
        )
    }
    
    private func generateSmartReview() {
        isGenerating = true
        // Call local server AI endpoints or construct mock
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
            aiDraft = "I recently visited Blue Bottle Coffee and had a wonderful experience! I ordered a \(favoriteThing.isEmpty ? "Coffee" : favoriteThing). \(feedbackText) The service was prompt and the staff were highly welcoming. Highly recommend!"
            isGenerating = false
            hasCopied = false
        }
    }
    
    private func generateUltraReview() {
        isGenerating = true
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
            if reviewTone == "Friendly" {
                aiDraft = "Absolutely loved the \(serviceCategory) here! The staff were so warm and welcoming. Easily a solid \(serviceRating)-star experience. Can't wait to visit again!"
            } else {
                aiDraft = "The \(serviceCategory) provided was of exceptional quality. Highly professional staff and efficient service delivery. Rated \(serviceRating)/5 for outstanding standards."
            }
            isGenerating = false
            hasCopied = false
        }
    }
    
    private func copyToClipboard() {
        UIPasteboard.general.string = aiDraft
        hasCopied = true
    }
    
    private func redirectToGoogle() {
        if let url = URL(string: googleReviewUrl) {
            UIApplication.shared.open(url)
        }
    }
}

struct CustomerReviewView_Previews: PreviewProvider {
    static var previews: some View {
        CustomerReviewView()
    }
}
