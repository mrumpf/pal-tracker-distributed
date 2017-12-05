package main.java.io.pivotal.pal.tracker.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;
import org.springframework.context.annotation.ComponentScan;

@EnableZipkinStreamServer
@ComponentScan("io.pivotal.pal.tracker.zipkin")
public class App {
        public static void main(String[] args) {
            SpringApplication.run(App.class, args);
        }
}
