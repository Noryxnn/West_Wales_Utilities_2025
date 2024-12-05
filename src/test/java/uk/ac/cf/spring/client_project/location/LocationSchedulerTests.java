package uk.ac.cf.spring.client_project.location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LocationSchedulerTests {
    @Autowired
    private ScheduledTaskHolder scheduledTaskHolder;

    @Test
    public void archiveSchedulerTaskScheduled() {
        // Adapted from: https://stackoverflow.com/questions/64880738/test-a-scheduled-function-in-spring-boot-with-cron-property
        Set<ScheduledTask> scheduledTasks = scheduledTaskHolder.getScheduledTasks();
        long count = scheduledTasks.stream()
                .filter(scheduledTask -> scheduledTask.getTask() instanceof CronTask)
                .map(scheduledTask -> (CronTask) scheduledTask.getTask())
                .filter(cronTask -> cronTask.getExpression().equals("0 0 0 * * *")
                        && cronTask.toString().contains("LocationServiceImpl.archiveScheduler"))
                .count();

        assertThat(count).isEqualTo(1L);
    }
}
