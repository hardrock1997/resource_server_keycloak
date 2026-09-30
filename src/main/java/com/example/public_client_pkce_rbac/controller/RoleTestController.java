package com.example.public_client_pkce_rbac.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoleTestController {

    @GetMapping("/user/profile")
//    @PreAuthorize("hasRole('public_pkce_client_normal')")
    public String userProfile() {

        return "Hello Normal User";
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('public_pkce_client_admin')")
    public String adminDashboard() {

        return "Hello Admin User";
    }

}