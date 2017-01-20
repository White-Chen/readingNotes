package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spittr.data.SpitterRepository;
import spittr.entity.Spitter;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

/**
 * Created by ChenZhePC on 2017/1/16.
 */

@Controller
@RequestMapping("/spitter")
public class SpitterController {

    private SpitterRepository spitterRepository;

    @Autowired
    public SpitterController(
            SpitterRepository spitterRepository){
        this.spitterRepository = spitterRepository;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationForm(Model model){
        model.addAttribute(new SpitterForm());
        return "registerForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistration(
            @Valid SpitterForm spitterForm,
            Errors errors) throws IOException {
        if (errors.hasErrors())
            return "registerForm";
        spitterRepository.save(spitterForm.toSpitter());
        spitterForm
                .getProfilePicture()
                .transferTo(
                        new File("C:/Users/ChenZhe/Desktop/temp" + spitterForm.getUsername() + ".jpg"));
        return "redirect:/spitter/" + spitterForm.getUsername();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String showSpitterProfile(
            @PathVariable String username,
            Model model){
        Spitter spitter = spitterRepository.findByUsername(username);
        model.addAttribute(spitter);
        return "profile";
    }
}
