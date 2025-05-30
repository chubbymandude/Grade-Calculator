package project.GradeCalculator;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/*
    This class prevents fetches from being blocked by CORS
 */
@Configuration
public class WebConfig implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**") // Applies to all endpoints
        .allowedOrigins("http://127.0.0.1:5501") // Allow your frontend origin
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*");
    }
}
