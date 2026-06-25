import SwiftUI

struct QRDesignerView: View {
    @State private var campaignName = "Summer Campaign"
    @State private var foregroundColor = Color.black
    @State private var backgroundColor = Color.white
    @State private var ctaText = "Scan to Review Us!"
    @State private var qrImage: UIImage? = nil
    @State private var isSaving = false
    
    let targetLink = "https://search.google.com/local/writereview?placeid=ChIJu46S-uB-hYGRwT3QyP6Wj2Y"
    
    var body: some View {
        ZStack {
            LinearGradient(gradient: Gradient(colors: [Color.black, Color(red: 0.1, green: 0.1, blue: 0.25)]), startPoint: .top, endPoint: .bottom)
                .ignoresSafeArea()
            
            ScrollView {
                VStack(spacing: 24) {
                    Text("QR Designer")
                        .font(.largeTitle)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.top, 20)
                    
                    // QR Live Preview Card
                    VStack(spacing: 16) {
                        if let qr = qrImage {
                            Image(uiImage: qr)
                                .resizable()
                                .interpolation(.none)
                                .scaledToFit()
                                .frame(width: 240, height: 240)
                                .padding()
                                .background(backgroundColor)
                                .cornerRadius(16)
                                .shadow(radius: 10)
                        } else {
                            ProgressView()
                                .frame(width: 240, height: 240)
                        }
                        
                        if !ctaText.isEmpty {
                            Text(ctaText)
                                .font(.headline)
                                .fontWeight(.bold)
                                .foregroundColor(.white)
                                .padding(.horizontal, 16)
                                .padding(.vertical, 8)
                                .background(Color.orange)
                                .cornerRadius(8)
                        }
                    }
                    .padding()
                    
                    // Designers Configurations
                    VStack(alignment: .leading, spacing: 20) {
                        Text("CUSTOMIZE DESIGN")
                            .font(.caption)
                            .fontWeight(.bold)
                            .foregroundColor(.gray)
                        
                        TextField("Campaign Name", text: $campaignName)
                            .padding()
                            .background(Color.white.opacity(0.05))
                            .cornerRadius(12)
                            .foregroundColor(.white)
                        
                        TextField("Call To Action (CTA)", text: $ctaText)
                            .padding()
                            .background(Color.white.opacity(0.05))
                            .cornerRadius(12)
                            .foregroundColor(.white)
                        
                        // Color Pickers
                        HStack {
                            ColorPicker("Foreground", selection: $foregroundColor)
                                .foregroundColor(.white)
                            Spacer()
                            ColorPicker("Background", selection: $backgroundColor)
                                .foregroundColor(.white)
                        }
                        .padding()
                        .background(Color.white.opacity(0.05))
                        .cornerRadius(12)
                    }
                    .onChange(of: foregroundColor) { _ in updateQR() }
                    .onChange(of: backgroundColor) { _ in updateQR() }
                    
                    // Export Actions
                    HStack(spacing: 16) {
                        Button(action: shareQRCode) {
                            Label("Share", systemImage: "square.and.arrow.up")
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.white.opacity(0.1))
                                .foregroundColor(.white)
                                .cornerRadius(12)
                                .overlay(
                                    RoundedRectangle(cornerRadius: 12)
                                        .stroke(Color.white.opacity(0.2), lineWidth: 1)
                                )
                        }
                        
                        Button(action: saveCampaign) {
                            HStack {
                                if isSaving {
                                    ProgressView()
                                        .progressViewStyle(CircularProgressViewStyle(tint: .white))
                                } else {
                                    Text("Save Campaign")
                                        .fontWeight(.bold)
                                }
                            }
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(
                                LinearGradient(colors: [.orange, .pink], startPoint: .leading, endPoint: .trailing)
                            )
                            .foregroundColor(.white)
                            .cornerRadius(12)
                        }
                        .disabled(isSaving)
                    }
                }
                .padding()
            }
        }
        .onAppear {
            updateQR()
        }
    }
    
    private func updateQR() {
        qrImage = QRGeneratorService.shared.generateQRCode(
            from: targetLink,
            foregroundColor: foregroundColor,
            backgroundColor: backgroundColor
        )
    }
    
    private func shareQRCode() {
        guard let qr = qrImage else { return }
        let av = UIActivityViewController(activityItems: [qr], applicationActivities: nil)
        
        // Present ActivityController
        if let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
           let rootVC = windowScene.windows.first?.rootViewController {
            rootVC.present(av, animated: true, completion: nil)
        }
    }
    
    private func saveCampaign() {
        isSaving = true
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.2) {
            isSaving = false
        }
    }
}

struct QRDesignerView_Previews: PreviewProvider {
    static var previews: some View {
        QRDesignerView()
    }
}
