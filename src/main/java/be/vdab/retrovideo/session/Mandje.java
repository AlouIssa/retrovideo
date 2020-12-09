package be.vdab.retrovideo.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@SessionScope
public class Mandje implements Serializable {
    private static final long SerialVersionUID = 1L;
    private final Set<Long> ids = new LinkedHashSet<>();
    public void reset(){
        ids.clear();
    }

    public void toevoegen(long id){
        ids.add(id);
    }

    public long aantalInMandje(){
        return ids.size();
    }

    public void verwijderen(Long[] reeks){
        if (reeks != null){
            for (var een : reeks) {
                ids.remove(een);
            }
        }
    }

    public Set<Long> getIds() {
        return ids;
    }

}
