/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PAE.Project.KTP.dummy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
@Controller
public class DummyController {
    
    DummyJpaController dummyCtrl = new DummyJpaController();
  List<Dummy> data = new ArrayList<>();

  @RequestMapping("/read")
  public String getDummy(Model m) {
    try {
      data = dummyCtrl.findDummyEntities();

    } catch (Exception e) {

    }
    m.addAttribute("data", data);
    return "dummy";
  }

  @RequestMapping("/create")
  public String createDummy() {
    return "create/create";
  }

  @PostMapping(value = "/newdata", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseBody
  public String newDummyData(@RequestParam("gambar") MultipartFile f, HttpServletRequest r, HttpServletResponse res)
      throws ParseException, Exception {
    Dummy d = new Dummy();

    int id = Integer.parseInt(r.getParameter("id"));
    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(r.getParameter("tanggal"));
    byte[] img = f.getBytes();
    d.setId(id);
    d.setTanggal(date);
    d.setGambar(img);

    dummyCtrl.create(d);
    res.sendRedirect("/read");
    return "created";
  }

  @RequestMapping(value = "/img", method = RequestMethod.GET, produces = {
      MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE
  })
  public ResponseEntity<byte[]> getImg(@RequestParam("id") int id) throws Exception {
    Dummy d = dummyCtrl.findDummy(id);
    byte[] img = d.getGambar();
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(img);
  }

  @GetMapping("/delete/{id}")
  @ResponseBody
  public String deleteDummy(@PathVariable("id") int id, HttpServletResponse res) throws Exception {
    dummyCtrl.destroy(id);
    res.sendRedirect("/read");
    return "deleted";
  }

  @RequestMapping("/edit/{id}")
  public String updateDummy(@PathVariable("id") int id, Model m) throws Exception {
    Dummy d = dummyCtrl.findDummy(id);
    m.addAttribute("data", d);
    return "create/update";
  }

  @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseBody
  public String updateDummyData(@RequestParam("gambar") MultipartFile f, HttpServletRequest r, HttpServletResponse res)
      throws ParseException, Exception {
    Dummy d = new Dummy();

    int id = Integer.parseInt(r.getParameter("id"));
    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(r.getParameter("tanggal"));
    byte[] img = f.getBytes();
    d.setId(id);
    d.setTanggal(date);
    d.setGambar(img);

    dummyCtrl.edit(d);
    res.sendRedirect("/read");
    return "updated";
  }
}