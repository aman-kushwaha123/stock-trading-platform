package com.example.services.servicesImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    /*We use a secret key to generate the digital signature of a JWT.
    When the client sends the token back to the server, Spring Boot uses the same secret key to verify the signature.
    This ensures that the token has not been modified."
     */
    private String secretKey="";

    public JwtService() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");
        SecretKey skey=keyGenerator.generateKey();
        secretKey= Base64.getEncoder().encodeToString(skey.getEncoded());
    }

    public String generateToken(String username){
        /*
    claims (The Variable Name):This is the name of your variable.
    In JSON Web Tokens, the pieces of information
     inside the token's payload are officially called claims because they are assertions or claims made about the user.
*/

        Map<String,Object> claims=new HashMap<>();
        Date expirationDate=Date.from(Instant.now().plus(Duration.ofHours(1)));

        return Jwts.builder() // 1. Start the construction worker to build a new JWT string
                .claims()       //2. Open up the "Payload" (claims) section of the token
                .add(claims)    // Dump your custom Map (like email, user ID) into the payload
                .subject(username)  //4. Set the primary owner of this token (usually the username)
                .issuedAt(new Date(System.currentTimeMillis()))  //1.• Grabs the current time as a raw long number representing milliseconds since January 1, 1970 (UTC).
                .expiration(expirationDate)                       //Wraps those raw milliseconds into a legacy Java java.util.Date object so the application can work with it as a timestamp.
                .and()
                .signWith(getKey())
                .compact(); //this will generate token for us
    }

    private Claims extractClaims(String token){
           return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getKey(){
        byte[] keyBytes=Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
            final String username=extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)?true:false);
    }

    public Boolean isTokenExpired(String token){
        //return extractClaims(token).getExpiration();
        /*return extractClaim(token, new Function<Claims, Date>() {
            @Override
            public Date apply(Claims claims) {
                return claims.getExpiration();
            }
        });*/
       //java.util.Date object represents a point in time strictly earlier than the one passed as an argument.
        // It evaluates the underlying millisecond timestamps chronologically
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    //A generic method that takes a token and a "resolver" function, and returns whatever data type 'T' is requested
    //• The <T> right before the return type tells Java: "This is a generic method. The type of data it returns is dynamic and will be decided at runtime."
    //• The second T is the actual return type. If you are extracting a username, T becomes a String. If you are extracting the expiration date, T becomes a Date.
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractClaims(token);
        // 3. Run the resolver function on those claims to extract the exact property you want, and return it
        return claimResolver.apply(claims);
    }




}
