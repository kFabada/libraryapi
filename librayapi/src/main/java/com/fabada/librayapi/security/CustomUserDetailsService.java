package com.fabada.librayapi.security;

import com.fabada.librayapi.model.Usuario;
import com.fabada.librayapi.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Usuario usuario = usuarioService.obterPorLogin(login);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getRoles()
                        .toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
