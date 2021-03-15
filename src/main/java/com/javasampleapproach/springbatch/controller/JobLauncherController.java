package com.javasampleapproach.springbatch.controller;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class JobLauncherController {
 
    @Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    List<Job> jobs;
 
    @RequestMapping("/launchjob/{jobName}")
    public String handle(@PathVariable String jobName) throws Exception {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            Optional<Job> jobOptional=jobs.stream().filter(job->job.getName().equals(jobName)).findFirst();
            jobOptional.ifPresent(job-> {
                try {
                    jobLauncher.run(job, jobParameters);
                } catch (JobExecutionAlreadyRunningException e) {
                    e.printStackTrace();
                } catch (JobRestartException e) {
                    e.printStackTrace();
                } catch (JobInstanceAlreadyCompleteException e) {
                    e.printStackTrace();
                } catch (JobParametersInvalidException e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            logger.info(e.getMessage());
        }
 
        return "Done";
    }
}