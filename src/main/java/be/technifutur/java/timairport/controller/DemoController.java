package be.technifutur.java.timairport.controller;

import be.technifutur.java.timairport.model.form.DemoForm;
import be.technifutur.java.timairport.utils.DemoAnnot;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

    // Récupérable depuis la requête HTTP:
    // - URL/URI:
    //   - variable de chemin: segment de l'URI dont on a laissé le choix de la valeur
    //   - paramètre: (ex: htpp://localhost:8080/machin/truc?param=value)
    // - method: type de requpête envoyée, détermine l'action attendue
    //   - GET: récuérer une/plsr ressource.s
    //   - POST: envoyer, crére une ressource
    //   - PUT: remplace une ressource (modifier intégrablement
    //   - PATCH : modifie partiellement une ressource
    //   - DELETE: supprimer une ressource
    // - headers : méta-données sur la requête (MultiValueMap: 1 key potentiellement N valeurs)
    // - body: contenu de la requête

    // Dans la réponse HTTP :
    // - status: code représentant succès/échec de la requête
    // - headers: méta-données de la réponse
    // - body: contenu de la réponse

    // Ce qui se trouve dans l'URL
    @GetMapping("/url/{var:[A-Z]{1,3}}") // Avec utilisation d'un RegExr
    public void displayUrl(@PathVariable String var, @RequestParam("param") int var2) {
        System.out.println(var); // Variable de chemin {var}
        System.out.println(var2); // Variable du paramètre param

    }

    // Consumes vérifie la valeur du header content-type" de la requête
    // (valeur par défaut : "application/json")
    @PostMapping(value = "/body", consumes = "application/json")
    public void displayBody(@Valid @RequestBody DemoForm body) {
        System.out.println(body);
    }

    @GetMapping("/header")
    public void displayHeaders(@RequestHeader("accept") String accept) {
        System.out.println(accept);
    }

    @GetMapping("/param/all")
    public void displayAllParams(@RequestParam Map<String, String> params) {
        params.forEach(
                (key, value) -> System.out.printf("%s : %s\n", key, value)
        );
    }

    public void displayAllHeaders(@RequestHeader HttpHeaders headers) { // @RequestHeader MultiValueMap<String, String> headers
        System.out.println(headers.getAccept());
        headers.forEach(
                (key, value) -> System.out.printf("%s : %s\n", key, value)
        );
    }

    public void displayRequest(HttpServletRequest request) {
        request.getRequestURI();
        request.getRequestURL();
        request.getMethod();
    }

    /**
     * produces donne une valeur au header "content-type" de la réponse
     * (valeur par défaut : "application/json")
     * @return body
     */
    @GetMapping(value = "/response/basic", produces = "applicatoin/json")
    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody - pas nécessaire car @RestController
    public String cerateBasicresponse(){
        return "ma réponse";
    }

    @GetMapping("response/detailed")
    public ResponseEntity<String> createDetailedREsponse() {

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        return new ResponseEntity<>("ma réponse",headers, HttpStatus.OK);

        return ResponseEntity.status(HttpStatus.OK) // ou .ok directement
                .contentType(MediaType.APPLICATION_JSON)
                .body("ma réponse"); // ou .build()
    }

}
