package com.cadatro.demo.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistration;

import static org.mockito.Mockito.*;

class CorsConfigTest {

    @Test
    @DisplayName("Testa se as configurações de CORS são aplicadas corretamente")
    void addCorsMappingsTest() {
        // Arrange
        CorsConfig corsConfig = new CorsConfig();
        CorsRegistry corsRegistry = mock(CorsRegistry.class);
        CorsRegistration corsRegistration = mock(CorsRegistration.class);

        when(corsRegistry.addMapping("/**")).thenReturn(corsRegistration);
        when(corsRegistration.allowedOrigins("*")).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods("GET", "POST", "PUT")).thenReturn(corsRegistration);

        // Act
        corsConfig.addCorsMappings(corsRegistry);

        // Assert
        verify(corsRegistry).addMapping("/**");
        verify(corsRegistration).allowedOrigins("*");
        verify(corsRegistration).allowedMethods("GET", "POST", "PUT");
    }
}