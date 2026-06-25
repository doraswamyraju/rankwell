import SwiftUI

struct OnboardingView: View {
    @State private var businessName = ""
    @State private var businessAddress = ""
    @State private var googleMapsUrl = ""
    @State private var placeId = ""
    @State private var generatedReviewUrl = ""
    @State private var isResolving = false
    @State private var isSaving = false
    @State private var previewActive = false
    
    var body: some View {
        ZStack {
            LinearGradient(gradient: Gradient(colors: [Color.black, Color(red: 0.1, green: 0.1, blue: 0.25)]), startPoint: .top, endPoint: .bottom)
                .ignoresSafeArea()
            
            ScrollView {
                VStack(spacing: 24) {
                    // Header
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Add Your Business")
                            .font(.largeTitle)
                            .fontWeight(.bold)
                            .foregroundColor(.white)
                        
                        Text("Paste your Google Maps link to automatically generate Review triggers.")
                            .font(.body)
                            .foregroundColor(.gray)
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.top, 20)
                    
                    // Input Card
                    VStack(spacing: 20) {
                        VStack(alignment: .leading, spacing: 8) {
                            Text("BUSINESS NAME")
                                .font(.caption2)
                                .fontWeight(.bold)
                                .foregroundColor(.orange)
                            
                            TextField("e.g. Blue Bottle Coffee", text: $businessName)
                                .padding()
                                .background(Color.white.opacity(0.05))
                                .cornerRadius(8)
                                .foregroundColor(.white)
                        }
                        
                        VStack(alignment: .leading, spacing: 8) {
                            Text("ADDRESS (OPTIONAL)")
                                .font(.caption2)
                                .fontWeight(.bold)
                                .foregroundColor(.orange)
                            
                            TextField("e.g. 123 Market St, San Francisco", text: $businessAddress)
                                .padding()
                                .background(Color.white.opacity(0.05))
                                .cornerRadius(8)
                                .foregroundColor(.white)
                        }
                        
                        VStack(alignment: .leading, spacing: 8) {
                            Text("GOOGLE MAPS SHARE LINK")
                                .font(.caption2)
                                .fontWeight(.bold)
                                .foregroundColor(.orange)
                            
                            HStack {
                                TextField("https://maps.app.goo.gl/...", text: $googleMapsUrl)
                                    .autocapitalization(.none)
                                    .disableAutocorrection(true)
                                    .foregroundColor(.white)
                                
                                Button(action: resolveMapsLink) {
                                    if isResolving {
                                        ProgressView()
                                            .progressViewStyle(CircularProgressViewStyle(tint: .orange))
                                    } else {
                                        Text("Verify")
                                            .fontWeight(.bold)
                                            .foregroundColor(.orange)
                                    }
                                }
                            }
                            .padding()
                            .background(Color.white.opacity(0.05))
                            .cornerRadius(8)
                        }
                    }
                    .padding()
                    .background(Color.white.opacity(0.03))
                    .cornerRadius(16)
                    .overlay(
                        RoundedRectangle(cornerRadius: 16)
                            .stroke(Color.white.opacity(0.1), lineWidth: 1)
                    )
                    
                    // Preview Card (Resolved Place ID & Link)
                    if previewActive {
                        VStack(alignment: .leading, spacing: 16) {
                            Text("Generated Links")
                                .font(.headline)
                                .foregroundColor(.white)
                            
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Place Identifier (Place ID):")
                                    .font(.caption)
                                    .foregroundColor(.gray)
                                Text(placeId)
                                    .font(.system(.body, design: .monospaced))
                                    .foregroundColor(.yellow)
                            }
                            
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Direct Google Review Link:")
                                    .font(.caption)
                                    .foregroundColor(.gray)
                                Link(destination: URL(string: generatedReviewUrl) ?? URL(string: "https://google.com")!) {
                                    Text(generatedReviewUrl)
                                        .font(.caption)
                                        .foregroundColor(.orange)
                                        .underline()
                                        .lineLimit(1)
                                }
                            }
                        }
                        .padding()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .background(Color.orange.opacity(0.08))
                        .cornerRadius(12)
                        .overlay(
                            RoundedRectangle(cornerRadius: 12)
                                .stroke(Color.orange.opacity(0.2), lineWidth: 1)
                        )
                    }
                    
                    // Submit button
                    Button(action: saveBusiness) {
                        HStack {
                            if isSaving {
                                ProgressView()
                                    .progressViewStyle(CircularProgressViewStyle(tint: .white))
                            } else {
                                Text("Onboard Business")
                                    .fontWeight(.bold)
                            }
                        }
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(
                            LinearGradient(
                                colors: [.orange, .pink],
                                startPoint: .leading,
                                endPoint: .trailing
                            )
                        )
                        .foregroundColor(.white)
                        .cornerRadius(12)
                    }
                    .disabled(businessName.isEmpty || googleMapsUrl.isEmpty || isSaving)
                    
                    Spacer()
                }
                .padding()
            }
        }
    }
    
    private func resolveMapsLink() {
        isResolving = true
        // Mock resolution for UI scaffolding
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
            placeId = "ChIJu46S-uB-hYGRwT3QyP6Wj2Y"
            generatedReviewUrl = "https://search.google.com/local/writereview?placeid=\(placeId)"
            isResolving = false
            previewActive = true
        }
    }
    
    private func saveBusiness() {
        isSaving = true
        // Connect API here
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            isSaving = false
        }
    }
}

struct OnboardingView_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingView()
    }
}
