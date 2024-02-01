package fbc.com.felipeBarbo.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fbc.com.felipeBarbo.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;


@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired

    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        var servletPath = request.getServletPath();

        if(servletPath.startsWith("/tasks/")){
            //Pegar a autenticação (usuário e senha)
            var authorization = request.getHeader("Authorization");




            //separando o Basic do encode
            var authEncoded = authorization.substring("Basic".length()).trim();

            // decodando o meu authEncoded
            byte [] authDecode = Base64.getDecoder().decode(authEncoded);

            //convertendo o authDoecode para string
            var authString = new String(authDecode);


            //separando os valores entre ":" e colocando no array credentials
            String[] credentials = authString.split(":");

            //recebendo os valores do array credentials
            String username = credentials[0];
            String password = credentials[1];

            // Validar usuário

            var user = this.userRepository.findByUsername(username);
            if(user == null){
                response.sendError(401);
            }else{

                //Validando a senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if(passwordVerify.verified ){
                    // Segue o processo

                    //pegando o id do usuário e setando no request
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request,response);
                }else{
                    response.sendError(401);
                }

            }

        }else {
            filterChain.doFilter(request,response);
        }



    }
}
