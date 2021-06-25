package com.company.enroller.persistence;

import com.company.enroller.model.Participant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("participantService")
public class ParticipantService {

    DatabaseConnector connector;
    Session session;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ParticipantService(){
        session = DatabaseConnector.getInstance().getSession();
    }

    public Collection<Participant> getAll() {
        return session.createCriteria(Participant.class).list();
    }

    public Participant findByLogin(String login) {
        return (Participant) session.get(Participant.class, login);
    }

    public Participant add(Participant participant) {
        String hashedPassword = passwordEncoder.encode(participant.getPassword());
        participant.setPassword(hashedPassword);
        Transaction transaction = session.beginTransaction();
        session.save(participant);
        transaction.commit();
        return participant;
    }

    public void update(Participant participant) {
        Transaction transaction =session.beginTransaction();
        session.merge(participant);
        transaction.commit();
    }

    public void delete(Participant participant) {
        Transaction transaction = session.beginTransaction();
        session.delete(participant);
        transaction.commit();
    }

}
