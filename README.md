# todoList-spring-boot


# todoList-api 🚀

## 2. Como rodar o desafio 🔧

Basta seguir esse passo a passo:

```
git clone <https://github.com/Felipe-barbos/todoList-spring-boot.git>
cd todoList-spring-boot
mvn spring-boot:run

```

## 3. Rotas e suas funcionalidades ⚙️

### POST /localhost:8080/users/

A rota recebe name, email e password dentro do corpo da requisição, salva o usuário criado no banco em memória.

exemplo de instanciamento passando no request.body:
```js 
{
	"name": "Felipe Barbosa",
	"username": "felipe",
	"password": "paladino"
}

```

### POST /localhost:8080/tasks/

A rota recebe `email` e `password` no Basic auth, e os valores de uma task no corpo, assim criando a task no banco em memória

exemplo de instanciamento passando no request.body:
```js 
{
	"description": "teste 3",
	"title": "aula 1",
		"priority": "MÁXIMA",
	"startAt": "2024-06-10T12:03:00",
	"endAt": "2024-06-13T13:04:00",
	
}

```

### GET /localhost:8080/tasks/

A rota recebe `email` e `password` no Basic auth, e retornar todas as tasks criadas pelo usuário

### PUT /localhost:8080/tasks/:id

A rota recebe `email` e `password` no Basic auth, e o id da nota no route params que o usuário deseja atualizar as informações da nota.


exemplo de instanciamento passando no request.body:

```js
{
	"description": "teste 3",
	"title": "aula 1 (atualizando)",
		"priority": "MÍNIMA",
	"startAt": "2024-06-10T12:03:00",
	"endAt": "2024-06-13T13:04:00",
	
}
```





