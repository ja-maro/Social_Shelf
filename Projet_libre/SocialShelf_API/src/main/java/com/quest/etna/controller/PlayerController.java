package com.quest.etna.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.quest.etna.model.player.PlayerDTO;
import com.quest.etna.service.JsonService;
import com.quest.etna.service.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quest.etna.model.player.Player;
import com.quest.etna.repositories.PlayerRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/player")
public class PlayerController {


	@Autowired
	private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepo;
    @Autowired
    private JsonService jsonService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getById(@PathVariable int id) {
        Optional<Player> dbPlayer = playerRepo.findById(id);
        if (dbPlayer.isPresent()) {
            Player player = dbPlayer.get();
            PlayerDTO playerDTO = new PlayerDTO(player);
            return ResponseEntity.ok(playerDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAll() {
        List<PlayerDTO> results = playerService.getAllDTO();
//        List<PlayerDTO> results = new ArrayList<>();
//        Iterable<Player> players = playerRepo.findAll();
//        players.forEach(user -> {
//            PlayerDTO playerDTO = new PlayerDTO(user);
//            results.add(playerDTO);
//        });
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> update(@PathVariable int id, @RequestBody Player formPlayer, @CurrentSecurityContext(expression = "authentication") Authentication auth) {
        Optional<Player> dbPlayer = playerRepo.findById(id);
        if (isUser(auth, id) || hasRole("ROLE_ADMIN")) {
            if (dbPlayer.isPresent()) {
                Player updatedPlayer = updatePlayer(formPlayer, dbPlayer.get());
                if (hasRole("ROLE_ADMIN")) {
                    updatedPlayer = updateRole(formPlayer, updatedPlayer);
                }
                updatedPlayer = playerRepo.save(updatedPlayer);
                PlayerDTO playerDTO = new PlayerDTO(updatedPlayer);
                return ResponseEntity.ok(playerDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Updates username of user if a new one was given.
     *
     * @param formPlayer contains new username
     * @param player
     * @return
     */
    private Player updateRole(Player formPlayer, Player player) {
        if (null != formPlayer.getRole()) {
            player.setRole(formPlayer.getRole());
        }
        return player;
    }

    /**
     * Updates role of user if a new one was given.
     *
     * @param formPlayer contains new role
     * @param player
     * @return
     */
    private Player updatePlayer(Player formPlayer, Player player) {
        if (null != formPlayer.getUsername()) {
            player.setUsername(formPlayer.getUsername());
        }
        if (null != formPlayer.getEmail()) {
            player.setEmail(formPlayer.getEmail());
        }
        return player;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id, @CurrentSecurityContext(expression = "authentication") Authentication auth) {
        Optional<Player> dbUser = playerRepo.findById(id);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setContentType(MediaType.valueOf("application/json"));
        if (isUser(auth, id) || hasRole("ROLE_ADMIN")) {
            if(dbUser.isPresent()) {
                try {
                    playerRepo.delete(dbUser.get());
                    return new ResponseEntity<>(jsonService.successBody(true), responseHeader, HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.NOT_FOUND);
            }
        } else
            return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.FORBIDDEN);
    }
    
    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disable(@PathVariable int id, @CurrentSecurityContext(expression = "authentication") Authentication auth) {
        Optional<Player> dbUser = playerRepo.findById(id);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setContentType(MediaType.valueOf("application/json"));
        if(dbUser.isPresent()) {
            if (hasRole("ROLE_ADMIN")) {
                Player userDisable = dbUser.get();
                userDisable.setDisableDate(Instant.now());
                System.out.println(userDisable.getEmail());
                System.out.println(userDisable.getDisableDate());
                playerRepo.save(userDisable);
                return new ResponseEntity<>(jsonService.successBody(true), responseHeader, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(jsonService.successBody(false), responseHeader, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Checks whether current authenticated user has given role
     *
     * @param roleName
     * @return
     */
    public boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    /**
     * Checks whether current authenticated user is given user
     *
     * @param authentication
     * @param id
     * @return
     */
    public boolean isUser(Authentication authentication, int id) {
        try {
            String currentUser = authentication.getName();
            Player userAuthenticated = playerRepo.findByUsernameIgnoreCase(currentUser);
            Player userToModify = playerRepo.findById(id).get();
            return userToModify.equals(userAuthenticated);
        } catch (Exception e) {
            return false;
        }
    }
}
