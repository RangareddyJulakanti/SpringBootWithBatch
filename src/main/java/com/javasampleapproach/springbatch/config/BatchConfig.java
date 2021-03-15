package com.javasampleapproach.springbatch.config;
 
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import com.javasampleapproach.springbatch.step.Processor;
import com.javasampleapproach.springbatch.step.Reader;
import com.javasampleapproach.springbatch.step.Writer;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BatchConfig {
 
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
 
     
    @Bean
    public Job job1() {
        return jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
    @Bean
    public Job job2() {
        return jobBuilderFactory.get("job2")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }
   @Bean
   public RestTemplate restTemplate(){
        return new RestTemplate();
   }
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String> chunk(1)
                .reader(new Reader())
                .processor(new Processor())
                .writer(new Writer())
                .build();
    }

}
