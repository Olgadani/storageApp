package pro.sky.storageapp.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.storageapp.Model.Color;
import pro.sky.storageapp.Model.Size;
import pro.sky.storageapp.Model.Socks;
import pro.sky.storageapp.Services.SocksService;
import pro.sky.storageapp.dto.SockRequest;
import pro.sky.storageapp.exception.InSufficientSockQuantityException;
import pro.sky.storageapp.exception.InvalidSockRequestException;

@RestController
@RequestMapping("/socks")
public class SockController {
    private final SocksService socksService;

    public SockController(SocksService socksService) {
        this.socksService = socksService;
    }

    @ExceptionHandler(InvalidSockRequestException.class)
    public ResponseEntity<String> handleInvalidException(InvalidSockRequestException invalidSockRequestException) {
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());
    }
    @ExceptionHandler(InSufficientSockQuantityException.class)
    public ResponseEntity<String> handleInvalidException(InSufficientSockQuantityException inSufficientSockQuantityException) {
        return ResponseEntity.badRequest().body(inSufficientSockQuantityException.getMessage());
    }


    @PostMapping
    public void addSocks(@RequestBody SockRequest sockRequest) {
        socksService.addSock(sockRequest);
    }

    @PutMapping
    public void issueSock(@RequestBody SockRequest sockRequest) {
        socksService.issueSock(sockRequest);
    }

    @GetMapping
    public int getSocksCount(@RequestParam(required = false, name = "color") Color color,
                             @RequestParam(required = false, name = "size") Size size,
                             @RequestParam(required = false, name = "cottonMin") Integer cottonMin,
                             @RequestParam(required = false, name = "cottonMax") Integer cottonMax) {
        return socksService.getSockQuantity(color, size, cottonMin, cottonMax);

    }

    @DeleteMapping
    public void removeDefectedSocks(@RequestBody SockRequest sockRequest) {
        socksService.removeDefectedSocks(sockRequest);
    }


}
