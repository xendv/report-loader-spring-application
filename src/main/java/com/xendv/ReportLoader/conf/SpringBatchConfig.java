package com.xendv.ReportLoader.conf;

import com.xendv.ReportLoader.JobCompletionNotificationListener;
import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.processor.CompanyInfoProcessor;
import com.xendv.ReportLoader.processor.RecordFieldSetMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.net.MalformedURLException;

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

    @Bean
    public ItemReader<CompanyInfo> reader() // мб пакет не тот
            throws UnexpectedInputException, ParseException {
        FlatFileItemReader<CompanyInfo> reader = new FlatFileItemReader<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"username", "userid", "transactiondate", "amount"};
        tokenizer.setNames(tokens);
        reader.setResource(new ClassPathResource("animescsv.csv"));
        /*reader.setResource(inputCsv);
                DefaultLineMapper<CompanyInfo> lineMapper =
                        new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
        reader.setLineMapper(lineMapper);*/
        return reader;
    }

    public ItemReader<CompanyInfo> csvReader(@NotNull Resource inputCsv)
            throws UnexpectedInputException, ParseException {
        FlatFileItemReader<CompanyInfo> reader = new FlatFileItemReader<>();
        /*DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"username", "userid", "transactiondate", "amount"};
        tokenizer.setNames(tokens);*/
        reader.setResource(new ClassPathResource("animescsv.csv"));
        reader.setResource(inputCsv);
        DefaultLineMapper<CompanyInfo> lineMapper =
                new DefaultLineMapper<>();
        //lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
        reader.setLineMapper(lineMapper);
        return reader;
    }

    /*@Bean
    public ItemProcessor<CompanyInfo, CompanyInfo> processor() {
        return new CompanyInfoProcessor();
    }*/

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
    public ItemWriter<CompanyInfo> writer(Resource resource)
            throws MalformedURLException {
        JsonFileItemWriterBuilder<CompanyInfo> builder = new JsonFileItemWriterBuilder<>();
        JacksonJsonObjectMarshaller<CompanyInfo> marshaller = new JacksonJsonObjectMarshaller<>();
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
    protected Step step1(ItemReader<CompanyInfo> reader,
                         ItemProcessor<CompanyInfo, CompanyInfo> processor,
                         ItemWriter<CompanyInfo> writer) {

        return stepBuilderFactory.get("step1")
                .<CompanyInfo, CompanyInfo>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "csvToJSONJob")
    public Job csvToJsonJob(JobCompletionNotificationListener listener,
                            @Qualifier("step1") Step step1) {
        return jobBuilderFactory.get("csvToJSONJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean(name = "jsonToDbJob")
    public Job jsonToDbJob(JobCompletionNotificationListener listener,
                           @Qualifier("step1") Step step1) {
        return jobBuilderFactory.get("jsonToDbJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

}
