package com.spring.mongo.jobs;

import com.spring.mongo.jobs.entity.Company;
import com.spring.mongo.jobs.entity.Job;
import com.spring.mongo.jobs.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
        System.out.println("JobApplication is running...");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory,
                                       MappingMongoConverter converter) {
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return new MongoTemplate(databaseFactory, converter);
    }

    @Bean
    CommandLineRunner commandLineRunner(JobRepository jobRepository) {
        return args -> {

            jobRepository.deleteAll();

            List<Job> jobs = Arrays.asList(
                    new Job("1", "Senior Vue Dev", "Full-Time", "Boston, MA",
                            "We are seeking a talented Front-End Developer to join our team in Boston, MA. The ideal candidate will have strong skills in HTML, CSS, and JavaScript, with experience working with modern JavaScript frameworks such as Vue or Angular.",
                            "$90K - $100K", LocalDate.now().minusDays(1),
                            new Company("NewTek Solutions",
                                    "NewTek Solutions is a leading technology company specializing in web development and digital solutions.",
                                    "contact@teksolutions.com",
                                    "555-555-5555")),
                    new Job("2", "Front-End Engineer (Vue)", "Full-Time", "Miami, FL",
                            "Join our team as a Front-End Developer in sunny Miami, FL. We are looking for a motivated individual with a passion for crafting beautiful and responsive web applications.",
                            "$70K - $80K", LocalDate.now().minusDays(5),
                            new Company("Veneer Solutions",
                                    "Veneer Solutions is a creative agency specializing in digital design and development.",
                                    "contact@loremipsum.com",
                                    "555-555-5555")),
                    new Job("3", "Vue.js Developer", "Full-Time", "Brooklyn, NY",
                            "Are you passionate about front-end development? Join our team in vibrant Brooklyn, NY, and work on exciting projects that make a difference.",
                            "$70K - $80K", LocalDate.now().minusWeeks(2),
                            new Company("Dolor Cloud",
                                    "Dolor Cloud is a leading technology company specializing in digital solutions.",
                                    "contact@dolorsitamet.com",
                                    "555-555-5555")),
                    new Job("4", "Vue Front-End Developer", "Part-Time", "Pheonix, AZ",
                            "Join our team as a Part-Time Front-End Developer in beautiful Pheonix, AZ. We are looking for a self-motivated individual with a passion for creating engaging user experiences.",
                            "$60K - $70K", LocalDate.now().minusDays(4),
                            new Company("Alpha Elite",
                                    "Alpha Elite is a dynamic startup specializing in digital marketing and web development.",
                                    "contact@adipisicingelit.com",
                                    "555-555-5555")),
                    new Job("5", "Full Stack Vue Developer", "Full-Time", "Atlanta, GA",
                            "Exciting opportunity for a Full-Time Front-End Developer in bustling Atlanta, GA. We are seeking a talented individual with a passion for building elegant and scalable web applications.",
                            "$90K - $100K", LocalDate.now().minusMonths(2),
                            new Company("Browning Technologies",
                                    "Browning Technologies is a rapidly growing technology company specializing in e-commerce solutions.",
                                    "contact@consecteturadipisicing.com",
                                    "555-555-5555")),
                    new Job("6", "Vue Native Developer", "Full-Time", "Portland, OR",
                            "Join our team as a Front-End Developer in beautiful Portland, OR. We are looking for a skilled and enthusiastic individual to help us create innovative web solutions.",
                            "$100K - $110K", LocalDate.now().minusWeeks(3),
                            new Company("Port Solutions INC",
                                    "Port Solutions is a leading technology company specializing in software development and digital marketing.",
                                    "contact@ipsumlorem.com",
                                    "555-555-5555")),
                    new Job("7", "Vue.js Lead Developer", "Full-Time", "San Francisco, CA",
                            "We're looking for a Vue.js Lead Developer to join our team in San Francisco, CA. You'll be leading the front-end team and collaborating with back-end engineers to deliver world-class web applications.",
                            "$120K - $130K", LocalDate.now(),
                            new Company("Web Innovations Inc.",
                                    "Web Innovations is a fast-growing tech startup providing innovative web development services.",
                                    "hr@webinnovations.com",
                                    "555-123-4567"))
            );

            jobRepository.saveAll(jobs);
        };
    }
}
