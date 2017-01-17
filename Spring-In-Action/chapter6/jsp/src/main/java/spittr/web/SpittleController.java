package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import spittr.data.SpittleRepository;
import spittr.entity.Spittle;

import java.util.Date;

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
     * @param max   the max
     * @param count the count
     * @return the string, which match the jsp name
     */
    @RequestMapping(method = RequestMethod.GET, params = {"max","count"})
    public String spittles(
            Model model,
            @RequestParam("max") long max,
            @RequestParam("count") int count){
        model.addAttribute(
                spittleRepository.findSpittles(
                        max,count
                )
        );
        return "spittles";
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

    /**
     * Show spittle string.
     *
     * @param spittleId the spittle id
     * @param model     the model
     * @return the string
     */
    @RequestMapping(method = RequestMethod.GET, value = "/show")
    public String showSpittleByParams(
            @RequestParam("spittle_id") long spittleId,
            Model model){
        model.addAttribute(spittleRepository.findOne(spittleId));
        return "spittle";
    }

    /**
     * Show spittle string.
     *
     * @param spittleId the spittle id
     * @param model     the model
     * @return the string
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{spittleId}")
    public String showSpittleByPath(
            @PathVariable long spittleId,
            Model model){
        model.addAttribute(spittleRepository.findOne(spittleId));
        return "spittle";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String saveSpittle(SpittleForm form, Model model) throws Exception {
        spittleRepository.save(new Spittle(null, form.getMessage(), new Date(),
                form.getLongitude(), form.getLatitude()));
        return "redirect:/spittles";
    }
}
