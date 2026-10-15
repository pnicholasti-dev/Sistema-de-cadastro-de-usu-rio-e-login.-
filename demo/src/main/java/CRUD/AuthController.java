package CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {

        Usuario usuario = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos!"));


        if (!usuario.isAtivo()) {
            return ResponseEntity.status(403).body("Conta de usuário inativa.");
        }


        boolean senhaValida = passwordEncoder.matches(dto.getSenha(), usuario.getSenha());

        if (!senhaValida) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos!");
        }


        String token = jwtService.gerarToken(usuario.getEmail());


        return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok("Logout realizado com sucesso! Remova o token no cliente.");
    }
}
