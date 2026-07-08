package com.salon.backend.DTOs.Auth.Dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardLoginResponse {
    private String token;
    private String type="Bearer";

}
