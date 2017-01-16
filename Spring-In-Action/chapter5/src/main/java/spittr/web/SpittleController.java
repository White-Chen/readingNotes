package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spittr.data.SpittleRepository;

/**
 * Created by ChenZhePC on 2017/1/16.
 */
@Controller
@RequestMapping("/spittles")
public class SpittleController {

    private SpittleRepository spittleRepository;

    /**
     * Instantiates a new Spittle controller.
     *
     * @param spittleRepository the spittle repository
     */
    @Autowired
    public SpittleController(SpittleRepository spittleRepository){
        this.spittleRepository = spittleRepository;
    }


    /**
     * Spittles string.
     *
     * @param model the model import from springFramework
     * @return the string, which match the jsp name
     */
    @RequestMapping(method = RequestMethod.GET)
    public String spittles(Model model){
        model.addAttribute(
                spittleRepository.findSpittles(
                        Long.MAX_VALUE,20
                )
        );
        return "spittles";
    }
}
