package ru.bsluckyanchuk.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsluckyanchuk.dto.WorkoutRequest;
import ru.bsluckyanchuk.entity.Workout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkoutController {

    private final HashMap<Long, Workout> dataBase = new HashMap<>();

    // Получение списка всех тренировок
    @GetMapping("/workouts")
    public List<Workout> getAllWorkouts() {
        return new ArrayList<>(dataBase.values());
    }

    // Создание новой тренировки
    @PostMapping("/workouts")
    public ResponseEntity<String> createWorkout(@RequestBody WorkoutRequest workoutRequest) {
        Workout result = new Workout();
        result.setName(workoutRequest.getName());
        result.setDescipt(workoutRequest.getDescipt());
        result.setCoachName(workoutRequest.getCoachName());
        result.setDuration(workoutRequest.getDuration());
        dataBase.put((long) dataBase.size(), result);
        return ResponseEntity.ok("My id is " + (dataBase.size() - 1));
    }

    // Получение информации о конкретной тренировке
    @GetMapping("/workouts/{id}")
    public ResponseEntity<String> getWorkoutById(@PathVariable("id") Long id) {
        Workout answer = dataBase.get(id);
        return ResponseEntity.ok("Workout name is: " + answer.getName() + " description: " + answer.getDescipt() + " coach Name: " + answer.getCoachName());
    }

    // Обновление информации о тренировке
    @PutMapping("/workouts/{id}")
    public ResponseEntity<String> updateWorkout(@PathVariable("id") Long id, @RequestBody WorkoutRequest workoutRequest) {
        Workout result = new Workout();
        result.setName(workoutRequest.getName());
        result.setDescipt(workoutRequest.getDescipt());
        result.setCoachName(workoutRequest.getCoachName());
        result.setDuration(workoutRequest.getDuration());
        dataBase.put((long) dataBase.size(), result);
        dataBase.replace(id, result);
        return ResponseEntity.ok("Information is updated");
    }

    // Удаление тренировки
    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<String> deleteWorkout(@PathVariable("id") Long id) {
        dataBase.remove(id);
        return ResponseEntity.ok("Information is removed");
    }
}
