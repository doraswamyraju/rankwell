import SwiftUI

struct AnalyticsDashboardView: View {
    @State private var totalScans = 148
    @State private var conversions = 92
    @State private var conversionRate = "62.1%"
    
    // Review Modes distribution
    @State private var smartModeCount = 54
    @State private var ultraSmartCount = 28
    @State private var customCount = 10
    
    var body: some View {
        ZStack {
            LinearGradient(gradient: Gradient(colors: [Color.black, Color(red: 0.1, green: 0.1, blue: 0.25)]), startPoint: .top, endPoint: .bottom)
                .ignoresSafeArea()
            
            ScrollView {
                VStack(spacing: 24) {
                    Text("Analytics Overview")
                        .font(.largeTitle)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.top, 20)
                    
                    // High Level Summary Cards
                    HStack(spacing: 16) {
                        metricCard(title: "TOTAL SCANS", value: "\(totalScans)", icon: "qrcode.viewfinder", color: .blue)
                        metricCard(title: "CONVERSION", value: conversionRate, icon: "checkmark.seal.fill", color: .green)
                    }
                    
                    // Funnel Progress Card
                    VStack(alignment: .leading, spacing: 20) {
                        Text("CONVERSION FUNNEL")
                            .font(.caption)
                            .fontWeight(.bold)
                            .foregroundColor(.gray)
                        
                        funnelBar(label: "1. QR Scans / Landing Opened", count: totalScans, percent: 100)
                        funnelBar(label: "2. Review Mode Selected", count: smartModeCount + ultraSmartCount + customCount, percent: 84)
                        funnelBar(label: "3. Redirected to Google (Conversions)", count: conversions, percent: Int(Double(conversions) / Double(totalScans) * 100))
                    }
                    .padding()
                    .background(Color.white.opacity(0.03))
                    .cornerRadius(16)
                    .overlay(
                        RoundedRectangle(cornerRadius: 16)
                            .stroke(Color.white.opacity(0.1), lineWidth: 1)
                    )
                    
                    // Review mode choices breakdown
                    VStack(alignment: .leading, spacing: 16) {
                        Text("REVIEW MODES PREFERRED")
                            .font(.caption)
                            .fontWeight(.bold)
                            .foregroundColor(.gray)
                        
                        HStack(spacing: 16) {
                            modeStat(label: "Smart Mode", count: smartModeCount, color: .orange)
                            modeStat(label: "Ultra Smart", count: ultraSmartCount, color: .pink)
                            modeStat(label: "Custom", count: customCount, color: .purple)
                        }
                    }
                    .padding()
                    .background(Color.white.opacity(0.03))
                    .cornerRadius(16)
                    .overlay(
                        RoundedRectangle(cornerRadius: 16)
                            .stroke(Color.white.opacity(0.1), lineWidth: 1)
                    )
                    
                    Spacer()
                }
                .padding()
            }
        }
    }
    
    func metricCard(title: String, value: String, icon: String, color: Color) -> some View {
        VStack(alignment: .leading, spacing: 12) {
            HStack {
                Image(systemName: icon)
                    .foregroundColor(color)
                Spacer()
            }
            
            Text(value)
                .font(.system(size: 32, weight: .black, design: .rounded))
                .foregroundColor(.white)
            
            Text(title)
                .font(.caption2)
                .fontWeight(.bold)
                .foregroundColor(.gray)
        }
        .padding()
        .frame(maxWidth: .infinity)
        .background(Color.white.opacity(0.03))
        .cornerRadius(16)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(Color.white.opacity(0.1), lineWidth: 1)
        )
    }
    
    func funnelBar(label: String, count: Int, percent: Int) -> some View {
        VStack(alignment: .leading, spacing: 8) {
            HStack {
                Text(label)
                    .font(.footnote)
                    .foregroundColor(.white)
                Spacer()
                Text("\(count) (\(percent)%)")
                    .font(.caption)
                    .foregroundColor(.gray)
            }
            
            GeometryReader { geo in
                ZStack(alignment: .leading) {
                    RoundedRectangle(cornerRadius: 4)
                        .fill(Color.white.opacity(0.1))
                        .frame(height: 8)
                    
                    RoundedRectangle(cornerRadius: 4)
                        .fill(LinearGradient(colors: [.orange, .pink], startPoint: .leading, endPoint: .trailing))
                        .frame(width: geo.size.width * CGFloat(percent) / 100.0, height: 8)
                }
            }
            .frame(height: 8)
        }
    }
    
    func modeStat(label: String, count: Int, color: Color) -> some View {
        VStack(alignment: .leading, spacing: 4) {
            Text("\(count)")
                .font(.headline)
                .foregroundColor(.white)
            Text(label)
                .font(.caption2)
                .foregroundColor(color)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}

struct AnalyticsDashboardView_Previews: PreviewProvider {
    static var previews: some View {
        AnalyticsDashboardView()
    }
}
