package bimatlaptrinh.com.batch.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobMonitoringListener implements JobExecutionListener, StepExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("🚀 Job STARTED: {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("🏁 Job FINISHED with status: {}", jobExecution.getStatus());
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("❌ Job FAILED: {}", jobExecution.getAllFailureExceptions());
        }
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("➡️ Step STARTED: {}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("⬅️ Step FINISHED: {} | ReadCount={} | WriteCount={}",
                stepExecution.getStepName(),
                stepExecution.getReadCount(),
                stepExecution.getWriteCount()
        );
        return stepExecution.getExitStatus();
    }
}
