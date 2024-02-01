package fbc.com.felipeBarbo.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    /**
     * ID
     * Usuário (ID do usuário)
     * Descrioção
     * Título
     * Data de Início
     * Data de término
     * Prioridade
     *
     */

 @Id
 @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;

    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public void setTitle(String title) throws Exception{

        if(title.length() > 50){
            throw new Exception("O campo title deve conter no máximo 50 caracteres");
        }
        this.title = title;
    }



}