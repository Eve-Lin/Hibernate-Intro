import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;

public class Engine implements Runnable {

    private final EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
        this.removeObjectEx();

    }

    private void removeObjectEx(){
        List<Town> towns = this.entityManager.createQuery("select t from Town as t "
                +"where length(t.name) > 5", Town.class)
                .getResultList();


        this.entityManager.getTransaction().begin();
        towns.forEach(t -> this.entityManager.detach(t));

        for (Town town: towns) {
            town.setName(town.getName().toLowerCase());
        }

        towns.forEach(this.entityManager::merge);
        this.entityManager.flush();

this.entityManager.getTransaction().commit();
        System.out.println();

    }
}
