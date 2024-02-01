package fbc.com.felipeBarbo.todolist.task;


import fbc.com.felipeBarbo.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {


    @Autowired
    private ITaskRepository taksRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){

        //Recuperando o id do usuário authenticado
        var idUser = request.getAttribute("idUser");

        //armazenando o id do usuário na taskmodel
        taskModel.setIdUser((UUID) idUser);


        var currentDate = LocalDateTime.now();

        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início / data de término deve ser maior do que a data atual");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início  deve ser maior do que a data de termino");
        }


        var task = this.taksRepository.save(taskModel);

        return  ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> List(HttpServletRequest request){

        var idUser = request.getAttribute("idUser");
       var tasks =  this.taksRepository.findByIdUser((UUID) idUser);

       return tasks;
    }



    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){


        var task = this.taksRepository.findById(id).orElse(null);

        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tarefa não encontrada");
        }

        var idUser = request.getAttribute("idUser");

        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário não tem permissão para alaterar essa tarefa");
        }

         Utils.copyNonNullProperties(taskModel, task);



        var taskUpdated = this.taksRepository.save(task);

        return ResponseEntity.ok().body(taskUpdated);

    }
}
