package com.example.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class Application {
	static OriginTrackedMapPropertySource conf;

	public static void main(String[] args) {
		try {
			Application.conf = (OriginTrackedMapPropertySource) Application.yamlPropertySourceLoader();
			System.out.println("Conf. loaded: " + Application.conf);
		} catch (IOException e) {
			System.err.println("Review how are you saving your application properties...");
			e.printStackTrace();
		}
		
		SpringApplication.run(Application.class, args);
	}

	public static PropertySource<?> yamlPropertySourceLoader() throws IOException {
		YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
		List<PropertySource<?>> applicationYamlPropertySource = loader.load("application.yml",
				new ClassPathResource("application.yml"));
		return applicationYamlPropertySource.get(0); // By the time we have just one...
	}

	public static boolean atUCM() {
		return conf.getProperty("atUCM") != null;
	}

	public static HashMap<String, String> getDataSourceProps() {
		HashMap<String, String> c = new HashMap<>(3);
		if (atUCM()) {
			// Load OracleDB Config for UCM
			c.put("url", (String) conf.getProperty("spring.ucm-datasource.url"));
			c.put("username", (String) conf.getProperty("spring.ucm-datasource.username"));
			c.put("password", (String) conf.getProperty("spring.ucm-datasource.password"));
			c.put("driver-class-name", (String) conf.getProperty("spring.ucm-datasource.driver-class-name"));
		} else {
			c.put("url", (String) conf.getProperty("spring.datasource.url"));
			c.put("username", (String) conf.getProperty("spring.datasource.username"));
			c.put("password", (String) conf.getProperty("spring.datasource.password"));
			c.put("driver-class-name", (String) conf.getProperty("spring.datasource.driver-class-name"));
		}
		return c;
	}
}