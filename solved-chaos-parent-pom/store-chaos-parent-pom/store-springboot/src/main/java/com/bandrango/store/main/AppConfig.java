package com.bandrango.store.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan({
	"com.bandrango.store.controllers",
	"com.bandrango.store.services.impl",
	"com.bandrango.store.crud.service.impl"
})
@ImportResource({
	"classpath:/config/store-crud.xml"
})
public class AppConfig {

}
