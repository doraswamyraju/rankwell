import Foundation

enum APIError: Error {
    case invalidURL
    case noData
    case parsingError
    case serverError(String)
}

class APIService: ObservableObject {
    static let shared = APIService()
    private let baseURL = "http://localhost:5000/api" // Replace with your VPS domain or IP in production
    
    @Published var token: String? {
        didSet {
            UserDefaults.standard.set(token, forKey: "auth_token")
        }
    }
    
    init() {
        self.token = UserDefaults.standard.string(forKey: "auth_token")
    }
    
    func register(name: String, email: String, password: String) async throws -> [String: Any] {
        guard let url = URL(string: "\(baseURL)/auth/register") else { throw APIError.invalidURL }
        
        let body: [String: String] = ["name": name, "email": email, "password": password]
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = try? JSONSerialization.data(withJSONObject: body)
        
        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
            throw APIError.serverError("Registration failed")
        }
        
        if let json = try? JSONSerialization.jsonObject(with: data) as? [String: Any] {
            if let token = json["token"] as? String {
                DispatchQueue.main.async {
                    self.token = token
                }
            }
            return json
        }
        throw APIError.parsingError
    }
    
    func login(email: String, password: String) async throws -> [String: Any] {
        guard let url = URL(string: "\(baseURL)/auth/login") else { throw APIError.invalidURL }
        
        let body: [String: String] = ["email": email, "password": password]
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.httpBody = try? JSONSerialization.data(withJSONObject: body)
        
        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
            throw APIError.serverError("Invalid credentials")
        }
        
        if let json = try? JSONSerialization.jsonObject(with: data) as? [String: Any] {
            if let token = json["token"] as? String {
                DispatchQueue.main.async {
                    self.token = token
                }
            }
            return json
        }
        throw APIError.parsingError
    }
    
    func previewBusinessLink(mapsUrl: String) async throws -> [String: Any] {
        guard let url = URL(string: "\(baseURL)/businesses/preview-link") else { throw APIError.invalidURL }
        guard let token = token else { throw APIError.serverError("No active session") }
        
        let body: [String: String] = ["googleMapsUrl": mapsUrl]
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.setValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        request.httpBody = try? JSONSerialization.data(withJSONObject: body)
        
        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
            throw APIError.serverError("Link verification failed")
        }
        
        if let json = try? JSONSerialization.jsonObject(with: data) as? [String: Any] {
            return json
        }
        throw APIError.parsingError
    }
    
    func onboardBusiness(name: String, address: String, mapsUrl: String) async throws -> [String: Any] {
        guard let url = URL(string: "\(baseURL)/businesses") else { throw APIError.invalidURL }
        guard let token = token else { throw APIError.serverError("No active session") }
        
        let body: [String: String] = ["name": name, "address": address, "googleMapsUrl": mapsUrl]
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.setValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        request.httpBody = try? JSONSerialization.data(withJSONObject: body)
        
        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
            throw APIError.serverError("Onboarding failed")
        }
        
        if let json = try? JSONSerialization.jsonObject(with: data) as? [String: Any] {
            return json
        }
        throw APIError.parsingError
    }
}
