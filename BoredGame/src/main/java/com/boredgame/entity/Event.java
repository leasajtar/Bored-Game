// Event.java
package com.boredgame.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String game_name;       // "Catan", "Uno", itd.
    private LocalDateTime event_datetime;
    private Integer max_players;      // max broj igrača uključujući organizatora
    private String status;

    // @ManyToOne znači: više dogadaja može biti u jednom kafiću
    @ManyToOne
    @JoinColumn(name = "cafe_id")  // u tablici postoji stupac kafic_id
    private Cafe cafe_id;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer_id;

    // @OneToMany: jedan dogadaj ima puno pridruzivanja
    // mappedBy = "dogadaj" znači da je veza definirana u klasi Pridruzivanje
    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private List<Joining> joinings = new ArrayList<>();

    // Getteri i setteri
    public Integer getId() { return id; }
    public String getNazivIgre() { return game_name; }
    public LocalDateTime getDatumVrijeme() { return event_datetime; }
    public Integer getMaxPlayers() { return max_players; }
    public String getStatus() { return status; }
    public Cafe getKafic() { return cafe_id; }
    public User getOrganizator() { return organizer_id; }
    public List<Joining> getPridruzivanja() { return joinings; }
}
