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
    
    @State private var selectedTab = 0 // 0 = Search, 1 = Paste Link
    @State private var searchQuery = ""
    @State private var searchResults: [[String: Any]] = []
    @State private var isSearching = false
    
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
                        
                        Text("Search your business profile or paste your Google Maps link.")
                            .font(.body)
                            .foregroundColor(.gray)
                    }
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.top, 20)
                    
                    // Segment Tab Picker
                    Picker("Onboarding Mode", selection: $selectedTab) {
                        Text("Search Profile").tag(0)
                        Text("Paste Link").tag(1)
                    }
                    .pickerStyle(SegmentedPickerStyle())
                    .padding(.bottom, 8)
                    .accentColor(.orange)
                    
                    // Input Card
                    if selectedTab == 0 {
                        // Search Form
                        VStack(spacing: 16) {
                            VStack(alignment: .leading, spacing: 8) {
                                Text("BUSINESS NAME OR KEYWORDS")
                                    .font(.caption2)
                                    .fontWeight(.bold)
                                    .foregroundColor(.orange)
                                
                                HStack {
                                    TextField("e.g. Starbucks Market St", text: $searchQuery)
                                        .autocapitalization(.none)
                                        .disableAutocorrection(true)
                                        .foregroundColor(.white)
                                    
                                    Button(action: searchBusinesses) {
                                        if isSearching {
                                            ProgressView()
                                                .progressViewStyle(CircularProgressViewStyle(tint: .orange))
                                        } else {
                                            Text("Search")
                                                .fontWeight(.bold)
                                                .foregroundColor(.orange)
                                        }
                                    }
                                }
                                .padding()
                                .background(Color.white.opacity(0.05))
                                .cornerRadius(8)
                            }
                            
                            // Results List
                            if !searchResults.isEmpty {
                                VStack(alignment: .leading, spacing: 8) {
                                    Text("RESULTS (ESTABLISHMENTS ONLY)")
                                        .font(.caption2)
                                        .fontWeight(.bold)
                                        .foregroundColor(.gray)
                                    
                                    ScrollView {
                                        VStack(spacing: 12) {
                                            ForEach(0..<searchResults.count, id: \.self) { index in
                                                let item = searchResults[index]
                                                Button(action: { selectBusiness(result: item) }) {
                                                    VStack(alignment: .leading, spacing: 4) {
                                                        Text(item["name"] as? String ?? "")
                                                            .font(.subheadline)
                                                            .fontWeight(.bold)
                                                            .foregroundColor(.white)
                                                            .multilineTextAlignment(.leading)
                                                        Text(item["formattedAddress"] as? String ?? "")
                                                            .font(.caption)
                                                            .foregroundColor(.gray)
                                                            .multilineTextAlignment(.leading)
                                                    }
                                                    .frame(maxWidth: .infinity, alignment: .leading)
                                                    .padding()
                                                    .background(Color.white.opacity(0.05))
                                                    .cornerRadius(8)
                                                }
                                            }
                                        }
                                    }
                                    .frame(maxHeight: 200)
                                }
                            }
                        }
                        .padding()
                        .background(Color.white.opacity(0.03))
                        .cornerRadius(16)
                        .overlay(
                            RoundedRectangle(cornerRadius: 16)
                                .stroke(Color.white.opacity(0.1), lineWidth: 1)
                        )
                    } else {
                        // Manual Form
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
                    }
                    
                    // Preview Card (Resolved Place ID & Link)
                    if previewActive {
                        VStack(alignment: .leading, spacing: 16) {
                            Text("Selected Business Details")
                                .font(.headline)
                                .foregroundColor(.white)
                            
                            VStack(alignment: .leading, spacing: 4) {
                                Text("Name:")
                                    .font(.caption2)
                                    .foregroundColor(.orange)
                                Text(businessName)
                                    .foregroundColor(.white)
                            }
                            
                            if !businessAddress.isEmpty {
                                VStack(alignment: .leading, spacing: 4) {
                                    Text("Address:")
                                        .font(.caption2)
                                        .foregroundColor(.orange)
                                    Text(businessAddress)
                                        .foregroundColor(.white)
                                }
                            }
                            
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
                    .disabled(businessName.isEmpty || isSaving)
                    
                    Spacer()
                }
                .padding()
            }
        }
    }
    
    private func searchBusinesses() {
        guard !searchQuery.isEmpty else { return }
        isSearching = true
        Task {
            do {
                let results = try await APIService.shared.searchGoogleBusinesses(query: searchQuery)
                DispatchQueue.main.async {
                    self.searchResults = results
                    self.isSearching = false
                }
            } catch {
                DispatchQueue.main.async {
                    self.isSearching = false
                }
                print("Search failed: \(error)")
            }
        }
    }
    
    private func selectBusiness(result: [String: Any]) {
        self.businessName = result["name"] as? String ?? ""
        self.businessAddress = result["formattedAddress"] as? String ?? ""
        self.googleMapsUrl = result["googleMapsUrl"] as? String ?? ""
        self.placeId = result["placeId"] as? String ?? ""
        self.generatedReviewUrl = result["googleReviewUrl"] as? String ?? ""
        self.previewActive = true
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
        Task {
            do {
                let _ = try await APIService.shared.onboardBusiness(name: businessName, address: businessAddress, mapsUrl: googleMapsUrl)
                DispatchQueue.main.async {
                    isSaving = false
                    previewActive = false
                    businessName = ""
                    businessAddress = ""
                    googleMapsUrl = ""
                    placeId = ""
                    generatedReviewUrl = ""
                    searchResults = []
                    searchQuery = ""
                }
            } catch {
                DispatchQueue.main.async {
                    isSaving = false
                }
                print("Save failed: \(error)")
            }
        }
    }
}

struct OnboardingView_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingView()
    }
}
