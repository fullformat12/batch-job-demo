package bimatlaptrinh.com.batch.config;

import bimatlaptrinh.com.batch.model.User;
import bimatlaptrinh.com.batch.pocessor.UserProcessor;
import bimatlaptrinh.com.batch.reader.UserReader;
import bimatlaptrinh.com.batch.tasklet.ExportExcelTasklet;
import bimatlaptrinh.com.batch.writer.UserExcelWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@EnableBatchProcessing not needed with Spring Boot 3+
@RequiredArgsConstructor
public class BatchConfig {

    private final UserReader userReader;
    private final UserProcessor processor;
    private final UserExcelWriter writer;
    private final ExportExcelTasklet exportExcelTasklet;

    @Bean
    public Step readDbStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("readDbStep", jobRepository)
                .<User, User>chunk(5, tx)
                .reader(userReader.dbReader(null))
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step exportExcelStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("exportExcelStep", jobRepository)
                .tasklet(exportExcelTasklet, tx)
                .build();
    }

    @Bean
    public Job userJob(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new JobBuilder("userJob", jobRepository)
                .start(readDbStep(jobRepository, tx))
                .next(exportExcelStep(jobRepository, tx))
                .build();
    }
}