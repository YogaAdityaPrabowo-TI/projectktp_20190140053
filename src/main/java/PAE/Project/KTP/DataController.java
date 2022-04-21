/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PAE.Project.KTP;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author HP
 */
@Controller
public class DataController {
    
    DataJpaController datactrl = new DataJpaController();
    List<Data> newdata = new ArrayList<>();
    
    @RequestMapping("/main")
    public String getMain(){
        return"menu";
    }
    
    @RequestMapping("/data")
    public String getDataKTP(Model model){
        
        try{
            newdata = datactrl.findDataEntities(0, 1);
        }
        catch (Exception e) {}
        model.addAttribute("goData", newdata);
        
        return "database";
    }
    
    @RequestMapping("/edit")
    public String doEdit(){
        return "editktp";
    }
}
