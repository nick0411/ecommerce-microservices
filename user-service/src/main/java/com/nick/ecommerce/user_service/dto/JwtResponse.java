package com.nick.ecommerce.user_service.dto;

import java.util.List;

public record JwtResponse(String token, String username, List<String> roles) {
}
