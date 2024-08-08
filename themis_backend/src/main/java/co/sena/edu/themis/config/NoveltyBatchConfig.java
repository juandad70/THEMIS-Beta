package co.sena.edu.themis.config;

import co.sena.edu.themis.Entity.Novelty;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class NoveltyBatchConfig {

    @Bean
    public JpaPagingItemReader<Novelty> reader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Novelty>()
                .name("noveltyReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT n FROM Novelty n")
                .pageSize(10)
                .build();
    }

    @Bean
    public NoveltyItemProcessor processor() {
        return new NoveltyItemProcessor();
    }

    @Bean
    public JpaItemWriter<Novelty> writer(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Novelty> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Job processNoveltyJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("processNoveltyJob", jobRepository)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      JpaPagingItemReader<Novelty> reader, NoveltyItemProcessor processor, JpaItemWriter<Novelty> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Novelty, Novelty>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}