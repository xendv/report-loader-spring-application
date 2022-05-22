package com.xendv.ReportLoader.conf;

import com.xendv.ReportLoader.JobCompletionNotificationListener;
import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.model.FullInfo;
import com.xendv.ReportLoader.processor.CompanyInfoProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.stream.Collectors;

//@Configuration
//@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /*
        @Value("input/record.csv")
        private Resource inputCsv;

        @Value("file:xml/output.xml")
        private Resource outputXml;
    */

    /*    @Bean(name = "jobLauncher")
        public JobLauncher jobLauncher() throws Exception {
            SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
            jobLauncher.afterPropertiesSet();
            return jobLauncher;
        }*/
    @Bean
    @StepScope
    public FlatFileItemReader<FullInfo> reader(@Value("#{jobParameters['filePath']}") @NotNull String filePath) {
        FlatFileItemReader<FullInfo> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(filePath));
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                var headers = Arrays.stream(FullInfo.class.getFields())
                        .map(Field::getName)
                        .collect(Collectors.toList());
                setNames(headers.toArray(String[]::new));
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(FullInfo.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<CompanyInfo, String> companyToJsonProcessor() {
        return new CompanyInfoProcessor();
    }

    @Bean
    public ItemWriter<CompanyInfo> writer(DataSource dataSource)
            throws MalformedURLException {

        /*itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("transactionRecord");
        itemWriter.setResource(outputXml);*/
        return new FlatFileItemWriter<>();
    }


    @Bean
    @StepScope
    public ItemWriter<FullInfo> writer(Resource resource)
            throws MalformedURLException {
        JsonFileItemWriterBuilder<FullInfo> builder = new JsonFileItemWriterBuilder<>();
        JacksonJsonObjectMarshaller<FullInfo> marshaller = new JacksonJsonObjectMarshaller<>();
        return builder
                .name("companyInfoItemWriter")
                .jsonObjectMarshaller(marshaller)
                .resource(resource)
                .build();

        //return itemWriter;
    }
/*    @Bean
    public Marshaller marshaller() {
        *//*Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(new Class[]{CompanyInfo.class});
        return marshaller;*//*
    }*/

    @Bean
    protected Step step1(ItemReader<FullInfo> reader,
                         ItemProcessor<FullInfo, FullInfo> processor,
                         ItemWriter<FullInfo> writer) {
        return stepBuilderFactory.get("step1")
                .<FullInfo, FullInfo>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "dbfToJSONJob")
    public Job dbfToJsonJob(JobCompletionNotificationListener listener,
                            @Qualifier("step1") Step step1) {
        return jobBuilderFactory.get("dbfToJSONJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }
}
