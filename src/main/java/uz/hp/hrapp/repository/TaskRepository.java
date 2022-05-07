package uz.hp.hrapp.repository;


import uz.hp.hrapp.entity.Task;
import uz.hp.hrapp.entity.User;
import uz.hp.hrapp.entity.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    //    Optional<User> findByEmail(String email);
    List<Task> findAllByTaskGiverAndCreatedAtBetweenAndStatus(User taskGiver, Timestamp startTime, Timestamp endTime, TaskStatus status);

    List<Task> findAllByTaskTaker(User taskTaker);

    List<Task> findAllByTaskGiver(User taskGiver);
//     List<Task> findAllByTaskTakerAndCompletedDateBetween(User taskTaker, Timestamp completedDate, Timestamp completedDate2);
}
