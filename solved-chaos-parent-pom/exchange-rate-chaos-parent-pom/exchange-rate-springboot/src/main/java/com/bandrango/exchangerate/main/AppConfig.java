package com.bandrango.exchangerate.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan({
	"com.bandrango.exchangerate.controllers",
	"com.bandrango.exchangerate.services.impl",
	"com.bandrango.exchangerate.crud.service.impl"
})
@ImportResource({
	"classpath:/config/exchange-rate-crud.xml"
})
public class AppConfig {

}
