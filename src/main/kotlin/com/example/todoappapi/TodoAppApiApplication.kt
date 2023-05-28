package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.stereotype.Service
import org.springframework.jdbc.core.JdbcTemplate
import java.util.*

@SpringBootApplication
class TodoApplication

fun main(args: Array<String>) {
    runApplication<TodoApplication>(*args)
}

@RestController
class TodosController(val service: MessageService) {
    @GetMapping("/")
    fun index(): List<Todo> = service.findTodos()

    @PostMapping("/")
    fun post(@RequestBody todos: List<Todo>) {
        service.save(todos)
    }
}

data class Todo(
    val id: String,
    val title: String,
    val date: String,
    val time: String,
    val description: String,
    var isChecked: Boolean,
)

@Service
class MessageService(val db: JdbcTemplate) {
    fun findTodos(): List<Todo> = db.query("select * from todos") { response, _ ->
        Todo(
            response.getString("id"),
            response.getString("title"),
            response.getString("date"),
            response.getString("time"),
            response.getString("description"),
            response.getBoolean("isChecked"),
        )
    }

    fun save(todos: List<Todo>){
        for (todo in todos) {
            val id = todo.id ?: UUID.randomUUID().toString()
            db.update("insert into todos values ( ?, ?, ?, ?, ?, ?)",
                id, todo.title, todo.description, todo.date, todo.time, todo.isChecked )
        }
    }
}