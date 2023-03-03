package com.poc.batch.config.batch;

import com.poc.batch.dto.PushAppCampaignItemDto;
import com.poc.batch.listener.PushAppCampaignJobExecutionListener;
import com.poc.batch.model.PushAppCampaignItemEntity;
import com.poc.batch.processor.PushAppCampaignItemProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory ;

    @Bean
    public ItemReader<PushAppCampaignItemDto> reader(DataSource dataSource) {

        return new JdbcCursorItemReaderBuilder<PushAppCampaignItemDto>()
                .name("book_reader")
                .sql("EXEC FIND_CAMPANAS_PUSH_APP")
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(PushAppCampaignItemDto.class))
                .build();
    }

    @Bean
    public PushAppCampaignItemProcessor processor() {

        return new PushAppCampaignItemProcessor();
    }

    @Bean
    public ItemWriter<PushAppCampaignItemEntity> writer(DataSource dataSource) {

        JdbcBatchItemWriterBuilder<PushAppCampaignItemEntity> jdbcBatchItemWriterBuilder = new JdbcBatchItemWriterBuilder<>();
        jdbcBatchItemWriterBuilder.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        jdbcBatchItemWriterBuilder.sql("INSERT INTO PUSH_APP_CAMPAIGN_HISTORIC\n" +
                "(id, line, campaign, message, validate, flag)\n" +
                "VALUES(:id, :line, :campaign, :message, :validate, :flag)");
        jdbcBatchItemWriterBuilder.assertUpdates(false);
        jdbcBatchItemWriterBuilder.dataSource(dataSource);
        return jdbcBatchItemWriterBuilder.build();
    }

    @Bean
    public Job createPushAppCampaignJob(PushAppCampaignJobExecutionListener listener, Step step1) {
        return jobBuilderFactory
                .get("createPushAppCampaignJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemReader<PushAppCampaignItemDto> reader, ItemWriter<PushAppCampaignItemEntity> writer,
                      ItemProcessor<PushAppCampaignItemDto, PushAppCampaignItemEntity> processor) {
        return stepBuilderFactory
                .get("step1")
                .<PushAppCampaignItemDto, PushAppCampaignItemEntity>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
