package br.com.drop.demo.jwt;

import br.com.drop.demo.model.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinada;



    public String gerarToken(Usuario usuario){

        long expString = Long.valueOf(expiracao);
        LocalDateTime dadaHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Date data = Date.from(dadaHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        String active = usuario.is_active_user() ? "false" : "true";

        return Jwts.builder()
                .setIssuer("drop_api") // Emitente do token
                .setSubject(usuario.getEmail())
                .claim("is_active", active)
                .setIssuedAt(new Date()) // Data de emissão do token
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinada)
                .compact();


    }



    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinada)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token){
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){
            return false;
        }
    }

    /*public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            boolean usuarioAtivo = (boolean) claims.get("ativo");
            System.out.println("USUARIO: " + usuarioAtivo);
            return !claims.getExpiration().before(new Date()) && usuarioAtivo;
        } catch (Exception e) {
            return false;
        }
    }*/




    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        Claims claims = obterClaims(token);
        return claims.getSubject();
    }






}
