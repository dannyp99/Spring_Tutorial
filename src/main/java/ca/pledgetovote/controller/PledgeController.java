package ca.pledgetovote.controller;

import ca.pledgetovote.model.Pledge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class PledgeController {

  private List<Pledge> pledges = new ArrayList<>();
  private AtomicLong nextId = new AtomicLong();

  @GetMapping("/hello")
  public String getHello() {
    return "Hello Spring Boot World";
  }

  @PostMapping("/pledges")
  @ResponseStatus(HttpStatus.CREATED)
  public Pledge createPledge(@RequestBody Pledge pledge) {
    pledge.setId(nextId.getAndIncrement());
    pledges.add(pledge);
    return pledge;
  }

  @GetMapping("/pledges")
  public List<Pledge> getPledge() {
    return pledges;
  }

  @GetMapping("/pledges/{id}")
  public Pledge getOnePledge(@PathVariable("id") long id) {
    for (Pledge pl : pledges) {
      if (pl.getId() == id) {
        return pl;
      }
    }
    throw new IllegalArgumentException();
  }

  @PostMapping("pledges/{id}")
    public Pledge editOnePledge(@PathVariable("id") long id,
                                @RequestBody Pledge newPledge){
      for (Pledge pl : pledges) {
          if (pl.getId() == id) {
              pledges.remove(pl);
              newPledge.setId(id);
              pledges.add(newPledge);
              return pl;
          }
      }
      throw new IllegalArgumentException();
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST,
                    reason = "Requested ID not found")
  @ExceptionHandler(IllegalArgumentException.class)
    public void invalidID(){
      //nothing
  }

}
