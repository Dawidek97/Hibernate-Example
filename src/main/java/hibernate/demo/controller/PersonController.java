package hibernate.demo.controller;

import hibernate.demo.model.Person;
import hibernate.demo.repozytory.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/add")
    public String show(ModelMap modelMap){
        modelMap.put("person", new Person());
        return "form";
    }

    @GetMapping("/")
    public String getAll(ModelMap modelMap) {
        modelMap.put("people", personRepository.findAll());
        return "all";
    }

    @PostMapping("/")
    public String create(@Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "form";
        } else {
            personRepository.save(person);
            return "redirect:/all";
        }
    }

    @GetMapping("/all")
    public String findAll(ModelMap modelMap){
        modelMap.put("people", personRepository.findAllSortById());
        return "all";
    }

    @GetMapping("people/{id}/delete")
    public String delete(@PathVariable Integer id){
        personRepository.deleteById(id);
        return  "redirect:/all";
    }

    @GetMapping("people/{id}/update")
    public String update(@PathVariable Integer id, ModelMap modelMap){
        Person person = personRepository.findById(id).get();
        modelMap.put("person", person);
        return "form";
    }

    @PostMapping("/people/search")
    public String sort(ModelMap modelMap, @RequestParam String option) {
        try {
            Integer age = Integer.parseInt(option);
            modelMap.put("people", personRepository.findAllSortNameOrAge(option,age));
        } catch (NumberFormatException e) {
            modelMap.put("people", personRepository.findAllSortNameOrAge(option,0));
        }
        return "all";
    }
}
