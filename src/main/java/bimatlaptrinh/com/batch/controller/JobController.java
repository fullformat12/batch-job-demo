package bimatlaptrinh.com.batch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobController {

    private final JobLauncher jobLauncher;
    private final Job userJob;

    @GetMapping("/run-job")
    public String runJob() throws Exception {
        jobLauncher.run(userJob, new JobParameters());
        return "Job started!";
    }
}