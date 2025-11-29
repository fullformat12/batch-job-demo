package bimatlaptrinh.com.batch.tasklet;

import bimatlaptrinh.com.batch.writer.UserExcelWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExportExcelTasklet implements Tasklet {
    private final UserExcelWriter excelWriter;

    @Override
    public RepeatStatus execute(
            org.springframework.batch.core.StepContribution contribution,
            org.springframework.batch.core.scope.context.ChunkContext chunkContext
    ) throws Exception {

        excelWriter.writeToExcel();
        System.out.println("File Excel đã được xuất thành công!");
        return RepeatStatus.FINISHED;
    }
}
