package bimatlaptrinh.com.batch.monitor;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchMetricsListener implements JobExecutionListener, StepExecutionListener {

    private final MeterRegistry meterRegistry;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job STARTED: {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            meterRegistry.counter("spring_batch_job_completed_total",
                    "job", jobName).increment();
        } else {
            meterRegistry.counter("spring_batch_job_failed_total",
                    "job", jobName).increment();
        }
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step START: {}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String stepName = stepExecution.getStepName();

        meterRegistry.counter("spring_batch_step_read_count",
                "step", stepName).increment(stepExecution.getReadCount());

        meterRegistry.counter("spring_batch_step_write_count",
                "step", stepName).increment(stepExecution.getWriteCount());

        return stepExecution.getExitStatus();
    }
}