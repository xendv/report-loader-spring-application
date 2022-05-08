package com.xendv.ReportLoader.conf;

import com.xendv.ReportLoader.model.CompanyInfo;
import com.xendv.ReportLoader.processor.CompanyInfoProcessor;
import com.xendv.ReportLoader.processor.RecordFieldSetMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import javax.xml.bind.Marshaller;
import java.net.MalformedURLException;

public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Value("input/record.csv")
    private Resource inputCsv;

    @Value("file:xml/output.xml")
    private Resource outputXml;

    @Bean
    public ItemReader<CompanyInfo> itemReader() // мб пакет не тот
            throws UnexpectedInputException, ParseException {
        FlatFileItemReader<CompanyInfo> reader = new FlatFileItemReader<CompanyInfo>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"username", "userid", "transactiondate", "amount"};
        tokenizer.setNames(tokens);
        reader.setResource(inputCsv);
        DefaultLineMapper<CompanyInfo> lineMapper =
                new DefaultLineMapper<CompanyInfo>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public ItemProcessor<CompanyInfo, CompanyInfo> itemProcessor() {
        return new CompanyInfoProcessor();
    }

    @Bean
    public ItemWriter<CompanyInfo> itemWriter(Marshaller marshaller)
            throws MalformedURLException {
        StaxEventItemWriter<CompanyInfo> itemWriter =
                new StaxEventItemWriter<CompanyInfo>();
        itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("transactionRecord");
        itemWriter.setResource(outputXml);
        return itemWriter;
    }

    @Bean
    public Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(new Class[]{CompanyInfo.class});
        return marshaller;
    }

    @Bean
    protected Step step1(ItemReader<CompanyInfo> reader,
                         ItemProcessor<CompanyInfo, CompanyInfo> processor,
                         ItemWriter<CompanyInfo> writer) {
        return steps.get("step1").<CompanyInfo, CompanyInfo>chunk(10)
                .reader(reader).processor(processor).writer(writer).build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobs.get("firstBatchJob").start(step1).build();
    }

}
