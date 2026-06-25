import SwiftUI
import CoreImage
import CoreImage.CIFilterBuiltins

class QRGeneratorService {
    static let shared = QRGeneratorService()
    private let context = CIContext()
    private let filter = CIFilter.qrCodeGenerator()
    
    /**
     Generates a stylized QR Code image
     */
    func generateQRCode(
        from urlString: String,
        foregroundColor: Color = .black,
        backgroundColor: Color = .white,
        logo: UIImage? = nil
    ) -> UIImage? {
        guard let data = urlString.data(using: .ascii) else { return nil }
        filter.message = data
        filter.correctionLevel = "H" // High error correction to allow logo overlays
        
        guard let outputImage = filter.outputImage else { return nil }
        
        // Scale the image (CoreImage QR outputs are small, e.g., 23x23)
        let scaleX = 512.0 / outputImage.extent.size.width
        let scaleY = 512.0 / outputImage.extent.size.height
        let transformedImage = outputImage.transformed(by: CGAffineTransform(scaleX: scaleX, y: scaleY))
        
        // Colorize using CIColorMonochrome
        let uiColorFront = UIColor(foregroundColor)
        let uiColorBack = UIColor(backgroundColor)
        
        // Convert to colored image
        let colorFilter = CIFilter(name: "CIFalseColor")
        colorFilter?.setValue(transformedImage, forKey: kCIInputImageKey)
        colorFilter?.setValue(CIColor(color: uiColorFront), forKey: "inputColor0") // Foreground
        colorFilter?.setValue(CIColor(color: uiColorBack), forKey: "inputColor1") // Background
        
        guard let coloredImage = colorFilter?.outputImage,
              let cgImage = context.createCGImage(coloredImage, from: coloredImage.extent) else {
            return nil
        }
        
        let qrImage = UIImage(cgImage: cgImage)
        
        // Overlay Logo if provided
        if let logo = logo {
            return overlayLogo(on: qrImage, with: logo)
        }
        
        return qrImage
    }
    
    private func overlayLogo(on qrImage: UIImage, with logoImage: UIImage) -> UIImage {
        let size = qrImage.size
        UIGraphicsBeginImageContext(size)
        
        qrImage.draw(in: CGRect(origin: .zero, size: size))
        
        // Center logo overlay (max 20% width/height of QR code)
        let logoWidth = size.width * 0.20
        let logoHeight = size.height * 0.20
        let logoRect = CGRect(
            x: (size.width - logoWidth) / 2,
            y: (size.height - logoHeight) / 2,
            width: logoWidth,
            height: logoHeight
        )
        
        // Draw white card behind logo for contrast
        let path = UIBezierPath(roundedRect: logoRect.insetBy(dx: -4, dy: -4), cornerRadius: 8)
        UIColor.white.setFill()
        path.fill()
        
        logoImage.draw(in: logoRect)
        
        let finalImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        return finalImage ?? qrImage
    }
}
